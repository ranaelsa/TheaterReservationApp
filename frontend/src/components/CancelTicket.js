"use client"
import React, { useState } from 'react';
import useApi from '@/hooks/useApi';

const CancelTicket = () => {
  const [formData, setFormData] = useState({
    ticketID: '',
  });
  const [errors, setErrors] = useState({});
  const { callApi, data, loading, error } = useApi(`http://localhost:8080/api/tickets/cancel/${formData.ticketID}`, 'DELETE');

  const validateField = (name, value) => {
    // Skip validation if the field is empty
    if (!value) return '';
    switch (name) {
      case 'ticketID':
        return value.length === 8 ? '' : 'Ticket ID must be 8 characters long.';
      default:
        return '';
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
    ["ticketID"].forEach((field) => {
      const error = validateField(field, formData[field]);
      if (error) currentErrors[field] = error;
    });
    setErrors(currentErrors);

    if (Object.keys(currentErrors).length === 0) {
      // No validation errors, proceed with API call
      await callApi();
    }
  };

  return (
    <div className="flex flex-col items-center mt-16 px-4 sm:px-8">
      <h1 className="text-2xl font-bold mb-8">Cancel a Ticket</h1>
      <form onSubmit={handleSubmit} className="w-full max-w-md space-y-4">
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
          disabled={loading}
        >
          {loading ? 'Cancelling...' : 'Cancel Ticket'}
        </button>
        {error && <p className="text-red-500 text-sm mt-4">{error}</p>}
        {data && (
          <p className="text-green-500 text-sm mt-4">
            {data.message}
          </p>
        )}
      </form>
    </div>
  );
};

export default CancelTicket;
