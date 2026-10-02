package br.com.yggdrasil.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.SaleItemRequestDTO;
import br.com.yggdrasil.dto.SaleRequestDTO;
import br.com.yggdrasil.exception.InsufficientStockException;
import br.com.yggdrasil.exception.ProductNotFoundException;
import br.com.yggdrasil.exception.UserNotFoundException;
import br.com.yggdrasil.exception.SaleNotFoundException;
import br.com.yggdrasil.model.entity.SaleItem;
import br.com.yggdrasil.model.entity.Product;
import br.com.yggdrasil.model.entity.User;
import br.com.yggdrasil.model.entity.Sale;
import br.com.yggdrasil.repository.ProductRepository;
import br.com.yggdrasil.repository.UserRepository;
import br.com.yggdrasil.repository.SaleRepository;
import jakarta.transaction.Transactional;

@Service
public class SaleService {

	private final SaleRepository saleRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public SaleService(SaleRepository saleRepository, UserRepository userRepository, ProductRepository productRepository) {
		this.saleRepository = saleRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	@Transactional
	public Sale createSale(SaleRequestDTO dto) {
		User salesperson = userRepository.findById(dto.getSalespersonId())
                .orElseThrow(() -> new UserNotFoundException(dto.getSalespersonId()));
		
		Sale sale = new Sale();
        sale.setSalesperson(salesperson);		
        
		BigDecimal totalAmount = BigDecimal.ZERO;
		
		for(SaleItemRequestDTO itemDto: dto.getItems()) {
			
			Product product = productRepository.findById(itemDto.getProductId())
					.orElseThrow(() -> new ProductNotFoundException(itemDto.getProductId()));
			
			if(product.getStockQuantity() < itemDto.getQuantity()) {
				throw new InsufficientStockException(product.getName(), product.getStockQuantity(), itemDto.getQuantity());
			}
			
			product.setStockQuantity(product.getStockQuantity() - itemDto.getQuantity());
			
			SaleItem item = new SaleItem(); 
			item.setQuantity(itemDto.getQuantity());
			item.setUnitPrice(product.getSalePrice());
			item.setCostPrice(product.getCostPrice());
			item.setProduct(product);
			item.setSale(sale);
			
			sale.getItems().add(item);
			
			BigDecimal subtotal = product.getSalePrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
			totalAmount = totalAmount.add(subtotal);
			
		}
		
		sale.setTotalAmount(totalAmount);
		return saleRepository.save(sale);
	}
	
	public Sale getSaleById(Long id) {
	    return saleRepository.findById(id)
	            .orElseThrow(() -> new SaleNotFoundException(id));
	}
	
	public Page<Sale> getLatestSales(int pageNumber, int pageSize) {
		if(pageSize > 30) pageSize = 30;
	    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "saleDate"));
	    return saleRepository.findAll(pageRequest);
		
		
	}
	
}
