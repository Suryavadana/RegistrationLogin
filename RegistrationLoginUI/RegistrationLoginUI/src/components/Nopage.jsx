import React from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from '../auth/AuthContext'; // Import the AuthContext

const Nopage = () => {
  const navigate = useNavigate();
  const { logout } = useAuth(); // Destructure logout from AuthContext

  const handleLogout = async () => {
    try {
      await logout(); // Call logout function from AuthContext
      navigate('/login'); // Redirect to login page after logout
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  return (
    <div className="container mt-5">
      <p>Welcome to Event Finder</p>
      <button className="btn btn-primary" onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
};

export default Nopage;
