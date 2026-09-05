package br.com.yggdrasil.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.ItemVendaRequestDTO;
import br.com.yggdrasil.dto.VendaRequestDTO;
import br.com.yggdrasil.exception.EstoqueInsuficienteException;
import br.com.yggdrasil.exception.ProdutoNaoEncontradoException;
import br.com.yggdrasil.exception.UsuarioNaoEncontradoException;
import br.com.yggdrasil.exception.VendaNaoEncontradaException;
import br.com.yggdrasil.model.entity.ItemVenda;
import br.com.yggdrasil.model.entity.Produto;
import br.com.yggdrasil.model.entity.Usuario;
import br.com.yggdrasil.model.entity.Venda;
import br.com.yggdrasil.repository.ProdutoRepository;
import br.com.yggdrasil.repository.UsuarioRepository;
import br.com.yggdrasil.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class VendaService {

	private final VendaRepository vendaRepository;
	private final UsuarioRepository usuarioRepository;
	private final ProdutoRepository produtoRepository;

	public VendaService(VendaRepository vendaRepository, UsuarioRepository usuarioRepository,
			ProdutoRepository produtoRepository) {
		this.vendaRepository = vendaRepository;
		this.usuarioRepository = usuarioRepository;
		this.produtoRepository = produtoRepository;
	}

	@Transactional
	public Venda registrarVenda(VendaRequestDTO dto) {
		Usuario vendedor = usuarioRepository.findById(dto.getVendedorId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dto.getVendedorId()));
		
		Venda venda = new Venda();
		venda.setVendedor(vendedor);
		
		BigDecimal valorTotal = BigDecimal.ZERO;
		
		for(ItemVendaRequestDTO itemDto: dto.getItens()) {
			Produto produto = produtoRepository.findById(itemDto.getProdutoId())
					.orElseThrow(() -> new ProdutoNaoEncontradoException(itemDto.getProdutoId()));
			
			if(produto.getQuantidadeEstoque() < itemDto.getQuantidade()) {
				throw new EstoqueInsuficienteException(produto.getNome(), produto.getQuantidadeEstoque(), itemDto.getQuantidade());
			}
			
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemDto.getQuantidade());
			
			ItemVenda item = new ItemVenda(); 
			item.setQuantidade(itemDto.getQuantidade());
			item.setPrecoUnitario(produto.getPrecoVenda());
			item.setPrecoCusto(produto.getPrecoCusto());
			item.setProduto(produto);
			item.setVenda(venda);
			
			venda.getItens().add(item);
			
			BigDecimal subtotal = produto.getPrecoVenda().multiply(BigDecimal.valueOf(itemDto.getQuantidade()));
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
