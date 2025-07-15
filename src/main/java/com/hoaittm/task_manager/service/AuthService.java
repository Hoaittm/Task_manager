package com.hoaittm.task_manager.service;

import com.hoaittm.task_manager.dto.request.AuthRequest;
import com.hoaittm.task_manager.dto.request.IntrospectRequest;
import com.hoaittm.task_manager.dto.response.AuthResponse;
import com.hoaittm.task_manager.dto.response.IntrospectResponse;
import com.hoaittm.task_manager.entity.User;
import com.hoaittm.task_manager.exception.AppException;
import com.hoaittm.task_manager.exception.ErrorCode;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected  String SIGNER_KEY ;
    public IntrospectResponse introspect (IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
       var verified =  signedJWT.verify(verifier);
       return IntrospectResponse.builder()
               .valid(verified && expityTime.after(new Date()))
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

    private String generateToken(User user) throws KeyLengthException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("hoaittm")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))

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
            user.getRoles().forEach(stringJoiner::add );

        return stringJoiner.toString();
    }
}
