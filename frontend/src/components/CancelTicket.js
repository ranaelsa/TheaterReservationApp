"use client"
import React, { useState } from 'react';

const CancelTicket = () => {
  const [formData, setFormData] = useState({
    email: '',
    ticketID: '',
  });
  const [errors, setErrors] = useState({});

  const validateField = (name, value) => {
    // Skip validation if the field is empty
    if (!value) return '';
    switch (name) {
      case 'email':
        return /\S+@\S+\.\S+/.test(value) ? '' : 'Invalid email format.';
      case 'ticketID':
        return value.length === 10 ? '' : 'Ticket ID must be 10 characters long.';
      default:
        return '';
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
    ['email', 'ticketID'].forEach(field => {
      const error = validateField(field, formData[field]);
      if (error) currentErrors[field] = error;
    });
    setErrors(currentErrors); // Set the accumulated errors
  };

  return (
    <div className="flex flex-col items-center mt-16 px-4 sm:px-8">
      <h1 className="text-2xl font-bold mb-8">Cancel a Ticket</h1>
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
          type="text" // Corrected this line
          name="ticketID"
          placeholder="Ticket ID"
          value={formData.ticketID}
          onChange={handleChange}
          className="w-full p-2 border border-gray-300 rounded text-black"
          required
        />
        {errors.ticketID && <p className="text-red-500 text-sm">{errors.ticketID}</p>}
        <div className="text-sm text-gray-300 mt-3 space-y-1">
          <p>A coupon will be sent to the email associated with the ticket.</p>
          <p>There is a 15% cancellation fee for unregistered users.</p>
          <p>Tickets can only be cancelled up to 72 hours prior to showtime.</p>
        </div>
        <button
          type="submit"
          className="w-full p-2 bg-[#991b1b] hover:bg-[#ef4444] text-white font-bold rounded"
        >
          Cancel Ticket
        </button>
      </form>
    </div>
  );
};

export default CancelTicket;
