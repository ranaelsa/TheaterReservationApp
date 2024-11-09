import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, Navigation, Pagination } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import MovieCard from './MovieCard';

const FeaturedSection = ({ movies }) => {
  return (
    <div className="flex flex-col items-center mt-8 pb-12">
        <h1 className="text-5xl font-bold mb-4">Upcoming Releases</h1>
      <p className="text-xl mb-6">As a registered user, you can buy tickets for these films before public announcement</p>
      <Swiper
        modules={[Navigation, Pagination]}
        centeredSlides={false}
        breakpoints={{
          640: { slidesPerView: 2 },
          768: { slidesPerView: 3 },
        }}
        loop={true}
        navigation
        pagination={{ clickable: true }}
        style={{
          padding: '0 40px',
          width: '70%',
          maxWidth: '1200px',
        }}
      >
        {movies.map((movie) => (
          <SwiperSlide key={movie.id}>
            <MovieCard movie={movie} />
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
};

export default FeaturedSection;
