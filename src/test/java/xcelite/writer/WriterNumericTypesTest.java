package xcelite.writer;


import org.junit.BeforeClass;
import org.junit.Test;
import xcelite.model.WriterNumericTypesBean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Test if all of Java's simple numeric types and all subclasses of
 * {@link Number} get written as numeric Excel cells.
 * Complete based on the Java 1.6 numeric types.
 *
 * Some tests reflect the fact that version 1.0.x did not know about all
 * numeric types in Java and wrote some as String cells.
 * Care must be taken to adapt those tests in future versions that purposely
 * deviate from the 1.0.x behavior
 */

public class WriterNumericTypesTest extends AbstractTestBaseForWriterTests {
    static final WriterNumericTypesBean bean = new WriterNumericTypesBean();

    @BeforeClass
    static public void setup()throws Exception {
        setup(bean);
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void mustWriteShortsOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        short val = bean.getShortSimpleType();
        Object obj = columnsMap.get("shortSimpleType");
        assertTrue( "Values of type short must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).shortValue());
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void mustWriteIntOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        int val = bean.getIntegerSimpleType();
        Object obj = columnsMap.get("integerSimpleType");
        assertTrue("Values of type int must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).intValue());
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */

    @Test
    public void mustWriteLongOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        long val = bean.getLongSimpleType();
        Object obj = columnsMap.get("longSimpleType");
        assertTrue("Values of type long must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).longValue());
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void mustWriteIntegerOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        int val = bean.getIntegerObjectType();
        Object obj = columnsMap.get("integerObjectType");
        assertTrue("Values of type Integer must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).intValue());
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void mustWriteLongObjectOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        Long val = bean.getLongObjectType();
        Object obj = columnsMap.get("longObjectType");
        assertTrue("Values of type Long must be written as numeric", (obj instanceof Number));
        assertEquals(val.longValue(), ((Number)obj).longValue());
    }

    /*
   COMPATIBILITY: Version 1.0.x writes BigInteger as String
                  Changed in version 1.2 and later; 1.2 and later must write BigInteger
                  as Double
   */
    @Test
    public void mustWriteBigIntegerTypeOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        BigInteger val = bean.getBigIntegerType();
        Object obj = columnsMap.get("bigIntegerType");
        assertTrue("Values of type BigInteger must be written as numeric", (obj instanceof String));
        assertEquals(val.longValue(), Long.parseLong((String)obj));
    }

    /*
    COMPATIBILITY: Version 1.0.x writes AtomicInteger as String
                   Changed in version 1.2 and later; 1.2 and later must write AtomicInteger
                   as Double
    */
    @Test
    public void mustWriteAtomicIntegerOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        AtomicInteger val = bean.getIntegerAtomicType();
        Object obj = columnsMap.get("integerAtomicType");
        assertTrue("Values of type AtomicInteger must be written as numeric", (obj instanceof String));
        assertEquals(val.intValue(), Integer.parseInt((String)obj));
    }
    /*
    COMPATIBILITY: Version 1.0.x writes AtomicLong as String
                   Changed in version 1.2 and later; 1.2 and later must write AtomicLong
                   as Double
    */
    @Test
    public void mustWriteAtomicLongOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        AtomicLong val = bean.getLongAtomicType();
        Object obj = columnsMap.get("longAtomicType");
        assertTrue("Values of type AtomicLong must be written as numeric", (obj instanceof String));
        assertEquals(val.longValue(), Long.parseLong((String)obj));
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void mustWriteFloatOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        float val = bean.getFloatSimpleType();
        Object obj = columnsMap.get("floatSimpleType");
        assertTrue("Values of type float must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).floatValue(), 0.0);
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void mustWriteDoubleOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        double val = bean.getDoubleSimpleType();
        Object obj = columnsMap.get("doubleSimpleType");
        assertTrue( "Values of type double must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).doubleValue(), 0.0);
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void mustWriteFloatObjectOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        Float val = bean.getFloatObjectType();
        Object obj = columnsMap.get("floatObjectType");
        assertTrue("Values of type Float must be written as numeric", (obj instanceof Number));
        assertEquals(val.floatValue(), ((Number)obj).floatValue(), 0.0);
    }

    @Test
    public void mustWriteDoubleObjectOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        Double val = bean.getDoubleObjectType();
        Object obj = columnsMap.get("doubleObjectType");
        assertTrue("Values of type Double must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).doubleValue(), 0.0);
    }

    /*
    COMPATIBILITY: Version 1.0.x writes BigDecimal as String
                   Changed in version 1.2 and later; 1.2 and later must write BigDecimal
                   as Double
    */
    @Test
    public void mustWriteBigDecimalOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        BigDecimal val = bean.getBigDecimalType();
        Object obj = columnsMap.get("bigDecimalType");
        assertTrue("Values of type BigDecimal must be written as numeric", (obj instanceof String));
        assertEquals(val.doubleValue(), Double.parseDouble((String)obj), 0.0);
    }

    /*
    COMPATIBILITY: Version 1.0.x writes Number as String
                   Changed in version 1.2 and later; 1.2 and later must write Number
                   as Double
    */
    @Test
    public void mustWritedNumberTypeOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        Number val = bean.getNumberType();
        Object obj = columnsMap.get("numberType");
        assertTrue(
                "Values of type Number must be written as numeric", (obj instanceof String));
        assertEquals(val.doubleValue(), Double.parseDouble((String)obj), 0.0);
    }
}
