import React, { useEffect, useState } from 'react';
import { useShowtime } from '../context/ShowtimeContext'; // Import custom context for selected options

const PaymentPage = ({ userId, onCompletePayment }) => {
  const { selectedMovie, selectedTheater, selectedShowtime, selectedSeat } = useShowtime();
  
  const [cardNumber, setCardNumber] = useState('');
  const [expiry, setExpiry] = useState('');
  const [cvc, setCvc] = useState('');
  const [email, setEmail] = useState('');
  const [coupon, setCoupon] = useState('');
  const [discount, setDiscount] = useState(0);
  const [totalAmount, setTotalAmount] = useState(10); // Example base price
  const [finalAmount, setFinalAmount] = useState(totalAmount);

  // Fetch user data (e.g., saved payment info) when component mounts
  useEffect(() => {
    const fetchUserData = async () => {
      if (userId) {
        // Fetch saved payment info and email for registered user
        const response = await fetch(`/api/user/${userId}`);
        const data = await response.json();
        if (data) {
          setCardNumber(data.cardNumber);
          setExpiry(data.expiry);
          setCvc(data.cvc);
          setEmail(data.email);
        }
      }
    };
    fetchUserData();
  }, [userId]);

  // Handle coupon application
  const applyCoupon = async () => {
    if (coupon) {
      const response = await fetch(`/api/apply-coupon?code=${coupon}`);
      const result = await response.json();
      if (result.success) {
        setDiscount(result.discountAmount);
        setFinalAmount(totalAmount - result.discountAmount);
      } else {
        alert('Invalid coupon code');
      }
    }
  };

  // Handle payment submission
  const handlePayment = async (e) => {
    e.preventDefault();
    // Payment processing logic here
    const paymentDetails = {
      movie: selectedMovie,
      theater: selectedTheater,
      showtime: selectedShowtime,
      seat: selectedSeat,
      amount: finalAmount,
      cardNumber,
      expiry,
      cvc,
      email,
    };
    
    const response = await fetch('/api/process-payment', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(paymentDetails),
    });

    const result = await response.json();
    if (result.success) {
      alert('Payment successful!');
      onCompletePayment(); // Redirect to confirmation or home page
    } else {
      alert('Payment failed, please try again.');
    }
  };

  return (
    <div className="p-6 max-w-md mx-auto bg-white rounded shadow-md mt-16">
      <h2 className="text-2xl font-bold mb-4 text-black">Confirm Payment Details</h2>
      <form onSubmit={handlePayment}>
        {/* Card Information */}
        <div className="mb-4">
          <label className="block text-gray-700">Card Number</label>
          <input
            type="text"
            value={cardNumber}
            onChange={(e) => setCardNumber(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-4 py-2"
            placeholder="Card Number"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700">Expiry Date</label>
          <input
            type="text"
            value={expiry}
            onChange={(e) => setExpiry(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-4 py-2"
            placeholder="MM/YY"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700">CVC</label>
          <input
            type="text"
            value={cvc}
            onChange={(e) => setCvc(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-4 py-2"
            placeholder="CVC"
            required
          />
        </div>

        {/* Email Field */}
        <div className="mb-4">
          <label className="block text-gray-700">Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-4 py-2"
            placeholder="Email"
            required
          />
        </div>

        {/* Coupon Code */}
        <div className="mb-4">
          <label className="block text-gray-700">Coupon Code</label>
          <div className="flex">
            <input
              type="text"
              value={coupon}
              onChange={(e) => setCoupon(e.target.value)}
              className="flex-grow border border-gray-300 rounded-l-lg px-4 py-2"
              placeholder="Enter coupon code"
            />
            <button
              type="button"
              onClick={applyCoupon}
              className="bg-[#854d0e] hover:bg-[#a16207] text-white px-4 py-2 rounded-r-lg"
            >
              Apply
            </button>
          </div>
        </div>

        {/* Display Amount and Discount */}
        <div className="mb-6 text-lg font-semibold text-black">
          <p>Subtotal: ${totalAmount.toFixed(2)}</p>
          {discount > 0 && <p>Discount: -${discount.toFixed(2)}</p>}
          <p className="text-xl">Final Amount: ${finalAmount.toFixed(2)}</p>
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          className="w-full bg-[#854d0e] hover:bg-[#a16207] text-white py-2 rounded-lg font-bold"
        >
          Complete Payment
        </button>
      </form>
    </div>
  );
};

export default PaymentPage;
