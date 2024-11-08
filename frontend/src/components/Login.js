"use client"
import React, { useState } from 'react';

const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [errors, setErrors] = useState({});

  const validateField = (name, value) => {
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

  const handleSubmit = (e) => {
    e.preventDefault();
    const currentErrors = {};
    ['email', 'password'].forEach(field => {
      const error = validateField(field, formData[field]);
      if (error) currentErrors[field] = error;
    });
  };

  return (
    <div className="flex flex-col items-center mt-16 px-4 sm:px-8">
      <h1 className="text-2xl font-bold mb-8">Login to Existing Account</h1>
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
