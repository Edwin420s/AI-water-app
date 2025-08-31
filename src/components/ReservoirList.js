import React from 'react';
import ReservoirCard from './ReservoirCard';
import './ReservoirList.css';

const ReservoirList = ({ reservoirs, onRefresh }) => {
  if (reservoirs.length === 0) {
    return (
      <div className="reservoir-list">
        <div className="no-results">
          <h3>No reservoirs found</h3>
          <p>Try adjusting your search criteria or browse all available reservoirs.</p>
          <button className="btn btn-primary" onClick={onRefresh}>
            Refresh Data
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="reservoir-list">
      <div className="list-header">
        <h2 className="section-title">Available Reservoirs</h2>
        <p>Click on a reservoir to view detailed information</p>
      </div>
      
      <div className="reservoir-grid">
        {reservoirs.map(reservoir => (
          <ReservoirCard 
            key={reservoir.id} 
            reservoir={reservoir} 
          />
        ))}
      </div>
    </div>
  );
};

export default ReservoirList;
