package csan.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import csan.springframework.commands.UnitOfMesureCommand;
import csan.springframework.converters.UnitOfMesureToUnitOfMesureCommand;
import csan.springframework.model.UnitOfMesure;
import csan.springframework.repositories.UnitOfMesureRepository;

public class UomServiceImplTest {
	
	UnitOfMesureToUnitOfMesureCommand unitOfMesureToUnitOfMesureCommand;
	UomService service;
	
	@Mock
	UnitOfMesureRepository unitOfMesureRepository;

	public UomServiceImplTest() {
		this.unitOfMesureToUnitOfMesureCommand = new UnitOfMesureToUnitOfMesureCommand();
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new UomServiceImpl(unitOfMesureRepository, unitOfMesureToUnitOfMesureCommand);
	}

	@Test
	public void testListAllUom() {
		Set<UnitOfMesure> uomSet = new HashSet<>();
		UnitOfMesure uom1 = new UnitOfMesure();
		uom1.setId(1L);
		UnitOfMesure uom2 = new UnitOfMesure();
		uom2.setId(Long.valueOf("2"));
		uomSet.add(uom1);
		uomSet.add(uom2);
		
		when(unitOfMesureRepository.findAll()).thenReturn(uomSet);
		
		Set<UnitOfMesureCommand> commands = service.listAllUom();
		assertEquals(uomSet.size(), commands.size());
		verify(unitOfMesureRepository,times(1)).findAll();
	}

}
