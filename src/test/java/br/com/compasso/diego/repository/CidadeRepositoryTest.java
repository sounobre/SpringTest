package br.com.compasso.diego.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.compasso.diego.model.Cidade;

@DataJpaTest
public class CidadeRepositoryTest {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Test
	public void shouldLoadCitiesIgnoringCases() {
		
		Cidade cidade = new Cidade("Rio de Janeiro", "Rio de Janeiro");
		cidadeRepository.save(cidade);
		
		Cidade cidade2 = cidadeRepository.findByCidadeIgnoreCase("rio de janeiro");		
		assertNotNull(cidade2);
		assertEquals("Rio de Janeiro", cidade2.getCidade());
	}
	
}
