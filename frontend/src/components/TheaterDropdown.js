// src/components/TheaterDropdown.js
import React from 'react';

const TheaterDropdown = ({ theaters, selectedTheater, onSelectTheater }) => {
  return (
    <div className="relative inline-block m-4">
        <select 
        value={selectedTheater} 
        onChange={(e) => onSelectTheater(e.target.value)}
        className="appearance-none w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm bg-white text-gray-700 hover:border-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
        >
        <option value="">Select a Theater</option>
        {theaters.map((theater) => (
            <option key={theater.id} value={theater.name}>
            {theater.name}
            </option>
        ))}
        </select>
    </div>
  );
};

export default TheaterDropdown;
