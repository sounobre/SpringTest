package br.com.compasso.diego.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.compasso.diego.model.Cliente;
import br.com.compasso.diego.repository.ClienteRepository;

public class UpdateClientForm {

	@NotNull
	@NotEmpty
	private String nome;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Optional<Cliente> update(Long id, ClienteRepository clienteRepository) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		cliente.get().setNome(this.nome);

		return cliente;
	}
}
