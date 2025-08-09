import { Route, Routes } from "react-router-dom"
import Home from "../pages/Home";
import SignUp from "../features/auth/SignUp";
import MainLayout from "../layouts/MainLayout";
import SignIn from "../features/auth/SignIn";
import  CreateTask from "../features/tasks/CreateTask";
import MyInfo from "../features/user/MyInfo";
import UpdateTask from "../features/tasks/UpdateTask";

const ClientRouter = () => {
    return(
        <Routes>
          <Route path="/" element={<MainLayout />}>
        <Route index element={<Home />} />
        <Route path="signup" element={<SignUp />} />
      <Route path="signin" element={<SignIn />} />
      <Route path="create" element={<CreateTask/>}/>
          <Route path="update/:id" element={<UpdateTask/>}/>
      <Route path="user" element={<MyInfo/>}/>
    {/* Add more routes here */}
      </Route>
        </Routes>
    )
}

export default ClientRouter;