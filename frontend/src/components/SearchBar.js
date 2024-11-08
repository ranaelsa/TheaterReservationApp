const SearchBar = ({ searchQuery, setSearchQuery }) => {
    return (
      <div className="flex w-full">
        <input
          type="text"
          placeholder="Search for movies..."
          className="w-full py-2 px-4 rounded-l-m text-black rounded-r-none rounded-l-md"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
        <button className="bg-[#854d0e] hover:bg-[#a16207] text-white px-6 py-2 rounded-r-md">
          Search
        </button>
      </div>



    );
  };
  
  export default SearchBar;
  