import Link from 'next/link';

const HeroSection = () => {
    return (
      <section className="relative bg-cover bg-center h-[500px] text-white" style={{ backgroundImage: 'url(/path-to-image.jpg)' }}>
        <div className="absolute inset-0 bg-black opacity-50"></div>
        <div className="relative z-10 flex justify-center items-center h-full text-center">
          <div>
            <h1 className="text-5xl font-bold mb-4">Latest Movies at AcmePlex</h1>
            <p className="text-xl mb-6">Explore the best movies and book your tickets now</p>
            <Link href="/movies" className="px-8 py-3 bg-blue-600 hover:bg-blue-700 text-white rounded-full text-lg">
              Book Now
            </Link>
          </div>
        </div>
      </section>
    );
  };
  
  export default HeroSection;
  