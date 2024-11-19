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

  const { callApi: callApiPublic, data: publicMovies, loading, error } = useApi('http://localhost:8080/api/movies/public');
  const { callApi: callApiNonPublic, data: nonPublicMovies, loading: loadingNonPublic, error: errorNonPublic } = useApi('http://localhost:8080/api/movies/nonpublic');
  const { callApi: callApiByTheater, data: moviesByTheaterData, loading: moviesByTheaterLoading, error: moviesByTheaterError } = useApi(`http://localhost:8080/api/movies/theater/${selectedTheater?.id || ''}`);

  useEffect(() => {
    setIsClient(true);
  }, []);

  useEffect(() => {
    if (!publicMovies) {
      callApiPublic();
    }
  }, [callApiPublic, publicMovies]);

  useEffect(() => {
    if (!nonPublicMovies) {
      callApiNonPublic();
    }
  }, [callApiNonPublic, nonPublicMovies]);

  useEffect(() => {
    if (selectedTheater) {
      callApiByTheater().then(() => {
        console.log('Fetched movies for theater:', moviesByTheaterData); // Debug here
      });
    }
  }, [callApiByTheater, selectedTheater]);

  // Modified filtering logic
  const filteredMovies = selectedTheater
  ? (moviesByTheaterData || []).filter(movie =>
      movie.title.toLowerCase().includes(searchQuery.toLowerCase())
    )
  : (publicMovies || []).filter(movie =>
      movie.title.toLowerCase().includes(searchQuery.toLowerCase())
    );

  if (!isClient) return null;
  if (loading || loadingNonPublic || moviesByTheaterLoading) return <div>Loading...</div>;
  if (error || errorNonPublic || moviesByTheaterError) return <div>Error loading movies: {error || errorNonPublic || moviesByTheaterError}</div>;

  return (
    <ShowtimeProvider>
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
            <MovieGrid 
              movies={filteredMovies} 
            />
            {selectedTheater && moviesByTheaterData?.length === 0 && (
              <p className="text-center mt-4">No movies available for this theater.</p>
            )}
          </div>
        </div>

        <Footer />
        <ShowtimeWindow />
      </div>
    </ShowtimeProvider>
  );
};

export default HomePage;