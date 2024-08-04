import axios from 'axios';

// Create an Axios instance with a base URL
export const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080', // Your API base URL
  withCredentials: true // Ensure cookies are sent and received
});

export default axiosInstance;
