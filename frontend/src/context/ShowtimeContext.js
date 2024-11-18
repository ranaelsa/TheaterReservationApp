import React, { createContext, useState, useContext, useEffect } from 'react';
import useApi from '../hooks/useApi';

const ShowtimeContext = createContext();

export const ShowtimeProvider = ({ children }) => {
  // State for storing theaters
  const [theaters, setTheaters] = useState([]); // Default to an empty array
  const { callApi, data: fetchedTheaters, loading, error } = useApi('http://localhost:8080/api/theaters', 'GET');

  // Fetch theaters when the component mounts (only once)
  useEffect(() => {
    const fetchTheaters = async () => {
      try {
        const theatersData = await callApi(); // Fetch theaters
        setTheaters(theatersData); // Update state with fetched theaters
      } catch (err) {
        console.error("Error fetching theaters:", err);
      }
    };

    fetchTheaters();
  }, [callApi]); // Empty dependency array to run this effect only once

  // State for selected theater
  const [selectedTheater, setSelectedTheater] = useState(() => {
    const savedTheater = localStorage.getItem('selectedTheater');
    return savedTheater ? JSON.parse(savedTheater) : null; // Parse JSON if available
  });

  const onSelectTheater = (theater) => {
    // Only update if the new selection is different from the current one
    if (theater !== selectedTheater) {
      setSelectedTheater(theater);
      localStorage.setItem('selectedTheater', JSON.stringify(theater)); // Store as JSON
    }
  };

  // State for show window visibility and other showtime logic
  const [showWindow, setShowWindow] = useState(false);
  const [selectedMovie, setSelectedMovie] = useState(null);

  const openShowWindow = (movie) => {
    setSelectedMovie(movie);
    setShowWindow(true);
  };

  const closeShowWindow = () => {
    setShowWindow(false);
    setSelectedMovie(null);
  };

  return (
    <ShowtimeContext.Provider value={{
      theaters,
      selectedTheater,
      onSelectTheater,
      openShowWindow,
      closeShowWindow,
      showWindow,
      selectedMovie,
      loading, // You can also expose loading state here if needed
      error, // Error handling if you want to show an error in the UI
    }}>
      {children}
    </ShowtimeContext.Provider>
  );
};

export const useShowtime = () => useContext(ShowtimeContext);
