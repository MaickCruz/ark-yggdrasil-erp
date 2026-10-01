package br.com.yggdrasil.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yggdrasil.dto.SaleRequestDTO;
import br.com.yggdrasil.dto.SaleResponseDTO;
import br.com.yggdrasil.model.entity.Sale;
import br.com.yggdrasil.service.SaleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SaleController {
	
	private final SaleService saleService;
	
	public SaleController(SaleService saleService) {
		this.saleService = saleService;
	}
	
	@PreAuthorize("hasRole('SALESPERSON')")
	@PostMapping
	public ResponseEntity<SaleResponseDTO> createSale(@Valid @RequestBody SaleRequestDTO dto) {
		
		SaleResponseDTO sale =
				new SaleResponseDTO(saleService.createSale(dto));
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(sale);
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/{id}")
	public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long id) {
	    Sale sale = saleService.getSaleById(id);
	    SaleResponseDTO response = new SaleResponseDTO(sale);
	    return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping
	public ResponseEntity<Page<SaleResponseDTO>> getLatestSales(
	        @RequestParam(defaultValue = "0") int pageNumber,
	        @RequestParam(defaultValue = "10") int pageSize) {

	    Page<Sale> sales = 
	    		saleService.getLatestSales(pageNumber, pageSize);
	    
	    Page<SaleResponseDTO> response = 
	    		sales.map(SaleResponseDTO::new);
	    
	    return ResponseEntity.ok(response);
	}
}
