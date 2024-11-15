"use client"
import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import useApi from '../hooks/useApi';

const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [errors, setErrors] = useState({});
  const { callApi, loading, error } = useApi('http://localhost:8080/api/users/login', 'POST');
  const router = useRouter();

  const validateField = (name, value) => {
    // Skip validation if the field is empty
    if (!value) return '';
    switch (name) {
      case 'email':
        return /\S+@\S+\.\S+/.test(value) ? '' : 'Invalid email format.';
      case 'password':
        return value.length >= 6 ? '' : 'Password must be at least 6 characters.';
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    setErrors({ ...errors, [name]: validateField(name, value) });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const currentErrors = {};
    ['email', 'password'].forEach(field => {
      const error = validateField(field, formData[field]);
      if (error) currentErrors[field] = error;
    });
    setErrors(currentErrors);

    // If there are no validation errors, proceed with the API call
    if (Object.keys(currentErrors).length === 0) {
      try {
        const response = await callApi(formData); // Wait for API response
        if (response) {
          console.log('Login successful:', response);
          localStorage.setItem('userID', response.id); // Store user ID
          router.push('/'); // Redirect to homepage on successful login
        } 
      } catch (err) {
        console.error('Error during login:', err);
        setErrors({ api: 'Login failed. Please try again.' }); // Handle API error
      }
    }
};

  return (
    <div className="flex flex-col items-center mt-16 px-4 sm:px-8" >
      <h1 className="text-2xl font-bold mb-8">Login to Existing Account</h1>
      {error && (
        <p className="text-red-600 text-sm mt-2 mb-2 font-bold rounded">{error}</p> // Changed the color slightly and added margin-top
      )}
      <form onSubmit={handleSubmit} className="w-full max-w-md space-y-4">
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded text-black"
              required
            />
            {errors.email && <p className="text-red-500 text-sm">{errors.email}</p>}
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={formData.password}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded text-black"
              required
            />
            {errors.password && <p className="text-red-500 text-sm">{errors.password}</p>}
            <button
              type="submit"
              className="w-full p-2 bg-[#854d0e] hover:bg-[#a16207] text-white font-bold rounded"
            >
              Login
            </button>
      </form>
    </div>
  );
};
export default Login;
