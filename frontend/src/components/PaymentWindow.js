import React, { useState } from 'react';
import { useShowtime } from '../context/ShowtimeContext'; // Import custom context for selected options
import useApi from '../hooks/useApi'; // Ensure this is your custom hook

const PaymentWindow = ({ onCompletePayment }) => {
  const { selectedMovie, selectedTheater, selectedShowtime, selectedSeat } = useShowtime();

  const [cardNumber, setCardNumber] = useState('');
  const [expiryDate, setExpiry] = useState('');
  const [cvc, setCvc] = useState('');
  const [email, setEmail] = useState('');
  const [coupon, setCoupon] = useState('');
  const [discount, setDiscount] = useState(0);
  const [totalAmount, setTotalAmount] = useState(10); // Example base price
  const [finalAmount, setFinalAmount] = useState(totalAmount);

  const userID = localStorage.getItem('userID'); // Check if the user is logged in
  const { callApi, data, loading, error } = useApi(
    userID ? `http://localhost:8080/api/users/${userID}` : null,
    'GET'
  );

  // Autofill form with saved user data
  const handleUseSavedPaymentInfo = async () => {
    const data = await callApi(); // Fetch user data
    console.log('User data:', data);
    if (data) {
      setCardNumber(data.cardNumber || '');
      setExpiry(data.expiryDate || '');
      setCvc(data.cvc || '');
      setEmail(data.email || '');
    }
  };

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

    const paymentDetails = {
      movie: selectedMovie,
      theater: selectedTheater,
      showtime: selectedShowtime,
      seat: selectedSeat,
      amount: finalAmount,
      cardNumber,
      expiryDate,
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

  // Render logic
  return (
    <div className="p-6 max-w-md mx-auto bg-white rounded shadow-md mt-16">
      <h2 className="text-2xl font-bold mb-4 text-black">Confirm Payment Details</h2>

      {userID && !loading && (
        <button
          type="button"
          onClick={handleUseSavedPaymentInfo}
          className="w-full mb-4 bg-[#854d0e] hover:bg-[#a16207] text-white py-2 rounded-lg font-bold"
        >
          Use Saved Payment Information
        </button>
      )}

      {loading && <p className="text-gray-700 mb-4">Loading saved payment information...</p>}
      {error && <p>Error loading saved payment information. Please try again.</p>}

      <form onSubmit={handlePayment}>
        {/* Card Information */}
        <div className="mb-4">
          <label className="block text-gray-700">Card Number</label>
          <input
            type="text"
            value={cardNumber}
            onChange={(e) => setCardNumber(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-black"
            placeholder="Card Number"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700">Expiry Date</label>
          <input
            type="text"
            value={expiryDate}
            onChange={(e) => setExpiry(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-black"
            placeholder="MMYY"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700">CVC</label>
          <input
            type="text"
            value={cvc}
            onChange={(e) => setCvc(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-black"
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
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-black"
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
              className="flex-grow border border-gray-300 rounded-l-lg px-4 py-2 text-black"
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

export default PaymentWindow;
