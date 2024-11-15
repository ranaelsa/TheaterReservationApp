import { useEffect, useState } from 'react';
import Link from 'next/link';

const Navbar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    // Check if userID is present in localStorage
    const userID = localStorage.getItem('userID');
    if (userID) {
      setIsLoggedIn(true);
    }
  }, []);

  const handleLogout = () => {
    // Clear localStorage and refresh the page
    localStorage.removeItem('userID');
    setIsLoggedIn(false);
    window.location.reload();  // Refresh the page
  };

  return (
    <nav className="bg-black text-white p-4 fixed w-full top-0 left-0 z-50 shadow-md">
      <div className="max-w-screen-xl mx-auto flex justify-between items-center">
        <Link href="/" className="text-3xl font-bold">AcmePlex</Link>
        <div className="flex space-x-4">
          {/* Always visible buttons */}
          <Link href="/cancel" className="px-4 py-2 bg-[#854d0e] hover:bg-[#a16207] rounded-md">Cancel Ticket</Link>

          {/* Conditionally visible buttons based on login state */}
          {!isLoggedIn ? (
            <>
              <Link href="/register" className="px-4 py-2 bg-[#854d0e] hover:bg-[#a16207] rounded-md">Register</Link>
              <Link href="/login" className="px-4 py-2 bg-[#854d0e] hover:bg-[#a16207] rounded-md">Login</Link>
            </>
          ) : (
            <button 
              onClick={handleLogout} 
              className="px-4 py-2 bg-[#854d0e] hover:bg-[#a16207] rounded-md">
              Logout
            </button>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
