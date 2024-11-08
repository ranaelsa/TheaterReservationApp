import Link from 'next/link';

const HeroSection = () => {
    return (
      <section className="relative bg-cover bg-center h-[500px] text-white" style={{ backgroundImage: 'url(https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D)' }}>
        <div className="absolute inset-0 bg-black opacity-50"></div>
        <div className="relative z-10 flex justify-center items-center h-full text-center">
          <div>
            <h1 className="text-5xl font-bold mb-4">Your gateway to unforgettable cinema</h1>
            <p className="text-xl mb-6">Catch the latest movies on the big screen and book your tickets now.</p>
          </div>
        </div>
      </section>
    );
  };
  
  export default HeroSection;
  