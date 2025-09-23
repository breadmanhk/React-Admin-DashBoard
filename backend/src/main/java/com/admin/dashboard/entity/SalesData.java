package com.admin.dashboard.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sales_data")
public class SalesData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month", nullable = false)
    private String period; // Jul, Aug, Sep, etc.

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sales;

    @Column(name = "date", nullable = false)
    private LocalDate saleDate;

    @Column
    private String category;

    // Constructors
    public SalesData() {}

    public SalesData(String period, BigDecimal sales, LocalDate saleDate) {
        this.period = period;
        this.sales = sales;
        this.saleDate = saleDate;
    }

    public SalesData(String period, BigDecimal sales, LocalDate saleDate, String category) {
        this.period = period;
        this.sales = sales;
        this.saleDate = saleDate;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public BigDecimal getSales() { return sales; }
    public void setSales(BigDecimal sales) { this.sales = sales; }

    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}