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
 * Provides {@link PropertyName} related utility functions.
 *
 * @author Haixing Hu
 */
public class PropertyNameUtils {

  public static final String JAXB_DEFAULT_VALUE = "##default";

  public static final String JACKSON_DEFAULT_VALUE = "";

  /**
   * Tests whether a string is the default value of a name or namespace of a
   * JAXB annotation.
   * <p>
   * A default name or namespace is either a {@code null}, or an empty string,
   * or the string "##default".
   *
   * @param value
   *     the string value to be tested.
   * @return
   *     {@code true} if the string value is the default value of a  name or
   *     namespace of a JAXB annotation; {@code false} otherwise.
   */
  public static boolean isJaxbDefaultValue(@Nullable final String value) {
    return (value == null)
        || (value.length() == 0)
        || value.equals(JAXB_DEFAULT_VALUE);
  }

  /**
   * Tests whether a string value is the default value of a name or namespace
   * of a JACKSON annotation.
   * <p>
   * A default name or namespace is either a {@code null}, or an empty string.
   *
   * @param value
   *     the string value to be tested.
   * @return
   *     {@code true} if the string value is the default value of a name or
   *     namespace of a JACKSON annotation; {@code false} otherwise.
   */
  public static boolean isJacksonDefaultValue(@Nullable final String value) {
    return (value == null)
        || value.equals(JACKSON_DEFAULT_VALUE);
  }

  /**
   * Constructs a {@link PropertyName} for an XML element for the specified
   * name and namespace provided by an JAXB annotation.
   *
   * @param name
   *     the provided name, which could be {@code null}, or the default value.
   * @param namespace
   *     the provided namespace, which could be {@code null}, or the default value.
   * @return
   *     The {@link PropertyName} for an XML element for the specified name and
   *     namespace provided by an JAXB annotation; or {@code null} if the
   *     provided name is the default value of the name of a JAXB annotation.
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
   * Constructs a {@link PropertyName} for an XML attribute for the specified
   * name and namespace provided by an JAXB annotation.
   *
   * @param name
   *     the provided name, which could be {@code null}, or the default value.
   * @param namespace
   *     the provided namespace, which could be {@code null}, or the default value.
   * @return
   *     The {@link PropertyName} for an XML attribute for the specified name and
   *     namespace provided by an JAXB annotation; or {@code null} if the
   *     provided name is the default value of the name of a JAXB annotation.
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
   * Constructs a {@link PropertyName} for an XML element for the specified
   * name and namespace provided by an JACKSON annotation.
   *
   * @param name
   *     the provided name, which could be {@code null}, or the default value.
   * @param namespace
   *     the provided namespace, which could be {@code null}, or the default value.
   * @return
   *     The {@link PropertyName} for an XML element for the specified name and
   *     namespace provided by an JACKSON annotation; or {@code null} if the
   *     provided name is the default value of the name of a JACKSON annotation.
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
   * Constructs a {@link PropertyName} for an XML attribute for the specified
   * name and namespace provided by an JACKSON annotation.
   *
   * @param name
   *     the provided name, which could be {@code null}, or the default value.
   * @param namespace
   *     the provided namespace, which could be {@code null}, or the default value.
   * @return
   *     The {@link PropertyName} for an XML attribute for the specified name and
   *     namespace provided by an JACKSON annotation; or {@code null} if the
   *     provided name is the default value of the name of a JACKSON annotation.
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
   * Constructs a {@link PropertyName} for a JSON property for the specified
   * name and namespace provided by an JACKSON annotation.
   *
   * @param name
   *     the provided name, which could be {@code null}, or the default value.
   * @param namespace
   *     the provided namespace, which could be {@code null}, or the default value.
   * @return
   *     The {@link PropertyName} for a JSON property for the specified name and
   *     namespace provided by an JACKSON annotation; or {@code null} if the
   *     provided name is the default value of the name of a JACKSON annotation.
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

  public static PropertyName newXmlElementName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    return new PropertyName(translateName(namingStrategy, name));
  }

  public static PropertyName newXmlAttributeName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    return new PropertyName("@" + translateName(namingStrategy, name));
  }

  public static PropertyName newXmlWrapperElementName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    return newXmlElementName(namingStrategy, name);
  }

  public static PropertyName newXmlWrappedElementName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    final String translateName = translateName(namingStrategy, name);
    // translate the plural form of the field name to its singular form
    final PluralToSingularTransformer singularTransformer = PluralToSingularTransformer.INSTANCE;
    final String singularName = singularTransformer.transform(translateName);
    return new PropertyName(singularName);
  }

  public static PropertyName newJsonPropertyName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    return new PropertyName(translateName(namingStrategy, name));
  }

  public static PropertyName newJsonWrappedPropertyName(final PropertyNamingStrategy namingStrategy,
      final String name) {
    final String translateName = translateName(namingStrategy, name);
    // translate the plural form of the field name to its singular form
    final PluralToSingularTransformer singularTransformer = PluralToSingularTransformer.INSTANCE;
    final String singularName = singularTransformer.transform(translateName);
    return new PropertyName(singularName);
  }
}