import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import RegistrationForm from './components/RegistrationForm';
import LoginForm from './components/LoginForm';
import Nopage from './components/Nopage';
import { AuthProvider } from './auth/AuthContext'; // Assuming AuthProvider is exported correctly


import 'bootstrap/dist/css/bootstrap.min.css';
import ProtectedComponent from './components/ProtectedComponent';

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<RegistrationForm />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/nopage" element={<Nopage />} />
          <Route path="/protected/*" element={<ProtectedComponent />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
