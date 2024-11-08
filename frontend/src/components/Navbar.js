import Link from 'next/link';

const Navbar = () => {
  return (
    <nav className="bg-black text-white p-4 fixed w-full top-0 left-0 z-50 shadow-md">
      <div className="max-w-screen-xl mx-auto flex justify-between items-center">
        <Link href="/" className="text-3xl font-bold">AcmePlex</Link>
        <div className="flex space-x-4">
          <Link href="/register" className="px-4 py-2 bg-blue-500 hover:bg-blue-600 rounded-md">Register</Link>
          <Link href="/login" className="px-4 py-2 bg-blue-500 hover:bg-green-600 rounded-md">Login</Link>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
