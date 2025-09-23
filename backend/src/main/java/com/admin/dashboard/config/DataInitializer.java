package com.admin.dashboard.config;

import com.admin.dashboard.entity.*;
import com.admin.dashboard.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SalesDataRepository salesDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
        initializeProducts();
        initializeOrders();
        initializeSalesData();
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            // Main admin account
            User mainAdmin = new User("Admin User", "admin@example.com", passwordEncoder.encode("admin123"), User.Role.ADMIN);

            // Additional test users
            User admin = new User("John Doe", "john@example.com", passwordEncoder.encode("password123"), User.Role.ADMIN);
            User jane = new User("Jane Smith", "jane@example.com", passwordEncoder.encode("password123"), User.Role.ADMIN);
            User bob = new User("Bob Johnson", "bob@example.com", passwordEncoder.encode("password123"), User.Role.CUSTOMER);
            bob.setStatus(User.Status.INACTIVE);
            User alice = new User("Alice Brown", "alice@example.com", passwordEncoder.encode("password123"), User.Role.CUSTOMER);
            User charlie = new User("Charlie Wilson", "charlie@example.com", passwordEncoder.encode("password123"), User.Role.MODERATOR);

            userRepository.saveAll(Arrays.asList(mainAdmin, admin, jane, bob, alice, charlie));

            System.out.println("==============================================");
            System.out.println("Initial users created successfully!");
            System.out.println("Admin Login: admin@example.com / admin123");
            System.out.println("==============================================");
        }
    }

    private void initializeProducts() {
        if (productRepository.count() == 0) {
            Product earbuds = new Product("Wireless Earbuds", "Electronics", new BigDecimal("59.99"), 143);
            earbuds.setSales(1200);
            earbuds.setImageUrl("https://images.unsplash.com/photo-1627989580309-bfaf3e58af6f");

            Product wallet = new Product("Leather Wallet", "Accessories", new BigDecimal("39.99"), 89);
            wallet.setSales(800);

            Product smartwatch = new Product("Smart Watch", "Electronics", new BigDecimal("199.99"), 56);
            smartwatch.setSales(650);

            Product yogaMat = new Product("Yoga Mat", "Fitness", new BigDecimal("29.99"), 210);
            yogaMat.setSales(950);

            Product coffeeMaker = new Product("Coffee Maker", "Home", new BigDecimal("79.99"), 78);
            coffeeMaker.setSales(720);

            productRepository.saveAll(Arrays.asList(earbuds, wallet, smartwatch, yogaMat, coffeeMaker));
        }
    }

    private void initializeOrders() {
        if (orderRepository.count() == 0) {
            Order order1 = new Order("John Doe", new BigDecimal("235.40"), Order.Status.DELIVERED);
            order1.setOrderId("ORD001");
            order1.setOrderDate(LocalDateTime.of(2023, 7, 1, 10, 0));

            Order order2 = new Order("Jane Smith", new BigDecimal("412.00"), Order.Status.PROCESSING);
            order2.setOrderId("ORD002");
            order2.setOrderDate(LocalDateTime.of(2023, 7, 2, 14, 30));

            Order order3 = new Order("Bob Johnson", new BigDecimal("162.50"), Order.Status.SHIPPED);
            order3.setOrderId("ORD003");
            order3.setOrderDate(LocalDateTime.of(2023, 7, 3, 9, 15));

            Order order4 = new Order("Alice Brown", new BigDecimal("750.20"), Order.Status.PENDING);
            order4.setOrderId("ORD004");
            order4.setOrderDate(LocalDateTime.of(2023, 7, 4, 16, 45));

            Order order5 = new Order("Charlie Wilson", new BigDecimal("95.80"), Order.Status.DELIVERED);
            order5.setOrderId("ORD005");
            order5.setOrderDate(LocalDateTime.of(2023, 7, 5, 11, 20));

            Order order6 = new Order("Eva Martinez", new BigDecimal("310.75"), Order.Status.PROCESSING);
            order6.setOrderId("ORD006");
            order6.setOrderDate(LocalDateTime.of(2023, 7, 6, 13, 10));

            Order order7 = new Order("David Lee", new BigDecimal("528.90"), Order.Status.SHIPPED);
            order7.setOrderId("ORD007");
            order7.setOrderDate(LocalDateTime.of(2023, 7, 7, 15, 30));

            Order order8 = new Order("Grace Taylor", new BigDecimal("189.60"), Order.Status.DELIVERED);
            order8.setOrderId("ORD008");
            order8.setOrderDate(LocalDateTime.of(2023, 7, 8, 12, 0));

            orderRepository.saveAll(Arrays.asList(order1, order2, order3, order4, order5, order6, order7, order8));
        }
    }

    private void initializeSalesData() {
        if (salesDataRepository.count() == 0) {
            SalesData[] salesData = {
                new SalesData("Jul", new BigDecimal("4200"), LocalDate.of(2023, 7, 1)),
                new SalesData("Aug", new BigDecimal("3800"), LocalDate.of(2023, 8, 1)),
                new SalesData("Sep", new BigDecimal("5100"), LocalDate.of(2023, 9, 1)),
                new SalesData("Oct", new BigDecimal("4600"), LocalDate.of(2023, 10, 1)),
                new SalesData("Nov", new BigDecimal("5400"), LocalDate.of(2023, 11, 1)),
                new SalesData("Dec", new BigDecimal("7200"), LocalDate.of(2023, 12, 1)),
                new SalesData("Jan", new BigDecimal("6100"), LocalDate.of(2024, 1, 1)),
                new SalesData("Feb", new BigDecimal("5900"), LocalDate.of(2024, 2, 1)),
                new SalesData("Mar", new BigDecimal("6800"), LocalDate.of(2024, 3, 1)),
                new SalesData("Apr", new BigDecimal("6300"), LocalDate.of(2024, 4, 1)),
                new SalesData("May", new BigDecimal("7100"), LocalDate.of(2024, 5, 1)),
                new SalesData("Jun", new BigDecimal("7500"), LocalDate.of(2024, 6, 1))
            };

            salesDataRepository.saveAll(Arrays.asList(salesData));
        }
    }
}