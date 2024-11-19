// TheaterDropdown.js
import React, { useEffect } from 'react';
import { useShowtime } from '../context/ShowtimeContext';

const TheaterDropdown = () => {
  const { theaters, selectedTheater, onSelectTheater } = useShowtime();

  const handleSelectTheater = (theaterName) => {
    console.log('Selecting theater:', theaterName);
    const selected = theaters.find((theater) => theater.name === theaterName);
    if (selected) {
      console.log('Found theater:', selected);
      onSelectTheater(selected);
    }
  };

  useEffect(() => {
    const savedTheater = localStorage.getItem('selectedTheater');
    if (savedTheater) {
      try {
        const parsedTheater = JSON.parse(savedTheater);
        const validTheater = theaters.find((theater) => theater.id === parsedTheater.id);
        if (validTheater) {
          onSelectTheater(validTheater);
        }
      } catch (error) {
        console.error('Error parsing localStorage:', error);
      }
    }
  }, [theaters, onSelectTheater]);

  return (
    <div className="relative w-1/3">
      <select
        value={selectedTheater ? selectedTheater.name : ""}
        onChange={(e) => handleSelectTheater(e.target.value)}
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