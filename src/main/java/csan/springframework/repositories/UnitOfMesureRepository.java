package csan.springframework.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import csan.springframework.model.UnitOfMesure;

public interface UnitOfMesureRepository extends CrudRepository<UnitOfMesure, Long>{
	
	Optional<UnitOfMesure> findByUom (String uom);

}
