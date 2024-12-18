////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Stores the information about a type of beans.
 *
 * @author Haixing Hu
 */
public class BeanInfo {

  public static final String DEFAULT_ID_PROPERTY_NAME = "id";

  /**
   * The list of names of excluded property methods.
   */
  public static final Set<String> EXCLUDED_PROPERTY_METHODS = new HashSet<>();
  static {
    // allow hashCode() and getClass()
    EXCLUDED_PROPERTY_METHODS.add("clone");
    EXCLUDED_PROPERTY_METHODS.add("notify");
    EXCLUDED_PROPERTY_METHODS.add("notifyAll");
    EXCLUDED_PROPERTY_METHODS.add("wait");
    EXCLUDED_PROPERTY_METHODS.add("finalize");
    EXCLUDED_PROPERTY_METHODS.add("equals");
    EXCLUDED_PROPERTY_METHODS.add("toString");
    // add customized methods
    EXCLUDED_PROPERTY_METHODS.add("cloneEx");
    EXCLUDED_PROPERTY_METHODS.add("assign");
  };

  private static final ClassValue<BeanInfo> CACHE = new ClassValue<>() {
    @Override
    protected BeanInfo computeValue(final Class<?> type) {
      return new BeanInfo(type);
    }
  };

  /**
   * Factory methods for creating a {@link BeanInfo}.
   *
   * @param type
   *     the type of the beans.
   * @return
   *     the object storing the information of the beans.
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
   * Gets the name of bean class, i.e., the simple name of the class of the beans.
   *
   * @return
   *     the name of bean class, i.e., the simple name of the class of the beans.
   */
  public String getName() {
    return type.getSimpleName();
  }

  /**
   * Gets the type of the beans, i.e., the class object of the beans.
   *
   * @return
   *     the type of the beans, i.e., the class object of the beans.
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Tests whether the bean class has a property with the specified name.
   *
   * @param name
   *     the name of the property.
   * @return
   *     {@code true} if the bean class has a property with the specified name;
   *     {@code false} otherwise.
   */
  public boolean hasProperty(final String name) {
    return propertyMap.containsKey(name);
  }

  /**
   * Gets the list of properties of the beans.
   *
   * @return
   *     the list of properties of the beans.
   */
  public List<Property> getProperties() {
    return new ArrayList<>(properties);
  }

  /**
   * Gets the list of properties of the beans with the specified names.
   *
   * @param names
   *     the names of the properties.
   * @return
   *     the list of properties of the beans with the specified names.
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
   * Gets the list of properties of the beans that satisfy the specified condition.
   *
   * @param cond
   *     the condition to be satisfied.
   * @return
   *     the list of properties of the beans that satisfy the specified condition.
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
   * Gets the property of the beans with the specified name.
   *
   * @param name
   *     the name of the property.
   * @return
   *     the property of the beans with the specified name, or {@code null} if
   *     the bean class does not have a property with the specified name.
   */
  @Nullable
  public Property getProperty(final String name) {
    return propertyMap.get(name);
  }

  /**
   * Gets the property of the beans with the specified getter method.
   *
   * @param <T>
   *     the type of the beans.
   * @param <R>
   *     the type of the property.
   * @param getter
   *     the getter method of the property.
   * @return
   *     the property of the beans with the specified getter method, or
   *     {@code null} if the bean class does not have a property with the
   *     specified getter method.
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
   * Gets the list of properties of the beans that are reference properties.
   *
   * @return
   *     the list of properties of the beans that are reference properties.
   */
  public List<Property> getReferenceProperties() {
    return properties.stream()
                     .filter(Property::isReference)
                     .collect(Collectors.toList());
  }

  /**
   * Gets the list of properties of the beans that are non-computed properties.
   *
   * @return
   *     the list of properties of the beans that are non-computed properties.
   */
  public List<Property> getNonComputedProperties() {
    return properties.stream()
                     .filter(Property::isNonComputed)
                     .collect(Collectors.toList());
  }

  /**
   * Gets the property of the beans that is ID property.
   *
   * @return
   *     the property of the beans that is ID property, or {@code null} if the
   *     bean class does not have an ID property.
   */
  @Nullable
  public Property getIdProperty() {
    return idProperty;
  }

  /**
   * Tests whether the bean class has an ID property.
   *
   * @return
   *     {@code true} if the bean class has an ID property; {@code false}
   *     otherwise.
   */
  public boolean hasIdProperty() {
    return idProperty != null;
  }

  /**
   * Tests whether the bean class has an auto-generated ID property.
   *
   * @return
   *    {@code true} if the bean class has an auto-generated ID property;
   *    {@code false} otherwise.
   */
  public boolean hasAutoGeneratedIdProperty() {
    return (idProperty != null) && idProperty.isAutoGeneratedId();
  }

  /**
   * Gets the list of properties that a specified unique property is respect to.
   *
   * @param uniqueProperty
   *     the specified unique property of the bean class.
   * @return
   *     the list of properties that the specified unique property is respect to.
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
   * Gets the ID of the specified model.
   *
   * @param model
   *     the specified model, i.e., an instance of the bean class.
   * @return
   *     the ID of the specified model.
   * @throws IllegalArgumentException
   *     if the bean class does not have an ID property.
   */
  public Object getId(final Object model) {
    if (idProperty == null) {
      throw new IllegalArgumentException(
          "No ID property found for the model: " + this.getName());
    }
    return idProperty.getValue(model);
  }

  /**
   * Sets the ID of the specified model.
   *
   * @param model
   *     the specified model, i.e., an instance of the bean class.
   * @param value
   *     the new value of the ID.
   * @throws IllegalArgumentException
   *     if the bean class does not have an ID property.
   */
  public void setId(final Object model, @Nullable final Object value) {
    if (idProperty == null) {
      throw new IllegalArgumentException(
          "No ID property found for the model: " + this.getName());
    }
    idProperty.setValue(model, value);
  }

  /**
   * Gets the value of the specified property of the specified model.
   *
   * @param model
   *     the specified model, i.e., an instance of the bean class.
   * @param propertyName
   *     the name of the property.
   * @return
   *     the value of the specified property of the specified model, which may
   *     be {@code null}.
   * @throws IllegalArgumentException
   *     if the bean class does not have a property with the specified name.
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
   * Gets the value of the specified property of the specified model.
   *
   * @param model
   *     the specified model, i.e., an instance of the bean class.
   * @param property
   *     the specified property.
   * @return
   *     the value of the specified property of the specified model, which may
   *     be {@code null}.
   * @throws IllegalArgumentException
   *     if the bean class does not have the specified property.
   */
  public Object get(final Object model, final Property property) {
    if (property.getOwnerClass() != type) {
      throw new IllegalArgumentException("The owner class of the property "
          + "should be the same as the class of the specified bean.");
    }
    return property.getValue(model);
  }

  /**
   * Sets the value of the specified property of the specified model.
   *
   * @param model
   *     the specified model, i.e., an instance of the bean class.
   * @param propertyName
   *     the name of the property.
   * @param propertyValue
   *     the new value of the property, which may be {@code null}.
   * @throws IllegalArgumentException
   *     if the bean class does not have a property with the specified name.
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
   * Sets the value of the specified property of the specified model.
   *
   * @param model
   *     the specified model, i.e., an instance of the bean class.
   * @param property
   *     the specified property.
   * @param propertyValue
   *     the new value of the property, which may be {@code null}.
   * @throws IllegalArgumentException
   *     if the bean class does not have the specified property.
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
    return new ToStringBuilder(this).append("type", type)
                                    .append("properties", properties)
                                    .append("propertyMap", propertyMap)
                                    .append("idProperty", idProperty)
                                    .toString();
  }
}
