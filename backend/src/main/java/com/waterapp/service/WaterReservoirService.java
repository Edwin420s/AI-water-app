package com.waterapp.service;

import com.waterapp.dto.WaterReservoirDto;
import com.waterapp.entity.WaterReservoir;
import com.waterapp.repository.WaterReservoirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WaterReservoirService {
    
    @Autowired
    private WaterReservoirRepository waterReservoirRepository;
    
    /**
     * Get all active reservoirs
     * @return List of all active reservoirs
     */
    public List<WaterReservoirDto> getAllActiveReservoirs() {
        List<WaterReservoir> reservoirs = waterReservoirRepository.findByIsActiveTrue();
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get reservoir by ID
     * @param id reservoir ID
     * @return Optional containing reservoir if found
     */
    public Optional<WaterReservoirDto> getReservoirById(Long id) {
        Optional<WaterReservoir> reservoir = waterReservoirRepository.findById(id);
        return reservoir.map(WaterReservoirDto::new);
    }
    
    /**
     * Search reservoirs by location (county, sub-county, ward, or name)
     * @param searchTerm search term
     * @return List of matching reservoirs
     */
    public List<WaterReservoirDto> searchReservoirsByLocation(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllActiveReservoirs();
        }
        
        List<WaterReservoir> reservoirs = waterReservoirRepository.searchReservoirs(searchTerm.trim());
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get reservoirs by county
     * @param county county name
     * @return List of reservoirs in the specified county
     */
    public List<WaterReservoirDto> getReservoirsByCounty(String county) {
        List<WaterReservoir> reservoirs = waterReservoirRepository.findByCountyIgnoreCase(county);
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get reservoirs by sub-county
     * @param subCounty sub-county name
     * @return List of reservoirs in the specified sub-county
     */
    public List<WaterReservoirDto> getReservoirsBySubCounty(String subCounty) {
        List<WaterReservoir> reservoirs = waterReservoirRepository.findBySubCountyIgnoreCase(subCounty);
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get reservoirs by ward
     * @param ward ward name
     * @return List of reservoirs in the specified ward
     */
    public List<WaterReservoirDto> getReservoirsByWard(String ward) {
        List<WaterReservoir> reservoirs = waterReservoirRepository.findByWardIgnoreCase(ward);
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get reservoirs by name
     * @param name reservoir name or part of it
     * @return List of reservoirs matching the name
     */
    public List<WaterReservoirDto> getReservoirsByName(String name) {
        List<WaterReservoir> reservoirs = waterReservoirRepository.findByNameContainingIgnoreCase(name);
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get reservoirs by status
     * @param status GOOD, WARNING, or CRITICAL
     * @return List of reservoirs with the specified status
     */
    public List<WaterReservoirDto> getReservoirsByStatus(String status) {
        List<WaterReservoir> reservoirs = waterReservoirRepository.findByStatus(status.toUpperCase());
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get reservoirs within a geographic radius
     * @param latitude center latitude
     * @param longitude center longitude
     * @param radiusKm radius in kilometers
     * @return List of reservoirs within the specified radius
     */
    public List<WaterReservoirDto> getReservoirsWithinRadius(BigDecimal latitude, BigDecimal longitude, double radiusKm) {
        List<WaterReservoir> reservoirs = waterReservoirRepository.findReservoirsWithinRadius(latitude, longitude, radiusKm);
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get critical reservoirs (water level below 40%)
     * @return List of critical reservoirs
     */
    public List<WaterReservoirDto> getCriticalReservoirs() {
        List<WaterReservoir> reservoirs = waterReservoirRepository.findCriticalReservoirs();
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Get reservoirs that are estimated to run out soon
     * @param days number of days to check
     * @return List of reservoirs running out soon
     */
    public List<WaterReservoirDto> getReservoirsRunningOutSoon(int days) {
        List<WaterReservoir> reservoirs = waterReservoirRepository.findReservoirsRunningOutSoon(days);
        return reservoirs.stream()
                .map(WaterReservoirDto::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Update reservoir water level
     * @param id reservoir ID
     * @param currentLevelM3 new current water level
     * @return Updated reservoir DTO
     */
    public Optional<WaterReservoirDto> updateWaterLevel(Long id, BigDecimal currentLevelM3) {
        Optional<WaterReservoir> reservoirOpt = waterReservoirRepository.findById(id);
        
        if (reservoirOpt.isPresent()) {
            WaterReservoir reservoir = reservoirOpt.get();
            reservoir.setCurrentLevelM3(currentLevelM3);
            reservoir.setLastUpdated(LocalDateTime.now());
            
            WaterReservoir updatedReservoir = waterReservoirRepository.save(reservoir);
            return Optional.of(new WaterReservoirDto(updatedReservoir));
        }
        
        return Optional.empty();
    }
    
    /**
     * Get reservoir statistics
     * @return Statistics about reservoirs
     */
    public ReservoirStatistics getReservoirStatistics() {
        List<WaterReservoir> allReservoirs = waterReservoirRepository.findByIsActiveTrue();
        
        long totalReservoirs = allReservoirs.size();
        long goodReservoirs = allReservoirs.stream()
                .filter(r -> r.getStatus().equals("GOOD"))
                .count();
        long warningReservoirs = allReservoirs.stream()
                .filter(r -> r.getStatus().equals("WARNING"))
                .count();
        long criticalReservoirs = allReservoirs.stream()
                .filter(r -> r.getStatus().equals("CRITICAL"))
                .count();
        
        return new ReservoirStatistics(totalReservoirs, goodReservoirs, warningReservoirs, criticalReservoirs);
    }
    
    /**
     * Inner class for reservoir statistics
     */
    public static class ReservoirStatistics {
        private final long totalReservoirs;
        private final long goodReservoirs;
        private final long warningReservoirs;
        private final long criticalReservoirs;
        
        public ReservoirStatistics(long totalReservoirs, long goodReservoirs, 
                                 long warningReservoirs, long criticalReservoirs) {
            this.totalReservoirs = totalReservoirs;
            this.goodReservoirs = goodReservoirs;
            this.warningReservoirs = warningReservoirs;
            this.criticalReservoirs = criticalReservoirs;
        }
        
        // Getters
        public long getTotalReservoirs() { return totalReservoirs; }
        public long getGoodReservoirs() { return goodReservoirs; }
        public long getWarningReservoirs() { return warningReservoirs; }
        public long getCriticalReservoirs() { return criticalReservoirs; }
    }
}
