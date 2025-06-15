////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;

import ltd.qubit.commons.util.transformer.string.PluralToSingularTransformer;

/**
 * 提供{@link PropertyName}相关的工具函数。
 *
 * @author 胡海星
 */
public class PropertyNameUtils {

  /**
   * JAXB注解的默认值常量。
   */
  public static final String JAXB_DEFAULT_VALUE = "##default";

  /**
   * Jackson注解的默认值常量。
   */
  public static final String JACKSON_DEFAULT_VALUE = "";

  /**
   * 测试字符串是否为JAXB注解的名称或命名空间的默认值。
   * <p>
   * 默认名称或命名空间可以是{@code null}、空字符串或字符串"##default"。
   *
   * @param value
   *     要测试的字符串值。
   * @return
   *     如果字符串值是JAXB注解的名称或命名空间的默认值，则返回{@code true}；否则返回{@code false}。
   */
  public static boolean isJaxbDefaultValue(@Nullable final String value) {
    return (value == null)
        || (value.length() == 0)
        || value.equals(JAXB_DEFAULT_VALUE);
  }

  /**
   * 测试字符串值是否为Jackson注解的名称或命名空间的默认值。
   * <p>
   * 默认名称或命名空间可以是{@code null}或空字符串。
   *
   * @param value
   *     要测试的字符串值。
   * @return
   *     如果字符串值是Jackson注解的名称或命名空间的默认值，则返回{@code true}；否则返回{@code false}。
   */
  public static boolean isJacksonDefaultValue(@Nullable final String value) {
    return (value == null)
        || value.equals(JACKSON_DEFAULT_VALUE);
  }

  /**
   * 为JAXB注解提供的指定名称和命名空间构造XML元素的{@link PropertyName}。
   *
   * @param name
   *     提供的名称，可以是{@code null}或默认值。
   * @param namespace
   *     提供的命名空间，可以是{@code null}或默认值。
   * @return
   *     JAXB注解提供的指定名称和命名空间的XML元素的{@link PropertyName}；
   *     如果提供的名称是JAXB注解名称的默认值，则返回{@code null}。
   */
  @Nullable
  public static PropertyName newJaxbXmlElementName(@Nullable final String name,
      @Nullable final String namespace) {
    if (isJaxbDefaultValue(name)) {
      return null;
    } else if (isJaxbDefaultValue(namespace)) {
      return PropertyName.construct(name);
    } else {
      return PropertyName.construct(name, namespace);
    }
  }

  /**
   * 为JAXB注解提供的指定名称和命名空间构造XML属性的{@link PropertyName}。
   *
   * @param name
   *     提供的名称，可以是{@code null}或默认值。
   * @param namespace
   *     提供的命名空间，可以是{@code null}或默认值。
   * @return
   *     JAXB注解提供的指定名称和命名空间的XML属性的{@link PropertyName}；
   *     如果提供的名称是JAXB注解名称的默认值，则返回{@code null}。
   */
  @Nullable
  public static PropertyName newJaxbXmlAttributeName(@Nullable final String name,
      @Nullable final String namespace) {
    if (isJaxbDefaultValue(name)) {
      return null;
    } else if (isJaxbDefaultValue(namespace)) {
      return PropertyName.construct("@" + name);
    } else {
      return PropertyName.construct("@" + name, namespace);
    }
  }

  /**
   * 为Jackson注解提供的指定名称和命名空间构造XML元素的{@link PropertyName}。
   *
   * @param name
   *     提供的名称，可以是{@code null}或默认值。
   * @param namespace
   *     提供的命名空间，可以是{@code null}或默认值。
   * @return
   *     Jackson注解提供的指定名称和命名空间的XML元素的{@link PropertyName}；
   *     如果提供的名称是Jackson注解名称的默认值，则返回{@code null}。
   */
  @Nullable
  public static PropertyName newJacksonXmlElementName(@Nullable final String name,
      @Nullable final String namespace) {
    if (isJacksonDefaultValue(name)) {
      return null;
    } else if (isJacksonDefaultValue(namespace)) {
      return PropertyName.construct(name);
    } else {
      return PropertyName.construct(name, namespace);
    }
  }

  /**
   * 为Jackson注解提供的指定名称和命名空间构造XML属性的{@link PropertyName}。
   *
   * @param name
   *     提供的名称，可以是{@code null}或默认值。
   * @param namespace
   *     提供的命名空间，可以是{@code null}或默认值。
   * @return
   *     Jackson注解提供的指定名称和命名空间的XML属性的{@link PropertyName}；
   *     如果提供的名称是Jackson注解名称的默认值，则返回{@code null}。
   */
  @Nullable
  public static PropertyName newJacksonXmlAttributeName(@Nullable final String name,
      @Nullable final String namespace) {
    if (isJacksonDefaultValue(name)) {
      return null;
    } else if (isJacksonDefaultValue(namespace)) {
      return PropertyName.construct("@" + name);
    } else {
      return PropertyName.construct("@" + name, namespace);
    }
  }

  /**
   * 为Jackson注解提供的指定名称和命名空间构造JSON属性的{@link PropertyName}。
   *
   * @param name
   *     提供的名称，可以是{@code null}或默认值。
   * @param namespace
   *     提供的命名空间，可以是{@code null}或默认值。
   * @return
   *     Jackson注解提供的指定名称和命名空间的JSON属性的{@link PropertyName}；
   *     如果提供的名称是Jackson注解名称的默认值，则返回{@code null}。
   */
  @Nullable
  public static PropertyName newJacksonJsonPropertyName(@Nullable final String name,
      @Nullable final String namespace) {
    if (isJacksonDefaultValue(name)) {
      return null;
    } else if (isJacksonDefaultValue(namespace)) {
      return PropertyName.construct(name);
    } else {
      return PropertyName.construct(name, namespace);
    }
  }

  /**
   * 使用属性命名策略翻译名称。
   *
   * @param namingStrategy
   *     属性命名策略。
   * @param name
   *     要翻译的名称。
   * @return
   *     翻译后的名称。
   */
  @SuppressWarnings("deprecation")
  public static String translateName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    if (namingStrategy instanceof PropertyNamingStrategyBase) {
      return ((PropertyNamingStrategyBase) namingStrategy).translate(name);
    } else if (namingStrategy instanceof NamingBase) {
      return ((NamingBase) namingStrategy).translate(name);
    } else {
      return name;
    }
  }

  /**
   * 创建XML元素名称的PropertyName。
   *
   * @param namingStrategy
   *     属性命名策略。
   * @param name
   *     原始名称。
   * @return
   *     XML元素的PropertyName。
   */
  public static PropertyName newXmlElementName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    return new PropertyName(translateName(namingStrategy, name));
  }

  /**
   * 创建XML属性名称的PropertyName。
   *
   * @param namingStrategy
   *     属性命名策略。
   * @param name
   *     原始名称。
   * @return
   *     XML属性的PropertyName。
   */
  public static PropertyName newXmlAttributeName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    return new PropertyName("@" + translateName(namingStrategy, name));
  }

  /**
   * 创建XML包装器元素名称的PropertyName。
   *
   * @param namingStrategy
   *     属性命名策略。
   * @param name
   *     原始名称。
   * @return
   *     XML包装器元素的PropertyName。
   */
  public static PropertyName newXmlWrapperElementName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    return newXmlElementName(namingStrategy, name);
  }

  /**
   * 创建XML被包装元素名称的PropertyName。
   * <p>
   * 该方法会将字段名称的复数形式转换为单数形式。
   *
   * @param namingStrategy
   *     属性命名策略。
   * @param name
   *     原始名称。
   * @return
   *     XML被包装元素的PropertyName。
   */
  public static PropertyName newXmlWrappedElementName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    final String translateName = translateName(namingStrategy, name);
    // translate the plural form of the field name to its singular form
    final PluralToSingularTransformer singularTransformer = PluralToSingularTransformer.INSTANCE;
    final String singularName = singularTransformer.transform(translateName);
    return new PropertyName(singularName);
  }

  /**
   * 创建JSON属性名称的PropertyName。
   *
   * @param namingStrategy
   *     属性命名策略。
   * @param name
   *     原始名称。
   * @return
   *     JSON属性的PropertyName。
   */
  public static PropertyName newJsonPropertyName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    return new PropertyName(translateName(namingStrategy, name));
  }

  /**
   * 创建JSON被包装属性名称的PropertyName。
   * <p>
   * 该方法会将字段名称的复数形式转换为单数形式。
   *
   * @param namingStrategy
   *     属性命名策略。
   * @param name
   *     原始名称。
   * @return
   *     JSON被包装属性的PropertyName。
   */
  public static PropertyName newJsonWrappedPropertyName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    final String translateName = translateName(namingStrategy, name);
    // translate the plural form of the field name to its singular form
    final PluralToSingularTransformer singularTransformer = PluralToSingularTransformer.INSTANCE;
    final String singularName = singularTransformer.transform(translateName);
    return new PropertyName(singularName);
  }
}