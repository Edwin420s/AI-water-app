package com.waterapp.dto;

import com.waterapp.entity.WaterReservoir;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WaterReservoirDto {
    private Long id;
    private String name;
    private String county;
    private String subCounty;
    private String ward;
    private String specificLocation;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal totalCapacityM3;
    private BigDecimal currentLevelM3;
    private BigDecimal currentLevelPercentage;
    private WaterReservoir.WaterQuality waterQuality;
    private LocalDateTime lastUpdated;
    private LocalDateTime estimatedRunoutDate;
    private Boolean isActive;
    private String description;
    private String managedBy;
    private String contactPhone;
    private String contactEmail;
    private String status;
    
    // Default constructor
    public WaterReservoirDto() {}
    
    // Constructor from WaterReservoir entity
    public WaterReservoirDto(WaterReservoir reservoir) {
        this.id = reservoir.getId();
        this.name = reservoir.getName();
        this.county = reservoir.getCounty();
        this.subCounty = reservoir.getSubCounty();
        this.ward = reservoir.getWard();
        this.specificLocation = reservoir.getSpecificLocation();
        this.latitude = reservoir.getLatitude();
        this.longitude = reservoir.getLongitude();
        this.totalCapacityM3 = reservoir.getTotalCapacityM3();
        this.currentLevelM3 = reservoir.getCurrentLevelM3();
        this.currentLevelPercentage = reservoir.getCurrentLevelPercentage();
        this.waterQuality = reservoir.getWaterQuality();
        this.lastUpdated = reservoir.getLastUpdated();
        this.estimatedRunoutDate = reservoir.getEstimatedRunoutDate();
        this.isActive = reservoir.getIsActive();
        this.description = reservoir.getDescription();
        this.managedBy = reservoir.getManagedBy();
        this.contactPhone = reservoir.getContactPhone();
        this.contactEmail = reservoir.getContactEmail();
        this.status = reservoir.getStatus();
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
    }
    
    public BigDecimal getCurrentLevelPercentage() {
        return currentLevelPercentage;
    }
    
    public void setCurrentLevelPercentage(BigDecimal currentLevelPercentage) {
        this.currentLevelPercentage = currentLevelPercentage;
    }
    
    public WaterReservoir.WaterQuality getWaterQuality() {
        return waterQuality;
    }
    
    public void setWaterQuality(WaterReservoir.WaterQuality waterQuality) {
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
