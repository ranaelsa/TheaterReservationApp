"use client";
import React, { createContext, useState, useContext, useEffect, useCallback } from 'react';
import useApi from '../hooks/useApi';

const ShowtimeContext = createContext();

export const ShowtimeProvider = ({ children }) => {
  // State for theaters and selected theater
  const [theaters, setTheaters] = useState([]);
  const [selectedTheater, setSelectedTheater] = useState(() => {
    const savedTheater = localStorage.getItem('selectedTheater');
    return savedTheater ? JSON.parse(savedTheater) : null;
  });

  const { callApi: getTheaters, data: fetchedTheaters, loading, error } = useApi('http://localhost:8080/api/theaters', 'GET');

  useEffect(() => {
    const fetchTheaters = async () => {
      try {
        const theatersData = await getTheaters(); 
        setTheaters(theatersData);
      } catch (err) {
        console.error("Error fetching theaters:", err);
      }
    };
  
    fetchTheaters(); 
  }, []);

const onSelectTheater = (theater) => {
  setSelectedTheater(theater);
  if (theater) {
    localStorage.setItem('selectedTheater', JSON.stringify(theater));
  } else {
    localStorage.removeItem('selectedTheater'); // Remove from localStorage when clearing
  }
};

  useEffect(() => {
    console.log('Fetched Theaters:', fetchedTheaters);
  }, [fetchedTheaters]);
  
  useEffect(() => {
    console.log('Theaters State:', theaters);
  }, [theaters]);
  
  useEffect(() => {
    console.log('Selected Theater State:', selectedTheater);
  }, [selectedTheater]);
  
  // State for movies, showtimes, and seats
  const [selectedMovie, setSelectedMovie] = useState(() => {
    const savedMovie = localStorage.getItem('selectedMovie');
    return savedMovie ? JSON.parse(savedMovie) : null;
  });

  const [selectedShowtime, setSelectedShowtime] = useState(() => {
    const savedShowtime = localStorage.getItem('selectedShowtime');
    return savedShowtime ? JSON.parse(savedShowtime) : null;
  });  

  const [selectedSeats, setSelectedSeats] = useState(() => {
    const savedSeats = localStorage.getItem('selectedSeats');
    return savedSeats ? JSON.parse(savedSeats) : [];
  });

  // Functions to manage showtime selection
  const onSelectShowtime = (showtime) => {
    setSelectedShowtime(showtime);
    localStorage.setItem('selectedShowtime', JSON.stringify(showtime));
    setSelectedSeats([]); // Clear seat selection when showtime changes
  };

  // Function to manage movie selection
  const onSelectMovie = (movie) => {
    setSelectedMovie(movie);
    localStorage.setItem('selectedMovie', JSON.stringify(movie));
  };

  // Functions to manage seat selection
  const addSeat = (seatId) => {
    const updatedSeats = [...selectedSeats, seatId];
    setSelectedSeats(updatedSeats);
    localStorage.setItem('selectedSeats', JSON.stringify(updatedSeats));
  };

  const removeSeat = (seatId) => {
    const updatedSeats = selectedSeats.filter((id) => id !== seatId);
    
    setSelectedSeats(updatedSeats);
    localStorage.setItem('selectedSeats', JSON.stringify(updatedSeats));
  };

  const clearSeats = () => {
    setSelectedSeats([]);
    localStorage.removeItem('selectedSeats');
  };

  // Manage the show window visibility
  const [showWindow, setShowWindow] = useState(false);
  const openShowWindow = (movie) => {
    setSelectedMovie(movie);
    setShowWindow(true);
  };

  const closeShowWindow = () => {
    setShowWindow(false);
    setSelectedMovie(null);
    setSelectedShowtime(null);
    setSelectedSeats([]);
  };

  return (
    <ShowtimeContext.Provider
      value={{
        theaters,
        selectedTheater,
        onSelectTheater,
        onSelectMovie,
        selectedMovie,
        setSelectedMovie,
        selectedShowtime,
        onSelectShowtime,
        selectedSeats,
        addSeat,
        removeSeat,
        clearSeats,
        showWindow,
        openShowWindow,
        closeShowWindow,
        loading,
        error,
      }}
    >
      {children}
    </ShowtimeContext.Provider>
  );
};

export const useShowtime = () => useContext(ShowtimeContext);
