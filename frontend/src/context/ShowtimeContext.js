import React, { createContext, useState, useContext } from 'react';

// Create context
const ShowtimeContext = createContext();

// Provider component
export const ShowtimeProvider = ({ children }) => {
  // State for storing theaters
  const [theaters] = useState([
    { id: 1, name: "Theater 1" },
    { id: 2, name: "Theater 2" },
    { id: 3, name: "Theater 3" },
    // Add more theaters as needed
  ]);

  // State for storing selected theater, initially from localStorage or default
  const [selectedTheater, setSelectedTheater] = useState(() => {
    const savedTheater = localStorage.getItem('selectedTheater');
    return savedTheater ? savedTheater : ''; // Use saved value or default to an empty string
  });

  // Update selected theater and persist it to localStorage
  const onSelectTheater = (theater) => {
    setSelectedTheater(theater);
    localStorage.setItem('selectedTheater', theater); // Save to localStorage
  };
  
  // State for show window visibility
  const [showWindow, setShowWindow] = useState(false);
  const [selectedMovie, setSelectedMovie] = useState(null);

  // Function to open the showtime window
  const openShowWindow = (movie) => {
    setSelectedMovie(movie); // Set the selected movie
    setShowWindow(true); // Open the window
  };

  // Function to close the showtime window
  const closeShowWindow = () => {
    setShowWindow(false); // Close the window
    setSelectedMovie(null); // Reset the selected movie
  };

  return (
        <ShowtimeContext.Provider value={{
          theaters,
          selectedTheater,
          onSelectTheater,
          setSelectedTheater,
          openShowWindow,
          closeShowWindow,
          showWindow,
          selectedMovie
        }}>
          {children}
        </ShowtimeContext.Provider>
      );
};

// Custom hook to use the ShowtimeContext
export const useShowtime = () => useContext(ShowtimeContext);
