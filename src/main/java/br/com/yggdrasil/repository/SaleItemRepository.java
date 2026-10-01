package br.com.yggdrasil.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.yggdrasil.dto.ProductDashboardDTO;
import br.com.yggdrasil.model.entity.SaleItem;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    // TODO:
    // Consolidate product metric DTOs into a single ProdutoDashboardDTO.
    // The "best-selling" and "most profitable" reports should use
    // the same DTO and differ only by sorting criteria.
    // Cost price must remain stored in SaleItem at the time of sale
    // to preserve historical accuracy.

    @Query("""
        SELECT new br.com.yggdrasil.dto.ProductDashboardDTO(
            p.id,
            p.name,
            SUM(si.quantity),
            SUM(si.unitPrice * si.quantity),
            SUM((si.unitPrice - si.costPrice) * si.quantity)
        )
        FROM SaleItem si
        JOIN si.product p
        JOIN si.sale s
        WHERE s.saleDate >= :start
          AND s.saleDate < :end
        GROUP BY p.id, p.name
        ORDER BY SUM(si.quantity) DESC
    """)
    List<ProductDashboardDTO> findBestSellingProducts(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("""
        SELECT new br.com.yggdrasil.dto.ProductDashboardDTO(
            p.id,
            p.name,
            SUM(si.quantity),
            SUM(si.unitPrice * si.quantity),
            SUM((si.unitPrice - si.costPrice) * si.quantity)
        )
        FROM SaleItem si
        JOIN si.product p
        JOIN si.sale s
        WHERE s.saleDate >= :start
          AND s.saleDate < :end
        GROUP BY p.id, p.name
        ORDER BY SUM((si.unitPrice - si.costPrice) * si.quantity) DESC
    """)
    List<ProductDashboardDTO> findMostProfitableProducts(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );
}