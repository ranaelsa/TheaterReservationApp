"use client"
import React, { useState } from 'react';

const Register = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    address: '',
    card_number: '',
    expiry_month: '',
    expiry_year: '',
    cvc: ''
  });
  const [errors, setErrors] = useState({});
  const [step, setStep] = useState(1);

  const validateField = (name, value) => {
    switch (name) {
      case 'name':
        return value.length > 1 ? '' : 'Name must be at least 2 characters long.';
      case 'email':
        return /\S+@\S+\.\S+/.test(value) ? '' : 'Invalid email format.';
      case 'password':
        return value.length >= 6 ? '' : 'Password must be at least 6 characters.';
      case 'address':
        return value.length > 5 ? '' : 'Address must be at least 6 characters long.';
      case 'card_number':
        return /^\d{16}$/.test(value) ? '' : 'Card number must be 16 digits.';
      case 'expiry_month':
        return /^\d{2}$/.test(value) && parseInt(value) >= 1 && parseInt(value) <= 12
          ? '' : 'Expiry month must be between 01 and 12.';
      case 'expiry_year':
        return /^\d{2}$/.test(value) ? '' : 'Expiry year must be in YY format.';
      case 'cvc':
        return /^\d{3,4}$/.test(value) ? '' : 'CVC must be 3 or 4 digits.';
      default:
        return '';
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    setErrors({ ...errors, [name]: validateField(name, value) });
  };

  const handleNext = () => {
    const currentErrors = {};
    ['name', 'email', 'password', 'address'].forEach(field => {
      const error = validateField(field, formData[field]);
      if (error) currentErrors[field] = error;
    });
    if (Object.keys(currentErrors).length === 0) {
      setStep(2);
    } else {
      setErrors(currentErrors);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const currentErrors = {};
    ['card_number', 'expiry_month', 'expiry_year', 'cvc'].forEach(field => {
      const error = validateField(field, formData[field]);
      if (error) currentErrors[field] = error;
    });
    if (Object.keys(currentErrors).length === 0) {
      console.log('User registered:', formData);
    } else {
      setErrors(currentErrors);
    }
  };

  return (
    <div className="flex flex-col items-center mt-16 px-4 sm:px-8">
      <h1 className="text-2xl font-bold mb-8">Become a Registered User Today!</h1>
      <form onSubmit={handleSubmit} className="w-full max-w-md space-y-4">
        
        {step === 1 && (
          <>
            <input
              type="text"
              name="name"
              placeholder="Name"
              value={formData.name}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded text-black"
              required
            />
            {errors.name && <p className="text-red-500 text-sm">{errors.name}</p>}

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

            <input
              type="text"
              name="address"
              placeholder="Address"
              value={formData.address}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded text-black"
              required
            />
            {errors.address && <p className="text-red-500 text-sm">{errors.address}</p>}

            <button
              type="button"
              onClick={handleNext}
              className="w-full p-2 bg-[#854d0e] hover:bg-[#a16207] text-white font-bold rounded"
            >
              Proceed to Payment
            </button>
          </>
        )}

        {step === 2 && (
          <>
            <button
              type="button"
              onClick={() => setStep(1)}
              className="w-full p-2 bg-[#854d0e] hover:bg-[#a16207] text-white font-bold rounded"
            >
              Previous Step
            </button>

            <input
              type="text"
              name="card_number"
              placeholder="Card Number"
              value={formData.card_number}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded text-black"
              required
            />
            {errors.card_number && <p className="text-red-500 text-sm">{errors.card_number}</p>}

            <div className="flex space-x-2">
              <input
                type="text"
                name="expiry_month"
                placeholder="MM"
                value={formData.expiry_month}
                onChange={handleChange}
                className="w-1/2 p-2 border border-gray-300 rounded text-black"
                maxLength="2"
                required
              />
              <span className="text-white">/</span>
              <input
                type="text"
                name="expiry_year"
                placeholder="YY"
                value={formData.expiry_year}
                onChange={handleChange}
                className="w-1/2 p-2 border border-gray-300 rounded text-black"
                maxLength="2"
                required
              />
            </div>
            {errors.expiry_month && <p className="text-red-500 text-sm">{errors.expiry_month}</p>}
            {errors.expiry_year && <p className="text-red-500 text-sm">{errors.expiry_year}</p>}

            <input
              type="text"
              name="cvc"
              placeholder="CVC"
              value={formData.cvc}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded text-black"
              required
            />
            {errors.cvc && <p className="text-red-500 text-sm">{errors.cvc}</p>}

            <p className="text-sm text-white">By registering, you agree to pay a $20 non-refundable fee per year.</p>
            <button
              type="submit"
              className="w-full p-2 bg-[#854d0e] hover:bg-[#a16207] text-white font-bold rounded"
            >
              Register
            </button>
          </>
        )}
      </form>
    </div>
  );
};

export default Register;
