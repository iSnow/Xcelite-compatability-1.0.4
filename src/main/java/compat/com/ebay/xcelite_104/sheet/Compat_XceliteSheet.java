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
package compat.com.ebay.xcelite_104.sheet;

import java.io.File;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Sheet;

import compat.com.ebay.xcelite_104.reader.Compat_SheetReader;
import compat.com.ebay.xcelite_104.writer.Compat_SheetWriter;

/**
 * Class description...
 *
 * @author kharel (kharel@ebay.com)
 * creation_date Nov 9, 2013
 * 
 */
public interface Compat_XceliteSheet {

  <T> Compat_SheetWriter<T> getBeanWriter(Class<T> type);
  <T> Compat_SheetReader<T> getBeanReader(Class<T> type);
  Compat_SheetWriter<Collection<Object>> getSimpleWriter();
  Compat_SheetReader<Collection<Object>> getSimpleReader();
  Sheet getNativeSheet();
  File getFile();
}
