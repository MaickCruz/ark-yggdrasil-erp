package br.com.yggdrasil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yggdrasil.model.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	List<Produto> findByQuantidadeEstoqueLessThanOrderByQuantidadeEstoqueAsc(Integer estoqueMinimo);
}
