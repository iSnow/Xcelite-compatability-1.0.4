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
package compat.com.ebay.xcelite_104.writer;

import java.util.Date;

import compat.com.ebay.xcelite_104.sheet.Compat_XceliteSheet;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Class description...
 *
 * @author kharel (kharel@ebay.com)
 * creation_date Nov 10, 2013
 * 
 */
public abstract class Compat_SheetWriterAbs<T> implements Compat_SheetWriter<T> {
  
  protected Compat_XceliteSheet sheet;
  protected boolean writeHeader;
  
  public Compat_SheetWriterAbs(Compat_XceliteSheet sheet, boolean writeHeader) {
    this.sheet = sheet;
    this.writeHeader = writeHeader;
  }
  
  protected void writeToCell(Cell cell, Object fieldValueObj, Class<?> dataType) {
    Class<?> type = fieldValueObj.getClass();
    if (dataType != null) {
      type = dataType;
    }
    if (type == Date.class) {
      cell.setCellValue((Date) fieldValueObj);
    } else if (type == Boolean.class) {
      cell.setCellValue((Boolean) fieldValueObj);
    } else if (type == Double.class || type == double.class || type == Integer.class || type == int.class
        || type == Long.class || type == long.class || type == Float.class || type == float.class
        || type == Short.class || type == short.class) {
      cell.setCellType(Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue(Double.valueOf(fieldValueObj.toString()));
    } else {
      cell.setCellType(Cell.CELL_TYPE_STRING);
      cell.setCellValue(fieldValueObj.toString());
    }
  }
  
  @Override
  public void generateHeaderRow(boolean generateHeaderRow) {
    this.writeHeader = generateHeaderRow;
  }
  
  @Override
  public Compat_XceliteSheet getSheet() {
    return sheet;
  }
}
