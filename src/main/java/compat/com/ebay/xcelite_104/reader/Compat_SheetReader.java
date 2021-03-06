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

import java.util.Collection;

import compat.com.ebay.xcelite_104.sheet.Compat_XceliteSheet;

public interface Compat_SheetReader<T> {

  /**
   * Reads the sheet and returns a collection of the specified type.
   * 
   * @return collection of the specified type
   */
  Collection<T> read();

  /**
   * Whether to skip the first row or not when reading the sheet.
   * 
   * @param skipHeaderRow true to skip the header row, false otherwise
   */
  void skipHeaderRow(boolean skipHeaderRow);

  /**
   * Gets the sheet.
   * 
   * @return the sheet
   */
  Compat_XceliteSheet getSheet();

  /**
   * Adds a row post processor. The row post processors will be executed in
   * insertion order.
   * 
   * @param rowPostProcessor the post row processor to add
   */
  void addRowPostProcessor(Compat_RowPostProcessor<T> rowPostProcessor);
  
  /**
   * Removes a row post processor.
   * 
   * @param rowPostProcessor the post row processor to remove
   */
  void removeRowPostProcessor(Compat_RowPostProcessor<T> rowPostProcessor);
}
