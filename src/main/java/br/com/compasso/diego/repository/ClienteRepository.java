package br.com.compasso.diego.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.compasso.diego.model.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {

	Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pagination);
}
