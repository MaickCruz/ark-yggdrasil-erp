package br.com.yggdrasil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yggdrasil.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByStockQuantityLessThanOrderByStockQuantityAsc(Integer minStock);
}
