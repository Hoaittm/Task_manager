interface Task{
    id:string;
    title:string;
    description:string;
    due_date:string;
    user_id:string;
}
import React, { useState } from 'react';
import axiosInstance from '../services/axiosInstance';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {  faEdit, faTrash } from '@fortawesome/free-solid-svg-icons';
const TaskItems: React.FC<{task:Task}> = ({task,onDelete}) => {
    const id = task.id;
   const handleDelete = async () => {
    try {
      await axiosInstance.delete(`tasks/${id}`);
      alert("Xoá thành công!");
      onDelete(task.id); // Gọi hàm xóa ở component cha
    } catch (error) {
      console.error("Delete error:", error);
      alert("Xoá thất bại!");
    }
  };

    return (
        <div className="task-items-container rounded-2xl bg-gray-100 p-4 shadow-md m-4">
        {/* Render task items here */}
      
                <div className="task-item  p-1   mb-2 ">
                    <h2 className="text-lg font-semibold">{task.title}</h2>
                    <p className="text-gray-700">{task.description}</p>
                    <p className="text-sm text-gray-500">Due: {task.due_date}</p>
                    <div className='flex flex-row items-center justify-between mt-2'>
                             <a href={`/update/${id}`} className='bg-amber-200 p-1 rounded-md'>
                             <FontAwesomeIcon icon={faEdit} className='mr-1' />
                             </a>
                            <button onClick={handleDelete} className='bg-amber-200 p-1 rounded-md mr-2'>
                              <FontAwesomeIcon icon={faTrash} className='mr-1' />
                            </button>
  

                    </div>
                </div>
        
   

        {/* You can map through tasks and display them */}
        </div>
    );
}
export default TaskItems;

