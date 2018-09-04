package xcelite.writer;

import org.apache.poi.ss.SpreadsheetVersion;
import org.junit.BeforeClass;
import org.junit.Test;
import xcelite.model.BeanWriterTestsBean;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BeanWriterTests extends AbstractTestBaseForWriterTests {
    private static BeanWriterTestsBean bean = new BeanWriterTestsBean();

    @BeforeClass
    public static void setup() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SpreadsheetVersion.EXCEL2007.getMaxTextLength(); i++) {
            sb.append("a");
        }
        bean.setLongString(sb.toString());
        setup(bean);
    }

    @Test
    public void mustWriteLongStringsOK() throws Exception {
        Map<String, Object> columnsMap = extractCellValues (workbook);
        String val = bean.getLongString();
        Object obj = columnsMap.get("LONG_STRING");
        assertEquals(val, obj);
    }


}
