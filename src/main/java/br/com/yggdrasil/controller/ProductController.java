package br.com.yggdrasil.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yggdrasil.dto.StockRequestDTO;
import br.com.yggdrasil.model.entity.Product;
import br.com.yggdrasil.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService productService;
	
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('MANAGER')")
	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		Product savedProduct = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	}
	
    @PreAuthorize("hasRole('SALESPERSON')")
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
	    	Product product = productService.getProductById(id);
	    	return ResponseEntity.ok(product);
	}
	
    @PreAuthorize("hasRole('SALESPERSON')")
	@GetMapping
	public Page<Product> getProductByPage(
	        @RequestParam(defaultValue = "0") int pageNumber,
	        @RequestParam(defaultValue = "10") int pageSize) {
	    return productService.getProductsByPage(pageNumber, pageSize);
	}
	
    @PreAuthorize("hasRole('MANAGER')")
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid Product updatedProduct) {
		Product p = productService.updateProductById(id, updatedProduct);
		return ResponseEntity.ok(p);
	}
	
    @PreAuthorize("hasRole('MANAGER')")
	@PutMapping("/{id}/stock")
	public ResponseEntity<Void> alterarEstoque(
			@PathVariable Long id, 
			@Valid @RequestBody StockRequestDTO stockRequest) {
		productService.updateStock(id, stockRequest.getQuantity());
		return ResponseEntity.noContent().build();
	}
	
    @PreAuthorize("hasRole('SALESPERSON')")
	@PutMapping("/{id}/stock/add")
	public ResponseEntity<Void> addStock(
			@PathVariable Long id, 
			@Valid @RequestBody StockRequestDTO stockRequest) {
		productService.addStock(id, stockRequest.getQuantity());
		return ResponseEntity.noContent().build();
	}
	
    @PreAuthorize("hasRole('MANAGER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
	    productService.deleteById(id);
	    return ResponseEntity.noContent().build();
	}
	
}
