package com.waterapp.controller;

import com.waterapp.dto.WaterReservoirDto;
import com.waterapp.service.WaterReservoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/reservoirs")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class WaterReservoirController {
    
    @Autowired
    private WaterReservoirService waterReservoirService;
    
    /**
     * Get all active reservoirs
     * @return ResponseEntity with list of all active reservoirs
     */
    @GetMapping
    public ResponseEntity<List<WaterReservoirDto>> getAllReservoirs() {
        List<WaterReservoirDto> reservoirs = waterReservoirService.getAllActiveReservoirs();
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get reservoir by ID
     * @param id reservoir ID
     * @return ResponseEntity with reservoir data
     */
    @GetMapping("/{id}")
    public ResponseEntity<WaterReservoirDto> getReservoirById(@PathVariable Long id) {
        return waterReservoirService.getReservoirById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Search reservoirs by location (county, sub-county, ward, or name)
     * @param search search term
     * @return ResponseEntity with list of matching reservoirs
     */
    @GetMapping("/search")
    public ResponseEntity<List<WaterReservoirDto>> searchReservoirs(@RequestParam String search) {
        List<WaterReservoirDto> reservoirs = waterReservoirService.searchReservoirsByLocation(search);
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get reservoirs by county
     * @param county county name
     * @return ResponseEntity with list of reservoirs in the county
     */
    @GetMapping("/county/{county}")
    public ResponseEntity<List<WaterReservoirDto>> getReservoirsByCounty(@PathVariable String county) {
        List<WaterReservoirDto> reservoirs = waterReservoirService.getReservoirsByCounty(county);
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get reservoirs by sub-county
     * @param subCounty sub-county name
     * @return ResponseEntity with list of reservoirs in the sub-county
     */
    @GetMapping("/subcounty/{subCounty}")
    public ResponseEntity<List<WaterReservoirDto>> getReservoirsBySubCounty(@PathVariable String subCounty) {
        List<WaterReservoirDto> reservoirs = waterReservoirService.getReservoirsBySubCounty(subCounty);
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get reservoirs by ward
     * @param ward ward name
     * @return ResponseEntity with list of reservoirs in the ward
     */
    @GetMapping("/ward/{ward}")
    public ResponseEntity<List<WaterReservoirDto>> getReservoirsByWard(@PathVariable String ward) {
        List<WaterReservoirDto> reservoirs = waterReservoirService.getReservoirsByWard(ward);
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get reservoirs by name
     * @param name reservoir name
     * @return ResponseEntity with list of reservoirs matching the name
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<WaterReservoirDto>> getReservoirsByName(@PathVariable String name) {
        List<WaterReservoirDto> reservoirs = waterReservoirService.getReservoirsByName(name);
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get reservoirs by status
     * @param status GOOD, WARNING, or CRITICAL
     * @return ResponseEntity with list of reservoirs with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<WaterReservoirDto>> getReservoirsByStatus(@PathVariable String status) {
        List<WaterReservoirDto> reservoirs = waterReservoirService.getReservoirsByStatus(status);
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get reservoirs within geographic radius
     * @param latitude center latitude
     * @param longitude center longitude
     * @param radius radius in kilometers
     * @return ResponseEntity with list of reservoirs within the radius
     */
    @GetMapping("/nearby")
    public ResponseEntity<List<WaterReservoirDto>> getReservoirsNearby(
            @RequestParam BigDecimal latitude,
            @RequestParam BigDecimal longitude,
            @RequestParam(defaultValue = "10.0") double radius) {
        
        List<WaterReservoirDto> reservoirs = waterReservoirService.getReservoirsWithinRadius(latitude, longitude, radius);
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get critical reservoirs (water level below 40%)
     * @return ResponseEntity with list of critical reservoirs
     */
    @GetMapping("/critical")
    public ResponseEntity<List<WaterReservoirDto>> getCriticalReservoirs() {
        List<WaterReservoirDto> reservoirs = waterReservoirService.getCriticalReservoirs();
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get reservoirs running out soon
     * @param days number of days to check (default: 30)
     * @return ResponseEntity with list of reservoirs running out soon
     */
    @GetMapping("/running-out")
    public ResponseEntity<List<WaterReservoirDto>> getReservoirsRunningOutSoon(
            @RequestParam(defaultValue = "30") int days) {
        
        List<WaterReservoirDto> reservoirs = waterReservoirService.getReservoirsRunningOutSoon(days);
        return ResponseEntity.ok(reservoirs);
    }
    
    /**
     * Get reservoir statistics
     * @return ResponseEntity with reservoir statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<WaterReservoirService.ReservoirStatistics> getReservoirStatistics() {
        WaterReservoirService.ReservoirStatistics stats = waterReservoirService.getReservoirStatistics();
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Update reservoir water level
     * @param id reservoir ID
     * @param currentLevel new current water level in cubic meters
     * @return ResponseEntity with updated reservoir data
     */
    @PutMapping("/{id}/water-level")
    public ResponseEntity<WaterReservoirDto> updateWaterLevel(
            @PathVariable Long id,
            @RequestParam BigDecimal currentLevel) {
        
        return waterReservoirService.updateWaterLevel(id, currentLevel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Health check endpoint
     * @return ResponseEntity with health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Water Reservoir Service is running!");
    }
}
