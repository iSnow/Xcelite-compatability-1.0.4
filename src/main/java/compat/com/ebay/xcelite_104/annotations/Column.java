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
package compat.com.ebay.xcelite_104.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import compat.com.ebay.xcelite_104.annotate.NoConverterClass;
import compat.com.ebay.xcelite_104.converters.ColumnValueConverter;

/**
 * Annotation to annotate a field to represent a column in excel file.
 * 
 * @author kharel (kharel@ebay.com)
 * creation_date Aug 29, 2013
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Column {
  
  /**
   * The actual name of the column that will be written to excel file. If no
   * name specified, the annotated field name will be taken.
   * @return asd
   */
  String name() default "";

  /**
   * If true, ignores the actual field type and serializes the field value as
   * String. Otherwise, uses the actual field type when writing the data.
   * @return asd
   */
  boolean ignoreType() default false;

  /**
   * The cell format to use when writing the data to excel file. Default is no
   * format.
   * @return asd
   */
  String dataFormat() default "";

  /**
   * Converter class to use when serializing/deserializing the data. Class must
   * implement
   * @return asd
   * {@link ColumnValueConverter
   * ColumnValueConverter}. Default is no converter.
   */
  Class<? extends ColumnValueConverter<?, ?>> converter() default NoConverterClass.class;
}
