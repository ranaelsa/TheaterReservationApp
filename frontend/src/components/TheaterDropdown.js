import React, { useEffect } from 'react';
import { useShowtime } from '../context/ShowtimeContext'; // Import useShowtime from context

const TheaterDropdown = () => {
  // Access theaters and selectedTheater from context
  const { theaters, selectedTheater, onSelectTheater } = useShowtime();

  // Update selected theater (handled by context)
  const handleSelectTheater = (theaterName) => {
    // Find the theater object based on the name
    const selected = theaters.find((theater) => theater.name === theaterName);
    if (selected) {
      onSelectTheater(selected); // Pass the full object to onSelectTheater
    }
  };

  // Retrieve saved theater from localStorage when component mounts
  useEffect(() => {
    const savedTheater = localStorage.getItem('selectedTheater');
    if (savedTheater) {
      try {
        const parsedTheater = JSON.parse(savedTheater); // Parse the saved object
        // Ensure the saved theater exists in the theaters list
        const validTheater = theaters.find((theater) => theater.id === parsedTheater.id);
        if (validTheater) {
          onSelectTheater(validTheater); // Update the context with saved theater
        }
      } catch (error) {
        console.error('Error parsing localStorage:', error);  // Handle parsing error
      }
    }
  }, [onSelectTheater]); // Add theaters to the dependency array for safety

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
