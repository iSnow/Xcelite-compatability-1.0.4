package xcelite.writer;


import org.junit.BeforeClass;
import org.junit.Test;
import xcelite.model.AbstractWriterTestsBean;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AbstractWriterTests extends AbstractTestBaseForWriterTests {
    private static AbstractWriterTestsBean bean = new AbstractWriterTestsBean();

    @BeforeClass
    public static void setup() throws Exception {
        setup(bean);
    }

    /*
    COMPATIBILITY: version 1.0.x writes boolean types as String,
                   version 1.2 and later must write boolean as Boolean cells
    */
    @Test
    public void mustWriteBooleanSimpleTypeOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        boolean val = bean.isBooleanSimpleType();
        Object obj = columnsMap.get("booleanSimpleType");
        assertEquals("Values of type boolean must be written as Boolean", String.class, obj.getClass());
        assertEquals(Boolean.toString(val), obj);
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void mustWriteBooleanObjectTypeOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        Boolean val = bean.getBooleanObjectType();
        Object obj = columnsMap.get("booleanObjectType");
        assertEquals("Values of type Boolean must be written as Boolean", val.getClass(), obj.getClass());
        assertEquals(val, obj);
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void mustWriteStringOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        String val = bean.getStringType();
        Object obj = columnsMap.get("stringType");
        assertEquals("Values of type String must be written as String", val.getClass(), obj.getClass());
        assertEquals(val, obj);
    }

}
