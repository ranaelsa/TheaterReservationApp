import MovieCard from "./MovieCard";

const MovieGrid = ({ movies, theaters}) => {
  return (
    <div className="flex justify-center mt-8 pb-12">
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
        {movies.map((movie) => (
          <MovieCard key={movie.id} movie={movie} theaters={theaters} />
        ))}
      </div>
    </div>
  );
};

export default MovieGrid;
