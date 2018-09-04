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
import compat.com.ebay.xcelite_104.exceptions.Compat_XceliteException;
import compat.com.ebay.xcelite_104.reader.Compat_SheetReader;
import compat.com.ebay.xcelite_104.sheet.Compat_XceliteSheet;
import org.junit.Test;
import xcelite.model.CamelCase;
import xcelite.model.ThaiCase;
import xcelite.model.UpperCase;
import xcelite.model.UsStringCellDateConverter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
/**
 * Test various upper/lower/camel-casings of the header-row
 * that defines the columns and mapping to model properties
 *
 * n.b. the provided Excel-sheets have a lookup of the sex
 * via the sexid and the second sheet. Date column is not
 * date formatted, but text.
 *
 * @author Thanthathon.b
 */
public class StringColumnCapitalizationTest {
    SimpleDateFormat usDateFormat = new SimpleDateFormat(UsStringCellDateConverter.DATE_PATTERN);

    private static String usTestData[][] = {
            {"Crystal",	"Maiden",	"01/02/1990",	"2",	"Female"},
            {"Witch",	"Doctor",	"01/01/1990",	"1",	"Male"}
    };

    private static String thaiTestData[][] = {
            {"แม่มด",	"น้ำแข็ง",	"01/02/1990",	"2",	"Female"},
            {"พ่อมด",	"หมอ",	"01/01/1990",	"1",	"Male"}
    };

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void model_UPPER_readUpperMustOK() throws ParseException {
        Compat_Xcelite xcelite = new Compat_Xcelite(new File("src/test/resources/UPPERCASE.xlsx"));
        Compat_XceliteSheet sheet = xcelite.getSheet(0);
        Compat_SheetReader<UpperCase> beanReader = sheet.getBeanReader(UpperCase.class);
        ArrayList<UpperCase> upper = new ArrayList<UpperCase>(beanReader.read());

        UpperCase first = upper.get(0);
        assertEquals("Name mismatch", usTestData[0][0], first.getName());
        assertEquals("Surname mismatch", usTestData[0][1], first.getSurname());
        assertEquals("Birthdate mismatch", usDateFormat.parse(usTestData[0][2]), first.getBirthDate());

        UpperCase second = upper.get(1);
        assertEquals("Name mismatch", usTestData[1][0], second.getName());
        assertEquals("Surname mismatch", usTestData[1][1], second.getSurname());
        assertEquals("Birthdate mismatch", usDateFormat.parse(usTestData[1][2]), second.getBirthDate());
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void model_camel_readCamelCaseMustOK() throws ParseException {
        Compat_Xcelite xcelite = new Compat_Xcelite(new File("src/test/resources/Camel Case.xlsx"));
        Compat_XceliteSheet sheet = xcelite.getSheet(0);
        Compat_SheetReader<CamelCase> beanReader = sheet.getBeanReader(CamelCase.class);
        ArrayList<CamelCase> upper = new ArrayList<CamelCase>(beanReader.read());

        CamelCase first = upper.get(0);
        assertEquals("Name mismatch", usTestData[0][0], first.getName());
        assertEquals("Surname mismatch", usTestData[0][1], first.getSurname());
        assertEquals("Birthdate mismatch", usDateFormat.parse(usTestData[0][2]), first.getBirthDate());

        CamelCase second = upper.get(1);
        assertEquals("Name mismatch", usTestData[1][0], second.getName());
        assertEquals("Surname mismatch", usTestData[1][1], second.getSurname());
        assertEquals("Birthdate mismatch", usDateFormat.parse(usTestData[1][2]), second.getBirthDate());
    }

    /*
    COMPATIBILITY: version 1.0.x passes, version 1.2 and later must conform
    */
    @Test
    public void model_Thai_readThaiCaseMustOK() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(UsStringCellDateConverter.DATE_PATTERN);
        Compat_Xcelite xcelite = new Compat_Xcelite(new File("src/test/resources/Thai Case.xlsx"));
        Compat_XceliteSheet sheet = xcelite.getSheet(0);
        Compat_SheetReader<ThaiCase> beanReader = sheet.getBeanReader(ThaiCase.class);
        ArrayList<ThaiCase> thais = new ArrayList<ThaiCase>(beanReader.read());

        ThaiCase first = thais.get(0);
        assertEquals("Name mismatch", thaiTestData[0][0], first.getName());
        assertEquals("Surname mismatch", thaiTestData[0][1], first.getSurname());
        assertEquals("Birthdate mismatch", df.parse(thaiTestData[0][2]), first.getBirthDate());

        ThaiCase second = thais.get(1);
        assertEquals("Name mismatch", thaiTestData[1][0], second.getName());
        assertEquals("Surname mismatch", thaiTestData[1][1], second.getSurname());
        assertEquals("Birthdate mismatch", df.parse(thaiTestData[1][2]), second.getBirthDate());

    }

    /*
    COMPATIBILITY: version 1.0.x passes, but returns empty objects (all properties are null)
                   version 1.2 and later must throw a ColumnNotFoundException if
                   case sensitive checking is enabled, must parse case-insensitive otherwise
                   and return data from the Excel sheet.
    */
    @Test
    public void model_UPPER_readLowerMustFail()throws Exception {
        Compat_Xcelite xcelite = new Compat_Xcelite(new File("src/test/resources/UPPERCASE.xlsx"));
        Compat_XceliteSheet sheet = xcelite.getSheet(0);
        Compat_SheetReader<CamelCase> beanReader = sheet.getBeanReader(CamelCase.class);
        Collection<CamelCase> data = beanReader.read();

        CamelCase first = data.iterator().next();
        assertEquals("Wrong row count", 2, data.size());
    }
}
