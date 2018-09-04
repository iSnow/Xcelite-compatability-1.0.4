package xcelite.reader;

import xcelite.model.UsStringCellDateConverter;

import java.text.SimpleDateFormat;

public class BeanReaderTests  {
    private SimpleDateFormat df = new SimpleDateFormat(UsStringCellDateConverter.DATE_PATTERN);

    private static String usTestData[][] = {
            {"Crystal",	"Maiden",	"01/02/1990",	"female"},
            {"Witch",	"Doctor",	"01/01/1990",	"male"}
    };

    /*
    @Test
    void mustReadHeaderRowWithMissingCellsOK() {
        XceliteOptions options = new XceliteOptions();
        options.setSkipRowsBeforeColumnDefinitionRow(3);

        List<CamelCase> upper = getData(options, "src/test/resources/ColumnDefinitionRowHasEmptyCells.xlsx");
        validateData(upper);
    }

    @Test
    void mustReadHeaderRowWithMissingCellsWithMissingCellPolicy_RETURN_BLANK_AS_NULL_OK() {
        XceliteOptions options = new XceliteOptions();
        options.setSkipRowsBeforeColumnDefinitionRow(3);
        options.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

        List<CamelCase> upper = getData(options, "src/test/resources/ColumnDefinitionRowHasEmptyCells.xlsx");
        validateData(upper);
    }

    @Test
    void mustReadHeaderRowWithMissingCellsWithMissingCellPolicy_RETURN_NULL_AND_BLANK_OK() {
        XceliteOptions options = new XceliteOptions();
        options.setSkipRowsBeforeColumnDefinitionRow(3);
        options.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);

        List<CamelCase> upper = getData(options, "src/test/resources/ColumnDefinitionRowHasEmptyCells.xlsx");
        validateData(upper);
    }
    @Test
    public void mustThrowReadingHeaderRowWithMissingCellsOK() {
        XceliteOptions options = new XceliteOptions();
        options.setSkipRowsBeforeColumnDefinitionRow(3);
        options.setMissingCellPolicy(Row.MissingCellPolicy.THROW);

        assertThrows(EmptyCellException.class, () -> {
            List<CamelCase> upper = getData(options, "src/test/resources/ColumnDefinitionRowHasEmptyCells.xlsx");
            validateData(upper);
        });
    }


    private List<CamelCase> getData(XceliteOptions options, String filePath)  {
        Compat_Xcelite xcelite = new Compat_Xcelite(new File(filePath));
        Compat_XceliteSheet sheet = xcelite.getSheet(0);
        Compat_SheetReader<CamelCase> beanReader = new Compat_BeanSheetReader<CamelCase>(sheet, options, CamelCase.class);
        ArrayList<CamelCase> data = new ArrayList<CamelCase>(beanReader.read());
        return data;
    }

    private void validateData(List<CamelCase> data) {
        CamelCase first = data.get(0);
        assertEquals(usTestData[0][0], first.getName(), "Name mismatch");
        assertEquals(usTestData[0][1], first.getSurname(), "Surname mismatch");
        assertEquals(df.parse(usTestData[0][2]), first.getBirthDate(), "Birthdate mismatch");

        CamelCase second = data.get(1);
        assertEquals(usTestData[1][0], second.getName(), "Name mismatch");
        assertEquals(usTestData[1][1], second.getSurname(), "Surname mismatch");
        assertEquals(df.parse(usTestData[1][2]), second.getBirthDate(), "Birthdate mismatch");
    }
*/
}
