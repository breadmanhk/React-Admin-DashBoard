# MySQL Database Setup Guide

## Prerequisites

1. Install MySQL Server (version 8.0 or higher recommended)
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Or use package managers:
     - Windows: Use MySQL Installer
     - Mac: `brew install mysql`
     - Linux: `sudo apt-get install mysql-server` (Ubuntu/Debian)

2. Ensure MySQL service is running:
   - Windows: Check Windows Services
   - Mac/Linux: `sudo systemctl status mysql` or `brew services list`

## Database Configuration

### 1. Create Database and User

Connect to MySQL as root:
```bash
mysql -u root -p
```

Create the database and user:
```sql
-- Create database
CREATE DATABASE IF NOT EXISTS admin_dashboard;

-- Create user (optional - if not using root)
CREATE USER IF NOT EXISTS 'admin_user'@'localhost' IDENTIFIED BY 'admin_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON admin_dashboard.* TO 'admin_user'@'localhost';

-- Apply changes
FLUSH PRIVILEGES;

-- Exit MySQL
EXIT;
```

### 2. Update Application Properties

If you're using different MySQL credentials, update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/admin_dashboard?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application

### With MySQL (Default)
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### With H2 Database (Alternative for testing)
```bash
cd backend
mvn spring-boot:run -Dspring.profiles.active=h2
```

## Verify Database Connection

1. Check application startup logs for successful connection
2. Tables will be automatically created on first run
3. Access the API at: http://localhost:8080/api

## Troubleshooting

### Connection Refused Error
- Ensure MySQL service is running
- Check if MySQL is listening on port 3306: `netstat -an | grep 3306`
- Verify firewall settings

### Access Denied Error
- Verify username and password
- Ensure user has proper privileges:
  ```sql
  SHOW GRANTS FOR 'your_user'@'localhost';
  ```

### Timezone Error
- Add serverTimezone parameter to connection URL:
  ```
  jdbc:mysql://localhost:3306/admin_dashboard?serverTimezone=UTC
  ```

### SSL Warning
- For development, you can disable SSL:
  ```
  jdbc:mysql://localhost:3306/admin_dashboard?useSSL=false
  ```

## Database Schema

The application will automatically create the following tables:

- `users` - User authentication and profile information
- `products` - Product catalog
- `orders` - Customer orders
- `order_items` - Order line items

## Sample Data

Initial admin user is created automatically:
- Email: admin@example.com
- Password: admin123
- Role: ADMIN

## Backup and Restore

### Backup Database
```bash
mysqldump -u root -p admin_dashboard > admin_dashboard_backup.sql
```

### Restore Database
```bash
mysql -u root -p admin_dashboard < admin_dashboard_backup.sql
```