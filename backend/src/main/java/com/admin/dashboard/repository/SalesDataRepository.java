package com.admin.dashboard.repository;

import com.admin.dashboard.entity.SalesData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesDataRepository extends JpaRepository<SalesData, Long> {

    @Query("SELECT s FROM SalesData s WHERE s.saleDate BETWEEN :startDate AND :endDate ORDER BY s.saleDate")
    List<SalesData> findSalesDataByDateRange(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM SalesData s WHERE s.category = :category ORDER BY s.saleDate")
    List<SalesData> findByCategory(@Param("category") String category);

    @Query("SELECT s.category, SUM(s.sales) FROM SalesData s GROUP BY s.category")
    List<Object[]> getSalesByCategory();

    @Query("SELECT s FROM SalesData s ORDER BY s.saleDate DESC")
    List<SalesData> findAllOrderByDateDesc();
}