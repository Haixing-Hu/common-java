////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.error.UnsupportedDataTypeException;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.reflect.ConstructorUtils;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.reflect.impl.SetterMethod;
import ltd.qubit.commons.reflect.impl.SetterMethodWithType;

import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyType;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyValue;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.setPropertyValue;

/**
 * {@link RowMapper}的基本实现。
 *
 * @param <T>
 *     被映射的实体的类型。
 * @author 胡海星
 */
public class BasicRowMapper<T> implements RowMapper<T> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final Class<T> type;

  private final String entityName;

  private final List<String> headers;

  private final Map<String, GetterMethod<T, ?>> getterMap;

  private final Map<String, SetterMethodWithType<T, ?>> setterMap;

  private final Map<String, String> propertyMap;

  private final Set<String> continueLastRowHeaders;

  @Nullable
  private final ColumnHeaderTransformer columnHeaderTransformer;

  @Nullable
  private final SetterMethod<T, Integer> rowNumberSetter;

  private boolean firstRowAsHeaders;

  private int rowNumber = 0;

  /**
   * 构造一个{@link BasicRowMapper}。
   *
   * @param builder
   *     用于构造{@link BasicRowMapper}的构造器。
   */
  public BasicRowMapper(final RowMapperBuilder<T> builder) {
    this.type = builder.type;
    this.entityName = builder.type.getSimpleName();
    this.headers = new ArrayList<>(builder.headers);
    this.getterMap = new HashMap<>(builder.getterMap);
    this.setterMap = new HashMap<>(builder.setterMap);
    this.propertyMap = new HashMap<>(builder.propertyMap);
    this.continueLastRowHeaders = new HashSet<>(builder.continueLastRowHeaders);
    this.columnHeaderTransformer = builder.columnHeaderTransformer;
    this.rowNumberSetter = builder.rowNumberSetter;
    this.firstRowAsHeaders = builder.firstRowAsHeaders;
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  public String getEntityName() {
    return entityName;
  }

  @Override
  public List<String> getHeaders() {
    return headers;
  }

  @Override
  public boolean isContinueLastRow(final String header) {
    return continueLastRowHeaders.contains(header);
  }

  public void setContinueLastRow(final String header, final boolean continueLastRow) {
    if (continueLastRow) {
      continueLastRowHeaders.add(header);
    } else {
      continueLastRowHeaders.remove(header);
    }
  }

  public Map<String, String> getPropertyMap() {
    return propertyMap;
  }

  @Nullable
  public SetterMethod<T, Integer> getRowNumberSetter() {
    return rowNumberSetter;
  }

  @Override
  public boolean isFirstRowAsHeaders() {
    return firstRowAsHeaders;
  }

  public void setFirstRowAsHeaders(final boolean firstRowAsHeaders) {
    this.firstRowAsHeaders = firstRowAsHeaders;
  }

  public void resetRowNumber() {
    rowNumber = 0;
  }

  @Override
  public Map<String, String> toRow(final T entity) {
    final Map<String, String> row = new HashMap<>();
    for (final String header : headers) {
      final Object value;
      if (propertyMap.containsKey(header)) {
        final String path = propertyMap.get(header);
        value = getPropertyValue(entity, path);
      } else if (getterMap.containsKey(header)) {
        final GetterMethod<T, ?> getter = getterMap.get(header);
        value = getter.invoke(entity);
      } else {
        continue;
      }
      logger.trace("Map the property value of entity {} to row column {}: {}",
          entityName, header, value);
      row.put(header, value == null ? "" : value.toString());
    }
    return row;
  }

  @Override
  public T fromRow(final Map<String, String> lastRow, final Map<String, String> currentRow) {
    final T entity = ConstructorUtils.newInstance(type);
    for (final String header : headers) {
      String valueText = currentRow.get(header);
      // 若当前行的数据为空且需要继续上一行的数据，则使用上一行的数据。
      if (StringUtils.isEmpty(valueText)
          && (lastRow != null)
          && continueLastRowHeaders.contains(header)) {
        final String lastValue = lastRow.get(header);
        currentRow.put(header, lastValue);
        valueText = lastValue;
      }
      logger.debug("from row: '{}' = '{}'", header, valueText);
      if (valueText == null) {
        continue;  // 忽略不存在的列
      }
      if (propertyMap.containsKey(header)) {
        final String path = propertyMap.get(header);
        // 设置指定路径的值
        final Class<?> propertyType = getPropertyType(type, path);
        final Object value = Type.parse(propertyType, valueText);
        // 设置值时，自动创建为null的中间对象
        setPropertyValue(entity, path, value, true);
      } else if (setterMap.containsKey(header)){
        final SetterMethodWithType<T, ?> setterWithType = setterMap.get(header);
        final Class<?> argumentClass = setterWithType.getArgumentClass();
        final SetterMethod<T, ?> setter = setterWithType.getSetter();
        final Type argumentType = Type.forClass(argumentClass);
        if (argumentType == null) {
          throw new UnsupportedDataTypeException(argumentClass);
        }
        final Object value = argumentType.parse(valueText);
        @SuppressWarnings("unchecked")
        final SetterMethod<T, Object> genericSetter = (SetterMethod<T, Object>) setter;
        genericSetter.invoke(entity, value);
      }
    }
    if (rowNumberSetter != null) {
      rowNumberSetter.invoke(entity, rowNumber);
    }
    rowNumber++;
    return entity;
  }

  @Override
  public String transformColumnHeader(final int index, final String header) {
    if (columnHeaderTransformer == null) {
      return RowMapper.super.transformColumnHeader(index, header);
    } else {
      return columnHeaderTransformer.transform(index, header);
    }
  }
}