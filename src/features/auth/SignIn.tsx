import React from "react";
import { LOGIN } from "../../services/apiService";
import { useNavigate } from "react-router-dom";
interface SignInForm {
  code: string;
  result:{
    token:string;
    authenticated:boolean;
  }
}
const SignIn: React.FC = () => {
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");
  const navigate = useNavigate();
const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();
  try {
    const response = await LOGIN("auth/token", { username, password })as SignInForm;
    console.log("Login successful:", response);

    const token = response.result.token;
    localStorage.setItem('token', token);
    localStorage.setItem('username', username);
    
    alert('Đăng nhập thành công!');
    navigate('/');
  } catch (error) {
    console.error("Login error:", error);
    alert('Đăng nhập thất bại!');
  }
};

  return (
  <div className="flex flex-col items-center mt-4 min-h-screen bg-gray-100">
    <h1 className="text-4xl font-bold text-purple-600 mb-6">Sign In</h1>
    <form className="bg-white p-8 rounded shadow-md w-96" onSubmit={handleSubmit}>
       
      <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="username">Username: </label>
        <input
         type="text"
          id="username" 
          name="username"
          className="w-full p-2 border border-gray-300 rounded" 
          value={username}
          onChange={(e)=>setUsername(e.target.value)}/>
      </div>
   
      <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="password">Password: </label>
        <input 
        type="password"
         id="password" 
         name="password"
          value={password}
          onChange={(e)=>setPassword(e.target.value)}
         className="w-full p-2 border border-gray-300 rounded"
          />
      </div>
        
      <button type="submit" className="w-full bg-purple-600 text-white p-2 rounded hover:bg-purple-700 transition">Sign In</button>
    </form> 
    <p className="mt-4 text-gray-600">Already have an account? <a href="/signup" className="text-purple-600 hover:underline">Sign Up</a></p>
  </div>
  );
};

export default SignIn;
