package br.com.compasso.diego.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.compasso.diego.model.Cidade;

public class CidadeForm {

	@NotNull
	@NotBlank
	private String cidade;
	private String estado;

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Cidade converter() {
		return new Cidade(cidade, estado);
	}
}
