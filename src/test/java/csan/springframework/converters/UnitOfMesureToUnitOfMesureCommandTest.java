package csan.springframework.converters;

import static org.junit.Assert.*;
import csan.springframework.commands.UnitOfMesureCommand;
import csan.springframework.model.UnitOfMesure;
import org.junit.Before;
import org.junit.Test;

public class UnitOfMesureToUnitOfMesureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    UnitOfMesureToUnitOfMesureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMesureToUnitOfMesureCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(converter.convert(new UnitOfMesure()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMesure uom = new UnitOfMesure();
        uom.setId(LONG_VALUE);
        uom.setUom(DESCRIPTION);
        //when
        UnitOfMesureCommand uomc = converter.convert(uom);

        //then
        assertEquals(LONG_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getUom());
    }

}