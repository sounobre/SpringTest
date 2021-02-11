package br.com.compasso.diego.controller.dto;

import br.com.compasso.diego.model.Cidade;

public class CidadeDTO {

	private Long id;
	private String cidade;
	private String estado;

	public CidadeDTO(Cidade cidade) {
		super();
		this.id = cidade.getId();
		this.cidade = cidade.getCidade();
		this.estado = cidade.getEstado();
	}

	public Long getId() {
		return id;
	}

	public String getCidade() {
		return cidade;
	}

	public String getEstado() {
		return estado;
	}
}
