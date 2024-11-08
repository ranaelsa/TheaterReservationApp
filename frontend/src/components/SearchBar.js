const SearchBar = ({ searchQuery, setSearchQuery }) => {
    return (
      <div className="flex items-center justify-center mt-8 mb-8">
        <input
          type="text"
          placeholder="Search for movies..."
          className="w-1/2 py-2 px-4 border-2 border-gray-300 rounded-l-md"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
        <button className="bg-blue-500 hover:bg-blue-600 text-white px-6 py-2 rounded-r-md">
          Search
        </button>
      </div>
    );
  };
  
  export default SearchBar;
  