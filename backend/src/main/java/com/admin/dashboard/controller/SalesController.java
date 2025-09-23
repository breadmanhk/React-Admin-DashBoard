package com.admin.dashboard.controller;

import com.admin.dashboard.entity.SalesData;
import com.admin.dashboard.service.SalesDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class SalesController {

    @Autowired
    private SalesDataService salesDataService;

    @GetMapping
    public ResponseEntity<List<SalesData>> getAllSalesData() {
        List<SalesData> salesData = salesDataService.getAllSalesData();
        return ResponseEntity.ok(salesData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesData> getSalesDataById(@PathVariable Long id) {
        return salesDataService.getSalesDataById(id)
                .map(salesData -> ResponseEntity.ok(salesData))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SalesData> createSalesData(@Valid @RequestBody SalesData salesData) {
        SalesData createdSalesData = salesDataService.createSalesData(salesData);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSalesData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesData> updateSalesData(@PathVariable Long id, @Valid @RequestBody SalesData salesData) {
        try {
            SalesData updatedSalesData = salesDataService.updateSalesData(id, salesData);
            return ResponseEntity.ok(updatedSalesData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesData(@PathVariable Long id) {
        try {
            salesDataService.deleteSalesData(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<SalesData>> getSalesDataByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<SalesData> salesData = salesDataService.getSalesDataByDateRange(startDate, endDate);
        return ResponseEntity.ok(salesData);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SalesData>> getSalesDataByCategory(@PathVariable String category) {
        List<SalesData> salesData = salesDataService.getSalesDataByCategory(category);
        return ResponseEntity.ok(salesData);
    }

    @GetMapping("/by-category")
    public ResponseEntity<Map<String, Object>> getSalesByCategory() {
        List<Object[]> salesByCategory = salesDataService.getSalesByCategory();
        Map<String, Object> response = new HashMap<>();
        response.put("salesByCategory", salesByCategory);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/overview")
    public ResponseEntity<List<SalesData>> getSalesOverview() {
        List<SalesData> salesData = salesDataService.getAllSalesData();
        return ResponseEntity.ok(salesData);
    }
}