package csan.springframework.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import csan.springframework.commands.UnitOfMesureCommand;
import csan.springframework.converters.UnitOfMesureToUnitOfMesureCommand;
import csan.springframework.repositories.UnitOfMesureRepository;

@Service
public class UomServiceImpl implements UomService {
	
	UnitOfMesureRepository unitOfMesureRepository;
	UnitOfMesureToUnitOfMesureCommand unitOfMesureToUnitOfMesureCommand;

	public UomServiceImpl(UnitOfMesureRepository unitOfMesureRepository,
			UnitOfMesureToUnitOfMesureCommand unitOfMesureToUnitOfMesureCommand) {
		this.unitOfMesureRepository = unitOfMesureRepository;
		this.unitOfMesureToUnitOfMesureCommand = unitOfMesureToUnitOfMesureCommand;
	}

	@Override
	public Set<UnitOfMesureCommand> listAllUom() {
		Set<UnitOfMesureCommand> uomSet = new HashSet<>();
		unitOfMesureRepository.findAll()
		.iterator().forEachRemaining(uom ->uomSet.add(unitOfMesureToUnitOfMesureCommand.convert(uom)));
		return uomSet;
	}

}
