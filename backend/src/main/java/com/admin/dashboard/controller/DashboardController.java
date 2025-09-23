package com.admin.dashboard.controller;

import com.admin.dashboard.service.OrderService;
import com.admin.dashboard.service.ProductService;
import com.admin.dashboard.service.SalesDataService;
import com.admin.dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SalesDataService salesDataService;

    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getDashboardOverview() {
        Map<String, Object> overview = new HashMap<>();

        // User stats
        overview.put("totalUsers", userService.getAllUsers().size());
        overview.put("activeUsers", userService.getActiveUsersCount());

        // Product stats
        overview.put("totalProducts", productService.getAllProducts().size());
        overview.put("productsInStock", productService.getProductsInStockCount());

        // Order stats
        overview.put("totalOrders", orderService.getAllOrders().size());
        overview.put("pendingOrders", orderService.getOrderCountByStatus(com.admin.dashboard.entity.Order.Status.PENDING));
        overview.put("totalRevenue", orderService.getTotalRevenue());

        // Recent activity - orders from last 30 days
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        LocalDateTime now = LocalDateTime.now();
        overview.put("recentOrders", orderService.getOrdersByDateRange(thirtyDaysAgo, now).size());
        overview.put("recentRevenue", orderService.getTotalRevenueByDateRange(thirtyDaysAgo, now));

        return ResponseEntity.ok(overview);
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getAnalytics() {
        Map<String, Object> analytics = new HashMap<>();

        // Sales data
        analytics.put("salesData", salesDataService.getAllSalesData());
        analytics.put("salesByCategory", salesDataService.getSalesByCategory());

        // Order distribution
        analytics.put("orderStatusDistribution", orderService.getOrderCountByStatus());

        // Product analytics
        analytics.put("topSellingProducts", productService.getTopSellingProducts(5));
        analytics.put("lowStockProducts", productService.getLowStockProducts(10));
        analytics.put("productsByCategory", productService.getProductCountByCategory());

        // User analytics
        analytics.put("userRoleDistribution", Map.of(
                "admin", userService.getUserCountByRole(com.admin.dashboard.entity.User.Role.ADMIN),
                "customer", userService.getUserCountByRole(com.admin.dashboard.entity.User.Role.CUSTOMER),
                "moderator", userService.getUserCountByRole(com.admin.dashboard.entity.User.Role.MODERATOR)
        ));

        return ResponseEntity.ok(analytics);
    }
}