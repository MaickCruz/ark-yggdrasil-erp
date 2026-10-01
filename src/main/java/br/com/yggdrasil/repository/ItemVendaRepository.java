package br.com.yggdrasil.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.yggdrasil.dto.ProdutoDashboardDTO;
import br.com.yggdrasil.model.entity.ItemVenda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

	// TODO:
	// Unificar os DTOs de métricas de produto em um único ProdutoDashboardDTO.
	// Os relatórios de "mais vendidos" e "mais lucrativos" devem utilizar
	// o mesmo DTO, diferenciando-se apenas pelo critério de ordenação.
	// Armazenar o preço de custo no ItemVenda no momento da venda para
	// preservar a fidelidade histórica dos cálculos.

	@Query("""
			    SELECT new br.com.yggdrasil.dto.ProdutoDashboardDTO(
			        p.id,
			        p.name,
			        SUM(iv.quantidade),
			        SUM(iv.precoUnitario * iv.quantidade),
			        SUM((iv.precoUnitario - iv.precoCusto) * iv.quantidade)
			    )
			    FROM ItemVenda iv
			    JOIN iv.produto p
			    JOIN iv.venda v
			    WHERE v.data >= :inicio
			      AND v.data < :fim
			    GROUP BY p.id, p.name
			    ORDER BY SUM(iv.quantidade) DESC
			""")
	List<ProdutoDashboardDTO> findProdutosMaisVendidos(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

	@Query("""
			    SELECT new br.com.yggdrasil.dto.ProdutoDashboardDTO(
			        p.id,
			        p.name,
			        SUM(iv.quantidade),
			        SUM(iv.precoUnitario * iv.quantidade),
			        SUM((iv.precoUnitario - iv.precoCusto) * iv.quantidade)
			    )
			    FROM ItemVenda iv
			    JOIN iv.produto p
			    JOIN iv.venda v
			    WHERE v.data >= :inicio
			      AND v.data < :fim
			    GROUP BY p.id, p.name
			    ORDER BY SUM((iv.precoUnitario - iv.precoCusto) * iv.quantidade) DESC
			""")
	List<ProdutoDashboardDTO> findProdutosMaisLucrativos(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

}
