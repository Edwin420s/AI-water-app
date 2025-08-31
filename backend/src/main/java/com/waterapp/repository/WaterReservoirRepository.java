package com.waterapp.repository;

import com.waterapp.entity.WaterReservoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface WaterReservoirRepository extends JpaRepository<WaterReservoir, Long> {
    
    /**
     * Find reservoirs by county
     * @param county county name
     * @return List of reservoirs in the specified county
     */
    List<WaterReservoir> findByCountyIgnoreCase(String county);
    
    /**
     * Find reservoirs by sub-county
     * @param subCounty sub-county name
     * @return List of reservoirs in the specified sub-county
     */
    List<WaterReservoir> findBySubCountyIgnoreCase(String subCounty);
    
    /**
     * Find reservoirs by ward
     * @param ward ward name
     * @return List of reservoirs in the specified ward
     */
    List<WaterReservoir> findByWardIgnoreCase(String ward);
    
    /**
     * Find reservoirs by name containing the search term
     * @param name reservoir name or part of it
     * @return List of reservoirs matching the name
     */
    List<WaterReservoir> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find active reservoirs
     * @return List of active reservoirs
     */
    List<WaterReservoir> findByIsActiveTrue();
    
    /**
     * Find reservoirs by status (GOOD, WARNING, CRITICAL)
     * @param status reservoir status
     * @return List of reservoirs with the specified status
     */
    @Query("SELECT r FROM WaterReservoir r WHERE " +
           "CASE " +
           "WHEN r.currentLevelPercentage >= 70 THEN 'GOOD' " +
           "WHEN r.currentLevelPercentage >= 40 THEN 'WARNING' " +
           "ELSE 'CRITICAL' " +
           "END = :status")
    List<WaterReservoir> findByStatus(@Param("status") String status);
    
    /**
     * Find reservoirs within a geographic radius (in kilometers)
     * @param latitude center latitude
     * @param longitude center longitude
     * @param radiusKm radius in kilometers
     * @return List of reservoirs within the specified radius
     */
    @Query("SELECT r FROM WaterReservoir r WHERE " +
           "(6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * " +
           "cos(radians(r.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(r.latitude)))) <= :radiusKm")
    List<WaterReservoir> findReservoirsWithinRadius(
        @Param("latitude") BigDecimal latitude,
        @Param("longitude") BigDecimal longitude,
        @Param("radiusKm") double radiusKm
    );
    
    /**
     * Search reservoirs by multiple criteria
     * @param searchTerm search term for name, county, sub-county, or ward
     * @return List of reservoirs matching the search criteria
     */
    @Query("SELECT r FROM WaterReservoir r WHERE " +
           "LOWER(r.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(r.county) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(r.subCounty) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(r.ward) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<WaterReservoir> searchReservoirs(@Param("searchTerm") String searchTerm);
    
    /**
     * Find reservoirs with critical water levels (below 40%)
     * @return List of reservoirs with critical water levels
     */
    @Query("SELECT r FROM WaterReservoir r WHERE r.currentLevelPercentage < 40 AND r.isActive = true")
    List<WaterReservoir> findCriticalReservoirs();
    
    /**
     * Find reservoirs that are estimated to run out soon (within specified days)
     * @param days number of days to check
     * @return List of reservoirs estimated to run out within the specified days
     */
    @Query("SELECT r FROM WaterReservoir r WHERE r.estimatedRunoutDate IS NOT NULL AND r.isActive = true")
    List<WaterReservoir> findReservoirsRunningOutSoon(@Param("days") int days);
}
