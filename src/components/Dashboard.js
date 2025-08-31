import React, { useState, useEffect } from 'react';
import ReservoirSearch from './ReservoirSearch';
import ReservoirList from './ReservoirList';
import './Dashboard.css';

const Dashboard = ({ user }) => {
  const [reservoirs, setReservoirs] = useState([]);
  const [filteredReservoirs, setFilteredReservoirs] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    // Load reservoirs data from backend API
    const loadReservoirs = async () => {
      try {
        setIsLoading(true);
        
        // Fetch data from backend API
        const response = await fetch('http://localhost:8080/api/reservoirs', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        
        // Transform backend data to frontend format
        const transformedReservoirs = data.map(reservoir => ({
          id: reservoir.id,
          name: reservoir.name,
          location: `${reservoir.subCounty}, ${reservoir.county}`,
          capacity: 100, // Total capacity as percentage
          currentLevel: Math.round((reservoir.currentLevelM3 / reservoir.totalCapacityM3) * 100),
          lastUpdated: reservoir.lastUpdated ? new Date(reservoir.lastUpdated).toLocaleDateString() : 'N/A',
          coordinates: { lat: reservoir.latitude, lng: reservoir.longitude },
          estimatedRunout: reservoir.estimatedRunoutDate ? new Date(reservoir.estimatedRunoutDate).toLocaleDateString() : 'N/A',
          waterQuality: reservoir.waterQualityRating,
          managedBy: reservoir.managedBy,
          description: reservoir.description,
          contactPhone: reservoir.contactPhone,
          contactEmail: reservoir.contactEmail,
          totalCapacityM3: reservoir.totalCapacityM3,
          currentLevelM3: reservoir.currentLevelM3,
          county: reservoir.county,
          subCounty: reservoir.subCounty,
          ward: reservoir.ward
        }));
        
        setReservoirs(transformedReservoirs);
        setFilteredReservoirs(transformedReservoirs);
      } catch (err) {
        console.error('Error loading reservoirs:', err);
        setError('Failed to load reservoir data from backend. Please ensure the backend is running.');
      } finally {
        setIsLoading(false);
      }
    };

    loadReservoirs();
  }, []);

  const handleSearch = (searchTerm, location) => {
    let filtered = reservoirs;
    
    if (searchTerm) {
      filtered = filtered.filter(reservoir => 
        reservoir.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }
    
    if (location) {
      filtered = filtered.filter(reservoir => 
        reservoir.county.toLowerCase().includes(location.toLowerCase()) ||
        reservoir.subCounty.toLowerCase().includes(location.toLowerCase()) ||
        reservoir.ward.toLowerCase().includes(location.toLowerCase())
      );
    }
    
    setFilteredReservoirs(filtered);
  };

  const handleReset = () => {
    setFilteredReservoirs(reservoirs);
  };

  if (isLoading) {
    return (
      <div className="dashboard">
        <div className="dashboard-header">
          <h1>Water Reservoir Dashboard</h1>
          <p>Welcome back, {user.name}!</p>
        </div>
        <div className="loading-container">
          <div className="loading-spinner"></div>
          <p>Loading Kenyan water reservoirs...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="dashboard">
        <div className="dashboard-header">
          <h1>Water Reservoir Dashboard</h1>
          <p>Welcome back, {user.name}!</p>
        </div>
        <div className="error-container">
          <p className="error-message">{error}</p>
          <button onClick={() => window.location.reload()} className="retry-btn">
            Retry
          </button>
        </div>
      </div>
    );
  }

  const totalReservoirs = filteredReservoirs.length;
  const averageCapacity = filteredReservoirs.length > 0 
    ? Math.round(filteredReservoirs.reduce((sum, r) => sum + r.currentLevel, 0) / filteredReservoirs.length)
    : 0;
  const criticalReservoirs = filteredReservoirs.filter(r => r.currentLevel < 20).length;

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h1>Water Reservoir Dashboard</h1>
        <p>Welcome back, {user.name}!</p>
      </div>
      
      <div className="stats-container">
        <div className="stat-card">
          <h3>Total Reservoirs</h3>
          <p className="stat-number">{totalReservoirs}</p>
        </div>
        <div className="stat-card">
          <h3>Average Capacity</h3>
          <p className="stat-number">{averageCapacity}%</p>
        </div>
        <div className="stat-card">
          <h3>Critical Levels</h3>
          <p className="stat-number">{criticalReservoirs}</p>
        </div>
      </div>

      <ReservoirSearch onSearch={handleSearch} onReset={handleReset} />
      <ReservoirList reservoirs={filteredReservoirs} />
    </div>
  );
};

export default Dashboard;
