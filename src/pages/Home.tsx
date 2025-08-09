import React, { useEffect } from "react";
import TaskItems from "../components/TaskItems";
import axiosInstance from "../services/axiosInstance";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFile, faFileContract } from "@fortawesome/free-solid-svg-icons";
interface Task {
    id:string;
    title:string;
    description:string;
    dueDate:string;
    user_id:string;
}
const Home: React.FC = () => {
  const [tasks,setTasks] = React.useState<Task[]>([]);
  const user_id = localStorage.getItem("user_id") || "";
  useEffect(() => {
    
    const fetchTasks = async () => {
      try {
        const response = await axiosInstance.get(`/tasks/${user_id}`);
        setTasks(response.data);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };

    fetchTasks();
  }, []);
  const handleDeleteTask = (id: string) => {
  setTasks(prev => prev.filter(task => task.id !== id));
};
  return (
   
   <div>

<div className="flex flex-col mr-2 mt-4">
  <div className="flex flex-row h-screen p-4 gap-4 bg-gray-100">
<div className="flex flex-col mr-2 w-md border border-gray-300 rounded-2xl h-screen p-4 bg-white shadow-sm ">
  {/* Tiêu đề */}
  <div className="flex items-center space-x-2 mb-4 bg-white h-12 ">
    <FontAwesomeIcon icon={faFile} className="text-gray-600" />
    <span className="text-gray-800 font-medium">INBOX</span>
  </div>

  <div className="flex-1 ">
    <textarea
      placeholder="Type your message..."
      className="w-full h-40 resize-none p-2 rounded border border-gray-300 text-gray-800 bg-white"
    />
  </div>

</div>

    <div>
      <div className="flex flex-col flex-1 w-[1300px] h-full p-4 bg-blue-300 shadow-sm rounded-2xl overflow-hidden">
        {/* Tiêu đề */}
        <div className="flex items-center space-x-2 mb-4 h-12">
    
      <span className="text-gray-800 font-bold text-xl ">To-do and Reading-List</span>
    </div>



    <div className="flex-1 overflow-x-auto ">
      <div className="flex flex-row ">
{tasks.map((task) => (
        <TaskItems key={task.id} task={task} onDelete={handleDeleteTask} />
      ))}
      </div>
      
    </div>
      </div>
    </div>
  </div>
{/* 
    <h1>TASK</h1>
   {tasks.map((task) => (
        <TaskItems task={task} onDelete = {handleDeleteTask} />
      ))} */}
   </div>
   </div>
  );
};

export default Home;
