package br.com.yggdrasil.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.exception.ProductNotFoundException;
import br.com.yggdrasil.model.entity.Product;
import br.com.yggdrasil.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
		
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Product getProductById(Long id) {
			return productRepository.findById(id)
					.orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	public Page<Product> getProductsByPage(int pageNumber, int pageSize) {
	    
		if (pageSize >= 30) pageSize = 30;
	    
	    PageRequest page = PageRequest.of(pageNumber, pageSize);
	    return productRepository.findAll(page);
	
	}
	
	public Product updateProductById(Long id, Product updatedProduct) {
		
		return productRepository.findById(id).map(existingProduct -> {
			
			existingProduct.setName(updatedProduct.getName());
			existingProduct.setDescription(updatedProduct.getDescription());
			existingProduct.setCostPrice(updatedProduct.getCostPrice());
			existingProduct.setSalePrice(updatedProduct.getSalePrice());
			existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
			
			return productRepository.save(existingProduct);
			
		}).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	// Manager or higher
	public Product updateStock(Long id, Integer quantity) {
		return productRepository.findById(id).map(existingProduct -> {
			
			existingProduct.setStockQuantity(quantity);
			return productRepository.save(existingProduct);
			
		}).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	// salesperson
	public Product addStock(Long id, Integer quantity) {
		return productRepository.findById(id).map(existingProduct -> {
			
			existingProduct.setStockQuantity(existingProduct.getStockQuantity() + quantity);
			return productRepository.save(existingProduct);
			
		}).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	public void deleteById(Long id) {
		if(!productRepository.existsById(id)) {
			throw new ProductNotFoundException(id);
		}
		productRepository.deleteById(id);
	}
}
