"use client"
import React, { useState } from 'react';

const Register = () => {
  const [formData, setFormData] = useState({ name: '', email: '', password: '', address: '', card_number: '', expiry_date: '', cvc: '' });

  const [step, setStep] = useState(1);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleNext = () => {
    setStep(2);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Registration logic here
    console.log('User registered:', formData);
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
              className="w-full p-2 border border-gray-300 rounded"
              required
            />
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded"
              required
            />
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={formData.password}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded"
              required
            />
            <input
              type="text"
              name="address"
              placeholder="Address"
              value={formData.address}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded"
              required
            />
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
            <input
              type="text"
              name="card_number"
              placeholder="Card Number"
              value={formData.card_number}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded"
              required
            />
            <input
              type="text"
              name="expiry_date"
              placeholder="Expiry Date"
              value={formData.expiry_date}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded"
              required
            />
            <input
              type="text"
              name="cvc"
              placeholder="CVC"
              value={formData.cvc}
              onChange={handleChange}
              className="w-full p-2 border border-gray-300 rounded"
              required
            />
            <p className="text-sm text-white">By registering, you agree to pay $20 per year.</p>
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
