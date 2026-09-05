package br.com.yggdrasil.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yggdrasil.dto.VendaRequestDTO;
import br.com.yggdrasil.dto.VendaResponseDTO;
import br.com.yggdrasil.model.entity.Venda;
import br.com.yggdrasil.service.VendaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendas")
public class VendaController {
	
	private final VendaService vendaService;
	
	public VendaController(VendaService vendaService) {
		this.vendaService = vendaService;
	}
	
	@PostMapping
	public ResponseEntity<VendaResponseDTO> registrarVenda(@Valid @RequestBody VendaRequestDTO dto) {
		VendaResponseDTO venda = new VendaResponseDTO(vendaService.registrarVenda(dto));
		return ResponseEntity.status(HttpStatus.CREATED).body(venda);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VendaResponseDTO> obterVendaPorId(@PathVariable Long id) {
	    Venda venda = vendaService.obterVendaPorId(id);
	    VendaResponseDTO dto = new VendaResponseDTO(venda);
	    return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<Page<VendaResponseDTO>> obterUltimasVendas(
	        @RequestParam(defaultValue = "0") int numeroPagina,
	        @RequestParam(defaultValue = "10") int qtdePagina) {

	    Page<Venda> vendas = vendaService.getUltimasVendas(numeroPagina, qtdePagina);
	    Page<VendaResponseDTO> resposta = vendas.map(VendaResponseDTO::new);
	    return ResponseEntity.ok(resposta);
	}
}
