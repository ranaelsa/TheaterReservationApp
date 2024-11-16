"use client";
import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import HeroSection from '../components/HeroSection';
import MovieGrid from '../components/MovieGrid';
import SearchBar from '../components/SearchBar';
import Footer from '../components/Footer';
import TheaterDropdown from '../components/TheaterDropdown';
import FeaturedSection from '@/components/FeaturedSection';
import { ShowtimeProvider } from '@/context/ShowtimeContext'; // Ensure you import the provider
import ShowtimeWindow from '@/components/ShowtimeWindow';
import './globals.css';
import useApi from '@/hooks/useApi';

const HomePage = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedTheater, setSelectedTheater] = useState('');
  const [isClient, setIsClient] = useState(false);

  const { callApi, data: movies, loading, error } = useApi('http://localhost:8080/api/movies/public');

  // Use an empty array if movies is null or undefined
  const filteredMovies = (movies || []).filter((movie) =>
    movie.title.toLowerCase().includes(searchQuery.toLowerCase())
  );

  useEffect(() => {
    setIsClient(true); // This will only execute on the client
  }, []);

  useEffect(() => {
    if (!movies) {
      callApi();
    }
  }, [callApi, movies]);

  if (!isClient) return null; // Avoid rendering on the server
  if (loading) return <div>Loading...</div>; // Show loading message while fetching data
  if (error) return <div>Error loading movies: {error}</div>; // Show error message if something goes wrong

  return (
    <ShowtimeProvider> {/* Wrap the entire content in ShowtimeProvider */}
      <div>
        <Navbar />
        <HeroSection />
        <FeaturedSection movies={movies} />
        
        {/* Align the search bar and dropdown with padding */}
        <div className="flex flex-col items-center w-full px-4 sm:px-8">
          <div className="w-full max-w-7xl mt-8">
            <h1 className="text-5xl font-bold mb-4">In Theatres Now</h1>

            <div className="flex w-full gap-4">
              <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
              <TheaterDropdown selectedTheater={selectedTheater} onSelectTheater={setSelectedTheater} />
            </div>
          </div>

          <div className="w-full max-w-7xl mt-8">
            <MovieGrid movies={filteredMovies} /> {/* Pass theaters here */}
          </div>
        </div>

        <Footer />
        
        {/* Conditionally render ShowtimeWindow based on context */}
        <ShowtimeWindow />
      </div>
    </ShowtimeProvider>
  );
};

export default HomePage;
