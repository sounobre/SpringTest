package br.com.compasso.diego.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.diego.controller.dto.CidadeDTO;
import br.com.compasso.diego.controller.form.CidadeForm;
import br.com.compasso.diego.model.Cidade;
import br.com.compasso.diego.repository.CidadeRepository;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeCidades", allEntries = true)
	public ResponseEntity<CidadeDTO> create(@RequestBody @Valid CidadeForm form, UriComponentsBuilder uriBuilder) {
		Cidade cidade = form.converter();
		cidadeRepository.save(cidade);

		URI uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cidade.getId()).toUri();
		return ResponseEntity.created(uri).body(new CidadeDTO(cidade));
	}

	@GetMapping
	@Cacheable(value = "listaDeCidades")
	public Page<Cidade> clientByName(@RequestParam(name = "cidade", required = false) String cidade,
			@RequestParam(name = "estado", required = false) String estado,
			@PageableDefault(sort = "cidade", direction = Direction.ASC, page = 0, size = 10) Pageable pagination) {

		if (cidade != null) {
			return cidadeRepository.findByCidadeContainingIgnoreCase(cidade, pagination);
		} else if (estado != null) {
			return cidadeRepository.findByEstadoContainingIgnoreCase(estado, pagination);
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reveja as informações enviadas por favor!");
	}
}
