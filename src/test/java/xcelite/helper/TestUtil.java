package xcelite.helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtil {

    public static void testColumnsForPoiRow(Row r, List<String> columnNames) {
        List<String> lColNames = new ArrayList<String>(columnNames);
        int numCells = r.getPhysicalNumberOfCells();
        assertEquals("mismatching number of columns", lColNames.size(), numCells);
        for (int i = r.getFirstCellNum(); i < r.getLastCellNum(); i++) {
            String testVal = getCellValue(r.getCell(i));
            assertTrue("data in cell "+ i +" doesn't match test data", lColNames.contains(testVal));
            lColNames.remove(testVal);
        }
        assertTrue("names of columns do not match", lColNames.isEmpty());
    }


    public static String getCellValue(Cell cell) {
        CellType type = cell.getCellTypeEnum();
        String retVal = null;
        switch (type) {
            case STRING:
                retVal = cell.getStringCellValue();
                break;
            case NUMERIC:
                retVal = ((Double)cell.getNumericCellValue()).toString();
                break;
            case BOOLEAN:
                retVal = ((Boolean)cell.getBooleanCellValue()).toString();
                break;
            case ERROR:
                retVal = "#ERROR";
                break;
            default:
        }
        return retVal;
    }
}
