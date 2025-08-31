import React, { useState } from 'react';
import './ReservoirSearch.css';

const ReservoirSearch = ({ onSearch, onReset, totalReservoirs, filteredCount }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [location, setLocation] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onSearch(searchTerm, location);
  };

  const handleReset = () => {
    setSearchTerm('');
    setLocation('');
    onReset();
  };

  return (
    <div className="search-container">
      <div className="container">
        <div className="search-header">
          <h2 className="section-title">Find Reservoirs</h2>
          <p>Search for water reservoirs by name or location</p>
        </div>

        <form onSubmit={handleSubmit} className="search-form">
          <div className="search-inputs">
            <div className="form-group">
              <label htmlFor="searchTerm">Reservoir Name</label>
              <input
                type="text"
                id="searchTerm"
                className="form-control"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                placeholder="Enter reservoir name..."
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="location">Location</label>
              <input
                type="text"
                id="location"
                className="form-control"
                value={location}
                onChange={(e) => setLocation(e.target.value)}
                placeholder="Enter city or area..."
              />
            </div>
          </div>

          <div className="search-actions">
            <button type="submit" className="btn btn-primary">
              🔍 Search Reservoirs
            </button>
            <button 
              type="button" 
              className="btn btn-secondary"
              onClick={handleReset}
            >
              Reset Filters
            </button>
          </div>
        </form>

        <div className="search-results-info">
          <p>
            Showing <strong>{filteredCount}</strong> of <strong>{totalReservoirs}</strong> reservoirs
          </p>
        </div>
      </div>
    </div>
  );
};

export default ReservoirSearch;
