package br.com.yggdrasil.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.ItemVendaRequestDTO;
import br.com.yggdrasil.dto.VendaRequestDTO;
import br.com.yggdrasil.exception.EstoqueInsuficienteException;
import br.com.yggdrasil.exception.ProductNotFoundException;
import br.com.yggdrasil.exception.UserNotFoundException;
import br.com.yggdrasil.exception.VendaNaoEncontradaException;
import br.com.yggdrasil.model.entity.ItemVenda;
import br.com.yggdrasil.model.entity.Product;
import br.com.yggdrasil.model.entity.User;
import br.com.yggdrasil.model.entity.Venda;
import br.com.yggdrasil.repository.ProductRepository;
import br.com.yggdrasil.repository.UserRepository;
import br.com.yggdrasil.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class VendaService {

	private final VendaRepository vendaRepository;
	private final UserRepository usuarioRepository;
	private final ProductRepository produtoRepository;

	public VendaService(VendaRepository vendaRepository, UserRepository usuarioRepository,
			ProductRepository produtoRepository) {
		this.vendaRepository = vendaRepository;
		this.usuarioRepository = usuarioRepository;
		this.produtoRepository = produtoRepository;
	}

	@Transactional
	public Venda registrarVenda(VendaRequestDTO dto) {
		User vendedor = usuarioRepository.findById(dto.getVendedorId())
                .orElseThrow(() -> new UserNotFoundException(dto.getVendedorId()));
		
		Venda venda = new Venda();
		venda.setVendedor(vendedor);
		
		BigDecimal valorTotal = BigDecimal.ZERO;
		
		for(ItemVendaRequestDTO itemDto: dto.getItens()) {
			Product produto = produtoRepository.findById(itemDto.getProdutoId())
					.orElseThrow(() -> new ProductNotFoundException(itemDto.getProdutoId()));
			
			if(produto.getStockQuantity() < itemDto.getQuantidade()) {
				throw new EstoqueInsuficienteException(produto.getName(), produto.getStockQuantity(), itemDto.getQuantidade());
			}
			
			produto.setStockQuantity(produto.getStockQuantity() - itemDto.getQuantidade());
			
			ItemVenda item = new ItemVenda(); 
			item.setQuantidade(itemDto.getQuantidade());
			item.setPrecoUnitario(produto.getSalePrice());
			item.setPrecoCusto(produto.getCostPrice());
			item.setProduto(produto);
			item.setVenda(venda);
			
			venda.getItens().add(item);
			
			BigDecimal subtotal = produto.getSalePrice().multiply(BigDecimal.valueOf(itemDto.getQuantidade()));
			valorTotal = valorTotal.add(subtotal);
			
		}
		
		venda.setValorTotal(valorTotal);
		return vendaRepository.save(venda);
	}
	
	public Venda obterVendaPorId(Long id) {
	    return vendaRepository.findById(id)
	            .orElseThrow(() -> new VendaNaoEncontradaException(id));
	}
	
	public Page<Venda> getUltimasVendas(int numeroPagina, int qtdePagina) {
		if(qtdePagina > 30) qtdePagina = 30;
	    PageRequest page = PageRequest.of(numeroPagina, qtdePagina, Sort.by(Sort.Direction.DESC, "data"));
	    return vendaRepository.findAll(page);
		
		
	}
	
}
