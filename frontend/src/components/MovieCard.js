// src/components/MovieCard.js

import Link from "next/link";
import Image from "next/image";

const MovieCard = ({ movie }) => {
  return (
    <div className="w-64 border p-4 rounded-lg shadow-lg bg-white">
      <Image
        src={movie.imageURL}
        alt={`${movie.title} poster`}
        width={200}
        height={300}
        className="rounded-lg mb-4 mx-auto"
      />
      <h3 className="text-xl text-black font-bold mb-2">{movie.title}</h3>
      <p className="text-gray-700 mb-2">{movie.description}</p>
      <p className="text-sm text-gray-600 mb-4">Showtimes: {movie.showtimes.join(", ")}</p>
      <Link href={`/book/${movie.id}`} className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition">
        Book Now
      </Link>
    </div>
  );
};

export default MovieCard;
