package br.com.yggdrasil.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yggdrasil.dto.RevenueResponseDTO;
import br.com.yggdrasil.dto.ProductDashboardDTO;
import br.com.yggdrasil.dto.SalespersonDashboardDTO;
import br.com.yggdrasil.model.entity.Product;
import br.com.yggdrasil.service.DashboardService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/dashboards")
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/best-selling-products")
	public ResponseEntity<List<ProductDashboardDTO>> getBestSellingProducts(@RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate, @RequestParam(defaultValue = "10") int limit) {

		return ResponseEntity.ok(dashboardService.getBestSellingProducts(startDate, endDate, limit));
	}

	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/most-profitable-products")
	public ResponseEntity<List<ProductDashboardDTO>> getMostProfitableProducts(@RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate, @RequestParam(defaultValue = "10") int limit) {

		return ResponseEntity.ok(dashboardService.getMostProfitableProducts(startDate, endDate, limit));
	}

	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/low-stock")
	public ResponseEntity<List<Product>> getLowStockProducts(
			@RequestParam(defaultValue = "10") @Min(value = 0, message = "Minimum stock cannot be negative.") int minStock) {
		return ResponseEntity.ok(dashboardService.getLowStockProducts(minStock));
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/revenue")
	public ResponseEntity<RevenueResponseDTO> getTotalRevenue() {
	    return ResponseEntity.ok(dashboardService.getTotalRevenue());
	}

	// TODO alterar para pegar o faturamento do ultimo mês ao invés dos ultimos 30 dias
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/revenue/last-30-days")
	public ResponseEntity<RevenueResponseDTO> getRevenueLast30Days() {
	    return ResponseEntity.ok(dashboardService.getRevenueLast30Days());
	}

	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/salespeople")
    public ResponseEntity<List<SalespersonDashboardDTO>> getSalespersonPerformance(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "10")
            @Min(1)
            @Max(50)
            int limit) {

        return ResponseEntity.ok(
                dashboardService.getSalespersonPerformance(
                        startDate,
                        endDate,
                        limit
                )
        );
    }

}