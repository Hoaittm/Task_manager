import axios from "axios";
const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/task_manager",
    withCredentials: true,
});
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token'); // Hoặc lấy từ Redux / cookie
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
export default axiosInstance;