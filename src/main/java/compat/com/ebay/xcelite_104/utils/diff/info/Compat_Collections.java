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
package compat.com.ebay.xcelite_104.utils.diff.info;

import java.util.Collection;

/**
 * Class description...
 *
 * @author kharel (kharel@ebay.com)
 * creation_date Nov 21, 2013
 * 
 */
public class Compat_Collections<T> {

  private final Collection<T> a;
  private final Collection<T> b;
  private final Collection<T> difference;
  
  public Compat_Collections(Collection<T> a, Collection<T> b, Collection<T> difference) {
    this.a = a;
    this.b = b;
    this.difference = difference;
  }

  public Collection<T> a() {
    return a;
  }

  public Collection<T> b() {
    return b;
  }

  public Collection<T> difference() {
    return difference;
  }  
}
