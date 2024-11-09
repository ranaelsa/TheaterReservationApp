import { useShowtime } from "../context/ShowtimeContext"; // Import the custom hook for context
import { useState } from "react";
import SeatSelectionModal from "./SeatSelectionModal";

const ShowtimeWindow = () => {
  const {
    showWindow,
    selectedMovie,
    closeShowWindow,
    selectedTheater,
    theaters,
    onSelectTheater,
  } = useShowtime(); // Access 'theaters' from context
  const [showSeatSelection, setShowSeatSelection] = useState(false); // State to control the seat selection modal
  const [selectedShowtime, setSelectedShowtime] = useState(null); // State for the selected showtime

  // Early return if the window should not be shown
  if (!showWindow || !selectedMovie) return null;

  // Handle theater selection in the dropdown
  const handleTheaterChange = (e) => {
    const selected = e.target.value;
    onSelectTheater(selected); // Use onSelectTheater to update the selected theater
  };

  // Handle showtime click to open seat selection modal
  const handleShowtimeClick = (time) => {
    setSelectedShowtime(time);
    setShowSeatSelection(true); // Show the seat selection modal
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[9999]">
      <div className="bg-white p-6 rounded-lg shadow-lg max-w-3xl w-full h-3/4 overflow-auto">
        <button
          onClick={closeShowWindow}
          className="text-gray-500 float-right text-xl font-bold mb-4"
          aria-label="Close"
        >
          &times;
        </button>
        <h2 className="text-2xl font-bold mb-4 text-black">
          Showtimes for {selectedMovie.title}
        </h2>

        <div className="mb-6">
          <label className="block text-lg font-semibold mb-2 text-black">
            Select Theater:
          </label>
          <select
            value={selectedTheater || ""}
            onChange={handleTheaterChange} // Use the handleTheaterChange function to update selected theater
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-gray-700"
          >
            {/* Use the theaters from context */}
            {theaters.map((theater) => (
              <option key={theater.id} value={theater.name}>
                {theater.name}
              </option>
            ))}
          </select>
        </div>

        <div>
          <h3 className="text-xl font-bold mb-4 text-black">
            Available Showtimes:
          </h3>
          {/* Replace this with real showtimes */}
          <ul>
            {["10:00 AM", "1:00 PM", "5:30 PM", "8:45 PM"].map(
              (time, index) => (
                <li key={index} className="text-lg mb-2">
                  <button
                    onClick={() => handleShowtimeClick(time)}
                    className="text-blue-500 underline"
                  >
                    {time}
                  </button>
                </li>
              )
            )}
          </ul>
        </div>
        {/* Seat Selection Modal */}
        {showSeatSelection && (
          <SeatSelectionModal
            showtime={selectedShowtime}
            onClose={() => setShowSeatSelection(false)} // Close the seat selection modal
          />
        )}
      </div>
    </div>
  );
};

export default ShowtimeWindow;
