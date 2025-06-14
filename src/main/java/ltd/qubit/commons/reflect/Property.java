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
   * 获取指定的属性。
   *
   * @param ownerClass
   *     指定属性的拥有者类的类对象。
   * @param name
   *     指定属性的名称。
   * @return
   *     指定的属性。
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
   * 获取指定的属性。
   *
   * @param <T>
   *     指定属性的拥有者类的类型。
   * @param <R>
   *     指定属性的类型。
   * @param ownerClass
   *     指定属性的拥有者类的类对象。
   * @param getter
   *     指定属性的getter方法引用。
   * @return
   *     指定的属性。
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
   * 构造一个 {@link Property} 对象。
   *
   * @param ownerClass
   *     bean属性的拥有者类。
   * @param name
   *     bean属性的名称。
   */
  private Property(final Class<?> ownerClass, final String name) {
    this(ownerClass, name, FieldUtils.getFieldInfo(ownerClass, Option.BEAN_FIELD, name));
  }

  /**
   * 构造一个 {@link Property} 对象。
   *
   * @param ownerClass
   *     bean属性的拥有者类。
   * @param name
   *     bean属性的名称。
   * @param fieldInfo
   *     bean属性的字段对象，可能为null。
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

  /**
   * 测试此属性是否有指定的注解。
   *
   * @param <T>
   *     注解类型。
   * @param annotationClass
   *     指定的注解类。
   * @return
   *     如果此属性有指定的注解则返回 {@code true}；否则返回 {@code false}。
   */
  private <T extends Annotation> boolean hasAnnotation(final Class<T> annotationClass) {
    if (field != null) {
      return field.isAnnotationPresent(annotationClass);
    } else if (readMethod != null) {
      return readMethod.isAnnotationPresent(annotationClass);
    } else {
      return false;
    }
  }

  /**
   * 获取此属性的指定注解。
   *
   * @param <T>
   *     注解类型。
   * @param annotationClass
   *     指定的注解类。
   * @return
   *     此属性的指定注解，如果没有此注解则返回 {@code null}。
   */
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
   * 获取bean属性的拥有者类。
   *
   * @return
   *     bean属性的拥有者类。
   */
  public Class<?> getOwnerClass() {
    return ownerClass;
  }

  /**
   * 获取bean属性的声明类。
   *
   * @return
   *     bean属性的声明类，可能与拥有者类不同。
   */
  public Class<?> getDeclaringClass() {
    return declaringClass;
  }

  /**
   * 获取bean属性的名称。
   *
   * @return
   *     bean属性的名称。
   */
  public String getName() {
    return name;
  }

  /**
   * 获取bean属性的全名，即"Bean.prop"形式的名称。
   *
   * @return
   *     bean属性的全名。
   */
  public String getFullname() {
    return fullname;
  }

  /**
   * 获取bean属性的类型。
   *
   * @return
   *     bean属性的类型。
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * 获取此bean属性的实际类型参数数组。
   *
   * @return
   *     此bean属性的实际类型参数数组，如果此bean属性不是参数化类型则返回 {@code null}。
   */
  @Nullable
  public final Class<?>[] getActualTypeArguments() {
    return (fieldInfo == null ? null : fieldInfo.getActualTypeArguments());
  }

  /**
   * 获取此bean属性的实际组件类型。
   *
   * @return
   *     此bean属性的实际组件类型，如果此bean属性不是泛型数组类型则返回 {@code null}。
   */
  @Nullable
  public final Class<?> getActualComponentType() {
    return (fieldInfo == null ? null : fieldInfo.getActualComponentType());
  }

  /**
   * 测试bean属性是否为公共可访问字段。
   *
   * @return
   *     如果bean属性是公共可访问字段则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isPublicField() {
    return publicField;
  }

  /**
   * 测试bean属性是否为受保护字段。
   *
   * @return
   *     如果bean属性是受保护字段则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isProtectedField() {
    return protectedField;
  }

  /**
   * 测试bean属性是否为私有字段。
   *
   * @return
   *     如果bean属性是私有字段则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isPrivateField() {
    return privateField;
  }

  /**
   * 测试bean属性是否为瞬态字段。
   *
   * @return
   *     如果bean属性是瞬态字段则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isTransientField() {
    return transientField;
  }

  /**
   * 测试bean属性是否为最终字段。
   *
   * @return
   *     如果bean属性是最终字段则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isFinalField() {
    return finalField;
  }

  /**
   * 测试bean属性是否为计算属性。
   *
   * @return
   *     如果bean属性是计算属性则返回 {@code true}；否则返回 {@code false}。
   * @see #isNonComputed()
   * @see #getComputedDependOn()
   */
  public boolean isComputed() {
    return computed;
  }

  /**
   * 测试bean属性是否为非计算属性。
   *
   * @return
   *     如果bean属性是非计算属性则返回 {@code true}；否则返回 {@code false}。
   * @see #isComputed()
   * @see #getComputedDependOn()
   */
  public boolean isNonComputed() {
    return (! computed);
  }

  /**
   * 获取此属性计算所依赖的字段名称。
   *
   * @return
   *     此属性计算所依赖的字段名称，如果没有此类字段则返回 {@code null}。
   * @see #isComputed()
   */
  @Nullable
  public final String[] getComputedDependOn() {
    final Computed annotation = getAnnotation(Computed.class);
    return (annotation == null ? null : nullIfEmpty(annotation.value()));
  }

  /**
   * 测试bean属性是否为只读属性。
   *
   * @return
   *     如果bean属性是只读属性则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isReadonly() {
    return readonly;
  }

  /**
   * 测试bean属性是否为基本类型属性。
   *
   * @return
   *     如果bean属性是基本类型属性则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isPrimitive() {
    return type.isPrimitive();
  }

  /**
   * 测试bean属性是否为数组类型属性。
   *
   * @return
   *     如果bean属性是数组类型属性则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isArray() {
    return type.isArray();
  }

  /**
   * 测试bean属性是否为集合类型属性。
   *
   * @return
   *     如果bean属性是集合类型属性则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isCollection() {
    return Collection.class.isAssignableFrom(type);
  }

  /**
   * 测试bean属性是否为映射类型属性。
   *
   * @return
   *     如果bean属性是映射类型属性则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isMap() {
    return Map.class.isAssignableFrom(type);
  }

  /**
   * 测试bean属性是否为JDK内置属性，即来自JDK内置类的属性。
   *
   * @return
   *     bean属性是否为JDK内置属性。
   */
  public boolean isJdkBuiltIn() {
    return ClassUtils.isJdkBuiltIn(declaringClass);
  }

  /**
   * 测试bean属性是否为模型的唯一标识符。
   *
   * @return
   *     如果bean属性是模型的唯一标识符则返回 {@code true}；否则返回 {@code false}。
   * @see #isUnique()
   */
  public boolean isIdentifier() {
    return hasAnnotation(Identifier.class);
  }

  /**
   * 测试bean属性是否为模型的自动生成唯一标识符。
   *
   * @return
   *     如果bean属性是模型的自动生成唯一标识符则返回 {@code true}；否则返回 {@code false}。
   * @see #isUnique()
   */
  public boolean isAutoGeneratedId() {
    final Identifier annotation = getAnnotation(Identifier.class);
    return (annotation != null && annotation.autoGenerated());
  }

  /**
   * 测试bean属性是否可为空。
   *
   * @return
   *     如果bean属性可为空则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isNullable() {
    return hasAnnotation(Nullable.class);
  }

  /**
   * 测试bean属性是否为唯一的。
   * <p>
   * <b>注意：</b> ID属性也被认为是唯一属性。
   *
   * @return
   *     如果bean属性是唯一的则返回 {@code true}；否则返回 {@code false}。
   * @see #isIdentifier()
   * @see #getUniqueRespectTo()
   */
  public boolean isUnique() {
    return hasAnnotation(Unique.class) || hasAnnotation(Identifier.class);
  }

  /**
   * 获取此属性相对于哪些字段是唯一的字段名称。
   *
   * @return
   *     此属性相对于哪些字段是唯一的字段名称，如果没有此类字段则返回 {@code null}。
   * @see #isUnique()
   */
  @Nullable
  public String[] getUniqueRespectTo() {
    final Unique annotation = getAnnotation(Unique.class);
    return (annotation == null ? null : nullIfEmpty(annotation.respectTo()));
  }

  /**
   * 测试此bean属性或此bean属性的子属性是否为对另一个模型属性的引用。
   *
   * @return
   *     如果此bean属性或此bean属性的子属性是对另一个模型属性的引用则返回 {@code true}；
   *     否则返回 {@code false}。
   * @see #isReferenceExisting()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isReference() {
    return reference;
  }

  /**
   * 测试此bean属性是否为对另一个模型属性的直接引用。
   *
   * @return
   *     如果此bean属性是对另一个模型属性的直接引用则返回 {@code true}；否则返回 {@code false}。
   * @see #isReferenceExisting()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isDirectReference() {
    return (referenceEntity != null) && (!referenceEntity.equals(Object.class));
  }

  /**
   * 测试此bean属性是否为对另一个模型属性的间接引用。
   *
   * @return
   *     如果此bean属性是对另一个模型属性的间接引用则返回 {@code true}；否则返回 {@code false}。
   * @see #isReferenceExisting()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isIndirectReference() {
    return (referenceEntity != null) && referenceEntity.equals(Object.class);
  }

  /**
   * 测试此bean属性是否为对另一个模型属性的引用，且被引用的模型必须存在于数据库中。
   *
   * @return
   *     如果此bean属性是对另一个模型属性的引用且被引用的模型必须存在于数据库中则返回 {@code true}；
   *     否则返回 {@code false}。
   * @see #isReference()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isReferenceExisting() {
    return referenceExisting;
  }

  /**
   * 测试此bean属性是否为对此bean的另一个属性的被引用实体的属性的引用。
   *
   * @return
   *     如果此bean属性是对此bean的另一个属性的被引用实体的属性的引用则返回 {@code true}；
   *     否则返回 {@code false}。
   * @see #isReference()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public boolean isReferenceToPath() {
    return (!StringUtils.isEmpty(referencePath));
  }

  /**
   * 获取此属性允许的大小范围，这也是与属性对应的数据库表列的宽度。
   *
   * @return
   *     此属性允许的大小范围。
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
   * 获取此属性所引用的实体类。
   *
   * @return
   *     此属性所引用的实体类。
   * @see #isReference()
   * @see #isReferenceExisting()
   * @see #getReferenceProperty()
   * @see #getReferencePath()
   */
  public Class<?> getReferenceEntity() {
    return referenceEntity;
  }

  /**
   * 获取此属性所引用的另一个实体的属性名称。
   *
   * @return
   *     此属性所引用的另一个实体的属性名称。
   * @see #isReference()
   * @see #isReferenceExisting()
   * @see #getReferenceEntity()
   * @see #getReferencePath()
   */
  public String getReferenceProperty() {
    return referenceProperty;
  }

  /**
   * 获取此属性所引用的拥有者对象的属性路径。
   *
   * @return
   *     此属性所引用的拥有者对象的属性路径。
   * @see #isReference()
   * @see #isReferenceExisting()
   * @see #getReferenceEntity()
   * @see #getReferenceProperty()
   */
  public String getReferencePath() {
    return referencePath;
  }

  /**
   * 获取字段在组合键中的索引。
   *
   * @return
   *     字段在组合键中的索引，如果没有此类索引则返回 {@code null}。
   */
  @Nullable
  public Integer getKeyIndex() {
    final KeyIndex annotation = getAnnotation(KeyIndex.class);
    return (annotation == null ? null : annotation.value());
  }

  /**
   * 获取此属性对应的 {@link FieldInfo} 对象。
   *
   * @return
   *     此属性对应的 {@link FieldInfo} 对象；如果此属性不对应字段则返回 {@code null}。
   */
  @Nullable
  public FieldInfo getFieldInfo() {
    return fieldInfo;
  }

  /**
   * 获取此属性对应的 {@link Field} 对象。
   *
   * @return
   *     此属性对应的 {@link Field} 对象；如果此属性不对应字段则返回 {@code null}。
   */
  @Nullable
  public Field getField() {
    return field;
  }

  /**
   * 获取此bean属性的getter的 {@link Method} 对象。
   *
   * @return
   *     此bean属性的getter的 {@link Method} 对象，始终不为null。
   */
  @Nullable
  public Method getReadMethod() {
    return readMethod;
  }

  /**
   * 获取此bean属性的setter的 {@link Method} 对象。
   *
   * @return
   *     此bean属性的setter的 {@link Method} 对象，如果此属性没有setter（即它是只读bean属性或计算bean属性）则返回 {@code null}。
   */
  @Nullable
  public Method getWriteMethod() {
    return writeMethod;
  }

  /**
   * 获取此bean属性的完全限定名。
   *
   * @return
   *      此bean属性的完全限定名。
   */
  public String getFullQualifiedName() {
    return ClassUtils.getFullCanonicalName(ownerClass) + "." + name;
  }

  /**
   * 获取指定对象的此bean属性的值。
   *
   * @param owner
   *     指定的对象。
   * @return
   *     指定对象的此bean属性的值。
   * @throws ReflectionException
   *     如果发生任何反射错误。
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
   * 设置指定对象的此bean属性的值。
   *
   * @param owner
   *     指定的对象。
   * @param value
   *     指定的值。
   * @throws ReflectionException
   *     如果发生任何反射错误。
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
   * 将指定对象的此bean属性的值设置为字符串。
   *
   * @param owner
   *     指定的对象。
   * @param stringValue
   *     要设置的值的字符串表示。
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
   * 将指定对象的此bean属性的值设置为字符串数组。
   *
   * @param owner
   *     指定的对象。
   * @param stringValues
   *     要设置的值的字符串表示数组。
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