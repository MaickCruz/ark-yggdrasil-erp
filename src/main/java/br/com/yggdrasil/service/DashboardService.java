package br.com.yggdrasil.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.FaturamentoResponseDTO;
import br.com.yggdrasil.dto.ProdutoDashboardDTO;
import br.com.yggdrasil.dto.VendedorDashboardDTO;
import br.com.yggdrasil.model.entity.Product;
import br.com.yggdrasil.repository.ItemVendaRepository;
import br.com.yggdrasil.repository.ProductRepository;
import br.com.yggdrasil.repository.VendaRepository;

@Service
public class DashboardService {

	private final ItemVendaRepository itemVendaRepository;
	private final VendaRepository vendaRepository;
	private final ProductRepository produtoRepository;

	public DashboardService(ItemVendaRepository itemVendaRepository, ProductRepository produtoRepository, VendaRepository vendaRepository) {

		this.itemVendaRepository = itemVendaRepository;
		this.produtoRepository = produtoRepository;
		this.vendaRepository = vendaRepository;
	}

	public List<ProdutoDashboardDTO> obterProdutosMaisVendidos(LocalDate inicio, LocalDate fim, int limite) {

		LocalDateTime inicioDateTime = inicio.atStartOfDay();
		LocalDateTime fimDateTime = fim.plusDays(1).atStartOfDay();

		return itemVendaRepository.findProdutosMaisVendidos(inicioDateTime, fimDateTime, PageRequest.of(0, limite));
	}

	public List<ProdutoDashboardDTO> obterProdutosMaisLucrativos(LocalDate inicio, LocalDate fim, int limite) {

		LocalDateTime inicioDateTime = inicio.atStartOfDay();
		LocalDateTime fimDateTime = fim.plusDays(1).atStartOfDay();

		return itemVendaRepository.findProdutosMaisLucrativos(inicioDateTime, fimDateTime, PageRequest.of(0, limite));
	}

	public List<Product> obterProdutosComEstoqueBaixo(Integer estoqueMinimo) {
		return produtoRepository.findByStockQuantityLessThanOrderByStockQuantityAsc(estoqueMinimo);
	}
	
	public FaturamentoResponseDTO obterFaturamentoTotal() {
	    return vendaRepository.calcularFaturamentoTotal();
	}

	public FaturamentoResponseDTO obterFaturamentoUltimos30Dias() {
	    LocalDateTime inicio = LocalDateTime.now().minusDays(30);
	    return vendaRepository.calcularFaturamentoDesde(inicio);
	}
	
	public List<VendedorDashboardDTO> obterDesempenhoVendedores(
            LocalDate inicio,
            LocalDate fim,
            int limite) {

        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.plusDays(1).atStartOfDay();

        return vendaRepository.findDesempenhoVendedores(
                inicioDateTime,
                fimDateTime,
                PageRequest.of(0, limite)
        );
	}
	
}