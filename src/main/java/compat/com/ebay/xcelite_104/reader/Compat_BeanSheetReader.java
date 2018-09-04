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

import static org.reflections.ReflectionUtils.withName;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compat.com.ebay.xcelite_104.annotate.Compat_NoConverterClass;
import compat.com.ebay.xcelite_104.annotations.Compat_AnyColumn;
import compat.com.ebay.xcelite_104.converters.Compat_ColumnValueConverter;
import compat.com.ebay.xcelite_104.exceptions.Compat_XceliteException;
import compat.com.ebay.xcelite_104.sheet.Compat_XceliteSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.reflections.ReflectionUtils;

import compat.com.ebay.xcelite_104.column.Compat_Col;
import compat.com.ebay.xcelite_104.column.Compat_ColumnsExtractor;
import compat.com.ebay.xcelite_104.column.Compat_ColumnsMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Class description...
 * 
 * @author kharel (kharel@ebay.com)
 * creation_date Sep 9, 2013
 * 
 */
public class Compat_BeanSheetReader<T> extends Compat_SheetReaderAbs<T> {

  private final LinkedHashSet<Compat_Col> columns;
  private final Compat_Col anyColumn;
  private final Compat_ColumnsMapper mapper;
  private final Class<T> type;
  private LinkedHashSet<String> header;
  private Iterator<Row> rowIterator;

  public Compat_BeanSheetReader(Compat_XceliteSheet sheet, Class<T> type) {
    super(sheet, false);
    this.type = type;
    Compat_ColumnsExtractor extractor = new Compat_ColumnsExtractor(type);
    extractor.extract();
    columns = extractor.getColumns(); 
    anyColumn = extractor.getAnyColumn();    
    mapper = new Compat_ColumnsMapper(columns);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<T> read() {
    buildHeader();
    List<T> data = Lists.newArrayList();    
    try {
      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        if (isBlankRow(row)) continue;
        T object = type.newInstance();
        
        int i = 0;
        for (String columnName : header) {
          Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
          Compat_Col col = mapper.getColumn(columnName);
          if (col == null) {            
            if (anyColumn != null) {
              Set<Field> fields = ReflectionUtils.getAllFields(object.getClass(), withName(anyColumn.getFieldName()));
              Field field = fields.iterator().next();
              if (!isColumnInIgnoreList(field, columnName)) {
                writeToAnyColumnField(field, object, cell, columnName);
              }
            }           
          } else {
            Set<Field> fields = ReflectionUtils.getAllFields(object.getClass(), withName(col.getFieldName()));
            Field field = fields.iterator().next();
            writeToField(field, object, cell, col);
          }
          i++;
        }
        boolean keepObject = true;
        for (Compat_RowPostProcessor<T> rowPostProcessor : rowPostProcessors) {
          keepObject = rowPostProcessor.process(object);
          if (!keepObject) break;
        }
        if (keepObject) {
          data.add(object);
        }
      }
    } catch (InstantiationException e1) {
      throw new RuntimeException(e1);
    } catch (IllegalAccessException e1) {
      throw new RuntimeException(e1);
    }
    return data;
  }

  private boolean isBlankRow(Row row) {
    Iterator<Cell> cellIterator = row.cellIterator();
    boolean blankRow = true;
    while (cellIterator.hasNext()) {  
      Object value = readValueFromCell(cellIterator.next());
      if (blankRow && value != null && !String.valueOf(value).isEmpty()) {
        blankRow = false;
      }
    }
    return blankRow;
  }

  private boolean isColumnInIgnoreList(Field anyColumnField, String columnName) {
    Compat_AnyColumn annotation = anyColumnField.getAnnotation(Compat_AnyColumn.class);
    Set<String> ignoreCols = Sets.newHashSet(annotation.ignoreCols());    
    return ignoreCols.contains(columnName);
  }

  @SuppressWarnings("unchecked")
  private void writeToAnyColumnField(Field field, T object, Cell cell, String columnName) {
    try {
      field.setAccessible(true);
      Object value = readValueFromCell(cell);
      if (value != null) {
        Compat_AnyColumn annotation = field.getAnnotation(Compat_AnyColumn.class);
        if (field.get(object) == null) {
          Map<String, Object> map = (Map<String, Object>) annotation.as().newInstance();
          field.set(object, map);
        }
        Map<String, Object> map = (Map<String, Object>) field.get(object);        
        if (annotation.converter() != Compat_NoConverterClass.class) {
          Compat_ColumnValueConverter<Object, ?> converter = (Compat_ColumnValueConverter<Object, ?>) annotation.converter()
              .newInstance();
          value = converter.deserialize(value);
        }       
        map.put(columnName, value);
      }
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } 
  }

  @SuppressWarnings("unchecked")
  private void writeToField(Field field, T object, Cell cell, Compat_Col column) {
    try {   
      Object cellValue = readValueFromCell(cell);      
      if (cellValue != null) {
        if (column.getConverter() != null) {
          Compat_ColumnValueConverter<Object, ?> converter = (Compat_ColumnValueConverter<Object, ?>) column.getConverter()
              .newInstance();
          cellValue = converter.deserialize(cellValue);
        } else {
          cellValue = convertToFieldType(cellValue, field.getType());
        }
      }
      field.setAccessible(true);
      field.set(object, cellValue);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    }
  }

  private Object convertToFieldType(Object cellValue, Class<?> fieldType) {
    String value = String.valueOf(cellValue);
    if (fieldType == Double.class || fieldType == double.class) {
      return Double.valueOf(value);
    }
    if (fieldType == Integer.class || fieldType == int.class) {
      return Double.valueOf(value).intValue();
    }
    if (fieldType == Short.class || fieldType == short.class) {
      return Double.valueOf(value).shortValue();
    }
    if (fieldType == Long.class || fieldType == long.class) {
      return Double.valueOf(value).longValue();
    }
    if (fieldType == Float.class || fieldType == float.class) {
      return Double.valueOf(value).floatValue();
    }
    if (fieldType == Character.class || fieldType == char.class) {
      return value.charAt(0);
    }
    if (fieldType == Date.class) {
      return DateUtil.getJavaDate(Double.valueOf(value));
    }
    return value;
  }

  private void buildHeader() {
    header = Sets.newLinkedHashSet();
    rowIterator = sheet.getNativeSheet().rowIterator();
    Row row = rowIterator.next();
    if (row == null) {
      throw new Compat_XceliteException("First row in sheet is empty. First row must contain header");
    }
    Iterator<Cell> itr = row.cellIterator();
    while (itr.hasNext()) {
      Cell cell = itr.next();
      header.add(cell.getStringCellValue());
    }   
  }
}
