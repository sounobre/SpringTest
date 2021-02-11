package br.com.compasso.diego.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.compasso.diego.model.Cidade;

public interface CidadeRepository extends PagingAndSortingRepository<Cidade, Long> {

	Page<Cidade> findByCidadeContainingIgnoreCase(String cidade, Pageable pagination);

	Cidade findByCidadeIgnoreCase(String cidade);

	Page<Cidade> findByEstadoContainingIgnoreCase(String estado, Pageable pagination);
}
