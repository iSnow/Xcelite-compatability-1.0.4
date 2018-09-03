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
package compat.com.ebay.xcelite_104.utils.diff.report;

import compat.com.ebay.xcelite_104.utils.diff.info.Compat_Sheets;
import compat.com.ebay.xcelite_104.utils.diff.info.Compat_Collections;
import compat.com.ebay.xcelite_104.utils.diff.info.Compat_Files;
import compat.com.ebay.xcelite_104.utils.diff.info.Compat_Info;

/**
 * Class description...
 *
 * @author kharel (kharel@ebay.com)
 * creation_date Nov 21, 2013
 * 
 */
public class Compat_ReportInfo<T> implements Compat_Info<T> {

  private final Compat_Files files;
  private final Compat_Sheets sheets;
  private final Compat_Collections<T> collections;
  
  public Compat_ReportInfo(Compat_Files files, Compat_Sheets sheets, Compat_Collections<T> collections) {
    this.files = files;
    this.sheets = sheets;
    this.collections = collections;
  }

  @Override
  public Compat_Files files() {
    return files;
  } 

  @Override
  public Compat_Sheets sheets() {
    return sheets;
  }

  @Override
  public Compat_Collections<T> collections() {
    return collections;
  }
}
