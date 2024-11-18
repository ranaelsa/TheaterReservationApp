import { useShowtime } from "../context/ShowtimeContext";
import { useState, useEffect } from "react";
import SeatSelectionModal from "./SeatSelectionModal";
import useApi from "../hooks/useApi";

const ShowtimeWindow = () => {
  const {
    showWindow,
    selectedMovie,
    closeShowWindow,
    selectedTheater,
    theaters,
    onSelectTheater,
  } = useShowtime();

  const [showSeatSelection, setShowSeatSelection] = useState(false);
  const [selectedShowtime, setSelectedShowtime] = useState(null);
  const [currentShowtimes, setCurrentShowtimes] = useState([]);
  
  const { callApi, data: showtimes, loading, error } = useApi(
    `http://localhost:8080/api/showtimes/movieandtheater/${selectedMovie?.id}/${selectedTheater?.id}`
  );

  useEffect(() => {
    const fetchShowtimes = async () => {
      if (selectedMovie?.id && selectedTheater?.id) {
        try {
          console.log("Fetching showtimes for:", selectedMovie.id, selectedTheater.id);
          await callApi();  // Make sure to await the API call
        } catch (error) {
          console.error("Failed to fetch showtimes", error);
        }
      }
    };

    if (selectedMovie?.id && selectedTheater?.id) {
      fetchShowtimes();
    }

    // Cleanup function to avoid state updates after unmount
    return () => {
      setCurrentShowtimes([]); // Optionally clear the showtimes when the component unmounts or when dependencies change
    };
  }, [selectedMovie?.id, selectedTheater?.id]); // Only depend on selectedMovie and selectedTheater

  // Only set the showtimes once the fetched data is available
  useEffect(() => {
    if (showtimes && showtimes !== currentShowtimes) {
      console.log("Fetched showtimes:", showtimes);
      setCurrentShowtimes(showtimes);
    }
  }, [showtimes]);  // Only run when showtimes changes

  const handleTheaterChange = (e) => {
    const selectedName = e.target.value;
    const selected = theaters.find((theater) => theater.name === selectedName);
    if (selected) {
      onSelectTheater(selected);
      console.log("Selected theater:", selected);
    }
  };

  const handleShowtimeClick = (showtime) => {
    setSelectedShowtime(showtime);
    setShowSeatSelection(true);
  };

  const formatShowtime = (datetime) => {
    const date = new Date(datetime);
    return date.toLocaleString('en-US', {
      weekday: 'short',
      month: 'short',
      day: 'numeric',
      hour: 'numeric',
      minute: '2-digit',
      hour12: true
    });
  };

  if (!showWindow || !selectedMovie) return null;

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
            value={selectedTheater?.name || ""}
            onChange={handleTheaterChange}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 text-gray-700"
          >
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
          {loading && <p>Loading showtimes...</p>}
          {error && <p className="text-red-500">{error}</p>}
          {!loading && !error && currentShowtimes.length > 0 ? (
            <ul>
              {currentShowtimes.map((showtime) => (
                <li key={showtime.id} className="text-lg mb-2">
                  <button
                    onClick={() => handleShowtimeClick(showtime)}
                    className="text-blue-500 underline"
                  >
                    {formatShowtime(showtime.startTime)}
                  </button>
                </li>
              ))}
            </ul>
          ) : (
            !loading && <p className="text-black">No showtimes available.</p>
          )}
        </div>

        {showSeatSelection && (
          <SeatSelectionModal
            showtime={selectedShowtime}
            onClose={() => setShowSeatSelection(false)}
          />
        )}
      </div>
    </div>
  );
};

export default ShowtimeWindow;
