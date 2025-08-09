import React, { useState } from "react";
import { REGISTER } from "../../services/apiService";
import { useNavigate } from "react-router-dom";

const SignUp: React.FC = () => {
    const navigate = useNavigate();
  const [form,setForm] = useState({
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    date: ""
  });
 const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await REGISTER("users", form);
      console.log("Registration successful:", response);
      
      alert('Đăng ký thành công!');
      navigate('/signin');
    } catch (error) {
      console.error("Registration error:", error);
      alert('Đăng ký thất bại!');
    }
  };
  
   const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  return (
  <div className="flex flex-col items-center mt-4 min-h-screen bg-gray-100">
    <h1 className="text-4xl font-bold text-purple-600 mb-6">Sign Up</h1>
    <form onSubmit={handleSubmit} className="bg-white p-8 rounded shadow-md w-96">
        <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="username">First name: </label>
        <input 
        type="text" 
        id="firstName" 
        name="firstName"
        value={form.firstName}
        onChange={handleChange}
        className="w-full p-2 border border-gray-300 rounded" 
        />
      </div>
        <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="username">Last name: </label>
        <input 
        type="text" 
        id="lastName" 
        name="lastName"
        value={form.lastName}
        onChange={handleChange}
        className="w-full p-2 border border-gray-300 rounded" />
      </div>
      <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="username">Username: </label>
        <input 
        type="text" 
        id="username" 
        name="username"
        value={form.username}
        onChange={handleChange}
        className="w-full p-2 border border-gray-300 rounded" />
      </div>
   
      <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="password">Password: </label>
        <input 
        type="password"
         id="password"
         name="password"
         value={form.password}
         onChange={handleChange}
          className="w-full p-2 border border-gray-300 rounded" />
      </div>
         <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="email">Date of Birth: </label>
        <input 
        type="date" 
        id="date"
        name="date" 
        value={form.date}
        onChange={handleChange}
        className="w-full p-2 border border-gray-300 rounded" />
      </div>
      <button type="submit" className="w-full bg-purple-600 text-white p-2 rounded hover:bg-purple-700 transition">Sign Up</button>
    </form> 
    <p className="mt-4 text-gray-600">Already have an account? <a href="/signin" className="text-purple-600 hover:underline">Sign In</a></p>
  </div>
  );
};

export default SignUp;
