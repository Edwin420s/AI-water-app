package com.waterapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "water_reservoirs")
public class WaterReservoir {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Reservoir name is required")
    @Size(min = 2, max = 200, message = "Reservoir name must be between 2 and 200 characters")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank(message = "County is required")
    @Column(name = "county", nullable = false)
    private String county;
    
    @NotBlank(message = "Sub-county is required")
    @Column(name = "sub_county", nullable = false)
    private String subCounty;
    
    @NotBlank(message = "Location/Ward is required")
    @Column(name = "ward", nullable = false)
    private String ward;
    
    @Column(name = "specific_location")
    private String specificLocation;
    
    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-4.5", message = "Latitude must be valid for Kenya")
    @DecimalMax(value = "4.5", message = "Latitude must be valid for Kenya")
    @Column(name = "latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;
    
    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "33.0", message = "Longitude must be valid for Kenya")
    @DecimalMax(value = "42.0", message = "Longitude must be valid for Kenya")
    @Column(name = "longitude", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;
    
    @NotNull(message = "Total capacity is required")
    @DecimalMin(value = "0.0", message = "Total capacity must be positive")
    @Column(name = "total_capacity_m3", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalCapacityM3;
    
    @NotNull(message = "Current level is required")
    @DecimalMin(value = "0.0", message = "Current level must be positive")
    @Column(name = "current_level_m3", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentLevelM3;
    
    @NotNull(message = "Current level percentage is required")
    @DecimalMin(value = "0.0", message = "Current level percentage must be between 0 and 100")
    @DecimalMax(value = "100.0", message = "Current level percentage must be between 0 and 100")
    @Column(name = "current_level_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal currentLevelPercentage;
    
    @Column(name = "water_quality_rating")
    @Enumerated(EnumType.STRING)
    private WaterQuality waterQuality;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Column(name = "estimated_runout_date")
    private LocalDateTime estimatedRunoutDate;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "description", length = 1000)
    private String description;
    
    @Column(name = "managed_by")
    private String managedBy;
    
    @Column(name = "contact_phone")
    private String contactPhone;
    
    @Column(name = "contact_email")
    private String contactEmail;
    
    // Default constructor
    public WaterReservoir() {
        this.isActive = true;
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Constructor with required fields
    public WaterReservoir(String name, String county, String subCounty, String ward, 
                         BigDecimal latitude, BigDecimal longitude, 
                         BigDecimal totalCapacityM3, BigDecimal currentLevelM3) {
        this();
        this.name = name;
        this.county = county;
        this.subCounty = subCounty;
        this.ward = ward;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalCapacityM3 = totalCapacityM3;
        this.currentLevelM3 = currentLevelM3;
        this.currentLevelPercentage = currentLevelM3.divide(totalCapacityM3, 2, BigDecimal.ROUND_HALF_UP)
                                                .multiply(new BigDecimal("100"));
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCounty() {
        return county;
    }
    
    public void setCounty(String county) {
        this.county = county;
    }
    
    public String getSubCounty() {
        return subCounty;
    }
    
    public void setSubCounty(String subCounty) {
        this.subCounty = subCounty;
    }
    
    public String getWard() {
        return ward;
    }
    
    public void setWard(String ward) {
        this.ward = ward;
    }
    
    public String getSpecificLocation() {
        return specificLocation;
    }
    
    public void setSpecificLocation(String specificLocation) {
        this.specificLocation = specificLocation;
    }
    
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public BigDecimal getTotalCapacityM3() {
        return totalCapacityM3;
    }
    
    public void setTotalCapacityM3(BigDecimal totalCapacityM3) {
        this.totalCapacityM3 = totalCapacityM3;
    }
    
    public BigDecimal getCurrentLevelM3() {
        return currentLevelM3;
    }
    
    public void setCurrentLevelM3(BigDecimal currentLevelM3) {
        this.currentLevelM3 = currentLevelM3;
        if (this.totalCapacityM3 != null && this.totalCapacityM3.compareTo(BigDecimal.ZERO) > 0) {
            this.currentLevelPercentage = currentLevelM3.divide(totalCapacityM3, 2, BigDecimal.ROUND_HALF_UP)
                                                    .multiply(new BigDecimal("100"));
        }
    }
    
    public BigDecimal getCurrentLevelPercentage() {
        return currentLevelPercentage;
    }
    
    public void setCurrentLevelPercentage(BigDecimal currentLevelPercentage) {
        this.currentLevelPercentage = currentLevelPercentage;
    }
    
    public WaterQuality getWaterQuality() {
        return waterQuality;
    }
    
    public void setWaterQuality(WaterQuality waterQuality) {
        this.waterQuality = waterQuality;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public LocalDateTime getEstimatedRunoutDate() {
        return estimatedRunoutDate;
    }
    
    public void setEstimatedRunoutDate(LocalDateTime estimatedRunoutDate) {
        this.estimatedRunoutDate = estimatedRunoutDate;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getManagedBy() {
        return managedBy;
    }
    
    public void setManagedBy(String managedBy) {
        this.managedBy = managedBy;
    }
    
    public String getContactPhone() {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public String getContactEmail() {
        return contactEmail;
    }
    
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    
    // Enum for water quality
    public enum WaterQuality {
        EXCELLENT, GOOD, FAIR, POOR, CRITICAL
    }
    
    // Helper method to get status based on current level
    public String getStatus() {
        if (currentLevelPercentage == null) return "UNKNOWN";
        
        if (currentLevelPercentage.compareTo(new BigDecimal("70")) >= 0) {
            return "GOOD";
        } else if (currentLevelPercentage.compareTo(new BigDecimal("40")) >= 0) {
            return "WARNING";
        } else {
            return "CRITICAL";
        }
    }
}
