# 💧 Water Reservoir Backend API

A comprehensive Java Spring Boot backend for the Water Reservoir Management System in Kenya. This API provides authentication, water reservoir search, and monitoring capabilities.

## 🏗️ Architecture

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17
- **Database**: H2 (in-memory for development)
- **Security**: JWT-based authentication
- **API**: RESTful endpoints with comprehensive documentation

## ✨ Features

### 🔐 Authentication & Authorization
- User registration and login
- JWT token-based authentication
- Role-based access control (USER, ADMIN)
- Secure password encryption with BCrypt

### 💧 Water Reservoir Management
- Search reservoirs by location (County, Sub-County, Ward)
- Geographic search within radius
- Water level monitoring and status tracking
- Critical reservoir alerts
- Runout date predictions

### 🌍 Kenya-Specific Data
- Real Kenyan counties and administrative units
- Accurate geographic coordinates
- Local water management companies
- Contact information for each reservoir

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd backend
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080/api`
   - H2 Console: `http://localhost:8080/h2-console`
   - Database URL: `jdbc:h2:mem:waterdb`
   - Username: `sa`
   - Password: `password`

## 📚 API Documentation

### Authentication Endpoints

#### User Registration
```http
POST /api/auth/signup
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "confirmPassword": "password123"
}
```

#### User Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

### Water Reservoir Endpoints

#### Get All Reservoirs
```http
GET /api/reservoirs
Authorization: Bearer <jwt-token>
```

#### Search Reservoirs by Location
```http
GET /api/reservoirs/search?search=Nairobi
Authorization: Bearer <jwt-token>
```

#### Get Reservoirs by County
```http
GET /api/reservoirs/county/Nairobi
Authorization: Bearer <jwt-token>
```

#### Get Reservoirs by Sub-County
```http
GET /api/reservoirs/subcounty/Gatundu%20North
Authorization: Bearer <jwt-token>
```

#### Get Reservoirs by Ward
```http
GET /api/reservoirs/ward/Ndakaini
Authorization: Bearer <jwt-token>
```

#### Get Critical Reservoirs
```http
GET /api/reservoirs/critical
Authorization: Bearer <jwt-token>
```

#### Get Reservoirs Running Out Soon
```http
GET /api/reservoirs/running-out?days=30
Authorization: Bearer <jwt-token>
```

#### Get Reservoir Statistics
```http
GET /api/reservoirs/statistics
Authorization: Bearer <jwt-token>
```

#### Update Water Level
```http
PUT /api/reservoirs/{id}/water-level?currentLevel=50000000
Authorization: Bearer <jwt-token>
```

## 🗄️ Database Schema

### Users Table
- `id` - Primary key
- `full_name` - User's full name
- `email` - Unique email address
- `password` - Encrypted password
- `created_at` - Account creation timestamp
- `last_login` - Last login timestamp
- `role` - User role (USER/ADMIN)

### Water Reservoirs Table
- `id` - Primary key
- `name` - Reservoir name
- `county` - County name
- `sub_county` - Sub-county name
- `ward` - Ward name
- `specific_location` - Specific location details
- `latitude` - Geographic latitude
- `longitude` - Geographic longitude
- `total_capacity_m3` - Total capacity in cubic meters
- `current_level_m3` - Current water level in cubic meters
- `current_level_percentage` - Current level as percentage
- `water_quality_rating` - Water quality (EXCELLENT/GOOD/FAIR/POOR/CRITICAL)
- `last_updated` - Last update timestamp
- `estimated_runout_date` - Estimated date when reservoir will run out
- `is_active` - Whether reservoir is active
- `description` - Reservoir description
- `managed_by` - Managing company/organization
- `contact_phone` - Contact phone number
- `contact_email` - Contact email address

## 🔍 Sample Data

The application comes pre-loaded with sample data from various Kenyan counties:

### Nairobi County
- Ndakaini Dam (Main water source)
- Ruiru Dam (Secondary source)

### Kiambu County
- Karimenu II Dam
- Thika High Level Dam

### Other Counties
- Nakuru, Mombasa, Kisumu, Eldoret, Nyeri, Machakos, Kitui, Garissa

## 🔐 Default Users

### Admin User
- Email: `admin@waterapp.ke`
- Password: `admin123`
- Role: `ADMIN`

### Sample User
- Email: `john@example.com`
- Password: `password123`
- Role: `USER`

## 🛠️ Configuration

### Application Properties
- **Server Port**: 8080
- **API Context Path**: `/api`
- **Database**: H2 in-memory
- **JWT Secret**: Configurable in `application.yml`
- **JWT Expiration**: 24 hours (configurable)
- **CORS**: Configured for React frontend

### Environment Variables
You can override these properties using environment variables:
- `SERVER_PORT`
- `JWT_SECRET`
- `JWT_EXPIRATION`
- `CORS_ALLOWED_ORIGINS`

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
```bash
mvn jacoco:report
```

## 🚀 Deployment

### Production Build
```bash
mvn clean package -Pprod
```

### Docker Support
```bash
docker build -t water-reservoir-backend .
docker run -p 8080:8080 water-reservoir-backend
```

## 🔒 Security Features

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **CORS Configuration**: Cross-origin resource sharing setup
- **Input Validation**: Comprehensive request validation
- **SQL Injection Protection**: JPA/Hibernate protection

## 📱 Frontend Integration

This backend is designed to work seamlessly with the React frontend:
- CORS configured for `http://localhost:3000`
- JWT token handling
- RESTful API design
- Comprehensive error handling

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:
- Check the API documentation
- Review the sample data
- Test with the provided endpoints
- Contact the development team

---

**Built with ❤️ for Kenya's Water Management** 💧🇰🇪
