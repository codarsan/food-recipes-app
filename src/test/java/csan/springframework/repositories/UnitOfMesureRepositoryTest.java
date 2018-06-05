package csan.springframework.repositories;

import static org.junit.Assert.*;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import csan.springframework.model.UnitOfMesure;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMesureRepositoryTest {

	@Autowired
	UnitOfMesureRepository unitOfMesureRepository;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindByUom() {
		Optional<UnitOfMesure> findByUom = unitOfMesureRepository.findByUom("Tablespoon");
		assertEquals("Tablespoon", findByUom.get().getUom());
	}

	@Test
	public void testFindByUomCup() {
		Optional<UnitOfMesure> findByUom = unitOfMesureRepository.findByUom("Cup");
		assertEquals("Cup", findByUom.get().getUom());
	}
}
