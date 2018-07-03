package csan.springframework.converters;

import csan.springframework.commands.UnitOfMesureCommand;
import csan.springframework.model.UnitOfMesure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class UnitOfMesureCommandToUnitOfMesure implements Converter<UnitOfMesureCommand, UnitOfMesure>{

    @Synchronized
    @Nullable
    @Override
    public UnitOfMesure convert(UnitOfMesureCommand source) {
        if (source == null) {
            return null;
        }

        final UnitOfMesure uom = new UnitOfMesure();
        uom.setId(source.getId());
        uom.setUom(source.getUom());
        return uom;
    }
}