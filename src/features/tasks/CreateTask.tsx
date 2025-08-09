import React from 'react';
import TaskForm from '../../components/TaskForm';
import axiosInstance from '../../services/axiosInstance';
import { useNavigate } from 'react-router-dom';
const CreateTask : React.FC = () => {
    const navigate = useNavigate();
    const handleCreateTask = async(data: { title: string; description: string; due_date: string }) => {
        // Handle the task creation logic here
          try {
              const response = await axiosInstance.post("tasks", data);
              console.log("Create task successful:", response);
              
              alert('Tao thành công!');
              navigate('/');
            } catch (error) {
              console.error("Create error:", error);
              alert('Đăng ký thất bại!');
            }
        console.log("Task created:", data);
        // You can also redirect or show a success message after creation
    }
    return (
        <div className='flex flex-col items-center mt-4 min-h-screen bg-gray-100'>
     
        {/* Add form or components to create a task here */}
        <TaskForm onSubmit={handleCreateTask} />
        {/* You can add more components or logic as needed */}
        </div>
    );
}
export default CreateTask;