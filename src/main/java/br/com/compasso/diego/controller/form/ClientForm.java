package br.com.compasso.diego.controller.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.compasso.diego.enums.Sexo;
import br.com.compasso.diego.model.Cidade;
import br.com.compasso.diego.model.Cliente;
import br.com.compasso.diego.repository.CidadeRepository;

public class ClientForm {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	private Sexo sexo;
	@NotNull
	private String dtNasc;
	@NotNull
	private String cidade;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public void setDtNasc(String dtNasc) {
		this.dtNasc = dtNasc;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Cliente converter(CidadeRepository cidadeRepository) {
		Cidade cidadeExistente = cidadeRepository.findByCidadeIgnoreCase(cidade);
		LocalDate dataNascimento = LocalDate.parse(dtNasc, formatter);
		Long idade = ChronoUnit.YEARS.between(dataNascimento, LocalDate.now());

		if (cidadeExistente == null) {
			Cidade novaCidade = new Cidade();
			novaCidade.setCidade(cidade);
			cidadeRepository.save(novaCidade);

			return this.create(nome, sexo, dataNascimento, novaCidade, idade);
		}
		return this.create(nome, sexo, dataNascimento, cidadeExistente, idade);
	}

	private Cliente create(@NotNull @NotBlank String nome, @NotNull Sexo sexo, @NotNull LocalDate dtNasc,
			Cidade novaCidade, Long idade) {
		Cliente cliente = new Cliente(nome, sexo, dtNasc, novaCidade);
		cliente.setIdade(idade);
		return cliente;
	}
}
