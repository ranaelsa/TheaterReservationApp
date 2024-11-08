import MovieCard from "./MovieCard";

const MovieGrid = ({ movies }) => {
  return (
    <section className="py-5 bg-light">
      <div className="container mx-auto text-center">
        <h1 className="mb-4 text-4xl">Movies</h1>
        <p className="text-muted mb-5">Choose a movie to book</p>

        <div className="flex justify-center">
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-auto-fit gap-6 mx-auto">
            {movies.map((movie) => (
              <MovieCard key={movie.id} movie={movie} />
            ))}
          </div>
        </div>
      </div>
    </section>
  );
};

export default MovieGrid;
