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
package compat.com.ebay.xcelite_104.reader;

import java.util.List;

import compat.com.ebay.xcelite_104.sheet.Compat_XceliteSheet;
import org.apache.poi.ss.usermodel.Cell;

import com.google.common.collect.Lists;

/**
 * Class description...
 *
 * @author kharel (kharel@ebay.com)
 * creation_date Nov 11, 2013
 * 
 */
public abstract class Compat_SheetReaderAbs<T> implements Compat_SheetReader<T> {

  protected final Compat_XceliteSheet sheet;
  protected final List<Compat_RowPostProcessor<T>> rowPostProcessors;
  protected boolean skipHeader;
  
  public Compat_SheetReaderAbs(Compat_XceliteSheet sheet, boolean skipHeader) {
    this.sheet = sheet;
    this.skipHeader = skipHeader;
    rowPostProcessors = Lists.newArrayList();
  }
  
  protected Object readValueFromCell(Cell cell) {
    if (cell == null) return null;
    Object cellValue = null;
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_BOOLEAN:
        cellValue = cell.getBooleanCellValue();
        break;
      case Cell.CELL_TYPE_NUMERIC:
        cellValue = cell.getNumericCellValue();
        break;
      default:
        cellValue = cell.getStringCellValue();
    }
    return cellValue;
  }
  
  @Override
  public void skipHeaderRow(boolean skipHeaderRow) {
    this.skipHeader = skipHeaderRow;
  }

  @Override
  public Compat_XceliteSheet getSheet() {
    return sheet;
  }
  
  @Override
  public void addRowPostProcessor(Compat_RowPostProcessor<T> rowPostProcessor) {
    rowPostProcessors.add(rowPostProcessor);
  }
  
  @Override
  public void removeRowPostProcessor(Compat_RowPostProcessor<T> rowPostProcessor) {
    rowPostProcessors.remove(rowPostProcessor);
  }
}
