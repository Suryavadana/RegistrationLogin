import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

axios.defaults.withCredentials = true;

const LoginForm = () => {
  const [form, setForm] = useState({ username: '', password: '' });
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/auth/login', form);
      setMessage('Login successful');
      // Handle login logic in your application (e.g., updating state)
      navigate('/nopage'); // Redirect to home or dashboard page after successful login
    } catch (error) {
      if (error.response) {
        setMessage(error.response.data);
      } else {
        setMessage('An error occurred. Please try again.');
      }
    }
  };

  return (
    <div className="container mt-5">
      <div className="card">
        <div className="card-body">
          <h2 className="card-title">Login Form</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Username:</label>
              <input
                type="text"
                className="form-control"
                name="username"
                value={form.username}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label className="form-label">Password:</label>
              <input
                type="password"
                className="form-control"
                name="password"
                value={form.password}
                onChange={handleChange}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary">Login</button>
          </form>
          {message && <p className="mt-3 text-danger">{message}</p>}
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
