package br.com.yggdrasil.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.exception.ProdutoNaoEncontradoException;
import br.com.yggdrasil.model.entity.Produto;
import br.com.yggdrasil.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
		
	public Produto cadastrarProduto(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto obterProdutoPorId(Long id) {
			return produtoRepository.findById(id)
					.orElseThrow(() -> new ProdutoNaoEncontradoException(id));
	}
	
	public Page<Produto> obterProdutoPorPagina(int numeroPagina, int qtdePagina) {
	    if (qtdePagina >= 30) qtdePagina = 30;
	    PageRequest page = PageRequest.of(numeroPagina, qtdePagina);
	    return produtoRepository.findAll(page);
	}
	
	public Produto editarProdutoPorId(Long id, Produto produtoAtualizado) {
		return produtoRepository.findById(id).map(produtoExistente -> {
			
			produtoExistente.setNome(produtoAtualizado.getNome());
			produtoExistente.setDescricao(produtoAtualizado.getDescricao());
			produtoExistente.setPrecoCusto(produtoAtualizado.getPrecoCusto());
			produtoExistente.setPrecoVenda(produtoAtualizado.getPrecoVenda());
			produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
			return produtoRepository.save(produtoExistente);
			
		}).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
	}
	
	//somente gerente+
	public Produto alterarEstoque(Long id, Integer qtdeProdutos) {
		return produtoRepository.findById(id).map(produtoExistente -> {
			
			produtoExistente.setQuantidadeEstoque(qtdeProdutos);
			return produtoRepository.save(produtoExistente);
			
		}).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
	}
	
	public Produto entradaEstoque(Long id, Integer qtdeProdutos) {
		return produtoRepository.findById(id).map(produtoExistente -> {
			
			produtoExistente.setQuantidadeEstoque(produtoExistente.getQuantidadeEstoque() + qtdeProdutos);
			return produtoRepository.save(produtoExistente);
			
		}).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
	}
	
	public void deletarProdutoPorId(Long id) {
		if(!produtoRepository.existsById(id)) {
			throw new ProdutoNaoEncontradoException(id);
		}
		produtoRepository.deleteById(id);
	}
}
