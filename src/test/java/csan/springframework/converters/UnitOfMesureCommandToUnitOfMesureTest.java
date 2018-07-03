package csan.springframework.converters;

import static org.junit.Assert.*;

import csan.springframework.commands.UnitOfMesureCommand;
import csan.springframework.model.UnitOfMesure;
import org.junit.Before;
import org.junit.Test;

public class UnitOfMesureCommandToUnitOfMesureTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    UnitOfMesureCommandToUnitOfMesure converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMesureCommandToUnitOfMesure();

    }

    @Test
    public void testNullParamter() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMesureCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMesureCommand command = new UnitOfMesureCommand();
        command.setId(LONG_VALUE);
        command.setUom(DESCRIPTION);

        //when
        UnitOfMesure uom = converter.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getUom());

}
}
