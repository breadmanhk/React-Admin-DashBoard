# Admin Dashboard Backend

A comprehensive Spring Boot REST API backend for the React Admin Dashboard frontend.

## Features

- **User Management**: CRUD operations for users with role-based access
- **Product Management**: Full product catalog with inventory tracking
- **Order Management**: Complete order processing and status tracking
- **Sales Analytics**: Sales data visualization and reporting
- **Dashboard Analytics**: Overview statistics and insights
- **Authentication**: JWT-based authentication system
- **Security**: Spring Security with CORS configuration
- **Database**: H2 in-memory database with JPA/Hibernate

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security 6 with JWT
- **Database**: H2 Database (in-memory)
- **ORM**: JPA/Hibernate
- **Validation**: Jakarta Validation
- **Build Tool**: Maven
- **Java Version**: 17

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Users
- `GET /api/users` - Get all users (with pagination and search)
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/stats` - Get user statistics

### Products
- `GET /api/products` - Get all products (with pagination and search)
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/low-stock` - Get low stock products
- `GET /api/products/top-selling` - Get top selling products
- `GET /api/products/stats` - Get product statistics

### Orders
- `GET /api/orders` - Get all orders (with pagination and search)
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/order-id/{orderId}` - Get order by order ID
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}` - Update order
- `PATCH /api/orders/{id}/status` - Update order status
- `DELETE /api/orders/{id}` - Delete order
- `GET /api/orders/status/{status}` - Get orders by status
- `GET /api/orders/date-range` - Get orders by date range
- `GET /api/orders/revenue` - Get revenue statistics
- `GET /api/orders/stats` - Get order statistics

### Sales
- `GET /api/sales` - Get all sales data
- `GET /api/sales/{id}` - Get sales data by ID
- `POST /api/sales` - Create sales data
- `PUT /api/sales/{id}` - Update sales data
- `DELETE /api/sales/{id}` - Delete sales data
- `GET /api/sales/date-range` - Get sales data by date range
- `GET /api/sales/category/{category}` - Get sales data by category
- `GET /api/sales/by-category` - Get sales grouped by category
- `GET /api/sales/overview` - Get sales overview

### Dashboard
- `GET /api/dashboard/overview` - Get dashboard overview statistics
- `GET /api/dashboard/analytics` - Get comprehensive analytics data

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
   ```bash
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

The application will start on `http://localhost:8080`

### Default Configuration

- **Server Port**: 8080
- **Context Path**: `/api`
- **Database**: H2 in-memory database
- **H2 Console**: Available at `http://localhost:8080/api/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

### Sample Data

The application automatically initializes with sample data including:
- 5 sample users (different roles and statuses)
- 5 sample products (various categories)
- 8 sample orders (different statuses)
- 12 months of sales data

### Default Login Credentials

- **Admin User**:
  - Email: `john@example.com`
  - Password: `password123`
  - Role: ADMIN

- **Regular User**:
  - Email: `alice@example.com`
  - Password: `password123`
  - Role: CUSTOMER

## Configuration

### CORS Configuration
The application is configured to accept requests from:
- `http://localhost:3000` (React development server)
- `http://localhost:5173` (Vite development server)

To modify CORS settings, update the `cors.allowed-origins` property in `application.properties`.

### JWT Configuration
JWT tokens are configured with:
- **Secret Key**: Configurable in `application.properties`
- **Expiration**: 24 hours (86400000 ms)

### Database Configuration
The application uses H2 in-memory database by default. To use a different database:

1. Add the appropriate database dependency to `pom.xml`
2. Update the datasource configuration in `application.properties`

## Security

- **Authentication**: JWT-based stateless authentication
- **Password Encryption**: BCrypt password encoder
- **CORS**: Configured for frontend integration
- **Session Management**: Stateless (no server-side sessions)

## Error Handling

The API returns appropriate HTTP status codes:
- `200 OK` - Successful requests
- `201 Created` - Successful resource creation
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server errors

## Development

### Project Structure
```
src/main/java/com/admin/dashboard/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── repository/     # JPA repositories
├── service/        # Business logic services
└── util/           # Utility classes
```

### Adding New Features

1. **Create Entity**: Define JPA entity in `entity` package
2. **Create Repository**: Extend JpaRepository in `repository` package
3. **Create Service**: Implement business logic in `service` package
4. **Create Controller**: Add REST endpoints in `controller` package
5. **Update Security**: Modify SecurityConfig if needed

## Testing

Run tests with:
```bash
mvn test
```

## Build for Production

Create a production JAR:
```bash
mvn clean package
```

Run the JAR:
```bash
java -jar target/dashboard-backend-0.0.1-SNAPSHOT.jar
```

## Integration with Frontend

This backend is designed to work with the React Admin Dashboard frontend. Key integration points:

1. **CORS**: Pre-configured for React development servers
2. **Data Format**: JSON responses match frontend expectations
3. **Pagination**: Supports frontend pagination requirements
4. **Search**: Implements search functionality for tables
5. **Authentication**: JWT tokens for secure API access

## Support

For issues and questions, please refer to the project documentation or create an issue in the repository.