"use client"

import { useState } from 'react';
import Navbar from '../components/Navbar';
import HeroSection from '../components/HeroSection';
import MovieGrid from '../components/MovieGrid';
import SearchBar from '../components/SearchBar';
import Footer from '../components/Footer';
import TheaterDropdown from '../components/TheaterDropdown';

// Mock data for movies
const mockMovies = [
  { id: 1, title: "Movie 1", showtimes: ["12:00 PM", "3:00 PM", "6:00 PM"], imageURL: "https://images.unsplash.com/photo-1489633908075-1c914e8ee5ea?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c3VyZmVyfGVufDB8fDB8fHww" },
  { id: 2, title: "Movie 2", showtimes: ["1:00 PM", "4:00 PM", "7:00 PM"], imageURL: "https://images.unsplash.com/photo-1541615060331-ca684e62c5d3?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8ZHVuZXxlbnwwfHwwfHx8MA%3D%3D" },
  { id: 3, title: "Movie 3", showtimes: ["2:00 PM", "5:00 PM", "8:00 PM"], imageURL: "https://images.unsplash.com/photo-1478479474071-8a3014d422c8?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8c3RhciUyMHdhcnN8ZW58MHx8MHx8fDA%3D" },
  // Add more movies here
];

const mockTheaters = [
  { id: 1, name: "Theater 1" },
  { id: 2, name: "Theater 2" },
  { id: 3, name: "Theater 3" },
  // Add more theaters here
];

const HomePage = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedTheater, setSelectedTheater] = useState('');
  
  const movies = mockMovies;
  const theaters = mockTheaters;

  const filteredMovies = movies.filter((movie) =>
    movie.title.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div>
      <Navbar />
      <HeroSection />
      <div className="search-container">
        <div className="search-bar">
          <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
        </div>
        <div className="dropdown-container">
          <TheaterDropdown theaters={theaters} selectedTheater={selectedTheater} onSelectTheater={setSelectedTheater} />
        </div>
      </div>
      <MovieGrid movies={filteredMovies} />
      <Footer />
    </div>
  );
};

export default HomePage;
