// src/pages/HomePage.js
"use client";
import { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from '../components/Navbar';
import HeroSection from '../components/HeroSection';
import MovieGrid from '../components/MovieGrid';
import SearchBar from '../components/SearchBar';
import Footer from '../components/Footer';
import TheaterDropdown from '../components/TheaterDropdown';
import FeaturedSection from '@/components/FeaturedSection';
import ShowtimeWindow from '@/components/ShowtimeWindow';
import './globals.css';
import useApi from '@/hooks/useApi';
import { useShowtime } from '@/context/ShowtimeContext';

const HomePage = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const { theaters, selectedTheater } = useShowtime();
  const [isClient, setIsClient] = useState(false);

  // Existing useApi hooks for public and non-public movies
  const { callApi, data: publicMovies, loading, error } = useApi('http://localhost:8080/api/movies/public');
  const { callApi: callApiNonPublic, data: nonPublicMovies, loading: loadingNonPublic, error: errorNonPublic } = useApi('http://localhost:8080/api/movies/nonpublic');

  // State variables for movies by theater
  const [moviesByTheater, setMoviesByTheater] = useState(null);
  const [loadingMoviesByTheater, setLoadingMoviesByTheater] = useState(false);
  const [errorMoviesByTheater, setErrorMoviesByTheater] = useState(null);

  useEffect(() => {
    setIsClient(true); // This will only execute on the client
  }, []);

  useEffect(() => {
    // Fetch public movies if not already loaded
    if (!publicMovies) {
      callApi();
    }
  }, [callApi, publicMovies]);

  useEffect(() => {
    // Fetch non-public movies if not already loaded
    if (!nonPublicMovies) {
      callApiNonPublic();
    }
  }, [callApiNonPublic, nonPublicMovies]);

  useEffect(() => {
    if (selectedTheater && selectedTheater.id) {
      const fetchMoviesByTheater = async () => {
        setLoadingMoviesByTheater(true);
        setErrorMoviesByTheater(null);
        try {
          const response = await axios.get(`http://localhost:8080/api/movies/theater/${selectedTheater.id}`);
          setMoviesByTheater(response.data);
        } catch (err) {
          setErrorMoviesByTheater(err.response?.data || err.message || 'Something went wrong');
          console.error(err);
        } finally {
          setLoadingMoviesByTheater(false);
        }
      };

      fetchMoviesByTheater();
    } else {
      // Clear moviesByTheater if no theater is selected
      setMoviesByTheater(null);
    }
  }, [selectedTheater]);

  // Determine which movies to display
  const moviesToDisplay = selectedTheater ? moviesByTheater : publicMovies;

  // Filter movies based on search query
  const filteredMovies = (moviesToDisplay || []).filter((movie) => {
    const matchesSearchQuery = movie.title.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesSearchQuery;
  });

  if (!isClient) return null; // Avoid rendering on the server

  if (loading || loadingNonPublic || (selectedTheater && loadingMoviesByTheater)) {
    return <div>Loading...</div>; // Show loading message while fetching data
  }

  if (error || errorNonPublic || errorMoviesByTheater) {
    const errorMessage = error || errorNonPublic || errorMoviesByTheater;
    return <div>Error loading movies: {errorMessage}</div>; // Show error message if something goes wrong
  }

  return (
    <div>
      <Navbar />
      <HeroSection />
      <FeaturedSection movies={nonPublicMovies} />
      
      <div className="flex flex-col items-center w-full px-4 sm:px-8">
        <div className="w-full max-w-7xl mt-8">
          <h1 className="text-5xl font-bold mb-4">In Theatres Now</h1>

          <div className="flex w-full gap-4">
            <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
            <TheaterDropdown selectedTheater={selectedTheater} />
          </div>
        </div>

        <div className="w-full max-w-7xl mt-8">
          <MovieGrid movies={filteredMovies} />
        </div>
      </div>

      <Footer />
      
      <ShowtimeWindow />
    </div>
  );
};

export default HomePage;
