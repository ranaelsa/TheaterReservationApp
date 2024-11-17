import React, { createContext, useState, useContext, useEffect } from 'react';
import useApi from '../hooks/useApi';

const ShowtimeContext = createContext();

export const ShowtimeProvider = ({ children }) => {
  // State for storing theaters
  const [theaters, setTheaters] = useState([]); // Default to an empty array
  const { callApi, data: fetchedTheaters, loading, error } = useApi('http://localhost:8080/api/theaters', 'GET');

  useEffect(() => {
    // Fetch theaters when the component mounts
    const fetchTheaters = async () => {
      try {
        const theatersData = await callApi(); // Fetch theaters
        setTheaters(theatersData); // Update state with fetched theaters
      } catch (err) {
        console.error("Error fetching theaters:", err);
      }
    };

    fetchTheaters();
  }, [callApi]);

  // State for selected theater and other states
  const [selectedTheater, setSelectedTheater] = useState(() => {
    const savedTheater = localStorage.getItem('selectedTheater');
    return savedTheater ? savedTheater : ''; 
  });

  const onSelectTheater = (theater) => {
    setSelectedTheater(theater);
    localStorage.setItem('selectedTheater', theater);
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
      setSelectedTheater,
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