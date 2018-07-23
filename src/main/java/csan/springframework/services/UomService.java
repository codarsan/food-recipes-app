package csan.springframework.services;

import java.util.Set;
import csan.springframework.commands.UnitOfMesureCommand;

public interface UomService {
	Set<UnitOfMesureCommand> listAllUom();
}
