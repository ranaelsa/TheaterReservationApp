import React, { useEffect } from 'react';
import { useShowtime } from '../context/ShowtimeContext'; // Import useShowtime from context

const TheaterDropdown = () => {
  // Access theaters and selectedTheater from context
  const { theaters, selectedTheater, onSelectTheater } = useShowtime();

  // Update selected theater (handled by context)
  const handleSelectTheater = (theater) => {
    onSelectTheater(theater); // Use setSelectedTheater from context to update the selected theater
  };

  // Retrieve saved theater from localStorage when component mounts
  useEffect(() => {
    const savedTheater = localStorage.getItem('selectedTheater');
    if (savedTheater) {
      onSelectTheater(savedTheater); // Update the context with saved theater
    }
  }, [onSelectTheater]);

  return (
    <div className="relative w-1/3">
      <select
        value={selectedTheater || ""}
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
