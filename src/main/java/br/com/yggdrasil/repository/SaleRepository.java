package br.com.yggdrasil.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.yggdrasil.dto.FaturamentoResponseDTO;
import br.com.yggdrasil.dto.VendedorDashboardDTO;
import br.com.yggdrasil.model.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
        SELECT new br.com.yggdrasil.dto.FaturamentoResponseDTO(
            SUM(s.totalAmount),
            COUNT(s)
        )
        FROM Sale s
    """)
    FaturamentoResponseDTO calcularFaturamentoTotal();

    @Query("""
        SELECT new br.com.yggdrasil.dto.FaturamentoResponseDTO(
            SUM(s.totalAmount),
            COUNT(s)
        )
        FROM Sale s
        WHERE s.saleDate >= :start
    """)
    FaturamentoResponseDTO calcularFaturamentoDesde(
            @Param("start") LocalDateTime start
    );

    @Query("""
        SELECT new br.com.yggdrasil.dto.VendedorDashboardDTO(
            u.id,
            u.name,
            COUNT(s.id),
            SUM(s.totalAmount)
        )
        FROM Sale s
        JOIN s.salesperson u
        WHERE s.saleDate >= :start
          AND s.saleDate < :end
        GROUP BY u.id, u.name
        ORDER BY SUM(s.totalAmount) DESC
    """)
    List<VendedorDashboardDTO> findDesempenhoVendedores(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );
}