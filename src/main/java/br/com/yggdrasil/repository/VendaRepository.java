package br.com.yggdrasil.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.yggdrasil.dto.FaturamentoResponseDTO;
import br.com.yggdrasil.dto.VendedorDashboardDTO;
import br.com.yggdrasil.model.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

	@Query("SELECT new br.com.yggdrasil.dto.FaturamentoResponseDTO(SUM(v.valorTotal), COUNT(v)) FROM Venda v")
	FaturamentoResponseDTO calcularFaturamentoTotal();

	@Query("SELECT new br.com.yggdrasil.dto.FaturamentoResponseDTO(SUM(v.valorTotal), COUNT(v)) FROM Venda v WHERE v.data >= :inicio")
	FaturamentoResponseDTO calcularFaturamentoDesde(@Param("inicio") LocalDateTime inicio);
	
    @Query("""
            SELECT new br.com.yggdrasil.dto.VendedorDashboardDTO(
                u.id,
                u.nome,
                COUNT(v.id),
                SUM(v.valorTotal)
            )
            FROM Venda v
            JOIN v.vendedor u
            WHERE v.data >= :inicio
              AND v.data < :fim
            GROUP BY u.id, u.nome
            ORDER BY SUM(v.valorTotal) DESC
        """)
        List<VendedorDashboardDTO> findDesempenhoVendedores(
                @Param("inicio") LocalDateTime inicio,
                @Param("fim") LocalDateTime fim,
                Pageable pageable
        );
	
	
}
