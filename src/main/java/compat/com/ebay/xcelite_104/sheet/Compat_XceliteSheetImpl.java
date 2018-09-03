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
import compat.com.ebay.xcelite_104.reader.Compat_BeanSheetReader;
import compat.com.ebay.xcelite_104.reader.Compat_SimpleSheetReader;
import compat.com.ebay.xcelite_104.writer.Compat_SheetWriter;
import compat.com.ebay.xcelite_104.writer.Compat_BeanSheetWriter;
import compat.com.ebay.xcelite_104.writer.Compat_SimpleSheetWriter;

/**
 * Class description...
 * 
 * @author kharel (kharel@ebay.com)
 * creation_date Nov 9, 2013
 * 
 */
public class Compat_XceliteSheetImpl implements Compat_XceliteSheet {

  private final Sheet sheet;
  private final File file;

  public Compat_XceliteSheetImpl(Sheet sheet) {
    this(sheet, null);
  }
  
  public Compat_XceliteSheetImpl(Sheet sheet, File file) {
    this.sheet = sheet;
    this.file = file;
  }
  
  @Override
  public Sheet getNativeSheet() {
    return sheet;
  }

  @Override
  public File getFile() {
    return file;
  }

  @Override
  public <T> Compat_SheetWriter<T> getBeanWriter(Class<T> type) {
    return new Compat_BeanSheetWriter<T>(this, type);
  }

  @Override
  public <T> Compat_SheetReader<T> getBeanReader(Class<T> type) {
    return new Compat_BeanSheetReader<T>(this, type);
  } 

  @Override
  public Compat_SimpleSheetWriter getSimpleWriter() {
    return new Compat_SimpleSheetWriter(this);
  }

  @Override
  public Compat_SheetReader<Collection<Object>> getSimpleReader() {
    return new Compat_SimpleSheetReader(this);
  }
}
