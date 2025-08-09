import { useNavigate, useParams } from "react-router-dom";
import axiosInstance from "../../services/axiosInstance";
import TaskForm from "../../components/TaskForm";
import { useEffect, useState } from "react";

const UpdateTask:React.FC = ()=>{
     const navigate = useNavigate();
     const id = useParams<{id: string}>().id;
       const [initialData, setInitialData] = useState<{
    title: string;
    description: string;
    due_date: string;
  } | null>(null);
useEffect(() => {
    const fetchTask = async () => {
      try {
        const response = await axiosInstance.get(`/tasks/${id}`);
        setInitialData(response.data); 
        console.log("jsadhjsakd: ",response.data);
      } catch (error) {
        console.error("Lỗi khi load task:", error);
        alert("Không lấy được dữ liệu task");
      }
    };

    fetchTask();
  }, [id]);
    const handleCreateTask = async(data: { title: string; description: string; due_date: string }) => {
        // Handle the task creation logic here
          try {
              const response = await axiosInstance.put(`tasks/${id}`, data);
              console.log("Update task successful:", response);
             
              alert('Update thành công!');
              navigate('/');
            } catch (error) {
              console.error("Create error:", error);
              alert(' Update thất bại!');
            }
        console.log("Task created:", data);
        // You can also redirect or show a success message after creation
    }
      if (!initialData) {
    return <p className="text-center mt-10">Đang tải dữ liệu...</p>;
  }
    return (
        <div className='flex flex-col items-center mt-4 min-h-screen bg-gray-100'>
            <h1 className='text-4xl font-bold text-blue-600 mb-6'>Update Task</h1>
        <TaskForm onSubmit={handleCreateTask} initialData={initialData} />
        {/* You can add more components or logic as needed */}
        </div>
    )
}
export default UpdateTask;