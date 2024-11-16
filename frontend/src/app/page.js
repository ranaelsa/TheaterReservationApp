"use client";
import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import HeroSection from '../components/HeroSection';
import MovieGrid from '../components/MovieGrid';
import SearchBar from '../components/SearchBar';
import Footer from '../components/Footer';
import TheaterDropdown from '../components/TheaterDropdown';
import FeaturedSection from '@/components/FeaturedSection';
import { ShowtimeProvider } from '@/context/ShowtimeContext'; 
import ShowtimeWindow from '@/components/ShowtimeWindow';
import './globals.css';
import useApi from '@/hooks/useApi';

const HomePage = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedTheater, setSelectedTheater] = useState('');
  const [isClient, setIsClient] = useState(false);

  const { callApi, data: publicMovies, loading, error } = useApi('http://localhost:8080/api/movies/public');
  const { callApi: callApiNonPublic, data: nonPublicMovies, loading: loadingNonPublic, error: errorNonPublic } = useApi('http://localhost:8080/api/movies/nonpublic');

  // Use an empty array if movies is null or undefined
  const filteredMovies = (publicMovies || []).filter((movie) =>
    movie.title.toLowerCase().includes(searchQuery.toLowerCase())
  );

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

  if (!isClient) return null; // Avoid rendering on the server
  if (loading || loadingNonPublic) return <div>Loading...</div>; // Show loading message while fetching data
  if (error || errorNonPublic) return <div>Error loading movies: {error || errorNonPublic}</div>; // Show error message if something goes wrong

  return (
    <ShowtimeProvider> {/* Wrap the entire content in ShowtimeProvider */}
      <div>
        <Navbar />
        <HeroSection />
        <FeaturedSection movies={nonPublicMovies} />
        
        <div className="flex flex-col items-center w-full px-4 sm:px-8">
          <div className="w-full max-w-7xl mt-8">
            <h1 className="text-5xl font-bold mb-4">In Theatres Now</h1>

            <div className="flex w-full gap-4">
              <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
              <TheaterDropdown selectedTheater={selectedTheater} onSelectTheater={setSelectedTheater} />
            </div>
          </div>

          <div className="w-full max-w-7xl mt-8">
            <MovieGrid movies={filteredMovies} />
          </div>
        </div>

        <Footer />
        
        <ShowtimeWindow />
      </div>
    </ShowtimeProvider>
  );
};

export default HomePage;
