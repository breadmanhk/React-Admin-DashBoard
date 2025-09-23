import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api';

// Create axios instance with default configuration
const axiosInstance = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add auth token
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor for error handling
axiosInstance.interceptors.response.use(
  (response) => response.data,
  (error) => {
    if (error.response) {
      // Server responded with error status
      const errorMessage = error.response.data?.message ||
                          error.response.data?.error ||
                          `HTTP ${error.response.status}: ${error.response.statusText}`;

      // Handle 401 Unauthorized - clear token and redirect to login
      if (error.response.status === 401) {
        localStorage.removeItem('token');
        window.location.href = '/login';
      }

      throw new Error(errorMessage);
    } else if (error.request) {
      // Request was made but no response received
      throw new Error('Network error - please check your connection');
    } else {
      // Error in request configuration
      throw new Error(error.message || 'Request failed');
    }
  }
);

class ApiService {
  // Authentication
  async login(email, password) {
    return axiosInstance.post('/auth/login', { email, password });
  }

  async register(name, email, password, role = 'CUSTOMER') {
    return axiosInstance.post('/auth/register', { name, email, password, role });
  }

  // Users
  async getUsers(page = 0, size = 10, search = '') {
    const params = { page, size };
    if (search) params.search = search;
    return axiosInstance.get('/users', { params });
  }

  async createUser(userData) {
    return axiosInstance.post('/users', userData);
  }

  async updateUser(id, userData) {
    return axiosInstance.put(`/users/${id}`, userData);
  }

  async deleteUser(id) {
    return axiosInstance.delete(`/users/${id}`);
  }

  // Products
  async getProducts(page = 0, size = 10, search = '') {
    const params = { page, size };
    if (search) params.search = search;
    return axiosInstance.get('/products', { params });
  }

  async createProduct(productData) {
    return axiosInstance.post('/products', productData);
  }

  async updateProduct(id, productData) {
    return axiosInstance.put(`/products/${id}`, productData);
  }

  async deleteProduct(id) {
    return axiosInstance.delete(`/products/${id}`);
  }

  // Orders
  async getOrders(page = 0, size = 10, search = '') {
    const params = { page, size };
    if (search) params.search = search;
    return axiosInstance.get('/orders', { params });
  }

  async updateOrderStatus(id, status) {
    return axiosInstance.patch(`/orders/${id}/status`, null, {
      params: { status }
    });
  }

  async getRevenueStats() {
    return axiosInstance.get('/orders/revenue');
  }

  // Sales
  async getSalesOverview() {
    return axiosInstance.get('/sales/overview');
  }

  async getSalesByCategory() {
    return axiosInstance.get('/sales/by-category');
  }

  // Dashboard
  async getDashboardOverview() {
    return axiosInstance.get('/dashboard/overview');
  }

  async getDashboardAnalytics() {
    return axiosInstance.get('/dashboard/analytics');
  }
}

// Export singleton instance
const apiService = new ApiService();
export default apiService;

// Also export the axios instance for custom requests
export { axiosInstance };