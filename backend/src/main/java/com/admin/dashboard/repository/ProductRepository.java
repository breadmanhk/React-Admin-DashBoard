package com.admin.dashboard.repository;

import com.admin.dashboard.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Product> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    List<Product> findByCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.stock < :threshold")
    List<Product> findLowStockProducts(@Param("threshold") Integer threshold);

    @Query("SELECT p.category, COUNT(p) FROM Product p GROUP BY p.category")
    List<Object[]> countProductsByCategory();

    @Query("SELECT p FROM Product p ORDER BY p.sales DESC")
    List<Product> findTopSellingProducts(Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.stock > 0")
    long countProductsInStock();
}