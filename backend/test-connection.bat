@echo off
echo ========================================
echo MySQL Connection Test Script
echo ========================================
echo.

set /p MYSQL_USER="Enter MySQL username (default: root): "
if "%MYSQL_USER%"=="" set MYSQL_USER=root

set /p MYSQL_PASS="Enter MySQL password: "

echo.
echo Testing MySQL connection...
mysql -u %MYSQL_USER% -p%MYSQL_PASS% -e "SELECT VERSION();" 2>nul
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Failed to connect to MySQL
    echo Please check:
    echo   1. MySQL service is running
    echo   2. Username and password are correct
    echo   3. MySQL is accessible on localhost:3306
) else (
    echo [SUCCESS] Connected to MySQL successfully!
    echo.
    echo Checking admin_dashboard database...
    mysql -u %MYSQL_USER% -p%MYSQL_PASS% -e "USE admin_dashboard; SELECT 'Database exists' as Status;" 2>nul
    if %errorlevel% neq 0 (
        echo [INFO] Database 'admin_dashboard' does not exist yet.
        echo.
        set /p CREATE_DB="Do you want to create it now? (Y/N): "
        if /i "%CREATE_DB%"=="Y" (
            echo Creating database...
            mysql -u %MYSQL_USER% -p%MYSQL_PASS% < database-setup.sql
            if %errorlevel% equ 0 (
                echo [SUCCESS] Database created successfully!
            ) else (
                echo [ERROR] Failed to create database
            )
        )
    ) else (
        echo [SUCCESS] Database 'admin_dashboard' exists!
        echo.
        echo Checking tables...
        mysql -u %MYSQL_USER% -p%MYSQL_PASS% -D admin_dashboard -e "SHOW TABLES;" 2>nul
    )
)

echo.
echo ========================================
echo Update application.properties with:
echo   spring.datasource.username=%MYSQL_USER%
echo   spring.datasource.password=YOUR_PASSWORD
echo ========================================
echo.
pause