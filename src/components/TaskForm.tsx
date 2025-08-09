import { useState } from "react";

interface TaskFormProps {
  initialData?: {
    title: string;
    description: string;
    due_date: string ;
  };
  onSubmit: (data: { title: string; description: string ,due_date:string}) => void;
}

const TaskForm: React.FC <TaskFormProps> = ({initialData,onSubmit}) => {
  const [title,setTitle] = useState(initialData?.title || "");
  const [description,setDescription] = useState(initialData?.description || "");
  const [due_date,setDate] = useState(initialData?.due_date || "");
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({ title, description, due_date });
  }
    return (
        <div>
            <div className="flex flex-col items-center mt-4 min-h-screen bg-gray-100">

    <form onSubmit={handleSubmit} className="bg-white p-8 rounded shadow-md w-96">
        <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="username">Title: </label>
        <input 
        type="text" 
        id="title" 
        name="title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        className="w-full p-2 border border-gray-300 rounded" 
        />
      </div>
        <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="username">Description: </label>
        <input 
        type="text" 
        id="description" 
        name="description"
       value={description}
        onChange={(e) => setDescription(e.target.value)}
        className="w-full p-2 border border-gray-300 rounded" />
      </div>
   
   
         <div className="mb-4">
        <label className="block text-gray-700 mb-2" htmlFor="email">Date : </label>
        <input 
        type="date" 
        id="date"
        name="date" 
      value={due_date} // Format date for input
        onChange={(e) => setDate(e.target.value)}
        className="w-full p-2 border border-gray-300 rounded" />
      </div>
      <button type="submit" className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-700 transition">Create</button>
    </form> 
    
  </div>
        </div>
    )
}
export default TaskForm;