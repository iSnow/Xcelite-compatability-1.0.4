/*
 * Copyright 2018 Thanthathon.b.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xcelite.reader;

import compat.com.ebay.xcelite_104.Compat_Xcelite;
import compat.com.ebay.xcelite_104.reader.Compat_BeanSheetReader;
import compat.com.ebay.xcelite_104.reader.Compat_SheetReader;
import compat.com.ebay.xcelite_104.sheet.Compat_XceliteSheet;
import org.junit.Test;
import xcelite.model.CamelCase;
import xcelite.model.UsStringCellDateConverter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test various combinations of completely empty or not empty rows before and after
 * the column definition row
 *
 * @author Johannes
 */
public class PaddingAroundColumnDefinitionTest {
    private SimpleDateFormat df = new SimpleDateFormat(UsStringCellDateConverter.DATE_PATTERN);

    private static String usTestData[][] = {
            {"Crystal",	"Maiden",	"01/02/1990",	"female"},
            {"Witch",	"Doctor",	"01/01/1990",	"male"}
    };

/*
    @Test
    public void readHeaderWithEmptyRowsBeforeMustOK() throws ParseException {
        XceliteOptions options = new XceliteOptions();
        options.setSkipRowsBeforeColumnDefinitionRow(3);

        List<CamelCase> upper = getData("src/test/resources/RowsBeforeColumnDefinition.xlsx");
        validateData(upper);
    }


    @Test
    public void readHeaderWithRowsBeforeMustOK() throws ParseException {
        XceliteOptions options = new XceliteOptions();
        options.setSkipRowsBeforeColumnDefinitionRow(3);

        List<CamelCase> upper = getData("src/test/resources/RowsBeforeColumnDefinition2.xlsx");
        validateData(upper);
    }

    @Test
    public void readHeaderWithEmptyRowsBeforeAfterMustOK() throws ParseException {
        XceliteOptions options = new XceliteOptions();
        options.setSkipRowsBeforeColumnDefinitionRow(3);
        options.setSkipRowsAfterColumnDefinitionRow(1);

        List<CamelCase> upper = getData("src/test/resources/RowsBeforeColumnDefinition3.xlsx");
        validateData(upper);
    }*/


    @Test
    public void readHeaderWithRowsBeforeAfterMustOK() throws ParseException {

        List<CamelCase> upper = getData("src/test/resources/RowsBeforeColumnDefinition4.xlsx");
        validateData(upper);
    }

    private List<CamelCase> getData(String filePath) {
        Compat_Xcelite xcelite = new Compat_Xcelite(new File(filePath));
        Compat_XceliteSheet sheet = xcelite.getSheet(0);
        Compat_SheetReader<CamelCase> beanReader = new Compat_BeanSheetReader<CamelCase>(sheet, CamelCase.class);
        ArrayList<CamelCase> data = new ArrayList<CamelCase>(beanReader.read());
        return data;
    }

    private void validateData(List<CamelCase> data) throws ParseException {
        CamelCase first = data.get(0);
        assertEquals(usTestData[0][0], first.getName(), "Name mismatch");
        assertEquals(usTestData[0][1], first.getSurname(), "Surname mismatch");
        assertEquals("Birthdate mismatch", df.parse(usTestData[0][2]), first.getBirthDate());

        CamelCase second = data.get(1);
        assertEquals(usTestData[1][0], second.getName(), "Name mismatch");
        assertEquals(usTestData[1][1], second.getSurname(), "Surname mismatch");
        assertEquals("Birthdate mismatch", df.parse(usTestData[1][2]), second.getBirthDate());
    }
}
