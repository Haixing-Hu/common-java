////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import jakarta.validation.constraints.Size;
import ltd.qubit.commons.annotation.*;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.range.CloseRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ltd.qubit.commons.lang.Argument.requireNonEmpty;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.nullIfEmpty;

/**
 * The model of a bean property.
 *
 * @author Haixing Hu
 */
@Immutable
public class Property {

  private static final Logger LOGGER = LoggerFactory.getLogger(Property.class);

  private static final ClassValue<Map<String, Property>> CACHE = new ClassValue<>() {
    @Override
    protected Map<String, Property> computeValue(final Class<?> type) {
      return new ConcurrentHashMap<>();
    }
  };

  /**
   * Gets the specified property.
   *
   * @param ownerClass
   *     the class object of the owner class of the specified property.
   * @param name
   *     the name of the specified property.
   * @return
   *     the specified property.
   */
  public static Property of(final Class<?> ownerClass, final String name) {
    final Map<String, Property> map = CACHE.get(ownerClass);
    return map.computeIfAbsent(name, n -> new Property(ownerClass, n));
  }

  // /**
  //  * Gets the specified property.
  //  *
  //  * @param ownerClass
  //  *     the class object of the owner class of the specified property.
  //  * @param field
  //  *     the field object of the specified property.
  //  * @return
  //  *     the specified property.
  //  */
  // public static Property of(final Class<?> ownerClass, final Field field) {
  //   final Map<String, Property> map = CACHE.get(ownerClass);
  //   return map.computeIfAbsent(field.getName(), n -> new Property(ownerClass, n, field));
  // }

  /**
   * Gets the specified property.
   *
   * @param <T>
   *     the type of the owner class of the specified property.
   * @param <R>
   *     the type of the specified property.
   * @param ownerClass
   *     the class object of the owner class of the specified property.
   * @param getter
   *     the method reference to the getter of the specified property.
   * @return
   *     the specified property.
   */
  public static <T, R> Property of(final Class<T> ownerClass,
      final GetterMethod<T, R> getter) {
    final String name = FieldUtils.getFieldName(ownerClass, getter);
    if (name == null) {
      throw new IllegalArgumentException("The specified method reference is not a valid getter");
    }
    return of(ownerClass, name);
  }

  private final Class<?> ownerClass;

  private final String name;

  private final String fullname;

  @Nullable
  private final FieldInfo fieldInfo;

  @Nullable
  private final Field field;

  private final Method readMethod;

  @Nullable
  private final Method writeMethod;

  private final Class<?> declaringClass;

  private final Class<?> type;

  private final boolean reference;

  private final Class<?> referenceEntity;

  private final String referenceProperty;

  private final boolean referenceExisting;

  private final String referencePath;

  /**
   * Constructs a {@link Property} object.
   *
   * @param ownerClass
   *     the owner class of the bean property.
   * @param name
   *     the name of the bean property.
   */
  private Property(final Class<?> ownerClass, final String name) {
    this(ownerClass, name,
        FieldUtils.getFieldInfo(ownerClass, Option.BEAN_FIELD, name));
  }

  /**
   * Constructs a {@link Property} object.
   *
   * @param ownerClass
   *     the owner class of the bean property.
   * @param name
   *     the name of the bean property.
   * @param fieldInfo
   *     the field object of the bean property, which may be null.
   */
  private Property(final Class<?> ownerClass, final String name,
      @Nullable final FieldInfo fieldInfo) {
    this.ownerClass = requireNonNull("ownerClass", ownerClass);
    this.name = requireNonEmpty("name", name);
    this.fullname = ownerClass.getSimpleName() + '.' + name;
    this.fieldInfo = fieldInfo;
    if (fieldInfo != null) {
      this.field = fieldInfo.getField();
      this.readMethod = FieldUtils.getReadMethod(this.field);
    } else {
      this.field = null;
      this.readMethod = FieldUtils.getReadMethod(ownerClass, name);
    }
    if (readMethod == null) {
      throw new IllegalArgumentException("Cannot find the read method for the "
          + "property " + name + " of the bean " + ownerClass.getCanonicalName());
    }
    final Reference referenceAnnotation;
    if (field != null) {
      type = field.getType();
      writeMethod = FieldUtils.getWriteMethod(field);
      declaringClass = field.getDeclaringClass();
      referenceAnnotation = field.getAnnotation(Reference.class);
    } else {
      type = readMethod.getReturnType();
      writeMethod = FieldUtils.getWriteMethod(ownerClass, name, type);
      declaringClass = readMethod.getDeclaringClass();
      referenceAnnotation = readMethod.getAnnotation(Reference.class);
    }
    if (referenceAnnotation == null) {
      reference = false;
      referenceExisting = false;
      referenceEntity = null;
      referenceProperty = null;
      referencePath = null;
    } else {
      reference = true;
      referenceExisting = referenceAnnotation.existing();
      referenceEntity = referenceAnnotation.entity();
      referenceProperty = referenceAnnotation.property();
      referencePath = referenceAnnotation.path();
    }
  }

  private <T extends Annotation> boolean hasAnnotation(final Class<T> annotationClass) {
    if (field != null) {
      return field.isAnnotationPresent(annotationClass);
    } else {
      return readMethod.isAnnotationPresent(annotationClass);
    }
  }

  private <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
    if (field != null) {
      return field.getAnnotation(annotationClass);
    } else {
      return readMethod.getAnnotation(annotationClass);
    }
  }

  /**
   * Gets the owner class of the bean property.
   *
   * @return
   *     the owner class of the bean property.
   */
  public Class<?> getOwnerClass() {
    return ownerClass;
  }

  /**
   * Gets the declaring class of the bean property.
   *
   * @return
   *     the declaring class of the bean property, may be not the same as the
   *     owner class.
   */
  public Class<?> getDeclaringClass() {
    return declaringClass;
  }

  /**
   * Gets the name of the bean property.
   *
   * @return
   *     the name of the bean property.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the fullname of the bean property, i.e., the name in the form of
   * "Bean.prop".
   *
   * @return
   *     the fullname of the bean property.
   */
  public String getFullname() {
    return fullname;
  }

  /**
   * Gets the type of the bean property.
   *
   * @return
   *     the type of the bean property.
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Gets the array of actual type arguments of this bean property.
   *
   * @return
   *     the array of actual type arguments  of this bean property, or
   *     {@code null} if this bean property is not of a parameterized type.
   */
  @Nullable
  public final Class<?>[] getActualTypeArguments() {
    return (fieldInfo == null ? null : fieldInfo.getActualTypeArguments());
  }

  /**
   * Gets the actual component type of this bean property.
   *
   * @return
   *     the actual component type of this bean property, or {@code null} if
   *     this bean property is not of a generic array type.
   */
  @Nullable
  public final Class<?> getActualComponentType() {
    return (fieldInfo == null ? null : fieldInfo.getActualComponentType());
  }

  /**
   * Tests whether the bean property is a computed property.
   *
   * @return
   *     {@code true} if the bean property is a computed property; {@code false}
   *     otherwise.
   * @see #isNonComputed()
   * @see #getComputedDependOn()
   */
  public boolean isComputed() {
    return readMethod.isAnnotationPresent(Computed.class);
  }

  /**
   * Tests whether the bean property is a non-computed property.
   *
   * @return
   *     {@code true} if the bean property is a non-computed property; {@code false}
   *     otherwise.
   * @see #isComputed()
   * @see #getComputedDependOn()
   */
  public boolean isNonComputed() {
    return ! readMethod.isAnnotationPresent(Computed.class);
  }

  /**
   * Gets the names of fields depending on whom this property is computed.
   *
   * @return
   *     the names of fields depending on whom this property is computed, or
   *     {@code null} if no such fields.
   * @see #isComputed()
   */
  @Nullable
  public final String[] getComputedDependOn() {
    final Computed annotation = readMethod.getAnnotation(Computed.class);
    return (annotation == null ? null : nullIfEmpty(annotation.value()));
  }

  /**
   * Tests whether the bean property is a readonly property.
   *
   * @return
   *     {@code true} if the bean property is a readonly property; {@code false}
   *     otherwise.
   */
  public boolean isReadonly() {
    return ((writeMethod == null)
        && (! readMethod.isAnnotationPresent(Computed.class)));
  }

  /**
   * Tests whether the bean property is a property of a primitive type.
   *
   * @return
   *     {@code true} if the bean property is a property of a primitive type;
   *     {@code false} otherwise.
   */
  public boolean isPrimitive() {
    return type.isPrimitive();
  }

  /**
   * Tests whether the bean property is a property of an array type.
   *
   * @return
   *     {@code true} if the bean property is a property of an array type;
   *     {@code false} otherwise.
   */
  public boolean isArray() {
    return type.isArray();
  }

  /**
   * Tests whether the bean property is a property of a collection type.
   *
   * @return
   *     {@code true} if the bean property is a property of a collection type;
   *     {@code false} otherwise.
   */
  public boolean isCollection() {
    return Collection.class.isAssignableFrom(type);
  }

  /**
   * Tests whether the bean property is a JDK built-in property, i.e., a property
   * form the JDK built-in classes.
   *
   * @return
   *     whether the bean property is a JDK built-in property.
   */
  public boolean isJdkBuiltIn() {
    return ClassUtils.isJdkBuiltIn(declaringClass);
  }

  /**
   * Tests whether the bean property is the unique identifier of the model.
   *
   * @return
   *     {@code true} if the bean property is the unique identifier of the
   *     model; {@code false} otherwise.
   * @see #isUnique()
   */
  public boolean isIdentifier() {
    return hasAnnotation(Identifier.class);
  }

  /**
   * Tests whether the bean property is the auto-generated unique identifier of
   * the model.
   *
   * @return
   *     {@code true} if the bean property is the auto-generated unique
   *     identifier of the model; {@code false} otherwise.
   * @see #isUnique()
   */
  public boolean isAutoGeneratedId() {
    final Identifier annotation = getAnnotation(Identifier.class);
    return (annotation != null && annotation.autoGenerated());
  }

  /**
   * Tests whether the bean property is nullable.
   *
   * @return
   *     {@code true} if the bean property is nullable; {@code false} otherwise.
   */
  public boolean isNullable() {
    return hasAnnotation(Nullable.class);
  }

  /**
   * Tests whether the bean property is unique.
   *
   * @return
   *     {@code true} if the bean property is unique; {@code false} otherwise.
   * @see #isIdentifier()
   * @see #getUniqueRespectTo()
   */
  public boolean isUnique() {
    return hasAnnotation(Unique.class) || hasAnnotation(Identifier.class);
  }

  /**
   * Gets the names of fields respect to whom this property is unique.
   *
   * @return
   *     the names of fields respect to whom this property is unique, or
   *     {@code null} if no such fields.
   * @see #isUnique()
   */
  @Nullable
  public String[] getUniqueRespectTo() {
    final Unique annotation = getAnnotation(Unique.class);
    return (annotation == null ? null : nullIfEmpty(annotation.respectTo()));
  }

  /**
   * Tests whether this bean property, or the sub-property of this bean property,
   * is a reference to a property of another model.
   *
   * @return
   *     {@code true} if this bean property, or the sub-property of this bean
   *     property, is a reference to a property of another model; {@code false}
   *     otherwise.
   * @see #isReferenceExisting()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isReference() {
    return reference;
  }

  /**
   * Tests whether this bean property is a direct reference to a property of
   * another model.
   *
   * @return
   *     {@code true} if this bean property is a direct reference to a property
   *     of another model; {@code false} otherwise.
   * @see #isReferenceExisting()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isDirectReference() {
    return (referenceEntity != null) && (!referenceEntity.equals(Object.class));
  }

  /**
   * Tests whether this bean property is a indirect reference to a property of
   * another model.
   *
   * @return
   *     {@code true} if this bean property is an indirect reference to a
   *     property of another model; {@code false} otherwise.
   * @see #isReferenceExisting()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isIndirectReference() {
    return (! isDirectReference());
  }

  /**
   * Tests whether this bean property is a reference to a property of another
   * model and the referenced model must exist in the database.
   *
   * @return
   *     {@code true} if this bean property is a reference to a property of
   *     another model and the referenced model must exist in the database;
   *     {@code false} otherwise.
   * @see #isReference()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isReferenceExisting() {
    return referenceExisting;
  }

  /**
   * Gets the allowed range of sizes of this property, which is also the width
   * of the database table column corresponding to the property.
   *
   * @return
   *     the allowed range of sizes of this property.
   */
  public CloseRange<Integer> getSizeRange() {
    final Size size = getAnnotation(Size.class);
    if (size == null) {
      return null;
    } else {
      final Integer min = size.min();
      final Integer max = (size.max() == Integer.MAX_VALUE ? null : size.max());
      return new CloseRange<>(min, max);
    }
  }

  /**
   * Gets the class of the entity to whose property this property is referenced.
   *
   * @return
   *     the class of the entity to whose property this property is referenced.
   * @see #isReference()
   * @see #isReferenceExisting()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public Class<?> getReferenceEntity() {
    return referenceEntity;
  }

  /**
   * Gets the name of the property of another entity to which this property is
   * referenced.
   *
   * @return
   *     the name of the property of another entity to which this property is
   *     referenced.
   * @see #isReference()
   * @see #isReferenceExisting()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public String getReferenceProperty() {
    return referenceProperty;
  }

  /**
   * Gets the path of the property of the owner object to which this property is
   * referenced.
   *
   * @return
   *     the path of the property of the owner object to which this property is
   *     referenced.
   * @see #isReference()
   * @see #isReferenceExisting()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   */
  public String getReferencePath() {
    return referencePath;
  }

  /**
   * Gets the index of the field in a composed key.
   *
   * @return
   *     the index of the field in a composed key, or {@code null} if there is
   *     no such index.
   */
  @Nullable
  public Integer getKeyIndex() {
    final KeyIndex annotation = getAnnotation(KeyIndex.class);
    return (annotation == null ? null : annotation.value());
  }

  /**
   * Gets the {@link FieldInfo} object this property is corresponding to.
   *
   * @return
   *     the {@link FieldInfo} object this property is corresponding to; or
   *     {@code null} if this property does not correspond to a field.
   */
  @Nullable
  public FieldInfo getFieldInfo() {
    return fieldInfo;
  }

  /**
   * Gets the {@link Field} object this property is corresponding to.
   *
   * @return
   *     the {@link Field} object this property is corresponding to; or
   *     {@code null} if this property does not correspond to a field.
   */
  @Nullable
  public Field getField() {
    return field;
  }

  /**
   * Gets the {@link Method} object of the getter of this bean property.
   *
   * @return
   *     the {@link Method} object of the getter of this bean property, which is
   *     always not null.
   */
  public Method getReadMethod() {
    return readMethod;
  }

  /**
   * Gets the {@link Method} object of the setter of this bean property.
   *
   * @return
   *     the {@link Method} object of the setter of this bean property, which is
   *     {@code null} if this property has no setter, i.e., it is a readonly
   *     bean property or a computed bean property.
   */
  @Nullable
  public Method getWriteMethod() {
    return writeMethod;
  }

  /**
   * Gets the qualified name of this bean property.
   *
   * @return
   *      the qualified name of this bean property.
   */
  public String getQualifiedName() {
    return ownerClass.getSimpleName() + "." + name;
  }

  /**
   * Gets the value of this bean property of the specified object.
   *
   * @param owner
   *     a specified object.
   * @return
   *     the value of this bean property of the specified object.
   * @throws ReflectionException
   *     if any reflection error occurs.
   */
  public Object getValue(final Object owner) {
    try {
      return readMethod.invoke(owner);
    } catch (final IllegalAccessException | InvocationTargetException | RuntimeException e) {
      LOGGER.error("Failed to get value for {}: {}", fullname, e.getMessage());
      throw new ReflectionException(e);
    }
  }

  /**
   * Sets the value of this bean property of the specified object.
   *
   * @param owner
   *     the specified object.
   * @param value
   *     the specified value.
   * @throws ReflectionException
   *     if any reflection error occurs.
   */
  public void setValue(final Object owner, @Nullable final Object value) {
    if (writeMethod == null) {
      throw new ReflectionException("Cannot write a readonly or computed property: "
          + getQualifiedName());
    }
    try {
      writeMethod.invoke(owner, value);
    } catch (final IllegalAccessException | InvocationTargetException | RuntimeException e) {
      LOGGER.error("Failed to set value for {}: {}", fullname, e.getMessage());
      throw new ReflectionException(e);
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    // 确定一个属性仅需其 ownerClass 和 name
    final Property other = (Property) o;
    return Equality.equals(ownerClass, other.ownerClass)
        && Equality.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    // 确定一个属性仅需其 ownerClass 和 name
    result = Hash.combine(result, multiplier, ownerClass);
    result = Hash.combine(result, multiplier, name);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("ownerClass", ownerClass)
        .append("name", name)
        .append("fullname", fullname)
        .append("field", field)
        .append("readMethod", readMethod)
        .append("writeMethod", writeMethod)
        .append("type", type)
        .append("declaringClass", declaringClass)
        .append("reference", reference)
        .append("referenceExisting", referenceExisting)
        .append("referenceEntity", referenceEntity)
        .append("referenceProperty", referenceProperty)
        .append("referencePath", referencePath)
        .append("computed", isComputed())
        .append("computedDependOn", getComputedDependOn())
        .append("readonly", isReadonly())
        .append("identifier", isIdentifier())
        .append("nullable", isNullable())
        .append("primitive", isPrimitive())
        .append("array", isArray())
        .append("collection", isCollection())
        .append("unique", isUnique())
        .append("uniqueRespectTo", getUniqueRespectTo())
        .append("jdkBuiltIn", isJdkBuiltIn())
        .append("keyIndex", getKeyIndex())
        .append("sizeRange", getSizeRange())
        .toString();
  }
}
