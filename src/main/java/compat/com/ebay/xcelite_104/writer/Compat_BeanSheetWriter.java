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
package compat.com.ebay.xcelite_104.writer;

import static org.reflections.ReflectionUtils.withName;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import compat.com.ebay.xcelite_104.annotate.Compat_NoConverterClass;
import compat.com.ebay.xcelite_104.converters.Compat_ColumnValueConverter;
import compat.com.ebay.xcelite_104.sheet.Compat_XceliteSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.reflections.ReflectionUtils;

import compat.com.ebay.xcelite_104.column.Compat_Col;
import compat.com.ebay.xcelite_104.column.Compat_ColumnsExtractor;
import compat.com.ebay.xcelite_104.styles.Compat_CellStylesBank;
import com.google.common.collect.Sets;

public class Compat_BeanSheetWriter<T> extends Compat_SheetWriterAbs<T> {

  private final LinkedHashSet<Compat_Col> columns;
  private final Compat_Col anyColumn;
  private org.apache.poi.ss.usermodel.Row headerRow;
  private int rowIndex = 0;

  public Compat_BeanSheetWriter(Compat_XceliteSheet sheet, Class<T> type) {
    super(sheet, true);
    Compat_ColumnsExtractor extractor = new Compat_ColumnsExtractor(type);
    extractor.extract();
    columns = extractor.getColumns();
    anyColumn = extractor.getAnyColumn();
  }

  @Override
  public void write(Collection<T> data) {
    if (writeHeader) {
      writeHeader();
    }
    writeData(data);
  }

  @SuppressWarnings("unchecked")
  private void writeData(Collection<T> data) {
    try {
      Set<Compat_Col> columnsToAdd = Sets.newTreeSet();
      for (T t : data) {
        if (anyColumn != null) {
          appendAnyColumns(t, columnsToAdd);
        }
      }
      addColumns(columnsToAdd, true);
      for (T t : data) {
        org.apache.poi.ss.usermodel.Row row = sheet.getNativeSheet().createRow(rowIndex);
        int i = 0;
        for (Compat_Col col : columns) {
          Set<Field> fields = ReflectionUtils.getAllFields(t.getClass(), withName(col.getFieldName()));
          Field field = fields.iterator().next();
          field.setAccessible(true);
          Object fieldValueObj = null;
          if (col.isAnyColumn()) {
            Map<String, Object> anyColumnMap = (Map<String, Object>) field.get(t);
            fieldValueObj = anyColumnMap.get(col.getName());
          } else {
            fieldValueObj = field.get(t);
          }
          Cell cell = row.createCell(i);
          writeToCell(cell, col, fieldValueObj);
          i++;
        }
        rowIndex++;
      }
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  private void writeToCell(Cell cell, Compat_Col col, Object fieldValueObj) {
    if (fieldValueObj == null) {
      cell.setCellValue((String) null);
      return;
    }
    if (col.getConverter() != null) {
      try {
        Compat_ColumnValueConverter<?, Object> converter = (Compat_ColumnValueConverter<?, Object>) col.getConverter().newInstance();
        fieldValueObj = converter.serialize(fieldValueObj);
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    if (col.getDataFormat() != null) {
      cell.setCellStyle(Compat_CellStylesBank.get(sheet.getNativeSheet().getWorkbook()).getCustomDataFormatStyle(
          col.getDataFormat()));
    }

    if (col.getType() == Date.class) {
      if (col.getDataFormat() == null) {
        cell.setCellStyle(Compat_CellStylesBank.get(sheet.getNativeSheet().getWorkbook()).getDateStyle());
      }
    }

    writeToCell(cell, fieldValueObj, col.getType());
  }

  private void writeHeader() {
    headerRow = sheet.getNativeSheet().createRow(rowIndex);
    rowIndex++;
    addColumns(columns, false);
  }

  @SuppressWarnings("unchecked")
  private void appendAnyColumns(T t, Set<Compat_Col> columnToAdd) {
    try {
      Set<Field> fields = ReflectionUtils.getAllFields(t.getClass(), withName(anyColumn.getFieldName()));
      Field anyColumnField = fields.iterator().next();
      anyColumnField.setAccessible(true);
      Map<String, Object> fieldValueObj = (Map<String, Object>) anyColumnField.get(t);
      for (Map.Entry<String, Object> entry : fieldValueObj.entrySet()) {
        Compat_Col column = new Compat_Col(entry.getKey(), anyColumnField.getName());
        column.setType(entry.getValue() == null ? String.class : entry.getValue().getClass());
        column.setAnyColumn(true);
        if (anyColumn.getConverter() != Compat_NoConverterClass.class) {
          column.setConverter(anyColumn.getConverter());
        }
        columnToAdd.add(column);
      }
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }  

  private void addColumns(Set<Compat_Col> columnsToAdd, boolean append) {
    int i = (headerRow == null || headerRow.getLastCellNum() == -1) ? 0 : headerRow.getLastCellNum();
    for (Compat_Col column : columnsToAdd) {
      if (append && columns.contains(column))
        continue;
      if (writeHeader) {
        Cell cell = headerRow.createCell(i);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(Compat_CellStylesBank.get(sheet.getNativeSheet().getWorkbook()).getBoldStyle());
        cell.setCellValue(column.getName());
        i++;
      }
      columns.add(column);
    }
  }
}
