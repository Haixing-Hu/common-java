////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.reflect.FieldUtils.getAllFields;
import static ltd.qubit.commons.reflect.MethodUtils.getAllMethods;
import static ltd.qubit.commons.reflect.PropertyUtils.getPropertyNameFromGetter;

/**
 * 存储关于Bean类型的信息。
 *
 * @author 胡海星
 */
public class BeanInfo {

  public static final String DEFAULT_ID_PROPERTY_NAME = "id";

  /**
   * The list of names of excluded property methods.
   */
  public static final Set<String> EXCLUDED_PROPERTY_METHODS = new HashSet<>();
  static {
    // allow getClass()
    EXCLUDED_PROPERTY_METHODS.add("clone");
    EXCLUDED_PROPERTY_METHODS.add("notify");
    EXCLUDED_PROPERTY_METHODS.add("notifyAll");
    EXCLUDED_PROPERTY_METHODS.add("wait");
    EXCLUDED_PROPERTY_METHODS.add("finalize");
    EXCLUDED_PROPERTY_METHODS.add("equals");
    EXCLUDED_PROPERTY_METHODS.add("hashCode");
    EXCLUDED_PROPERTY_METHODS.add("toString");
    // add customized methods
    EXCLUDED_PROPERTY_METHODS.add("cloneEx");
    EXCLUDED_PROPERTY_METHODS.add("assign");
    EXCLUDED_PROPERTY_METHODS.add("desensitizedClone");
  }

  private static final ClassValue<BeanInfo> CACHE = new ClassValue<>() {
    @Override
    protected BeanInfo computeValue(final Class<?> type) {
      return new BeanInfo(type);
    }
  };

  /**
   * 创建 {@link BeanInfo} 的工厂方法。
   *
   * @param type
   *     Bean的类型。
   * @return
   *     存储Bean信息的对象。
   */
  public static BeanInfo of(final Class<?> type) {
    return CACHE.get(type);
  }

  private final Class<?> type;
  private final List<Property> properties;
  private final Map<String, Property> propertyMap;
  private final Property idProperty;

  private BeanInfo(final Class<?> type) {
    this.type = type;
    this.properties = new ArrayList<>();
    this.propertyMap = new HashMap<>();
    collectPropertiesFromBeanMethods();
    collectPropertiesFromFields();
    this.idProperty = findIdProperty();
  }

  private void collectPropertiesFromBeanMethods() {
    final List<Method> methods = getAllMethods(type, Option.BEAN_METHOD);
    for (final Method method : methods) {
      if (EXCLUDED_PROPERTY_METHODS.contains(method.getName())) {
        continue; // ignore the excluded methods.
      }
      final String propertyName = getPropertyNameFromGetter(method);
      if ((propertyName == null) || propertyMap.containsKey(propertyName)) {
        continue;
      }
      final Property property;
      try {
        property = Property.of(type, propertyName);
      } catch (final IllegalArgumentException e) {
        // no such property
        continue;
      }
      properties.add(property);
      propertyMap.put(propertyName, property);
    }
  }

  private void collectPropertiesFromFields() {
    final List<Field> fields = getAllFields(type, Option.BEAN_FIELD);
    for (final Field field : fields) {
      final String propertyName = field.getName();
      if (propertyMap.containsKey(propertyName)) {
        continue;
      }
      final Property property = Property.of(type, propertyName);
      properties.add(property);
      propertyMap.put(propertyName, property);
    }
  }

  private Property findIdProperty() {
    for (final Property prop : properties) {
      if (prop.isIdentifier()) {
        return prop;
      }
    }
    return null;
  }

  /**
   * 获取Bean类的名称，即Bean类的简单名称。
   *
   * @return
   *     Bean类的名称，即Bean类的简单名称。
   */
  public String getName() {
    return type.getSimpleName();
  }

  /**
   * 获取Bean的类型，即Bean的类对象。
   *
   * @return
   *     Bean的类型，即Bean的类对象。
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * 测试Bean类是否具有指定名称的属性。
   *
   * @param name
   *     属性的名称。
   * @return
   *     如果Bean类具有指定名称的属性则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean hasProperty(final String name) {
    return propertyMap.containsKey(name);
  }

  /**
   * 获取Bean的属性列表。
   *
   * @return
   *     Bean的属性列表。
   */
  public List<Property> getProperties() {
    return new ArrayList<>(properties);
  }

  /**
   * 获取具有指定名称的Bean属性列表。
   *
   * @param names
   *     属性的名称。
   * @return
   *     具有指定名称的Bean属性列表。
   */
  public List<Property> getProperties(final String... names) {
    final List<Property> result = new ArrayList<>();
    for (final String name : names) {
      if (propertyMap.containsKey(name)) {
        result.add(propertyMap.get(name));
      }
    }
    return result;
  }

  /**
   * 获取满足指定条件的Bean属性列表。
   *
   * @param cond
   *     要满足的条件。
   * @return
   *     满足指定条件的Bean属性列表。
   */
  public List<Property> getProperties(final Predicate<Property> cond) {
    final List<Property> result = new ArrayList<>();
    for (final Property prop : properties) {
      if (cond.test(prop)) {
        result.add(prop);
      }
    }
    return result;
  }

  /**
   * 获取具有指定名称的Bean属性。
   *
   * @param name
   *     属性的名称。
   * @return
   *     具有指定名称的Bean属性，如果Bean类没有指定名称的属性则返回 {@code null}。
   */
  @Nullable
  public Property getProperty(final String name) {
    return propertyMap.get(name);
  }

  /**
   * 获取具有指定getter方法的Bean属性。
   *
   * @param <T>
   *     Bean的类型。
   * @param <R>
   *     属性的类型。
   * @param getter
   *     属性的getter方法。
   * @return
   *     具有指定getter方法的Bean属性，如果Bean类没有指定getter方法的属性则返回 {@code null}。
   */
  @Nullable
  public <T, R> Property getProperty(final GetterMethod<T, R> getter) {
    @SuppressWarnings(
        "unchecked") final String fieldName = FieldUtils.getFieldName(
        (Class<T>) type, getter);
    if (fieldName == null) {
      return null;
    } else {
      return propertyMap.get(fieldName);
    }
  }

  /**
   * 获取Bean中作为引用属性的属性列表。
   *
   * @return
   *     Bean中作为引用属性的属性列表。
   */
  public List<Property> getReferenceProperties() {
    return properties.stream()
                     .filter(Property::isReference)
                     .collect(Collectors.toList());
  }

  /**
   * 获取Bean中作为非计算属性的属性列表。
   *
   * @return
   *     Bean中作为非计算属性的属性列表。
   */
  public List<Property> getNonComputedProperties() {
    return properties.stream()
                     .filter(Property::isNonComputed)
                     .collect(Collectors.toList());
  }

  /**
   * 获取Bean中作为唯一属性的属性列表。
   *
   * @return
   *     Bean中作为唯一属性的属性列表。注意ID属性<b>包含</b>在结果中。
   */
  public List<Property> getUniqueProperties() {
    return properties.stream()
                     .filter(Property::isUnique)
                     .collect(Collectors.toList());
  }

  /**
   * 获取Bean的ID属性。
   *
   * @return
   *     Bean的ID属性，如果Bean类没有ID属性则返回 {@code null}。
   */
  @Nullable
  public Property getIdProperty() {
    return idProperty;
  }

  /**
   * 测试Bean类是否具有ID属性。
   *
   * @return
   *     如果Bean类具有ID属性则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean hasIdProperty() {
    return idProperty != null;
  }

  /**
   * 测试Bean类是否具有自动生成的ID属性。
   *
   * @return
   *    如果Bean类具有自动生成的ID属性则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean hasAutoGeneratedIdProperty() {
    return (idProperty != null) && idProperty.isAutoGeneratedId();
  }

  /**
   * 获取指定唯一属性所关联的属性列表。
   *
   * @param uniqueProperty
   *     Bean类的指定唯一属性。
   * @return
   *     指定唯一属性所关联的属性列表。
   */
  public List<Property> getRespectToProperties(final Property uniqueProperty) {
    final String[] respectTo = uniqueProperty.getUniqueRespectTo();
    final List<Property> result = new ArrayList<>();
    if (respectTo != null) {
      for (final String name : respectTo) {
        final Property prop = propertyMap.get(name);
        if (prop == null) {
          throw new IllegalStateException("The name in the respect to property "
              + "of the @Unique annotation must be a property of the model: "
              + name);
        }
        result.add(prop);
      }
    }
    result.add(uniqueProperty);
    return result;
  }

  /**
   * 获取指定模型的ID。
   *
   * @param model
   *     指定的模型，即Bean类的实例。
   * @return
   *     指定模型的ID。
   * @throws IllegalArgumentException
   *     如果Bean类没有ID属性。
   */
  public Object getId(final Object model) {
    if (idProperty == null) {
      throw new IllegalArgumentException(
          "No ID property found for the model: " + this.getName());
    }
    return idProperty.getValue(model);
  }

  /**
   * 设置指定模型的ID。
   *
   * @param model
   *     指定的模型，即Bean类的实例。
   * @param value
   *     ID的新值。
   * @throws IllegalArgumentException
   *     如果Bean类没有ID属性。
   */
  public void setId(final Object model, @Nullable final Object value) {
    if (idProperty == null) {
      throw new IllegalArgumentException(
          "No ID property found for the model: " + this.getName());
    }
    idProperty.setValue(model, value);
  }

  /**
   * 获取指定模型的指定属性值。
   *
   * @param model
   *     指定的模型，即Bean类的实例。
   * @param propertyName
   *     属性的名称。
   * @return
   *     指定模型的指定属性值，可能为 {@code null}。
   * @throws IllegalArgumentException
   *     如果Bean类没有指定名称的属性。
   */
  @Nullable
  public Object get(final Object model, final String propertyName) {
    final Property property = this.getProperty(propertyName);
    if (property == null) {
      throw new IllegalArgumentException("Cannot find property '"
          + propertyName
          + "' for the model "
          + this.getName());
    }
    return property.getValue(model);
  }

  /**
   * 获取指定模型的指定属性值。
   *
   * @param model
   *     指定的模型，即Bean类的实例。
   * @param property
   *     指定的属性。
   * @return
   *     指定模型的指定属性值，可能为 {@code null}。
   * @throws IllegalArgumentException
   *     如果Bean类没有指定的属性。
   */
  public Object get(final Object model, final Property property) {
    if (property.getOwnerClass() != type) {
      throw new IllegalArgumentException("The owner class of the property "
          + "should be the same as the class of the specified bean.");
    }
    return property.getValue(model);
  }

  /**
   * 设置指定模型的指定属性值。
   *
   * @param model
   *     指定的模型，即Bean类的实例。
   * @param propertyName
   *     属性的名称。
   * @param propertyValue
   *     属性的新值，可能为 {@code null}。
   * @throws IllegalArgumentException
   *     如果Bean类没有指定名称的属性。
   */
  public void set(final Object model, final String propertyName,
      @Nullable final Object propertyValue) {
    final Property property = this.getProperty(propertyName);
    if (property == null) {
      throw new IllegalArgumentException("Cannot find property '"
          + propertyName
          + "' for the model "
          + this.getName());
    }
    property.setValue(model, propertyValue);
  }

  /**
   * 设置指定模型的指定属性值。
   *
   * @param model
   *     指定的模型，即Bean类的实例。
   * @param property
   *     指定的属性。
   * @param propertyValue
   *     属性的新值，可能为 {@code null}。
   * @throws IllegalArgumentException
   *     如果Bean类没有指定的属性。
   */
  public void set(final Object model, final Property property,
      final Object propertyValue) {
    if (property.getOwnerClass() != type) {
      throw new IllegalArgumentException("The owner class of the property "
          + "should be the same as the class of the specified bean.");
    }
    property.setValue(model, propertyValue);
  }

  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final BeanInfo other = (BeanInfo) o;
    return Equality.equals(type, other.type)
        && Equality.equals(properties, other.properties)
        && Equality.equals(propertyMap, other.propertyMap)
        && Equality.equals(idProperty, other.idProperty);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, properties);
    result = Hash.combine(result, multiplier, propertyMap);
    result = Hash.combine(result, multiplier, idProperty);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("type", type)
      .append("properties", properties)
      .append("propertyMap", propertyMap)
      .append("idProperty", idProperty)
      .toString();
  }
}