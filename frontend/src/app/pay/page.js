"use client";
import Footer from "@/components/Footer";
import PaymentWindow from "@/components/PaymentWindow";
import { ShowtimeProvider } from "@/context/ShowtimeContext";

const PaymentPage = () => {
  return (
    <ShowtimeProvider>
    <div
      className="flex flex-col min-h-screen"
      style={{
        backgroundImage: 'url(https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D)',
        backgroundSize: 'cover',        // Ensures the image covers the whole container
        backgroundPosition: 'center',   // Centers the image
        backgroundRepeat: 'no-repeat',  // Prevents the image from repeating
      }}
    >
      <div className="flex-grow">
        <PaymentWindow />
      </div>
      <Footer />
    </div>
    </ShowtimeProvider>
  );
}

export default PaymentPage;
