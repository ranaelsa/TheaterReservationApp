"use client";
import React, { useEffect } from 'react';
import { useShowtime } from '../context/ShowtimeContext'; // Import useShowtime from context

const TheaterDropdown = () => {
  // Access theaters and selectedTheater from context
  const { theaters, selectedTheater, onSelectTheater } = useShowtime();

  // Update selected theater (handled by context)
  const handleSelectTheater = (theaterName) => {
    if (theaterName === "") {
      onSelectTheater(null); // Clear the selected theater
    } else {
      // Find the theater object based on the name
      const selected = theaters.find((theater) => theater.name === theaterName);
      if (selected) {
        onSelectTheater(selected); // Update the context with the selected theater
      }
    }
  };
  

  // Retrieve saved theater from localStorage when component mounts
  useEffect(() => {
    const savedTheater = localStorage.getItem('selectedTheater');
    if (savedTheater) {
      try {
        const parsedTheater = JSON.parse(savedTheater);
        const validTheater = theaters.find((theater) => theater.id === parsedTheater.id);
        if (validTheater) {
          onSelectTheater(validTheater);
        } else {
          onSelectTheater(null); // Clear if the saved theater is invalid
        }
      } catch (error) {
        console.error('Error parsing localStorage:', error);
        onSelectTheater(null); // Clear on parsing error
      }
    } else {
      onSelectTheater(null); // Ensure selectedTheater is null if nothing is saved
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
