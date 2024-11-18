import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { useShowtime } from "../context/ShowtimeContext"; // Import the custom hook

const SeatSelectionModal = ({ showtime, onClose }) => {
  const router = useRouter();
  const { selectedSeats, addSeat, removeSeat, clearSeats } = useShowtime(); // Get context values for seat management

  const formatShowtime = (datetime) => {
    const date = new Date(datetime);
    return date.toLocaleString("en-US", {
      weekday: "short",
      month: "short",
      day: "numeric",
      hour: "numeric",
      minute: "2-digit",
      hour12: true,
    });
  };

  const initialSeats = {
    A: [
      "unavailable",
      "unavailable",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "unavailable",
      "unavailable",
    ],
    B: [
      "unavailable",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "unavailable",
    ],
    C: [
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
    ],
    D: [
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
    ],
    E: [
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
    ],
    F: [
      "unavailable",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "available",
      "unavailable",
    ],
  };

  const [seats, setSeats] = useState(initialSeats);
  const [localSelectedSeats, setLocalSelectedSeats] = useState(selectedSeats); // Add local state for selected seats

  // Toggle seat selection
  const handleSeatClick = (row, seatIndex) => {
    setSeats((prevSeats) => ({
      ...prevSeats,
      [row]: prevSeats[row].map((seat, index) => {
        if (index === seatIndex) {
          if (seat === "available") {
            setLocalSelectedSeats((prevSeats) => [
              ...prevSeats,
              `${row}${seatIndex + 1}`,
            ]); // Add to local selected seats
            return "selected"; // Select if available
          }
          if (seat === "selected") {
            setLocalSelectedSeats((prevSeats) =>
              prevSeats.filter((id) => id !== `${row}${seatIndex + 1}`)
            ); // Remove from local selected seats
            return "available"; // Deselect if selected
          }
        }
        return seat; // Leave unchanged if unavailable
      }),
    }));
  };

  const handleProceed = () => {
    // After selection, sync with the context
    localSelectedSeats.forEach((seatId) => addSeat(seatId)); // Add all selected seats to context
    router.push("/pay");
  };

  useEffect(() => {
    // Clear selected seats when the modal closes
    if (!showtime) {
      clearSeats();
    }
  }, [showtime, clearSeats]);

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
        <h2 className="text-2xl font-bold mb-4 text-black">
          Select Seats for {formatShowtime(showtime.startTime)}
        </h2>

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
                    <div
                      key={`${row}-aisle-${seatIndex}`}
                      className="w-6"
                    ></div>
                  ) : null}
                  <div
                    key={`${row}-${seatIndex}`}
                    className={`w-10 h-10 flex items-center justify-center rounded cursor-pointer text-white ${
                      seat === "available"
                        ? "bg-green-500"
                        : seat === "selected"
                        ? "bg-blue-500"
                        : "bg-gray-400 cursor-not-allowed"
                    }`}
                    onClick={() =>
                      seat !== "unavailable" && handleSeatClick(row, seatIndex)
                    }
                  >
                    {row}
                    {seatIndex + 1}
                  </div>
                </React.Fragment>
              ))}
            </div>
          ))}
        </div>
        {/* Proceed Button */}
        <div className="mt-6 text-center">
          <button
            onClick={handleProceed}
            className="px-6 py-2 bg-blue-600 text-white rounded-lg font-semibold"
          >
            Proceed
          </button>
        </div>
      </div>
    </div>
  );
};

export default SeatSelectionModal;
