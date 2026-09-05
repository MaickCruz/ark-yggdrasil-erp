package br.com.yggdrasil.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yggdrasil.dto.EstoqueRequestDTO;
import br.com.yggdrasil.model.entity.Produto;
import br.com.yggdrasil.service.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	private final ProdutoService produtoService;
	
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

	@PostMapping
	public ResponseEntity<Produto> cadastrarProduto(@Valid @RequestBody Produto produto) {
		Produto produtoSalvo = produtoService.cadastrarProduto(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> obterProdutoPorId(@PathVariable Long id) {
	    	Produto produto = produtoService.obterProdutoPorId(id);
	    	return ResponseEntity.ok(produto);
	}
	
	@GetMapping
	public Page<Produto> obterProdutoPorPagina(
	        @RequestParam(defaultValue = "0") int numeroPagina,
	        @RequestParam(defaultValue = "10") int qtdePagina) {
	    return produtoService.obterProdutoPorPagina(numeroPagina, qtdePagina);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> editarProduto(@PathVariable Long id, @RequestBody @Valid Produto produtoAtualizado) {
		Produto p = produtoService.editarProdutoPorId(id, produtoAtualizado);
		return ResponseEntity.ok(p);
	}
	
	@PutMapping("/{id}/alterar-estoque")
	public ResponseEntity<Void> alterarEstoque(
			@PathVariable Long id, 
			@Valid @RequestBody EstoqueRequestDTO qtdeProduto) {
		produtoService.alterarEstoque(id, qtdeProduto.getQuantidade());
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}/entrada-estoque")
	public ResponseEntity<Void> entradaEstoque(
			@PathVariable Long id, 
			@Valid @RequestBody EstoqueRequestDTO qtdeProduto) {
		produtoService.entradaEstoque(id, qtdeProduto.getQuantidade());
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProdutoPorId(@PathVariable Long id) {
	    produtoService.deletarProdutoPorId(id);
	    return ResponseEntity.noContent().build();
	}
	
}
