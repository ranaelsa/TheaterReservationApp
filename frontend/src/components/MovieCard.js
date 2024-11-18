// MovieCard.js
import Image from "next/image";
import { useShowtime } from "@/context/ShowtimeContext";

const MovieCard = ({ movie }) => { // Default theaters to an empty array
  const { openShowWindow, onSelectMovie } = useShowtime();

  const handleBookNowClick = () => {
    console.log("Book Now clicked for", movie.title);
    onSelectMovie(movie);
    openShowWindow(movie);
  };

  return (
    <div className="max-w-[250px] border p-3 rounded-lg shadow-lg bg-white">
      <Image
        src={`/images/${movie.imageFileName}`}
        alt={`${movie.title} poster`}
        width={250}
        height={300}
        className="rounded-lg mb-4 mx-auto"
      />
      <h3 className="text-xl text-black font-bold mb-2">{movie.title}</h3>
      <p className="text-gray-700 mb-2">{movie.description}</p>
      <button
        onClick={handleBookNowClick}
        className="bg-[#854d0e] hover:bg-[#a16207] text-white px-4 py-2 rounded-lg transition"
      >
        Book Now
      </button>
    </div>
  );
};

export default MovieCard;
