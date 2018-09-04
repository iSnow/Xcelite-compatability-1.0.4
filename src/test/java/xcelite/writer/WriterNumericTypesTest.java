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
 */

public class WriterNumericTypesTest extends AbstractTestBaseForWriterTests {
    static final WriterNumericTypesBean bean = new WriterNumericTypesBean();

    @BeforeClass
    static public void setup()throws Exception {
        setup(bean);
    }

    @Test
    public void mustWriteShortsOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        short val = bean.getShortSimpleType();
        Object obj = columnsMap.get("shortSimpleType");
        assertTrue( "Values of type short must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).shortValue());
    }

    @Test
    public void mustWriteIntOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        int val = bean.getIntegerSimpleType();
        Object obj = columnsMap.get("integerSimpleType");
        assertTrue("Values of type int must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).intValue());
    }

    @Test
    public void mustWriteLongOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        long val = bean.getLongSimpleType();
        Object obj = columnsMap.get("longSimpleType");
        assertTrue("Values of type long must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).longValue());
    }


    @Test
    public void mustWriteIntegerOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        int val = bean.getIntegerObjectType();
        Object obj = columnsMap.get("integerObjectType");
        assertTrue("Values of type Integer must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).intValue());
    }


    @Test
    public void mustWriteLongObjectOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        Long val = bean.getLongObjectType();
        Object obj = columnsMap.get("longObjectType");
        assertTrue("Values of type Long must be written as numeric", (obj instanceof Number));
        assertEquals(val.longValue(), ((Number)obj).longValue());
    }

    @Test
    public void mustWriteBigIntegerTypeOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        BigInteger val = bean.getBigIntegerType();
        Object obj = columnsMap.get("bigIntegerType");
        assertTrue("Values of type BigInteger must be written as numeric", (obj instanceof Number));
        assertEquals(val.longValue(), ((Number)obj).longValue());
    }

    @Test
    public void mustWriteAtomicIntegerOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        AtomicInteger val = bean.getIntegerAtomicType();
        Object obj = columnsMap.get("integerAtomicType");
        assertTrue("Values of type AtomicInteger must be written as numeric", (obj instanceof Number));
        assertEquals(val.intValue(), ((Number)obj).intValue());
    }

    @Test
    public void mustWriteAtomicLongOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        AtomicLong val = bean.getLongAtomicType();
        Object obj = columnsMap.get("longAtomicType");
        assertTrue("Values of type AtomicLong must be written as numeric", (obj instanceof Number));
        assertEquals(val.longValue(), ((Number)obj).longValue());
    }

    @Test
    public void mustWriteFloatOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        float val = bean.getFloatSimpleType();
        Object obj = columnsMap.get("floatSimpleType");
        assertTrue("Values of type float must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).floatValue());
    }

    @Test
    public void mustWriteDoubleOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        double val = bean.getDoubleSimpleType();
        Object obj = columnsMap.get("doubleSimpleType");
        assertTrue( "Values of type double must be written as numeric", (obj instanceof Number));
        assertEquals(val, ((Number)obj).doubleValue());
    }

    @Test
    public void mustWriteFloatObjectOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        Float val = bean.getFloatObjectType();
        Object obj = columnsMap.get("floatObjectType");
        assertTrue("Values of type Float must be written as numeric", (obj instanceof Number));
        assertEquals(val.floatValue(), ((Number)obj).floatValue());
    }

    @Test
    public void mustWriteDoubleObjectOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        Double val = bean.getDoubleObjectType();
        Object obj = columnsMap.get("doubleObjectType");
        assertTrue("Values of type Double must be written as numeric", (obj instanceof Number));
        assertEquals(val.doubleValue(), ((Number)obj).doubleValue());
    }

    @Test
    public void mustWriteBigDecimalOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        BigDecimal val = bean.getBigDecimalType();
        Object obj = columnsMap.get("bigDecimalType");
        assertTrue("Values of type BigDecimal must be written as numeric", (obj instanceof Number));
        assertEquals(val.doubleValue(), ((Number)obj).doubleValue());
    }


    @Test
    public void mustWritedNumberTypeOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        Number val = bean.getNumberType();
        Object obj = columnsMap.get("numberType");
        assertTrue(
                "Values of type Number must be written as numeric", (obj instanceof Number));
        assertEquals(val.doubleValue(), ((Number)obj).doubleValue());
    }
}
