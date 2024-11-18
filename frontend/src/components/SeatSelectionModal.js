import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { useShowtime } from "../context/ShowtimeContext"; // Import the custom hook
import useApi from "@/hooks/useApi";

const SeatSelectionModal = ({ onClose }) => {
  const router = useRouter();
  const { selectedShowtime, selectedSeats, addSeat, removeSeat, clearSeats } = useShowtime(); // Get context values for seat management

  const { callApi, data: seatData, loading, error } = useApi(
    `http://localhost:8080/api/seat-availability/available/${selectedShowtime.id}`,
    "GET"
  ); // Fetch seat data from the API

  const [seats, setSeats] = useState({});

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

  const transformSeatData = (seatData) => {
    const seatMap = {};
  
    // Sort seatData by seatId to ensure proper ordering
    const sortedSeats = seatData.sort((a, b) => a.id.seatId - b.id.seatId);
  
    // Dynamically calculate row and column for each seat
    sortedSeats.forEach(({ id: { seatId }, isAvailable }) => {
      const totalSeatsPerRow = 11; // Fixed number of seats per row
      const totalRows = 6; // Fixed number of rows (A to F)
  
      // Calculate the row index (0 to 5) and column (0 to 10)
      const rowIndex = Math.floor((seatId - 1) / totalSeatsPerRow) % totalRows;
      const row = String.fromCharCode(65 + rowIndex); // Convert rowIndex to letter (A-F)
      const col = (seatId - 1) % totalSeatsPerRow; // Determine column
  
      if (!seatMap[row]) seatMap[row] = Array(totalSeatsPerRow).fill(null); // Initialize row if not present
  
      // Assign seat details
      seatMap[row][col] = {
        status: isAvailable ? "available" : "unavailable",
        seatId, // Store original seatId
      };
    });
  
    return seatMap;
  };
  
  
  

  const handleSeatClick = (row, seatIndex) => {
    const seatInfo = seats[row][seatIndex];
    if (seatInfo.status === "unavailable") return;
  
    setSeats((prevSeats) => ({
      ...prevSeats,
      [row]: prevSeats[row].map((seat, index) => {
        if (index === seatIndex) {
          return {
            ...seat,
            status: seat.status === "available" ? "selected" : "available",
          };
        }
        return seat;
      }),
    }));
  };

  useEffect(() => {
    if (selectedShowtime?.id) {
      callApi();
    }
  }, [selectedShowtime?.id]);

  useEffect(() => {
    if (seatData) {
      setSeats(transformSeatData(seatData));
    }
  }, [seatData]);

  useEffect(() => {
    const updatedSelectedSeats = [];
    Object.keys(seats).forEach((row) => {
      seats[row].forEach((seat) => {
        if (seat.status === "selected") {
          updatedSelectedSeats.push(seat.seatId); // Use the stored seatId
        }
      });
    });
  
    selectedSeats.forEach((seatId) => {
      if (!updatedSelectedSeats.includes(seatId)) {
        removeSeat(seatId); // Remove deselected seat from context
      }
    });
  
    updatedSelectedSeats.forEach((seatId) => {
      if (!selectedSeats.includes(seatId)) {
        addSeat(seatId); // Add selected seat to context
      }
    });
  }, [seats, selectedSeats, addSeat, removeSeat]);
  

const handleProceed = () => {
  clearSeats(); // Clear all previously selected seats from the context

  Object.keys(seats).forEach((row) => {
    seats[row].forEach((seat) => {
      if (seat.status === "selected" && !selectedSeats.includes(seat.seatId)) {
        addSeat(seat.seatId);
      }
    });
  });

  router.push("/pay");
};


  useEffect(() => {
    if (!selectedShowtime) {
      clearSeats();
    }
  }, [selectedShowtime, clearSeats]);

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
          Select Seats for {formatShowtime(selectedShowtime.startTime)}
        </h2>

        {/* Show loading indicator */}
        {loading ? (
          <div className="flex justify-center items-center h-40">
            <div className="loader w-10 h-10 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
          </div>
        ) : (
          <>
            {/* Stage Indicator */}
            <div className="text-center mb-4">
              <div className="text-gray-600 text-sm mb-1">STAGE</div>
              <div className="w-full h-2 bg-gray-300 rounded-full"></div>
            </div>

            {/* Seat selection grid */}
            <div className="flex flex-col items-center space-y-2">
              {Object.keys(seats).map((row, rowIndex) => (
                <div key={rowIndex} className="flex space-x-2 justify-center">
                  <div className="text-black font-semibold mr-2">{row}</div>
                  {seats[row].map((seat, seatIndex) => (
                    <React.Fragment key={`${row}-${seatIndex}`}>
                      {seatIndex === 2 || seatIndex === 9 ? (
                        <div
                          key={`${row}-aisle-${seatIndex}`}
                          className="w-6"
                        ></div>
                      ) : null}
                      <div
                        key={`${row}-${seatIndex}`}
                        className={`w-10 h-10 flex items-center justify-center rounded cursor-pointer text-white ${
                          seat.status === "available"
                            ? "bg-green-500"
                            : seat.status === "selected"
                            ? "bg-blue-500"
                            : "bg-gray-400 cursor-not-allowed"
                        }`}
                        onClick={() =>
                          seat.status !== "unavailable" && handleSeatClick(row, seatIndex)
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
          </>
        )}
        {/* Proceed Button */}
        {!loading && (
          <div className="mt-6 text-center">
            <button
              onClick={handleProceed}
              className="px-6 py-2 bg-blue-600 text-white rounded-lg font-semibold"
            >
              Proceed
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default SeatSelectionModal;
