import React from 'react';

const TheaterDropdown = ({ theaters, selectedTheater, onSelectTheater }) => {
  return (
    <div className="relative w-1/3">
      <select
        value={selectedTheater}
        onChange={(e) => onSelectTheater(e.target.value)}
        className="w-full border border-gray-300 rounded-lg px-4 py-2 text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
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
