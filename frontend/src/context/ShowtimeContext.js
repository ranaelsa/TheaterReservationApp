import React, { createContext, useState, useContext, useEffect } from 'react';
import useApi from '../hooks/useApi';

const ShowtimeContext = createContext();

export const ShowtimeProvider = ({ children }) => {
  // State for theaters and selected theater
  const [theaters, setTheaters] = useState([]);
  const [selectedTheater, setSelectedTheater] = useState(() => {
    const savedTheater = localStorage.getItem('selectedTheater');
    return savedTheater ? JSON.parse(savedTheater) : null;
  });

  const { callApi, data: fetchedTheaters, loading, error } = useApi('http://localhost:8080/api/theaters', 'GET');

  useEffect(() => {
    const fetchTheaters = async () => {
      try {
        const theatersData = await callApi();
        setTheaters(theatersData);
      } catch (err) {
        console.error("Error fetching theaters:", err);
      }
    };
    fetchTheaters();
  }, [callApi]);

  const onSelectTheater = (theater) => {
    if (theater !== selectedTheater) {
      setSelectedTheater(theater);
      localStorage.setItem('selectedTheater', JSON.stringify(theater));
    }
  };

  // State for movies, showtimes, and seats
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [selectedShowtime, setSelectedShowtime] = useState(null);
  const [selectedSeats, setSelectedSeats] = useState([]);

  // Functions to manage showtime selection
  const onSelectShowtime = (showtime) => {
    setSelectedShowtime(showtime);
    setSelectedSeats([]); // Clear seat selection when showtime changes
  };

  // Functions to manage seat selection
  const addSeat = (seatId) => {
    if (!selectedSeats.includes(seatId)) {
      setSelectedSeats((prevSeats) => [...prevSeats, seatId]);
    }
  };

  const removeSeat = (seatId) => {
    setSelectedSeats((prevSeats) => prevSeats.filter((id) => id !== seatId));
  };

  const clearSeats = () => setSelectedSeats([]);

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
