import React, { useState } from 'react';

const SeatSelectionModal = ({ showtime, onClose }) => {
  // Define the seat layout with row and seat labels
  const initialSeats = {
    A: ["unavailable", "unavailable", "available", "available", "available", "available", "available", "available", "available", "unavailable", "unavailable"],
    B: ["unavailable", "available", "available", "available", "available", "available", "available", "available", "available", "available", "unavailable"],
    C: ["available", "available", "available", "available", "available", "available", "available", "available", "available", "available", "available"],
    D: ["available", "available", "available", "available", "available", "available", "available", "available", "available", "available", "available"],
    E: ["available", "available", "available", "available", "available", "available", "available", "available", "available", "available", "available"],
    F: ["unavailable", "available", "available", "available", "available", "available", "available", "available", "available", "available", "unavailable"],
  };

  const [seats, setSeats] = useState(initialSeats);

  // Toggle seat selection
  const handleSeatClick = (row, seatIndex) => {
    setSeats((prevSeats) => ({
      ...prevSeats,
      [row]: prevSeats[row].map((seat, index) => {
        if (index === seatIndex) {
          if (seat === "available") return "selected"; // Select if available
          if (seat === "selected") return "available"; // Deselect if selected
        }
        return seat; // Leave unchanged if unavailable
      })
    }));
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[9999]">
      <div className="bg-white p-6 rounded-lg shadow-lg max-w-3xl w-full">
        <button
          onClick={onClose}
          className="text-gray-500 float-right text-xl font-bold mb-4"
          aria-label="Close"
        >
          &times;
        </button>
        <h2 className="text-2xl font-bold mb-4 text-black">Select Seats for {showtime}</h2>

        {/* Stage Indicator */}
        <div className="text-center mb-4">
          <div className="text-gray-600 text-sm mb-1">STAGE</div>
          <div className="w-full h-2 bg-gray-300 rounded-full"></div>
        </div>

        {/* Seat selection grid with aisle spacing */}
        <div className="flex flex-col items-center space-y-2">
          {Object.keys(seats).map((row, rowIndex) => (
            <div key={rowIndex} className="flex space-x-2 justify-center">
              {/* Row Label */}
              <div className="text-black font-semibold mr-2">{row}</div>
              {seats[row].map((seat, seatIndex) => (
                <React.Fragment key={`${row}-${seatIndex}`}>
                  {/* Insert spacing for aisles between columns 2-3 and 9-10 */}
                  {seatIndex === 2 || seatIndex === 9 ? (
                    <div key={`${row}-aisle-${seatIndex}`} className="w-6"></div>
                  ) : null}
                  <div
                    key={`${row}-${seatIndex}`}
                    className={`w-10 h-10 flex items-center justify-center rounded cursor-pointer text-white ${
                      seat === "available" ? "bg-green-500" :
                      seat === "selected" ? "bg-blue-500" :
                      "bg-gray-400 cursor-not-allowed"
                    }`}
                    onClick={() =>
                      seat !== "unavailable" && handleSeatClick(row, seatIndex)
                    }
                  >
                    {row}{seatIndex + 1}
                  </div>
                </React.Fragment>
              ))}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default SeatSelectionModal;
