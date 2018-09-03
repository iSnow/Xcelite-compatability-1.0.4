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

import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import compat.com.ebay.xcelite_104.annotate.Compat_NoConverterClass;
import compat.com.ebay.xcelite_104.annotations.Compat_AnyColumn;
import compat.com.ebay.xcelite_104.annotations.Compat_Column;
import compat.com.ebay.xcelite_104.annotations.Compat_Row;
import compat.com.ebay.xcelite_104.exceptions.Compat_XceliteException;
import org.reflections.ReflectionUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


public class Compat_ColumnsExtractor {

  private final Class<?> type;
  private Set<Compat_Col> columns;
  private Compat_Col anyColumn;
  private Set<Compat_Col> colsOrdering;

  public Compat_ColumnsExtractor(Class<?> type) {
    this.type = type;
    columns = Sets.newLinkedHashSet();
    columnsOrdering();
  }

  private void columnsOrdering() {
    Compat_Row rowAnnotation = type.getAnnotation(Compat_Row.class);
    if (rowAnnotation == null || rowAnnotation.colsOrder() == null || rowAnnotation.colsOrder().length == 0) return;
    colsOrdering = Sets.newLinkedHashSet();
    for (String column : rowAnnotation.colsOrder()) {
      colsOrdering.add(new Compat_Col(column));
    }
  }

  @SuppressWarnings("unchecked")
  public void extract() {    
    Set<Field> columnFields = ReflectionUtils.getAllFields(type, withAnnotation(Compat_Column.class));
    for (Field columnField : columnFields) {
      Compat_Column annotation = columnField.getAnnotation(Compat_Column.class);
      Compat_Col col = null;
      if (annotation.name().isEmpty()) {
        col = new Compat_Col(columnField.getName(), columnField.getName());
      } else {
        col = new Compat_Col(annotation.name(), columnField.getName());
      }      
      
      if (annotation.ignoreType()) {
        col.setType(String.class);
      } else {
        col.setType(columnField.getType());
      }
      if (!annotation.dataFormat().isEmpty()) {
        col.setDataFormat(annotation.dataFormat());
      }
      if (annotation.converter() != Compat_NoConverterClass.class) {
        col.setConverter(annotation.converter());
      }
      columns.add(col);
    }   
    
    if (colsOrdering != null) {
      orderColumns();
    }
    
    extractAnyColumn();
  }
  
  @SuppressWarnings("unchecked")
  private void extractAnyColumn() {
    Set<Field> anyColumnFields = ReflectionUtils.getAllFields(type, withAnnotation(Compat_AnyColumn.class));
    if (anyColumnFields.size() > 0) {
      if (anyColumnFields.size() > 1) {
        throw new Compat_XceliteException("Multiple AnyColumn fields are not allowed");
      }
      Field anyColumnField = anyColumnFields.iterator().next();
      if (!anyColumnField.getType().isAssignableFrom(Map.class)) {
        throw new Compat_XceliteException(
            String.format("AnyColumn field \"%s\" should be of type Map.class or assignable from Map.class",
                anyColumnField.getName()));
      }
      anyColumn = new Compat_Col(anyColumnField.getName(), anyColumnField.getName());
      anyColumn.setAnyColumn(true);
      Compat_AnyColumn annotation = anyColumnField.getAnnotation(Compat_AnyColumn.class);
      anyColumn.setType(annotation.as());
      if (annotation.converter() != Compat_NoConverterClass.class) {
        anyColumn.setConverter(annotation.converter());
      }
    }    
  }

  private void orderColumns() {
    // build temporary columns map and then use it to fill fieldName in colsOrdering set
    Map<String, Compat_Col> map = Maps.newHashMap();
    for (Compat_Col col : columns) {
      map.put(col.getName(), col);
    }
    
    for (Compat_Col col : colsOrdering) {
      if (columns.contains(col)) {
        Compat_Col column = map.get(col.getName());
        column.copyTo(col);
      } else {
        throw new RuntimeException(String.format("Unrecognized column \"%s\" in Row columns ordering", col.getName()));
      }
    }
    
    if (colsOrdering.size() != columns.size()) {
      throw new RuntimeException(String.format("Not all columns are specified in Row columns ordering"));
    }
    columns = colsOrdering;
  }

  public LinkedHashSet<Compat_Col> getColumns() {
    return (LinkedHashSet<Compat_Col>) columns;
  }

  public Compat_Col getAnyColumn() {
    return anyColumn;
  }
}
