// src/components/TheaterDropdown.js
import React from 'react';

const TheaterDropdown = ({ theaters, selectedTheater, onSelectTheater }) => {
  return (
    <div className="relative">
        <select 
        value={selectedTheater} 
        onChange={(e) => onSelectTheater(e.target.value)}
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
