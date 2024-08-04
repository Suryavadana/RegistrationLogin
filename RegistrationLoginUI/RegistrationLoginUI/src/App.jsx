// src/App.jsx
import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import RegistrationForm from './components/RegistrationForm';
import LoginForm from './components/LoginForm';
import Nopage from './components/Nopage';
import ProtectedComponent from './components/ProtectedComponent';
import ProtectedRoute from './components/ProtectedRoute'; // Import your ProtectedRoute component
import { AuthProvider } from './auth/AuthContext'; // Import AuthProvider

import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<RegistrationForm />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/nopage" element={<Nopage />} />
          <Route path="/protected" element={<ProtectedRoute component={ProtectedComponent} />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
