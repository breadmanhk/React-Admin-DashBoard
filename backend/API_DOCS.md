# API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication

### Login
**POST** `/auth/login`

Request body:
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "ADMIN"
}
```

### Register
**POST** `/auth/register`

Request body:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "CUSTOMER"
}
```

## Users API

### Get All Users
**GET** `/users?page=0&size=10&search=john`

Response:
```json
{
  "users": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john@example.com",
      "role": "ADMIN",
      "status": "ACTIVE",
      "createdAt": "2023-01-01T10:00:00",
      "updatedAt": "2023-01-01T10:00:00"
    }
  ],
  "currentPage": 0,
  "totalItems": 1,
  "totalPages": 1
}
```

### Create User
**POST** `/users`

Request body:
```json
{
  "name": "Jane Smith",
  "email": "jane@example.com",
  "password": "password123",
  "role": "CUSTOMER",
  "status": "ACTIVE"
}
```

## Products API

### Get All Products
**GET** `/products?page=0&size=10&search=wireless`

Response:
```json
{
  "products": [
    {
      "id": 1,
      "name": "Wireless Earbuds",
      "category": "Electronics",
      "price": 59.99,
      "stock": 143,
      "sales": 1200,
      "description": "High-quality wireless earbuds",
      "imageUrl": "https://example.com/image.jpg",
      "createdAt": "2023-01-01T10:00:00",
      "updatedAt": "2023-01-01T10:00:00"
    }
  ],
  "currentPage": 0,
  "totalItems": 1,
  "totalPages": 1
}
```

### Create Product
**POST** `/products`

Request body:
```json
{
  "name": "Smart Watch",
  "category": "Electronics",
  "price": 199.99,
  "stock": 50,
  "description": "Advanced smartwatch with health tracking",
  "imageUrl": "https://example.com/watch.jpg"
}
```

## Orders API

### Get All Orders
**GET** `/orders?page=0&size=10&search=ORD001`

Response:
```json
{
  "orders": [
    {
      "id": 1,
      "orderId": "ORD001",
      "customer": "John Doe",
      "total": 235.40,
      "status": "DELIVERED",
      "orderDate": "2023-07-01T10:00:00",
      "createdAt": "2023-07-01T10:00:00",
      "updatedAt": "2023-07-01T10:00:00",
      "orderItems": []
    }
  ],
  "currentPage": 0,
  "totalItems": 1,
  "totalPages": 1
}
```

### Update Order Status
**PATCH** `/orders/1/status?status=SHIPPED`

### Get Revenue Statistics
**GET** `/orders/revenue`

Response:
```json
{
  "totalRevenue": 2684.15,
  "period": "all-time"
}
```

## Sales API

### Get Sales Overview
**GET** `/sales/overview`

Response:
```json
[
  {
    "id": 1,
    "period": "Jul",
    "sales": 4200.00,
    "saleDate": "2023-07-01",
    "category": null
  },
  {
    "id": 2,
    "period": "Aug",
    "sales": 3800.00,
    "saleDate": "2023-08-01",
    "category": null
  }
]
```

### Get Sales by Category
**GET** `/sales/by-category`

Response:
```json
{
  "salesByCategory": [
    ["Electronics", 25000.00],
    ["Accessories", 15000.00],
    ["Fitness", 8000.00]
  ]
}
```

## Dashboard API

### Get Dashboard Overview
**GET** `/dashboard/overview`

Response:
```json
{
  "totalUsers": 5,
  "activeUsers": 4,
  "totalProducts": 5,
  "productsInStock": 5,
  "totalOrders": 8,
  "pendingOrders": 1,
  "totalRevenue": 2684.15,
  "recentOrders": 8,
  "recentRevenue": 2684.15
}
```

### Get Analytics
**GET** `/dashboard/analytics`

Response:
```json
{
  "salesData": [...],
  "salesByCategory": [...],
  "orderStatusDistribution": [...],
  "topSellingProducts": [...],
  "lowStockProducts": [...],
  "productsByCategory": [...],
  "userRoleDistribution": {
    "admin": 2,
    "customer": 2,
    "moderator": 1
  }
}
```

## Error Responses

### 400 Bad Request
```json
{
  "error": "Validation failed",
  "message": "Email already exists"
}
```

### 404 Not Found
```json
{
  "error": "Resource not found",
  "message": "User not found"
}
```

### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Invalid credentials"
}
```

## Status Codes

- `200 OK` - Request successful
- `201 Created` - Resource created successfully
- `204 No Content` - Resource deleted successfully
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Access denied
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## Query Parameters

### Pagination
- `page` - Page number (default: 0)
- `size` - Page size (default: 10)
- `sortBy` - Sort field (default: id)
- `sortDir` - Sort direction: asc/desc (default: asc)

### Search
- `search` - Search term for filtering results

### Filters
- `category` - Filter products by category
- `status` - Filter orders by status
- `startDate` - Filter by start date
- `endDate` - Filter by end date