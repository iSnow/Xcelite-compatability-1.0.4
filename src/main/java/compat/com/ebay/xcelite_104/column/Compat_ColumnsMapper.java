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
package compat.com.ebay.xcelite_104.column;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

/**
 * Class description...
 * 
 * @author kharel (kharel@ebay.com)
 * creation_date Nov 5, 2013
 * 
 */
public class Compat_ColumnsMapper {

  private final Map<String, Compat_Col> columnsMap;

  public Compat_ColumnsMapper(Set<Compat_Col> columns) {
    columnsMap = Maps.newHashMap();
    for (Compat_Col col : columns) {
      columnsMap.put(col.getName(), col);
    }
  } 
  
  public Compat_Col getColumn(String name) {
    return columnsMap.get(name);
  }
}
