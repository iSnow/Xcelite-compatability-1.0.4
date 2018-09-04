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
import xcelite.model.AnyColumnBean;
import xcelite.model.AnyColumnBeanDoneWrong;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 *
 * @author Thanthathon.b
 */
public class AnyColumnTest {

    private String columnNames[] = new String[]{
            "NAME", "SURNAME", "BIRTHDATE", "SEXID", "SEX"
    };

    private static Object testData[][] = {
            {"Crystal",	"Maiden",	"01/02/1990",	2.0,	"Female"},
            {"Witch",	"Doctor",	"01/01/1990",	1.0,	"Male"}
    };

    /*
        COMPATIBILITY: ColumnsMapper creates a HashMap of header columns, therefore
                        the order is not guaranteed.
                        Changed in version 1.2 and later
     */
    @Test
    public void mustReadColumnHeadersOK() {
        List<String> testColNames = new ArrayList<String>(Arrays.asList(columnNames));
        Compat_Xcelite xcelite = new Compat_Xcelite(new File("src/test/resources/UPPERCASE.xlsx"));
        Compat_XceliteSheet sheet = xcelite.getSheet(0);
        Compat_SheetReader<AnyColumnBean> beanReader = sheet.getBeanReader(AnyColumnBean.class);
        Collection<AnyColumnBean> datasets = beanReader.read();
        for (AnyColumnBean row : datasets) {
            List<String> dataColNames = new ArrayList<String>(row.getColumns().keySet());
            assertEquals("mismatching number of columns", testColNames.size(), dataColNames.size());
            for (String col : testColNames) {
                assertTrue("unknown column", dataColNames.contains(col));
            }
            for (String col : dataColNames) {
                assertTrue("unknown column", testColNames.contains(col));
            }
        }
    }


    /*
        COMPATIBILITY: ColumnsMapper creates a HashMap of header columns, therefore
                        the order of data in an AnyColumn is not guaranteed.
                        Changed in version 1.2 and later
     */
    @Test
    public void mustReadDataOK() {
        Compat_Xcelite xcelite = new Compat_Xcelite(new File("src/test/resources/UPPERCASE.xlsx"));
        Compat_XceliteSheet sheet = xcelite.getSheet(0);
        Compat_SheetReader<AnyColumnBean> beanReader = sheet.getBeanReader(AnyColumnBean.class);
        Collection<AnyColumnBean> datasets = beanReader.read();
        int cnt = 0;
        for (AnyColumnBean row : datasets) {
            List<Object> testColData = new ArrayList<Object>(Arrays.asList(testData[cnt++]));
            List<Object> dataColData = new ArrayList(row.getColumns().values());
            assertEquals("mismatching number of columns", testColData.size(), dataColData.size());
            for (Object col : testColData) {
                assertTrue("unknown column", dataColData.contains(col));
            }
            for (Object col : dataColData) {
                assertTrue("unknown column", testColData.contains(col));
            }
        }
    }

    /*
        COMPATIBILITY: throws an XceliteException.
                        Must be preserved in later versions
    */
    @Test(expected = Compat_XceliteException.class)
    @SuppressWarnings("unchecked")
    public void mustThrowOnInvalidBean() {
        Compat_Xcelite xcelite = new Compat_Xcelite(new File("src/test/resources/UPPERCASE.xlsx"));
        Compat_XceliteSheet sheet = xcelite.getSheet(0);
        Compat_SheetReader<AnyColumnBeanDoneWrong> beanReader = sheet.getBeanReader(AnyColumnBeanDoneWrong.class);
        Collection<AnyColumnBeanDoneWrong> datasets = beanReader.read();
        int cnt = 0;
        for (AnyColumnBeanDoneWrong row : datasets) {
            List<Object> testColNames = new ArrayList<Object>(Arrays.asList(testData[cnt++]));
            List<Object> dataColNames = new ArrayList(row.getColumns().values());
            System.out.println(testColNames);
            System.out.println(dataColNames);
        }
    }

}
