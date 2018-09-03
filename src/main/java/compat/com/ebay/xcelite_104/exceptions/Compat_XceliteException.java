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
package compat.com.ebay.xcelite_104.exceptions;

/**
 * Class description...
 *
 * @author kharel (kharel@ebay.com)
 * creation_date Sep 9, 2013
 * 
 */
public class Compat_XceliteException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public Compat_XceliteException(String message) {
    super(message);
  }

  public Compat_XceliteException(Exception exception) {
    super(exception);
  }

  public Compat_XceliteException(String message, Exception exception) {
    super(message, exception);
  }
}
