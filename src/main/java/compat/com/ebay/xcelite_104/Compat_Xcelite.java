/*
  Copyright [2013-2014] eBay Software Foundation

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package compat.com.ebay.xcelite_104;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import compat.com.ebay.xcelite_104.sheet.Compat_XceliteSheet;
import compat.com.ebay.xcelite_104.sheet.Compat_XceliteSheetImpl;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compat.com.ebay.xcelite_104.exceptions.XceliteException;

/**
 * Class description...
 * 
 * @author kharel (kharel@ebay.com)
 * creation_date Nov 9, 2013
 * 
 */
public class Compat_Xcelite {

  private final Workbook workbook;
  private File file;

  public Compat_Xcelite() {
    workbook = new XSSFWorkbook();
  }

  public Compat_Xcelite(File file) {
    try {
      this.file = file;
      workbook = new XSSFWorkbook(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates new sheet.
   * 
   * @return XceliteSheet object
   */
  public Compat_XceliteSheet createSheet() {
    return new Compat_XceliteSheetImpl(workbook.createSheet(), file);
  }

  /**
   * Creates new sheet with specified name.
   * 
   * @param name the sheet name   * 
   * @return XceliteSheet object
   */
  public Compat_XceliteSheet createSheet(String name) {
    return new Compat_XceliteSheetImpl(workbook.createSheet(name), file);
  }

  /**
   * Gets the sheet at the specified index.
   * 
   * @param sheetIndex the sheet index
   * @return XceliteSheet object
   */
  public Compat_XceliteSheet getSheet(int sheetIndex) {
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    if (sheet == null) {
      throw new XceliteException(String.format("Could not find sheet at index %s", sheetIndex));
    }
    return new Compat_XceliteSheetImpl(sheet, file);
  }

  /**
   * Gets the sheet with the specified index.
   * 
   * @param sheetName the sheet name
   * @return XceliteSheet object
   */
  public Compat_XceliteSheet getSheet(String sheetName) {
    Sheet sheet = workbook.getSheet(sheetName);
    if (sheet == null) {
      throw new XceliteException(String.format("Could not find sheet named \"%s\"", sheetName));
    }
    return new Compat_XceliteSheetImpl(sheet, file);
  }

  /**
   * Saves data to the same file given in construction. If no such file
   * specified an exception is thrown.
   */
  public void write() {
    if (file == null) {
      throw new XceliteException("No file given in Xcelite object construction. Consider using method write(file)");
    }
    write(file);
  }

  /**
   * Saves data to a new file.
   * 
   * @param file the file to save the data into
   */
  public void write(File file) {
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(file, false);
      workbook.write(out);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      new RuntimeException(e);
    } finally {
      if (out != null)
        try {
          out.close();
        } catch (IOException e) {
          new RuntimeException(e);
        }
    }
  }
  
  /**
   * Gets the excel file as byte array.
   * 
   * @return byte array which represents the excel file
   */
  public byte[] getBytes() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      workbook.write(baos);
    } catch (IOException e) {
      new RuntimeException(e);
    } finally {
      if (baos != null)
        try {
          baos.close();
        } catch (IOException e) {
          new RuntimeException(e);
        }
    }
    return baos.toByteArray();
  }
}
