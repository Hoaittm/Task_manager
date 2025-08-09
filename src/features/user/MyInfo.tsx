import { useEffect, useState } from "react";
import axiosInstance from "../../services/axiosInstance";

interface UserResponse {
  id: string;
  username: string;
  firstName: string;
  lastName:string;
  dob:string;
  roles: string[];
  // thêm các field khác nếu có
}

const MyInfo:React.FC = () => {
    const [user, setUser] =useState<UserResponse | null>(null);

    useEffect(() => {
    const fetchMyInfo = async () => {
      const token = localStorage.getItem("access_token");
      if (!token) return;

      try {
        const response = await axiosInstance.get("users/myinfo");
        setUser(response.data);
        console.log("User info fetched successfully:", response.data);
        localStorage.setItem("user_id", response.data.id);
      } catch (error) {
        console.error("Error fetching user info:", error);
      }
    };
       fetchMyInfo();
  }, []);

    return (
         <div className='flex flex-col items-center mt-4 min-h-screen bg-gray-100'>
      <h1 className='text-4xl font-bold text-blue-600 mb-6'>My Information</h1>
      {user ? (
        <div className='text-lg text-gray-700'>
          <p><strong>ID:</strong> {user.id}</p>
          <p><strong>Username:</strong> {user.username}</p>
          <p><strong>FirstName:</strong> {user.firstName}</p>
           <p><strong>LastName:</strong> {user.lastName}</p>
            <p><strong>Date of Birth:</strong> {user.dob}</p>
        </div>
      ) : (
        <p className='text-lg text-gray-700'>Loading user info...</p>
      )}
    </div>
    );
}
export default MyInfo;