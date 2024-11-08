import Link from "next/link";
import Image from "next/image";

const MovieCard = ({ movie }) => {
  return (
    <div className="max-w-[250px] border p-3 rounded-lg shadow-lg bg-white">
      <Image
        src={movie.imageURL}
        alt={`${movie.title} poster`}
        width={250}
        height={300}
        className="rounded-lg mb-4"
      />
      <h3 className="text-xl text-black font-bold mb-2">{movie.title}</h3>
      <p className="text-gray-700 mb-2">{movie.description}</p>
      <p className="text-sm text-gray-600 mb-4">Showtimes: {movie.showtimes.join(", ")}</p>
      <Link href={`/book/${movie.id}`} className="bg-[#854d0e] hover:bg-[#a16207] text-white px-4 py-2 rounded-lg transition">
        Book Now
      </Link>
    </div>
  );
};

export default MovieCard;
