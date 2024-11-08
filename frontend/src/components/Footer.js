import Link from 'next/link';

const Footer = () => {
    return (
      <footer className="bg-gray-800 text-white py-6">
        <div className="max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-8 flex justify-between">
          <p>&copy; 2024 AcmePlex. All Rights Reserved.</p>
          <div className="space-x-4">
            <Link href="/terms" className="hover:text-blue-500">Terms of Use</Link>
          </div>
        </div>
      </footer>
    );
  };
  
  export default Footer;
  