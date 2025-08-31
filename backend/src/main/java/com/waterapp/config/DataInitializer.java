package com.waterapp.config;

import com.waterapp.entity.User;
import com.waterapp.entity.WaterReservoir;
import com.waterapp.repository.UserRepository;
import com.waterapp.repository.WaterReservoirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WaterReservoirRepository waterReservoirRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize users if none exist
        if (userRepository.count() == 0) {
            initializeUsers();
        }
        
        // Initialize water reservoirs if none exist
        if (waterReservoirRepository.count() == 0) {
            initializeWaterReservoirs();
        }
    }
    
    private void initializeUsers() {
        // Create admin user
        User adminUser = new User();
        adminUser.setFullName("Admin User");
        adminUser.setEmail("admin@waterapp.ke");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setRole(User.UserRole.ADMIN);
        adminUser.setCreatedAt(LocalDateTime.now());
        userRepository.save(adminUser);
        
        // Create sample user
        User sampleUser = new User();
        sampleUser.setFullName("John Doe");
        sampleUser.setEmail("john@example.com");
        sampleUser.setPassword(passwordEncoder.encode("password123"));
        sampleUser.setRole(User.UserRole.USER);
        sampleUser.setCreatedAt(LocalDateTime.now());
        userRepository.save(sampleUser);
    }
    
    private void initializeWaterReservoirs() {
        // Nairobi County Reservoirs
        createReservoir("Ndakaini Dam", "Nairobi", "Gatundu North", "Ndakaini",
                new BigDecimal("-0.9833"), new BigDecimal("36.8167"),
                new BigDecimal("70000000"), new BigDecimal("49000000"),
                WaterReservoir.WaterQuality.GOOD, "Nairobi Water and Sewerage Company",
                "Nairobi's main water source", "+254-20-123456", "info@nairobiwater.co.ke");
        
        createReservoir("Ruiru Dam", "Nairobi", "Ruiru", "Ruiru",
                new BigDecimal("-1.1500"), new BigDecimal("36.9500"),
                new BigDecimal("15000000"), new BigDecimal("9000000"),
                WaterReservoir.WaterQuality.GOOD, "Nairobi Water and Sewerage Company",
                "Secondary water source for Nairobi", "+254-20-123457", "info@nairobiwater.co.ke");
        
        // Kiambu County Reservoirs
        createReservoir("Karimenu II Dam", "Kiambu", "Gatundu North", "Karimenu",
                new BigDecimal("-1.0167"), new BigDecimal("36.8167"),
                new BigDecimal("26000000"), new BigDecimal("18200000"),
                WaterReservoir.WaterQuality.EXCELLENT, "Athwater Limited",
                "Major water project for Nairobi metropolitan", "+254-20-123458", "info@athwater.co.ke");
        
        // Nakuru County Reservoirs
        createReservoir("Lake Nakuru", "Nakuru", "Nakuru East", "Nakuru East",
                new BigDecimal("-0.3667"), new BigDecimal("36.0833"),
                new BigDecimal("100000000"), new BigDecimal("35000000"),
                WaterReservoir.WaterQuality.FAIR, "Nakuru Water and Sanitation Company",
                "Natural lake with water treatment", "+254-51-123456", "info@nakuruwater.co.ke");
        
        // Mombasa County Reservoirs
        createReservoir("Mbaraki Reservoir", "Mombasa", "Mvita", "Mbaraki",
                new BigDecimal("-4.0500"), new BigDecimal("39.6667"),
                new BigDecimal("50000000"), new BigDecimal("20000000"),
                WaterReservoir.WaterQuality.GOOD, "Mombasa Water and Sanitation Company",
                "Main water storage for Mombasa", "+254-41-123456", "info@mombasawater.co.ke");
        
        // Kisumu County Reservoirs
        createReservoir("Kisumu Water Works", "Kisumu", "Kisumu Central", "Kisumu Central",
                new BigDecimal("-0.1000"), new BigDecimal("34.7500"),
                new BigDecimal("30000000"), new BigDecimal("12000000"),
                WaterReservoir.WaterQuality.GOOD, "Kisumu Water and Sanitation Company",
                "Water treatment and storage facility", "+254-57-123456", "info@kisumuwater.co.ke");
        
        // Eldoret County Reservoirs
        createReservoir("Eldoret Dam", "Uasin Gishu", "Eldoret East", "Eldoret East",
                new BigDecimal("0.5167"), new BigDecimal("35.2833"),
                new BigDecimal("25000000"), new BigDecimal("8750000"),
                WaterReservoir.WaterQuality.GOOD, "Eldoret Water and Sanitation Company",
                "Water storage for Eldoret town", "+254-53-123456", "info@eldoretwater.co.ke");
        
        // Thika County Reservoirs
        createReservoir("Thika High Level Dam", "Kiambu", "Thika Town", "Thika Town",
                new BigDecimal("-1.0333"), new BigDecimal("37.0833"),
                new BigDecimal("40000000"), new BigDecimal("28000000"),
                WaterReservoir.WaterQuality.EXCELLENT, "Thika Water and Sewerage Company",
                "High-level water storage for Thika", "+254-67-123456", "info@thikawater.co.ke");
        
        // Nyeri County Reservoirs
        createReservoir("Chania Dam", "Nyeri", "Nyeri Central", "Nyeri Central",
                new BigDecimal("-0.4167"), new BigDecimal("36.9500"),
                new BigDecimal("20000000"), new BigDecimal("8000000"),
                WaterReservoir.WaterQuality.GOOD, "Nyeri Water and Sanitation Company",
                "Water storage for Nyeri town", "+254-61-123456", "info@nyeriwater.co.ke");
        
        // Machakos County Reservoirs
        createReservoir("Machakos Water Works", "Machakos", "Machakos Town", "Machakos Town",
                new BigDecimal("-1.5167"), new BigDecimal("37.2667"),
                new BigDecimal("15000000"), new BigDecimal("6000000"),
                WaterReservoir.WaterQuality.FAIR, "Machakos Water and Sanitation Company",
                "Water treatment and storage", "+254-44-123456", "info@machakoswater.co.ke");
        
        // Critical reservoirs (low water levels)
        createReservoir("Kitui Dam", "Kitui", "Kitui Central", "Kitui Central",
                new BigDecimal("-1.3667"), new BigDecimal("38.0167"),
                new BigDecimal("10000000"), new BigDecimal("2500000"),
                WaterReservoir.WaterQuality.CRITICAL, "Kitui Water and Sanitation Company",
                "Severely low water levels", "+254-44-123457", "info@kituiwater.co.ke");
        
        createReservoir("Garissa Reservoir", "Garissa", "Garissa Township", "Garissa Township",
                new BigDecimal("-0.4500"), new BigDecimal("39.6500"),
                new BigDecimal("8000000"), new BigDecimal("1600000"),
                WaterReservoir.WaterQuality.CRITICAL, "Garissa Water and Sanitation Company",
                "Critical water shortage", "+254-46-123456", "info@garissawater.co.ke");
    }
    
    private void createReservoir(String name, String county, String subCounty, String ward,
                                BigDecimal latitude, BigDecimal longitude,
                                BigDecimal totalCapacity, BigDecimal currentLevel,
                                WaterReservoir.WaterQuality quality, String managedBy,
                                String description, String contactPhone, String contactEmail) {
        
        WaterReservoir reservoir = new WaterReservoir(name, county, subCounty, ward, 
                                                    latitude, longitude, totalCapacity, currentLevel);
        
        reservoir.setWaterQuality(quality);
        reservoir.setManagedBy(managedBy);
        reservoir.setDescription(description);
        reservoir.setContactPhone(contactPhone);
        reservoir.setContactEmail(contactEmail);
        
        // Calculate estimated runout date based on current level and usage patterns
        if (currentLevel.compareTo(BigDecimal.ZERO) > 0) {
            // Simple estimation: assume daily usage of 1% of current level
            double dailyUsagePercent = 0.01;
            double daysUntilRunout = currentLevel.doubleValue() / (currentLevel.doubleValue() * dailyUsagePercent);
            reservoir.setEstimatedRunoutDate(LocalDateTime.now().plusDays((long) daysUntilRunout));
        }
        
        waterReservoirRepository.save(reservoir);
    }
}
