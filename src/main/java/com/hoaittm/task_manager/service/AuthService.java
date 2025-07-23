package com.hoaittm.task_manager.service;

import com.hoaittm.task_manager.dto.request.AuthRequest;
import com.hoaittm.task_manager.dto.request.IntrospectRequest;
import com.hoaittm.task_manager.dto.request.LogoutRequest;
import com.hoaittm.task_manager.dto.request.RefeshRequest;
import com.hoaittm.task_manager.dto.response.AuthResponse;
import com.hoaittm.task_manager.dto.response.IntrospectResponse;
import com.hoaittm.task_manager.entity.InvalidatedToken;
import com.hoaittm.task_manager.entity.User;
import com.hoaittm.task_manager.exception.AppException;
import com.hoaittm.task_manager.exception.ErrorCode;
import com.hoaittm.task_manager.repository.InvalidateTokenRepository;
import com.hoaittm.task_manager.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthService {
    UserRepository userRepository;

    InvalidateTokenRepository invalidateTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected  String SIGNER_KEY ;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected  long VALID_DURATION ;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected  long REFRESHABLE_DURATION ;
    public IntrospectResponse introspect (IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        boolean isValid = true;
        try {
            verifyToken(token,false);
        }catch (AppException e){

                  isValid = false;
        }
       return IntrospectResponse.builder()
               .valid(isValid)
               .build();

    }
   public AuthResponse authenticate (AuthRequest request) throws KeyLengthException {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated=  passwordEncoder.matches(request.getPassword(),user.getPassword());

        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHETICATED);

        var token = generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try{
            var signToken = verifyToken(request.getToken(),true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expriryTime(expiryTime)
                    .build();
            invalidateTokenRepository.save(invalidatedToken);
        }catch (AppException e){
            log.info("Token already expired");
        }



    }

    public AuthResponse refreshToken (RefeshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(),true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expriryTime(expiryTime)
                .build();
        invalidateTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username).orElseThrow(
                ()-> new AppException(ErrorCode.UNAUTHETICATED)
        );
        var token = generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }
    private SignedJWT verifyToken(String token,boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = (isRefresh)
                ? new Date( signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION,ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified =  signedJWT.verify(verifier);

        if(!(verified && expityTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHETICATED);

        if(invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHETICATED);
        return signedJWT;
    }

    private String generateToken(User user) throws KeyLengthException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("hoaittm")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))

                .jwtID(UUID.randomUUID().toString())
                .claim("id",user.getId())
                .claim("scope",buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e){
            log.error("Can not create token: ", e);
            throw new RuntimeException(e);
        }

    }
    private String buildScope(User user){

        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles()))
          user.getRoles().forEach(role -> {
              stringJoiner.add("ROLE_" +role.getName());
              if(!CollectionUtils.isEmpty(role.getPermissions()))
                 role.getPermissions()
                         .forEach(permission -> stringJoiner.add(permission.getName()));
          });

        return stringJoiner.toString();
    }

//    public String getCurrentUserIdFromToken() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//        String id= jwt.getId();
//        return id;
//    }

}
