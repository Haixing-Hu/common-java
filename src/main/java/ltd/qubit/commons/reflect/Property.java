////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import jakarta.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.KeyIndex;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.Argument.requireNonEmpty;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.nullIfEmpty;

/**
 * Bean属性的模型。
 *
 * @author 胡海星
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

  private final boolean computed;

  private final boolean readonly;

  private final boolean publicField;

  private final boolean protectedField;

  private final boolean privateField;

  private final boolean transientField;

  private final boolean finalField;

  @Nullable
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
    this(ownerClass, name, FieldUtils.getFieldInfo(ownerClass, Option.BEAN_FIELD, name));
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
    this.field = (fieldInfo == null ? null : fieldInfo.getField());
    if (field != null) {
      type = field.getType();
      readMethod = FieldUtils.getReadMethod(field);
      writeMethod = FieldUtils.getWriteMethod(field);
      declaringClass = field.getDeclaringClass();
      publicField = ((field.getModifiers() & Modifier.PUBLIC) != 0);
      protectedField = ((field.getModifiers() & Modifier.PROTECTED) != 0);
      privateField = ((field.getModifiers() & Modifier.PRIVATE) != 0);
      transientField = ((field.getModifiers() & Modifier.TRANSIENT) != 0);
      finalField = ((field.getModifiers() & Modifier.FINAL) != 0);
    } else {
      readMethod = FieldUtils.getReadMethod(ownerClass, name);
      if (readMethod == null) {
        throw new IllegalArgumentException("The specified property has no field nor read method:" + fullname);
      }
      type = readMethod.getReturnType();
      writeMethod = FieldUtils.getWriteMethod(ownerClass, name, type);
      declaringClass = readMethod.getDeclaringClass();
      publicField = false;
      protectedField = false;
      privateField = false;
      transientField = false;
      finalField = false;
    }
    computed = (readMethod != null) && (readMethod.isAnnotationPresent(Computed.class));
    readonly = (!publicField) && (!computed) && (writeMethod == null);
    final Reference referenceAnnotation;
    if (field != null && field.isAnnotationPresent(Reference.class)) {
      referenceAnnotation = field.getAnnotation(Reference.class);
    } else if (readMethod != null && Reference.class.isAnnotationPresent(Reference.class)) {
      referenceAnnotation = readMethod.getAnnotation(Reference.class);
    } else {
      referenceAnnotation = null;
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
    } else if (readMethod != null) {
      return readMethod.isAnnotationPresent(annotationClass);
    } else {
      return false;
    }
  }

  private <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
    if (field != null) {
      return field.getAnnotation(annotationClass);
    } else if (readMethod != null) {
      return readMethod.getAnnotation(annotationClass);
    } else {
      return null;
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
   * Tests whether the bean property is a public accessible field.
   *
   * @return
   *     {@code true} if the bean property is a public accessible field;
   *     {@code false} otherwise.
   */
  public boolean isPublicField() {
    return publicField;
  }

  /**
   * Tests whether the bean property is a protected field.
   *
   * @return
   *     {@code true} if the bean property is a protected field; {@code false}
   *     otherwise.
   */
  public boolean isProtectedField() {
    return protectedField;
  }

  /**
   * Tests whether the bean property is a private field.
   *
   * @return
   *     {@code true} if the bean property is a private field; {@code false}
   *     otherwise.
   */
  public boolean isPrivateField() {
    return privateField;
  }

  /**
   * Tests whether the bean property is a transient field.
   *
   * @return
   *     {@code true} if the bean property is a transient field; {@code false}
   *     otherwise.
   */
  public boolean isTransientField() {
    return transientField;
  }

  /**
   * Tests whether the bean property is a final field.
   *
   * @return
   *     {@code true} if the bean property is a final field; {@code false}
   *     otherwise.
   */
  public boolean isFinalField() {
    return finalField;
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
    return computed;
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
    return (! computed);
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
    final Computed annotation = getAnnotation(Computed.class);
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
    return readonly;
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
   * Tests whether the bean property is a property of a map type.
   *
   * @return
   *     {@code true} if the bean property is a property of a map type;
   *     {@code false} otherwise.
   */
  public boolean isMap() {
    return Map.class.isAssignableFrom(type);
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
   * <p>
   * <b>NOTE: </b> The ID property is also considered as an unique property.
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
   * Tests whether this bean property is an indirect reference to a property of
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
    return (referenceEntity != null) && referenceEntity.equals(Object.class);
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
   * Tests whether this bean property is a reference to a property of the
   * referenced entity of another property of this bean.
   *
   * @return
   *     {@code true} if this bean property is a reference to a property of the
   *     referenced entity of another property of this bean; {@code false} otherwise.
   * @see #isReference()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isReferenceToPath() {
    return (!StringUtils.isEmpty(referencePath));
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
  @Nullable
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
   * Gets the full qualified name of this bean property.
   *
   * @return
   *      the full qualified name of this bean property.
   */
  public String getFullQualifiedName() {
    return ClassUtils.getFullCanonicalName(ownerClass) + "." + name;
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
    if (readMethod != null) {
      return MethodUtils.invokeMethod(readMethod, owner);
    } else if (field != null) {
      return FieldUtils.readField(field, owner);
    } else {
      throw new ReflectionException("Cannot read a write-only property: " + getFullQualifiedName());
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
    if (writeMethod != null) {
      MethodUtils.invokeMethod(writeMethod, owner, value);
    } else if (field != null) {
      FieldUtils.writeField(field, owner, value);
    } else {
      throw new ReflectionException("Cannot write a read-only or computed property: " + getFullQualifiedName());
    }
  }

  /**
   * Sets the value of this bean property of the specified object as a string.
   *
   * @param owner
   *     the specified object.
   * @param stringValue
   *     the string representation of the value to be set.
   *
   */
  public void setValueAsString(final Object owner, @Nullable final String stringValue) {
    if (stringValue == null) {
      setValue(owner, null);
    } else {
      final Object value = Type.parse(type, stringValue);
      setValue(owner, value);
    }
  }

  /**
   * Sets the values of this bean property of the specified object as a string.
   *
   * @param owner
   *     the specified object.
   * @param stringValues
   *     the string representation of the values to be set.
   */
  public void setValueAsStrings(final Object owner, @Nullable final String[] stringValues) {
    if (!type.isArray()) {
      throw new IllegalArgumentException("The property is not an array: " + getFullQualifiedName());
    }
    if (stringValues == null) {
      setValue(owner, null);
      return;
    }
    final int n = stringValues.length;
    final Class<?> componentType = type.getComponentType();
    if (n == 0) {
      final Object[] values = ArrayUtils.createArray(componentType, 0);
      setValue(owner, values);
    } else {
      final Object[] values = ArrayUtils.createArray(componentType, n);
      for (int i = 0; i < n; ++i) {
        values[i] = Type.parse(componentType, stringValues[i]);
      }
      setValue(owner, values);
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