# MySQL Connection Setup Guide

## Quick Setup

### 1. Test Your MySQL Connection

First, verify MySQL is running on your PC:

```bash
# Windows Command Prompt or PowerShell
mysql -u root -p

# If MySQL is running, you'll be prompted for password
```

### 2. Run the Database Setup Script

```bash
# Login to MySQL
mysql -u root -p

# Run the setup script
source C:\project\react-admin-dashboard\backend\database-setup.sql

# Or you can run it directly from command line:
mysql -u root -p < C:\project\react-admin-dashboard\backend\database-setup.sql
```

### 3. Update Connection Settings

Edit `backend\src\main\resources\application.properties`:

```properties
# Update these values with your MySQL credentials
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

## Connection Information

### Default Configuration
- **Host:** localhost
- **Port:** 3306 (MySQL default)
- **Database Name:** admin_dashboard
- **Connection URL:** `jdbc:mysql://localhost:3306/admin_dashboard?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC`

### Test Credentials (created by the application)
- **Admin Email:** admin@example.com
- **Admin Password:** admin123

## Testing the Connection

### Option 1: Test via MySQL Client

```sql
-- Check if database exists
SHOW DATABASES LIKE 'admin_dashboard';

-- Use the database
USE admin_dashboard;

-- Check tables (will be empty until you run the Spring Boot app)
SHOW TABLES;
```

### Option 2: Test via Spring Boot

```bash
cd backend

# Run with MySQL (default profile)
mvn spring-boot:run

# Or if MySQL is not available, run with H2
mvn spring-boot:run -Dspring.profiles.active=h2
```

## Common Connection Issues and Solutions

### 1. Access Denied Error
```
Access denied for user 'root'@'localhost'
```
**Solution:** Update the password in `application.properties`

### 2. Unknown Database Error
```
Unknown database 'admin_dashboard'
```
**Solution:** Run the setup script to create the database

### 3. Connection Refused
```
Connection refused: connect
```
**Solution:**
- Ensure MySQL service is running
- Check Windows Services: `services.msc` → Look for MySQL
- Or start from command line: `net start MySQL80` (or your MySQL version)

### 4. Timezone Error
```
The server time zone value is unrecognized
```
**Solution:** Already handled in connection URL with `serverTimezone=UTC`

### 5. SSL Warning
```
SSL connection warning
```
**Solution:** Already handled with `useSSL=false` for development

## Verify Everything is Working

1. **Start the Backend:**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

2. **Check Console Output:**
   Look for:
   ```
   ==============================================
   Initial users created successfully!
   Admin Login: admin@example.com / admin123
   ==============================================
   ```

3. **Test API Endpoints:**
   - Health Check: http://localhost:8080/api
   - Login Test (use Postman or curl):
     ```bash
     curl -X POST http://localhost:8080/api/auth/login \
       -H "Content-Type: application/json" \
       -d "{\"email\":\"admin@example.com\",\"password\":\"admin123\"}"
     ```

4. **Test from Frontend:**
   ```bash
   cd .. (go back to project root)
   npm run dev
   ```
   - Navigate to: http://localhost:5173
   - Try logging in with admin@example.com / admin123

## Database Schema Overview

After running the application, these tables will be created:

1. **users** - User accounts and authentication
2. **products** - Product catalog
3. **orders** - Customer orders
4. **order_items** - Order line items
5. **sales_data** - Sales analytics data

## Switching Between MySQL and H2

### Use MySQL (Production):
```bash
mvn spring-boot:run
```

### Use H2 (Development/Testing):
```bash
mvn spring-boot:run -Dspring.profiles.active=h2
```

## Monitoring Database

### Check Active Connections:
```sql
SHOW PROCESSLIST;
```

### Check Database Size:
```sql
SELECT
    table_schema AS 'Database',
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)'
FROM information_schema.tables
WHERE table_schema = 'admin_dashboard'
GROUP BY table_schema;
```

### View Table Records:
```sql
USE admin_dashboard;
SELECT COUNT(*) FROM users;
SELECT COUNT(*) FROM products;
SELECT COUNT(*) FROM orders;
```

## Need Help?

1. Check MySQL is running: `mysqladmin -u root -p status`
2. View Spring Boot logs for detailed error messages
3. Ensure firewall isn't blocking port 3306
4. Try connecting with MySQL Workbench to test credentials