package br.com.yggdrasil.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.RevenueResponseDTO;
import br.com.yggdrasil.dto.ProductDashboardDTO;
import br.com.yggdrasil.dto.SalespersonDashboardDTO;
import br.com.yggdrasil.model.entity.Product;
import br.com.yggdrasil.repository.SaleItemRepository;
import br.com.yggdrasil.repository.ProductRepository;
import br.com.yggdrasil.repository.SaleRepository;

@Service
public class DashboardService {

	private final SaleItemRepository saleItemRepository;
	private final SaleRepository saleRepository;
	private final ProductRepository productRepository;

	public DashboardService(SaleItemRepository saleItemRepository, ProductRepository saleRepository, SaleRepository productRepository) {

		this.saleItemRepository = saleItemRepository;
		this.productRepository = saleRepository;
		this.saleRepository = productRepository;
	}

	public List<ProductDashboardDTO> getBestSellingProducts(LocalDate startDate, LocalDate endDate, int limit) {

		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

		return saleItemRepository.findBestSellingProducts(startDateTime, endDateTime, PageRequest.of(0, limit));
	}

	public List<ProductDashboardDTO> getMostProfitableProducts(LocalDate startDate, LocalDate endDate, int limit) {

		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

		return saleItemRepository.findMostProfitableProducts(startDateTime, endDateTime, PageRequest.of(0, limit));
	}

	public List<Product> getLowStockProducts(Integer minStock) {
		return productRepository.findByStockQuantityLessThanOrderByStockQuantityAsc(minStock);
	}
	
	public RevenueResponseDTO getTotalRevenue() {
	    return saleRepository.calculateTotalRevenue();
	}

	public RevenueResponseDTO getRevenueLast30Days() {
	    LocalDateTime startDate = LocalDateTime.now().minusDays(30);
	    return saleRepository.calculateRevenueSince(startDate);
	}
	
	public List<SalespersonDashboardDTO> getSalespersonPerformance(
            LocalDate startDate,
            LocalDate endDate,
            int limit) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        return saleRepository.findSalespersonPerformance(
                startDateTime,
                endDateTime,
                PageRequest.of(0, limit)
        );
	}
	
}