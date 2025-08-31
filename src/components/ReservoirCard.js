import React, { useState } from 'react';
import './ReservoirCard.css';

const ReservoirCard = ({ reservoir }) => {
  const [isExpanded, setIsExpanded] = useState(false);

  const getStatusColor = (level) => {
    if (level >= 70) return 'status-good';
    if (level >= 40) return 'status-warning';
    return 'status-critical';
  };

  const getStatusText = (level) => {
    if (level >= 70) return 'Good';
    if (level >= 40) return 'Warning';
    return 'Critical';
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  const calculateDaysUntilRunout = (runoutDate) => {
    const today = new Date();
    const runout = new Date(runoutDate);
    const diffTime = runout - today;
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays;
  };

  const daysUntilRunout = calculateDaysUntilRunout(reservoir.estimatedRunout);

  return (
    <div className="reservoir-card" onClick={() => setIsExpanded(!isExpanded)}>
      <div className="reservoir-header">
        <h3>{reservoir.name}</h3>
        <p>{reservoir.location}</p>
      </div>
      
      <div className="reservoir-body">
        <div className="capacity-section">
          <div className="capacity-info">
            <span className="capacity-label">Current Level:</span>
            <span className="capacity-value">{reservoir.currentLevel}%</span>
          </div>
          
          <div className="capacity-bar">
            <div 
              className="capacity-fill" 
              style={{ width: `${reservoir.currentLevel}%` }}
            ></div>
          </div>
          
          <div className="capacity-details">
            <span>Max Capacity: {reservoir.capacity}%</span>
          </div>
        </div>

        <div className="status-section">
          <div className="status-item">
            <span className={`status-indicator ${getStatusColor(reservoir.currentLevel)}`}></span>
            <span>Status: {getStatusText(reservoir.currentLevel)}</span>
          </div>
          
          <div className="status-item">
            <span>Last Updated: {formatDate(reservoir.lastUpdated)}</span>
          </div>
        </div>

        <div className="runout-section">
          <h4>Estimated Runout Date</h4>
          <div className="runout-date">
            <strong>{formatDate(reservoir.estimatedRunout)}</strong>
          </div>
          <div className="runout-warning">
            {daysUntilRunout > 0 ? (
              <span className="warning-text">
                ⚠️ Will run out in approximately {daysUntilRunout} days
              </span>
            ) : (
              <span className="critical-text">
                🚨 Reservoir has run out!
              </span>
            )}
          </div>
        </div>

        {isExpanded && (
          <div className="reservoir-details">
            <div className="detail-item">
              <span className="detail-label">County:</span>
              <span className="detail-value">{reservoir.county}</span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Sub-County:</span>
              <span className="detail-value">{reservoir.subCounty}</span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Ward:</span>
              <span className="detail-value">{reservoir.ward}</span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Coordinates:</span>
              <span className="detail-value">
                {reservoir.coordinates.lat.toFixed(4)}, {reservoir.coordinates.lng.toFixed(4)}
              </span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Total Capacity:</span>
              <span className="detail-value">{reservoir.totalCapacityM3?.toLocaleString()} m³</span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Current Level:</span>
              <span className="detail-value">{reservoir.currentLevelM3?.toLocaleString()} m³</span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Water Quality:</span>
              <span className={`detail-value quality-${reservoir.waterQuality?.toLowerCase()}`}>
                {reservoir.waterQuality}
              </span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Managed By:</span>
              <span className="detail-value">{reservoir.managedBy}</span>
            </div>
            {reservoir.description && (
              <div className="detail-item">
                <span className="detail-label">Description:</span>
                <span className="detail-value">{reservoir.description}</span>
              </div>
            )}
            <div className="detail-item">
              <span className="detail-label">Contact Phone:</span>
              <span className="detail-value">{reservoir.contactPhone}</span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Contact Email:</span>
              <span className="detail-value">{reservoir.contactEmail}</span>
            </div>
          </div>
        )}

        <div className="card-footer">
          <button 
            className="btn btn-secondary expand-btn"
            onClick={(e) => {
              e.stopPropagation();
              setIsExpanded(!isExpanded);
            }}
          >
            {isExpanded ? 'Show Less' : 'Show More'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default ReservoirCard;
