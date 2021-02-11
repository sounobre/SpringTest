package br.com.compasso.diego.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.diego.controller.dto.ClienteDTO;
import br.com.compasso.diego.controller.form.ClientForm;
import br.com.compasso.diego.controller.form.UpdateClientForm;
import br.com.compasso.diego.model.Cliente;
import br.com.compasso.diego.repository.CidadeRepository;
import br.com.compasso.diego.repository.ClienteRepository;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<ClienteDTO> create(@RequestBody @Valid ClientForm form, UriComponentsBuilder uriBuilder) {
			Cliente cliente = form.converter(cidadeRepository);
			clienteRepository.save(cliente);

			URI uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
			return ResponseEntity.created(uri).body(new ClienteDTO(cliente));
	}

	@GetMapping
	public Page<ClienteDTO> clientByName(@RequestParam String nome,
			@PageableDefault(sort = "nome", direction = Direction.ASC, page = 0, size = 10) Pageable pagination) {
		Page<Cliente> cliente = clienteRepository.findByNomeContainingIgnoreCase(nome, pagination);
		return ClienteDTO.converter(cliente);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> clientById(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(new ClienteDTO(cliente.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ClienteDTO> updateClientName(@PathVariable Long id, @RequestBody @Valid UpdateClientForm form) {
		Optional<Cliente> optional = clienteRepository.findById(id);
		if (optional.isPresent()) {
			Optional<Cliente> cliente = form.update(id, clienteRepository);
			return ResponseEntity.ok(new ClienteDTO(cliente.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteClient(@PathVariable Long id) {
		Optional<Cliente> optional = clienteRepository.findById(id);
		if (optional.isPresent()) {
			clienteRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
