package br.com.compasso.diego.controller.dto;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import br.com.compasso.diego.enums.Sexo;
import br.com.compasso.diego.model.Cliente;

public class ClienteDTO {

	private Long id;
	private String nome;
	private Sexo sexo;
	private LocalDate dtNasc;
	private Long idade;
	private String cidade;

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.sexo = cliente.getSexo();
		this.dtNasc = cliente.getDtNasc();
		this.idade = cliente.getIdade();
		this.cidade = cliente.getCidade().getCidade();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public LocalDate getDtNasc() {
		return dtNasc;
	}

	public Long getIdade() {
		return idade;
	}

	public String getCidade() {
		return cidade;
	}

	public static Page<ClienteDTO> converter(Page<Cliente> cliente) {
		return cliente.map(ClienteDTO::new);
	}
}
