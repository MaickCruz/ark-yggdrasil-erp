package br.com.yggdrasil.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yggdrasil.dto.FaturamentoResponseDTO;
import br.com.yggdrasil.dto.ProdutoDashboardDTO;
import br.com.yggdrasil.dto.VendedorDashboardDTO;
import br.com.yggdrasil.model.entity.Produto;
import br.com.yggdrasil.service.DashboardService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/dashboards")
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@PreAuthorize("hasRole('GERENTE')")
	@GetMapping("/produtos-mais-vendidos")
	public ResponseEntity<List<ProdutoDashboardDTO>> obterProdutosMaisVendidos(@RequestParam LocalDate inicio,
			@RequestParam LocalDate fim, @RequestParam(defaultValue = "10") int limite) {

		return ResponseEntity.ok(dashboardService.obterProdutosMaisVendidos(inicio, fim, limite));
	}

	@PreAuthorize("hasRole('GERENTE')")
	@GetMapping("/produtos-mais-lucrativos")
	public ResponseEntity<List<ProdutoDashboardDTO>> obterProdutosMaisLucrativos(@RequestParam LocalDate inicio,
			@RequestParam LocalDate fim, @RequestParam(defaultValue = "10") int limite) {

		return ResponseEntity.ok(dashboardService.obterProdutosMaisLucrativos(inicio, fim, limite));
	}

	@PreAuthorize("hasRole('GERENTE')")
	@GetMapping("/estoque-baixo")
	public ResponseEntity<List<Produto>> obterProdutosComEstoqueBaixo(
			@RequestParam(defaultValue = "10") @Min(value = 0, message = "O estoque mínimo não pode ser negativo.") int estoqueMinimo) {
		return ResponseEntity.ok(dashboardService.obterProdutosComEstoqueBaixo(estoqueMinimo));
	}
	
	@PreAuthorize("hasRole('GERENTE')")
	@GetMapping("/faturamento")
	public ResponseEntity<FaturamentoResponseDTO> obterFaturamentoTotal() {
	    return ResponseEntity.ok(dashboardService.obterFaturamentoTotal());
	}

	@PreAuthorize("hasRole('GERENTE')")
	@GetMapping("/faturamento/ultimos-30-dias")
	public ResponseEntity<FaturamentoResponseDTO> obterFaturamentoUltimos30Dias() {
	    return ResponseEntity.ok(dashboardService.obterFaturamentoUltimos30Dias());
	}

	@PreAuthorize("hasRole('GERENTE')")
	@GetMapping("/vendedores")
    public ResponseEntity<List<VendedorDashboardDTO>> obterDesempenhoVendedores(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            @RequestParam(defaultValue = "10")
            @Min(1)
            @Max(50)
            int limite) {

        return ResponseEntity.ok(
                dashboardService.obterDesempenhoVendedores(
                        inicio,
                        fim,
                        limite
                )
        );
    }

}