# React Admin Dashboard

> A full-stack admin dashboard with React frontend, Spring Boot backend, and MySQL database

![Demo App](/public/screenshot-for-readme-0.png)
![Demo App](/public/screenshot-for-readme-1.png)
![Demo App](/public/screenshot-for-readme-2.png)
![Demo App](/public/screenshot-for-readme-3.png)

## 🚀 Overview

A comprehensive admin dashboard application featuring user authentication, data visualization, and complete CRUD operations for managing users, products, and orders. Built with modern web technologies and following best practices for both frontend and backend development.

## 🛠️ Tech Stack

### Frontend
- **React 18** - Component-based UI framework
- **Vite** - Fast development build tool
- **Tailwind CSS** - Utility-first CSS framework
- **Recharts** - Data visualization library
- **Framer Motion** - Animation library for smooth transitions
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls
- **Lucide React** - Modern icon library

### Backend
- **Spring Boot 3.2** - Java web framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer
- **JWT** - Token-based authentication
- **Maven** - Dependency management

### Database
- **MySQL 8+** - Primary database
- **H2** - Alternative in-memory database for testing

## ✨ Features

- 🔐 **Complete Authentication System**
  - User login/logout with JWT tokens
  - Role-based access control (Admin, Customer, Moderator)
  - Secure password handling
  - Protected routes and API endpoints

- 📊 **Interactive Dashboard**
  - Real-time data visualization with charts
  - Sales analytics and revenue tracking
  - User demographics and activity monitoring
  - Product performance metrics

- 👥 **User Management**
  - Create, read, update, delete users
  - Role assignment and status management
  - User activity tracking

- 📦 **Product Management**
  - Product catalog with categories
  - Stock tracking and sales data
  - Product creation and editing

- 🛒 **Order Management**
  - Order tracking and status updates
  - Revenue analytics
  - Order history and details

- 📱 **Responsive Design**
  - Mobile-first approach
  - Works seamlessly on all device sizes
  - Modern dark theme UI

## 🏁 Getting Started

### Prerequisites
- **Node.js** (v18 or higher)
- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8+** (or use H2 for testing)

### Frontend Setup

1. **Install dependencies**
   ```bash
   npm install
   ```

2. **Start development server**
   ```bash
   npm run dev
   ```

3. **Build for production**
   ```bash
   npm run build
   ```

### Backend Setup

1. **Configure Environment Variables**
   ```bash
   cd backend
   cp .env.example .env
   ```

   Edit `.env` file with your actual values:
   ```env
   DB_USERNAME=root
   DB_PASSWORD=your_mysql_password
   JWT_SECRET=your_super_secret_jwt_key_minimum_64_characters_long
   ```

2. **Create MySQL Database**
   ```sql
   CREATE DATABASE IF NOT EXISTS admin_dashboard;
   ```

3. **Install dependencies and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Alternative: Run with H2 database**
   ```bash
   mvn spring-boot:run -Dspring.profiles.active=h2
   ```

### Default Admin Account
- **Email:** admin@example.com
- **Password:** admin123
- **Role:** ADMIN

## 🔗 API Endpoints

The backend provides a RESTful API running on `http://localhost:8080/api` with the following main endpoints:

- **Authentication:** `/auth/login`, `/auth/register`
- **Users:** `/users` (CRUD operations)
- **Products:** `/products` (CRUD operations)
- **Orders:** `/orders` (tracking and management)
- **Dashboard:** `/dashboard/overview`, `/dashboard/analytics`
- **Sales:** `/sales/overview`, `/sales/by-category`

See `backend/API_DOCS.md` for complete API documentation.

## 📂 Project Structure

```
react-admin-dashboard/
├── src/                          # Frontend source code
│   ├── components/               # Reusable React components
│   ├── pages/                    # Page components
│   ├── context/                  # React Context (Auth)
│   ├── services/                 # API service layer
│   └── hooks/                    # Custom React hooks
├── backend/                      # Spring Boot backend
│   ├── src/main/java/            # Java source code
│   ├── src/main/resources/       # Configuration files
│   ├── pom.xml                   # Maven dependencies
│   └── API_DOCS.md              # API documentation
└── public/                       # Static assets
```

## 🧪 Development

### Frontend Commands
```bash
npm run dev      # Start development server
npm run build    # Build for production
npm run preview  # Preview production build
npm run lint     # Run ESLint
```

### Backend Commands
```bash
mvn clean install           # Build project
mvn spring-boot:run        # Run application
mvn test                   # Run tests
```

## 🔧 Environment Configuration

### Frontend (.env)
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### Backend (.env)
Copy `backend/.env.example` to `backend/.env` and configure:

```env
# Required
DB_USERNAME=root
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_jwt_secret_key_minimum_64_characters

# Optional (with defaults)
DB_NAME=admin_dashboard
JWT_EXPIRATION=86400000
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:5173
DDL_AUTO=create-drop
```

**Security Note:** The `.env` file is ignored by git and never committed. Always use the `.env.example` template for new environments.

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
