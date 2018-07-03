package csan.springframework.converters;

import csan.springframework.commands.UnitOfMesureCommand;
import csan.springframework.model.UnitOfMesure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMesureToUnitOfMesureCommand implements Converter<UnitOfMesure, UnitOfMesureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMesureCommand convert(UnitOfMesure unitOfMeasure) {

        if (unitOfMeasure != null) {
            final UnitOfMesureCommand uomc = new UnitOfMesureCommand();
            uomc.setId(unitOfMeasure.getId());
            uomc.setUom(unitOfMeasure.getUom());
            return uomc;
        }
        return null;
    }
}
