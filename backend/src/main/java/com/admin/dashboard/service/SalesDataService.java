package com.admin.dashboard.service;

import com.admin.dashboard.entity.SalesData;
import com.admin.dashboard.repository.SalesDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalesDataService {

    @Autowired
    private SalesDataRepository salesDataRepository;

    public List<SalesData> getAllSalesData() {
        return salesDataRepository.findAllOrderByDateDesc();
    }

    public Optional<SalesData> getSalesDataById(Long id) {
        return salesDataRepository.findById(id);
    }

    public List<SalesData> getSalesDataByDateRange(LocalDate startDate, LocalDate endDate) {
        return salesDataRepository.findSalesDataByDateRange(startDate, endDate);
    }

    public List<SalesData> getSalesDataByCategory(String category) {
        return salesDataRepository.findByCategory(category);
    }

    public SalesData createSalesData(SalesData salesData) {
        return salesDataRepository.save(salesData);
    }

    public SalesData updateSalesData(Long id, SalesData salesDataDetails) {
        SalesData salesData = salesDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales data not found"));

        salesData.setPeriod(salesDataDetails.getPeriod());
        salesData.setSales(salesDataDetails.getSales());
        salesData.setSaleDate(salesDataDetails.getSaleDate());
        salesData.setCategory(salesDataDetails.getCategory());

        return salesDataRepository.save(salesData);
    }

    public void deleteSalesData(Long id) {
        if (!salesDataRepository.existsById(id)) {
            throw new RuntimeException("Sales data not found");
        }
        salesDataRepository.deleteById(id);
    }

    public List<Object[]> getSalesByCategory() {
        return salesDataRepository.getSalesByCategory();
    }
}