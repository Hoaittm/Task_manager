
import React, {  useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBell } from '@fortawesome/free-solid-svg-icons';

const Header: React.FC = () => {
  const navigate = useNavigate();
  const [username,setUsername] = React.useState<string>("");
  useEffect(()  => {
    const username = localStorage.getItem('username');
    const token = localStorage.getItem('token');
    if(username && token) {
      setUsername(username);
    }else{
      setUsername("");
    }
  }
    ,[]);
const handleLogout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('username');
  setUsername("");
  navigate('/signin');}

  return (
    <header className="bg-gray-200 text-white p-4">
      <div className="flex justify-between items-center">
     <a href="/" ><h1 className="text-xl font-bold text-black">Task_manager</h1></a>
      <nav className="mt-1">
        <ul className="flex space-x-4">
      
          <input type="text" placeholder="Search tasks..." className="  p-2 rounded border border-gray-300 text-black" />
        
          <li><a href="/create" className=" text-black bg-gray-100 border-0 p-2">Create</a></li>
                
             <li><a href="/signup" className="hover:underline text-black">
              <FontAwesomeIcon icon={faBell} /></a></li>
         {username ? (
              <>
                <li className="text-black">Hello, <span className="font-semibold text-black">{username}</span></li>
                <li>
                  <button onClick={handleLogout} className="hover:underline text-black">
                    Logout
                  </button>
                </li>
              </>
            ) : (
              <>
                <li><a href="/signup" className="hover:underline text-black">Sign up</a></li>
                <li><a href="/signin" className="hover:underline text-black">Sign in</a></li>
              </>
            )}
        </ul>
      </nav>
      </div>
    </header>
  );
};

export default Header;
