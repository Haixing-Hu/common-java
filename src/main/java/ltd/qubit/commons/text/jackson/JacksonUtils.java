////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import ltd.qubit.commons.reflect.AnnotationGetter;
import ltd.qubit.commons.reflect.AnnotationTester;
import ltd.qubit.commons.reflect.ConstructorUtils;
import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.text.Remover;
import ltd.qubit.commons.util.transformer.string.SingularToPluralTransformer;

import static ltd.qubit.commons.reflect.PropertyUtils.getPropertyNameFromSetter;
import static ltd.qubit.commons.text.jackson.Operation.SERIALIZE;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newJacksonJsonPropertyName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newJacksonXmlElementName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newJaxbXmlAttributeName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newJaxbXmlElementName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newJsonPropertyName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newJsonWrappedPropertyName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newXmlAttributeName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newXmlElementName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newXmlWrappedElementName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.newXmlWrapperElementName;
import static ltd.qubit.commons.text.jackson.PropertyNameUtils.translateName;

/**
 * 提供 JACKSON ObjectMapper 相关工具方法。
 *
 * @author 胡海星
 */
public class JacksonUtils {

  private JacksonUtils() {}

  /**
   * 获取根包装器名称。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param type
   *     指定的类型。
   * @return
   *     根包装器的属性名称。
   */
  @Nonnull
  public static PropertyName getRootWrapperName(final ObjectMapper mapper,
      final Class<?> type) {
    final PropertyName rootName = getRootName(mapper, type);
    final SingularToPluralTransformer transformer = SingularToPluralTransformer.INSTANCE;
    final String name = transformer.transform(rootName.getSimpleName());
    return new PropertyName(name);
  }

  /**
   * 获取根名称。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param type
   *     指定的类型。
   * @return
   *     根的属性名称。
   */
  @Nonnull
  public static PropertyName getRootName(final ObjectMapper mapper, final Class<?> type) {
    if (mapper instanceof XmlMapper) {
      return getXmlRootName(mapper.getPropertyNamingStrategy(), type);
    } else if (mapper instanceof JsonMapper) {
      return getJsonRootName(mapper.getPropertyNamingStrategy(), type);
    } else {
      return getJsonRootName(mapper.getPropertyNamingStrategy(), type);
    }
  }

  /**
   * 获取 XML 根名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param type
   *     指定的类型。
   * @return
   *     XML 根的属性名称。
   */
  @Nonnull
  public static PropertyName getXmlRootName(final PropertyNamingStrategy namingStrategy,
      final Class<?> type) {
    if (type.isAnnotationPresent(XmlRootElement.class)) {
      final XmlRootElement rootElement = type.getAnnotation(XmlRootElement.class);
      final PropertyName pn = newJaxbXmlElementName(rootElement.name(), rootElement.namespace());
      if (pn != null) {
        return pn;
      }
    }
    if (type.isAnnotationPresent(JacksonXmlRootElement.class)) {
      final JacksonXmlRootElement rootElement = type.getAnnotation(JacksonXmlRootElement.class);
      final PropertyName pn = newJacksonXmlElementName(rootElement.localName(), rootElement.namespace());
      if (pn != null) {
        return pn;
      }
    }
    return newXmlElementName(namingStrategy, type.getSimpleName());
  }

  /**
   * 获取 JSON 根名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param type
   *     指定的类型。
   * @return
   *     JSON 根的属性名称。
   */
  @Nonnull
  public static PropertyName getJsonRootName(final PropertyNamingStrategy namingStrategy,
      final Class<?> type) {
    if (type.isAnnotationPresent(JsonRootName.class)) {
      final JsonRootName rootName = type.getAnnotation(JsonRootName.class);
      final PropertyName pn = newJacksonXmlElementName(rootName.value(), rootName.namespace());
      if (pn != null) {
        return pn;
      }
    }
    return newJsonPropertyName(namingStrategy, type.getSimpleName());
  }

  /**
   * 获取属性名称。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param field
   *     指定的字段。
   * @return
   *     属性名称。
   */
  @Nonnull
  public static PropertyName getPropertyName(final ObjectMapper mapper, final Field field) {
    if (mapper instanceof XmlMapper) {
      return getXmlPropertyName(mapper.getPropertyNamingStrategy(), field);
    } else if (mapper instanceof JsonMapper) {
      return getJsonPropertyName(mapper.getPropertyNamingStrategy(), field);
    } else {
      return getJsonPropertyName(mapper.getPropertyNamingStrategy(), field);
    }
  }

  /**
   * 获取属性名称。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param method
   *     指定的方法。
   * @return
   *     属性名称。
   */
  @Nonnull
  public static PropertyName getPropertyName(final ObjectMapper mapper, final Method method) {
    if (mapper instanceof XmlMapper) {
      return getXmlPropertyName(mapper.getPropertyNamingStrategy(), method);
    } else if (mapper instanceof JsonMapper) {
      return getJsonPropertyName(mapper.getPropertyNamingStrategy(), method);
    } else {
      return getJsonPropertyName(mapper.getPropertyNamingStrategy(), method);
    }
  }

  /**
   * 获取 XML 属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param field
   *     指定的字段。
   * @return
   *     XML 属性名称。
   */
  @Nonnull
  public static PropertyName getXmlPropertyName(final PropertyNamingStrategy namingStrategy,
      final Field field) {
    // note that we must check/get the annotation from the field as well as the
    // corresponding read method of the field
    return getXmlPropertyNameImpl(namingStrategy, field.getName(),
        (a) -> FieldUtils.isAnnotationPresent(field, a),
        (a) -> FieldUtils.getAnnotation(field, a));
  }

  /**
   * 获取 XML 属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param method
   *     指定的方法。
   * @return
   *     XML 属性名称，如果方法不是 setter 方法则返回 {@code null}。
   */
  @Nullable
  public static PropertyName getXmlPropertyName(final PropertyNamingStrategy namingStrategy,
      final Method method) {
    final String name = getPropertyNameFromSetter(method);
    if (name == null) {
      return null;
    } else {
      return getXmlPropertyNameImpl(namingStrategy, name,
          method::isAnnotationPresent, method::getAnnotation);
    }
  }

  @Nonnull
  private static PropertyName getXmlPropertyNameImpl(final PropertyNamingStrategy namingStrategy,
      final String name, final AnnotationTester tester, final AnnotationGetter getter) {
    if (tester.exist(XmlElement.class)) {
      final XmlElement a = (XmlElement) getter.get(XmlElement.class);
      assert a != null;
      final PropertyName pn = newJaxbXmlElementName(a.name(), a.namespace());
      if (pn != null) {
        return pn;
      } else {
        return newXmlElementName(namingStrategy, name);
      }
    } else if (tester.exist(XmlAttribute.class)) {
      final XmlAttribute a = (XmlAttribute) getter.get(XmlAttribute.class);
      assert a != null;
      final PropertyName pn = newJaxbXmlAttributeName(a.name(), a.namespace());
      if (pn != null) {
        return pn;
      } else {
        return newXmlAttributeName(namingStrategy, name);
      }
    } else {
      return newXmlElementName(namingStrategy, name);
    }
  }

  /**
   * 获取 JSON 属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param field
   *     指定的字段。
   * @return
   *     JSON 属性名称。
   */
  @Nonnull
  public static PropertyName getJsonPropertyName(final PropertyNamingStrategy namingStrategy,
      final Field field) {
    // note that we must check/get the annotation from the field as well as the
    // corresponding read method of the field
    return getJsonPropertyNameImpl(namingStrategy, field.getName(),
        (a) -> FieldUtils.isAnnotationPresent(field, a),
        (a) -> FieldUtils.getAnnotation(field, a));
  }

  /**
   * 获取 JSON 属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param method
   *     指定的方法。
   * @return
   *     JSON 属性名称，如果方法不是 setter 方法则返回 {@code null}。
   */
  @Nullable
  public static PropertyName getJsonPropertyName(final PropertyNamingStrategy namingStrategy,
      final Method method) {
    final String name = getPropertyNameFromSetter(method);
    if (name == null) {
      return null;
    } else {
      return getJsonPropertyNameImpl(namingStrategy, name,
          method::isAnnotationPresent, method::getAnnotation);
    }
  }

  @Nonnull
  private static PropertyName getJsonPropertyNameImpl(final PropertyNamingStrategy namingStrategy,
      final String name, final AnnotationTester tester, final AnnotationGetter getter) {
    if (tester.exist(JsonProperty.class)) {
      final JsonProperty a = (JsonProperty) getter.get(JsonProperty.class);
      assert a != null;
      final PropertyName pn = newJacksonXmlElementName(a.value(), a.namespace());
      if (pn != null) {
        return pn;
      }
    }
    return newJsonPropertyName(namingStrategy, name);
  }

  /**
   * 获取包装器属性名称。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param field
   *     指定的字段。
   * @return
   *     包装器属性名称。
   */
  @Nonnull
  public static PropertyName getWrapperPropertyName(final ObjectMapper mapper,
      final Field field) {
    if (mapper instanceof XmlMapper) {
      return getXmlWrapperPropertyName(mapper.getPropertyNamingStrategy(), field);
    } else if (mapper instanceof JsonMapper) {
      return getJsonWrapperPropertyName(mapper.getPropertyNamingStrategy(), field);
    } else {
      return getJsonWrapperPropertyName(mapper.getPropertyNamingStrategy(), field);
    }
  }

  /**
   * 获取包装器属性名称。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param method
   *     指定的方法。
   * @return
   *     包装器属性名称。
   */
  @Nonnull
  public static PropertyName getWrapperPropertyName(final ObjectMapper mapper,
      final Method method) {
    if (mapper instanceof XmlMapper) {
      return getXmlWrapperPropertyName(mapper.getPropertyNamingStrategy(), method);
    } else if (mapper instanceof JsonMapper) {
      return getJsonWrapperPropertyName(mapper.getPropertyNamingStrategy(), method);
    } else {
      return getJsonWrapperPropertyName(mapper.getPropertyNamingStrategy(), method);
    }
  }

  /**
   * 获取 XML 包装器属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param field
   *     指定的字段。
   * @return
   *     XML 包装器属性名称。
   */
  @Nonnull
  public static PropertyName getXmlWrapperPropertyName(final PropertyNamingStrategy namingStrategy,
      final Field field) {
    // note that we must check/get the annotation from the field as well as the
    // corresponding read method of the field
    return getXmlWrapperPropertyNameImpl(namingStrategy, field.getName(),
        (a) -> FieldUtils.isAnnotationPresent(field, a),
        (a) -> FieldUtils.getAnnotation(field, a));
  }

  /**
   * 获取 XML 包装器属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param method
   *     指定的方法。
   * @return
   *     XML 包装器属性名称，如果方法不是 setter 方法则返回 {@code null}。
   */
  @Nullable
  public static PropertyName getXmlWrapperPropertyName(final PropertyNamingStrategy namingStrategy,
      final Method method) {
    final String name = getPropertyNameFromSetter(method);
    if (name == null) {
      return null;
    } else {
      return getXmlWrapperPropertyNameImpl(namingStrategy, name,
          method::isAnnotationPresent, method::getAnnotation);
    }
  }

  @Nonnull
  private static PropertyName getXmlWrapperPropertyNameImpl(final PropertyNamingStrategy namingStrategy,
      final String name, final AnnotationTester tester, final AnnotationGetter getter) {
    if (tester.exist(XmlElementWrapper.class)) {
      final XmlElementWrapper a = (XmlElementWrapper) getter.get(XmlElementWrapper.class);
      assert a != null;
      final PropertyName pn = newJaxbXmlElementName(a.name(), a.namespace());
      if (pn != null) {
        return pn;
      }
    }
    if (tester.exist(XmlNoElementWrapper.class)) {
      return PropertyName.NO_NAME;
    }
    if (tester.exist(JacksonXmlElementWrapper.class)) {
      final JacksonXmlElementWrapper a = (JacksonXmlElementWrapper) getter.get(JacksonXmlElementWrapper.class);
      assert a != null;
      if (!a.useWrapping()) {
        return PropertyName.NO_NAME;
      }
      final PropertyName pn = newJacksonXmlElementName(a.localName(), a.namespace());
      if (pn != null) {
        return pn;
      }
    }
    return newXmlWrapperElementName(namingStrategy, name);
  }

  /**
   * 获取 JSON 包装器属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param field
   *     指定的字段。
   * @return
   *     JSON 包装器属性名称。
   */
  @Nonnull
  public static PropertyName getJsonWrapperPropertyName(final PropertyNamingStrategy namingStrategy,
      final Field field) {
    return getJsonWrapperPropertyNameImpl(namingStrategy, field.getName());
  }

  /**
   * 获取 JSON 包装器属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param method
   *     指定的方法。
   * @return
   *     JSON 包装器属性名称，如果方法不是 setter 方法则返回 {@code null}。
   */
  @Nullable
  public static PropertyName getJsonWrapperPropertyName(final PropertyNamingStrategy namingStrategy,
      final Method method) {
    final String name = getPropertyNameFromSetter(method);
    if (name == null) {
      return null;
    } else {
      return getJsonWrapperPropertyNameImpl(namingStrategy, name);
    }
  }

  @Nonnull
  private static PropertyName getJsonWrapperPropertyNameImpl(final PropertyNamingStrategy namingStrategy,
      final String name) {
    final String translateName = translateName(namingStrategy, name);
    return new PropertyName(translateName);
  }

  /**
   * 获取被包装的属性名称。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param field
   *     指定的字段。
   * @return
   *     被包装的属性名称。
   */
  @Nonnull
  public static PropertyName getWrappedPropertyName(final ObjectMapper mapper,
      final Field field) {
    if (mapper instanceof XmlMapper) {
      return getXmlWrappedPropertyName(mapper.getPropertyNamingStrategy(), field);
    } else if (mapper instanceof JsonMapper) {
      return getJsonWrappedPropertyName(mapper.getPropertyNamingStrategy(), field);
    } else {
      return getJsonWrappedPropertyName(mapper.getPropertyNamingStrategy(), field);
    }
  }

  /**
   * 获取被包装的属性名称。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param method
   *     指定的方法。
   * @return
   *     被包装的属性名称，如果方法不是 setter 方法则返回 {@code null}。
   */
  @Nullable
  public static PropertyName getWrappedPropertyName(final ObjectMapper mapper,
      final Method method) {
    if (mapper instanceof XmlMapper) {
      return getXmlWrappedPropertyName(mapper.getPropertyNamingStrategy(), method);
    } else if (mapper instanceof JsonMapper) {
      return getJsonWrappedPropertyName(mapper.getPropertyNamingStrategy(), method);
    } else {
      return getJsonWrappedPropertyName(mapper.getPropertyNamingStrategy(), method);
    }
  }

  /**
   * 获取 XML 被包装的属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param field
   *     指定的字段。
   * @return
   *     XML 被包装的属性名称。
   */
  @Nonnull
  public static PropertyName getXmlWrappedPropertyName(final PropertyNamingStrategy namingStrategy,
      final Field field) {
    // note that we must check/get the annotation from the field as well as the
    // corresponding read method of the field
    return getXmlWrappedPropertyNameImpl(namingStrategy, field.getName(),
        (a) -> FieldUtils.isAnnotationPresent(field, a),
        (a) -> FieldUtils.getAnnotation(field, a));
  }

  /**
   * 获取 XML 被包装的属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param method
   *     指定的方法。
   * @return
   *     XML 被包装的属性名称，如果方法不是 setter 方法则返回 {@code null}。
   */
  @Nullable
  public static PropertyName getXmlWrappedPropertyName(final PropertyNamingStrategy namingStrategy,
      final Method method) {
    final String name = getPropertyNameFromSetter(method);
    if (name == null) {
      return null;
    } else {
      return getXmlWrappedPropertyNameImpl(namingStrategy, name,
          method::isAnnotationPresent, method::getAnnotation);
    }
  }

  @Nonnull
  private static PropertyName getXmlWrappedPropertyNameImpl(final PropertyNamingStrategy namingStrategy,
      final String name, final AnnotationTester tester, final AnnotationGetter getter) {
    if (tester.exist(XmlElement.class)) {
      final XmlElement a = (XmlElement) getter.get(XmlElement.class);
      assert a != null;
      final PropertyName pn = newJaxbXmlElementName(a.name(), a.namespace());
      if (pn != null) {
        return pn;
      } else {
        return newXmlWrappedElementName(namingStrategy, name);
      }
    }
    if (tester.exist(XmlElements.class)) {
      // do not provide wrapped element name for @XmlElements annotated fields,
      // since it will use the customized name provided by the internal @XmlElement
      // annotation.
      // FIXME: There is a bug in JacksonXmlModule for serializing @XmlElements
      //  see https://github.com/FasterXML/jackson-dataformat-xml/issues/178
      //  and https://github.com/FasterXML/jackson-dataformat-xml/issues/230
      //  and https://github.com/FasterXML/jackson-module-jaxb-annotations/issues/51
      return newXmlWrappedElementName(namingStrategy, name);
    }
    if (tester.exist(JacksonXmlProperty.class)) {
      final JacksonXmlProperty a = (JacksonXmlProperty) getter.get(JacksonXmlProperty.class);
      assert a != null;
      final PropertyName pn = newJacksonXmlElementName(a.localName(), a.namespace());
      if (pn != null) {
        return pn;
      } else {
        return newXmlWrappedElementName(namingStrategy, name);
      }
    }
    return newXmlWrappedElementName(namingStrategy, name);
  }

  /**
   * 获取 JSON 被包装的属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param field
   *     指定的字段。
   * @return
   *     JSON 被包装的属性名称。
   */
  @Nonnull
  public static PropertyName getJsonWrappedPropertyName(final PropertyNamingStrategy namingStrategy,
      final Field field) {
    // note that we must check/get the annotation from the field as well as the
    // corresponding read method of the field
    return getJsonWrappedPropertyNameImpl(namingStrategy, field.getName(),
        (a) -> FieldUtils.isAnnotationPresent(field, a),
        (a) -> FieldUtils.getAnnotation(field, a));
  }

  /**
   * 获取 JSON 被包装的属性名称。
   *
   * @param namingStrategy
   *     指定的属性命名策略。
   * @param method
   *     指定的方法。
   * @return
   *     JSON 被包装的属性名称，如果方法不是 setter 方法则返回 {@code null}。
   */
  @Nullable
  public static PropertyName getJsonWrappedPropertyName(final PropertyNamingStrategy namingStrategy,
      final Method method) {
    final String name = getPropertyNameFromSetter(method);
    if (name == null) {
      return null;
    } else {
      return getJsonWrappedPropertyNameImpl(namingStrategy, name,
          method::isAnnotationPresent, method::getAnnotation);
    }
  }

  @Nonnull
  private static PropertyName getJsonWrappedPropertyNameImpl(final PropertyNamingStrategy namingStrategy,
      final String name, final AnnotationTester tester, final AnnotationGetter getter) {
    if (tester.exist(JsonProperty.class)) {
      final JsonProperty a = (JsonProperty) getter.get(JsonProperty.class);
      assert a != null;
      final PropertyName pn = newJacksonJsonPropertyName(a.value(), a.namespace());
      if (pn != null) {
        return pn;
      }
    }
    return newJsonWrappedPropertyName(namingStrategy, name);
  }

  /**
   * 使用 XML 适配器序列化字段值。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param annotation
   *     指定的 {@link XmlJavaTypeAdapter} 注解。
   * @param field
   *     指定的字段。
   * @param fieldValue
   *     指定的字段值。
   * @return
   *     序列化后的字符串。
   * @throws Exception
   *     如果序列化过程中发生错误。
   */
  @SuppressWarnings("rawtypes")
  public static String serializeWithAdapter(final ObjectMapper mapper,
      final XmlJavaTypeAdapter annotation, final Field field, final Object fieldValue)
      throws Exception {
    final Class<? extends XmlAdapter> adapterClass = annotation.value();
    final XmlAdapter adapter = ConstructorUtils.newInstance(adapterClass);
    return serializeWithAdapter(mapper, adapter, field, fieldValue);
  }

  /**
   * 使用 XML 适配器序列化字段值。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param adapter
   *     指定的 {@link XmlAdapter} 适配器。
   * @param field
   *     指定的字段，可以为 {@code null}。
   * @param fieldValue
   *     指定的字段值。
   * @return
   *     序列化后的字符串。
   * @throws Exception
   *     如果序列化过程中发生错误。
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static String serializeWithAdapter(final ObjectMapper mapper,
      final XmlAdapter adapter, @Nullable final Field field, final Object fieldValue)
      throws Exception {
    return (String) adapter.marshal(fieldValue);
  }

  /**
   * 使用 JSON 序列化器序列化字段值。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param annotation
   *     指定的 {@link JsonSerialize} 注解。
   * @param field
   *     指定的字段，可以为 {@code null}。
   * @param fieldValue
   *     指定的字段值。
   * @return
   *     序列化后的字符串。
   * @throws IOException
   *     如果序列化过程中发生 I/O 错误。
   */
  @SuppressWarnings("rawtypes")
  public static String serializeWithSerializer(final ObjectMapper mapper,
      final JsonSerialize annotation, @Nullable final Field field, final Object fieldValue)
      throws IOException {
    final Class<? extends JsonSerializer> cls = annotation.using();
    final JsonSerializer serializer = ConstructorUtils.newInstance(cls);
    if (mapper instanceof XmlMapper) {
      return serializeWithSerializer((XmlMapper) mapper, serializer, field, fieldValue);
    } else if (mapper instanceof JsonMapper) {
      return serializeWithSerializer((JsonMapper) mapper, serializer, field, fieldValue);
    } else {
      throw new IllegalArgumentException("Unsupported object mapper: " + mapper.getClass());
    }
  }

  private static final String TEMP_TAG_NAME = "value";
  private static final String TEMP_TAG_START = "<" + TEMP_TAG_NAME + ">";
  private static final String TEMP_TAG_END = "</" + TEMP_TAG_NAME + ">";

  /**
   * 使用 JSON 序列化器序列化字段值（用于 XML 映射器）。
   *
   * @param mapper
   *     指定的 XML 映射器。
   * @param serializer
   *     指定的 JSON 序列化器。
   * @param field
   *     指定的字段，可以为 {@code null}。
   * @param fieldValue
   *     指定的字段值。
   * @return
   *     序列化后的字符串。
   * @throws IOException
   *     如果序列化过程中发生 I/O 错误。
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static String serializeWithSerializer(final XmlMapper mapper,
      final JsonSerializer serializer, @Nullable final Field field,
      final Object fieldValue) throws IOException {
    final SerializerProvider provider = mapper.getSerializerProvider();
    final XmlFactory factory = mapper.getFactory();
    final StringWriter writer = new StringWriter();
    final ToXmlGenerator generator = factory.createGenerator(writer);
    // ToXmlGenerator的writeString()需要提供一个QName，然后会生成tag包含的字符串值
    // 因此我们需要提供一个临时的QName并从生成的字符串前后删除tag标签，得到序列化器序列化
    // 后的实际值。
    generator.setNextName(new QName(TEMP_TAG_NAME));
    JsonSerializer ser = serializer;
    // 对于 ContextualSerializer 需要调用 serializer.createContextual()
    if ((field != null) && (ser instanceof ContextualSerializer)) {
      final SerializationConfig config = mapper.getSerializationConfig();
      final JavaType type = config.constructType(field.getType());
      final BasicBeanProperty property = new BasicBeanProperty(mapper, SERIALIZE, field);
      ser = ((ContextualSerializer) ser).createContextual(provider, property);
    }
    ser.serialize(fieldValue, generator, provider);
    generator.flush();
    final String str = writer.toString();
    return new Remover()
        .forPrefix(TEMP_TAG_START)
        .forSuffix(TEMP_TAG_END)
        .removeFrom(str);
  }

  /**
   * 使用 JSON 序列化器序列化字段值（用于 JSON 映射器）。
   *
   * @param mapper
   *     指定的 JSON 映射器。
   * @param serializer
   *     指定的 JSON 序列化器。
   * @param field
   *     指定的字段，可以为 {@code null}。
   * @param fieldValue
   *     指定的字段值。
   * @return
   *     序列化后的字符串。
   * @throws IOException
   *     如果序列化过程中发生 I/O 错误。
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static String serializeWithSerializer(final JsonMapper mapper,
      final JsonSerializer serializer, @Nullable final Field field,
      final Object fieldValue) throws IOException {
    final SerializerProvider provider = mapper.getSerializerProvider();
    final JsonFactory factory = mapper.getFactory();
    final StringWriter writer = new StringWriter();
    final JsonGenerator generator = factory.createGenerator(writer);
    JsonSerializer ser = serializer;
    // 对于 ContextualSerializer 需要调用 serializer.createContextual()
    if ((field != null) && (ser instanceof ContextualSerializer)) {
      final SerializationConfig config = mapper.getSerializationConfig();
      final JavaType type = config.constructType(field.getType());
      final BasicBeanProperty property = new BasicBeanProperty(mapper, SERIALIZE,field);
      ser = ((ContextualSerializer) ser).createContextual(provider, property);
    }
    ser.serialize(fieldValue, generator, provider);
    generator.flush();
    return writer.toString();
  }

  /**
   * 获取注解字段。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param operation
   *     指定的操作类型。
   * @param field
   *     指定的字段。
   * @return
   *     注解字段对象。
   */
  public static AnnotatedField getAnnotatedField(final ObjectMapper mapper,
      final Operation operation, final Field field) {
    // algorithm comes from
    // com.fasterxml.jackson.databind.introspect.AnnotatedFieldCollector._findFields()
    final MapperConfig<?> config = operation.getConfig(mapper);
    final Class<?> beanClass = field.getDeclaringClass();
    final JavaType beanType = config.constructType(beanClass);
    final AnnotatedClass annotatedClass = AnnotatedClassResolver.resolve(config, beanType, config);
    final AnnotationIntrospector ai = config.getAnnotationIntrospector();
    final AnnotationMap annotations = new AnnotationMap();
    collectAnnotations(field.getAnnotations(), ai, annotations);
    // TODO: deal with mixins
    return new AnnotatedField(annotatedClass, field, annotations);
  }

  /**
   * 获取注解方法。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param operation
   *     指定的操作类型。
   * @param method
   *     指定的方法。
   * @return
   *     注解方法对象。
   */
  public static AnnotatedMethod getAnnotatedMethod(final ObjectMapper mapper,
      final Operation operation, final Method method) {
    final MapperConfig<?> config = operation.getConfig(mapper);
    final Class<?> beanClass = method.getDeclaringClass();
    final JavaType beanType = config.constructType(beanClass);
    final AnnotatedClass annotatedClass = AnnotatedClassResolver.resolve(config, beanType, config);
    final AnnotationIntrospector ai = config.getAnnotationIntrospector();
    final AnnotationMap annotations = new AnnotationMap();
    collectAnnotations(method.getAnnotations(), ai, annotations);
    // TODO: deal with mixins
    final AnnotationMap[] paramAnnotations =
        collectAnnotations(method.getParameterAnnotations(), ai);
    // TODO: deal with mixins
    return new AnnotatedMethod(annotatedClass, method, annotations, paramAnnotations);
  }

  private static void collectAnnotations(final Annotation[] annotations,
      final AnnotationIntrospector ai, final AnnotationMap result) {
    // algorithm comes from
    // com.fasterxml.jackson.databind.introspect.AnnotatedFieldCollector.collectAnnotations()
    for (final Annotation annotation : annotations) {
      result.add(annotation);
      if (ai.isAnnotationBundle(annotation)) {
        collectAnnotationsFromBundle(annotation, ai, result);
      }
    }
  }

  private static AnnotationMap[] collectAnnotations(final Annotation[][] annotationsList,
      final AnnotationIntrospector ai) {
    final int n = annotationsList.length;
    final AnnotationMap[] result = new AnnotationMap[n];
    for (int i = 0; i < n; ++i) {
      result[i] = new AnnotationMap();
      collectAnnotations(annotationsList[i], ai, result[i]);
    }
    return result;
  }

  private static void collectAnnotationsFromBundle(final Annotation bundle,
      final AnnotationIntrospector ai, final AnnotationMap result) {
    // algorithm comes from
    // com.fasterxml.jackson.databind.introspect.AnnotatedFieldCollector.collectFromBundle()
    final Annotation[] anns = ClassUtil.findClassAnnotations(bundle.annotationType());
    for (final Annotation a : anns) {
      if (isIgnorableAnnotation(a)) {
        continue;
      }
      if (result.has(a.annotationType())) {
        continue;
      }
      result.add(a);
      if (ai.isAnnotationBundle(a)) {
        collectAnnotationsFromBundle(a, ai, result);
      }
    }
  }

  private static boolean isIgnorableAnnotation(final Annotation a) {
    return (a instanceof Target) || (a instanceof Retention);
  }

  private static void addFiledMixIns(final Class<?> mixInClass,
      final Class<?> targetClass) {
    // algorithm comes from
    // com.fasterxml.jackson.databind.introspect.AnnotatedFieldCollector._addFieldMixIns()
    //  TODO
  }

  /**
   * 获取属性元数据。
   *
   * @param mapper
   *     指定的对象映射器。
   * @param operation
   *     指定的操作类型。
   * @param field
   *     指定的注解字段。
   * @return
   *     属性元数据。
   */
  public static PropertyMetadata getMetadata(final ObjectMapper mapper,
      final Operation operation, final AnnotatedField field) {
    // the algorithm comes from
    // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.getMetadata()
    final MapperConfig<?> config = operation.getConfig(mapper);
    final AnnotationIntrospector introspector = config.getAnnotationIntrospector();
    final Boolean required = introspector.hasRequiredMarker(field);
    final String description = introspector.findPropertyDescription(field);
    final Integer index = introspector.findPropertyIndex(field);
    final String defaultValue = introspector.findPropertyDefaultValue(field);
    final PropertyMetadata metadata;
    if (required == null && index == null && defaultValue == null) {
      if (description == null) {
        metadata = PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
      } else {
        metadata = PropertyMetadata.STD_REQUIRED_OR_OPTIONAL.withDescription(description);
      }
    } else {
      metadata = PropertyMetadata.construct(required, description, index, defaultValue);
    }
    // TODO:
    // if (operation == Operation.DESERIALIZE) {
    //   metadata = getSetterInfo(field, metadata, config);
    // }
    return metadata;
  }

  // /**
  //  * Helper method that contains logic for accessing and merging all setter
  //  * information that we needed, regarding things like possible merging of
  //  * property value, and handling of incoming nulls.
  //  *
  //  * <p>Only called for deserialization purposes.</p>
  //  *
  //  * @param field
  //  *     the primary annotated member of the property.
  //  * @param metadata
  //  *     the original metadata of the property.
  //  * @return
  //  *     the metadata of the property with setter information.
  //  */
  // private static PropertyMetadata getSetterInfo(final AnnotatedField field,
  //     final PropertyMetadata metadata, final MapperConfig<?> config) {
  //   // the code comes from
  //   // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder._getSetterInfo()
  //   boolean needMerge = true;
  //   Nulls valueNulls = null;
  //   Nulls contentNulls = null;
  //   PropertyMetadata result = metadata;
  //   // Slightly confusing: first, annotations should be accessed via primary member
  //   // (mutator); but accessor is needed for actual merge operation. So
  //   final AnnotatedMember acc = getAccessor();
  //   if (field != null) {
  //     // Ok, first: does property itself have something to say?
  //     final AnnotationIntrospector introspector = config.getAnnotationIntrospector();
  //     if (introspector != null) {
  //       if (acc != null) {
  //         final Boolean b = introspector.findMergeInfo(field);
  //         if (b != null) {
  //           needMerge = false;
  //           if (b) {
  //             result = result.withMergeInfo(MergeInfo.createForPropertyOverride(acc));
  //           }
  //         }
  //       }
  //       final JsonSetter.Value setterInfo = introspector.findSetterInfo(field);
  //       if (setterInfo != null) {
  //         valueNulls = setterInfo.nonDefaultValueNulls();
  //         contentNulls = setterInfo.nonDefaultContentNulls();
  //       }
  //     }
  //     // If not, config override?
  //     // 25-Oct-2016, tatu: Either this, or type of accessor...
  //     if (needMerge || (valueNulls == null) || (contentNulls == null)) {
  //       // 20-Jun-2020, tatu: Related to [databind#2757], need to find type
  //       //   but keeping mind that type for setters is trickier; and that
  //       //   generic typing gets tricky as well.
  //       final Class<?> rawType = rawTypeOf(field);
  //       final ConfigOverride co = config.getConfigOverride(rawType);
  //       final JsonSetter.Value setterInfo = co.getSetterInfo();
  //       if (setterInfo != null) {
  //         if (valueNulls == null) {
  //           valueNulls = setterInfo.nonDefaultValueNulls();
  //         }
  //         if (contentNulls == null) {
  //           contentNulls = setterInfo.nonDefaultContentNulls();
  //         }
  //       }
  //       if (needMerge && (acc != null)) {
  //         final Boolean b = co.getMergeable();
  //         if (b != null) {
  //           needMerge = false;
  //           if (b) {
  //             result = result.withMergeInfo(MergeInfo.createForTypeOverride(acc));
  //           }
  //         }
  //       }
  //     }
  //   }
  //   if (needMerge || (valueNulls == null) || (contentNulls == null)) {
  //     final JsonSetter.Value setterInfo = config.getDefaultSetterInfo();
  //     if (valueNulls == null) {
  //       valueNulls = setterInfo.nonDefaultValueNulls();
  //     }
  //     if (contentNulls == null) {
  //       contentNulls = setterInfo.nonDefaultContentNulls();
  //     }
  //     if (needMerge) {
  //       final Boolean b = config.getDefaultMergeable();
  //       if (isTrue(b) && (acc != null)) {
  //         result = result.withMergeInfo(MergeInfo.createForDefaults(acc));
  //       }
  //     }
  //   }
  //   if ((valueNulls != null) || (contentNulls != null)) {
  //     result = result.withNulls(valueNulls, contentNulls);
  //   }
  //   return result;
  // }

  /**
   * Helper method needed to work around oddity in type access for
   * {@code AnnotatedMethod}.
   *
   * @param member
   *     the annotated member.
   * @return
   *     the raw type of the annotated member.
   */
  private static Class<?> rawTypeOf(final AnnotatedMember member) {
    // The code comes from
    // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder._rawTypeOf()
    // AnnotatedMethod always returns type, but for setters we
    // actually need argument type
    if (member instanceof final AnnotatedMethod method) {
      if (method.getParameterCount() > 0) {
        // note: get raw type FROM full type since only that resolves
        // generic types
        return method.getParameterType(0).getRawClass();
      }
    }
    // same as above, must get fully resolved type to handled generic typing
    // of fields etc.
    return member.getType().getRawClass();
  }

  /**
   * 确保当前 token 为指定的 token。
   *
   * <p>此方法检查 JSON 解析器的当前 token 是否与预期的 token 相匹配。
   * 如果不匹配，则抛出 {@link IllegalStateException} 异常。</p>
   *
   * @param jp
   *     指定的 JSON 解析器。
   * @param expectedToken
   *     期望的 JSON token。
   * @throws IOException
   *     如果解析过程中发生 I/O 错误。
   * @throws IllegalStateException
   *     如果当前 token 与期望的 token 不匹配。
   */
  public static void ensureCurrentToken(final JsonParser jp, final JsonToken expectedToken)
      throws IOException {
    final JsonToken token = jp.currentToken();
    if (token != expectedToken) {
      throw new IllegalStateException("Expected " + expectedToken + " token, "
          + "but got " + token + " token");
    }
  }

  /**
   * 确保下一个 token 为指定的 token。
   *
   * <p>此方法移动到 JSON 解析器的下一个 token 并检查该 token 是否与预期的 token 相匹配。
   * 如果不匹配，则抛出 {@link IllegalStateException} 异常。</p>
   *
   * @param jp
   *     指定的 JSON 解析器。
   * @param expectedToken
   *     期望的 JSON token。
   * @throws IOException
   *     如果解析过程中发生 I/O 错误。
   * @throws IllegalStateException
   *     如果下一个 token 与期望的 token 不匹配。
   */
  public static void ensureNextToken(final JsonParser jp, final JsonToken expectedToken)
      throws IOException {
    final JsonToken token = jp.nextToken();
    if (token != expectedToken) {
      throw new IllegalStateException("Expected " + expectedToken + " token, "
          + "but got " + token + " token");
    }
  }

  /**
   * 确保当前字段名为指定的名称。
   *
   * <p>此方法检查 JSON 解析器的当前字段名是否与预期的字段名相匹配。
   * 如果不匹配，则抛出 {@link IllegalStateException} 异常。</p>
   *
   * @param jp
   *     指定的 JSON 解析器。
   * @param expectedName
   *     期望的字段名。
   * @throws IOException
   *     如果解析过程中发生 I/O 错误。
   * @throws IllegalStateException
   *     如果当前字段名与期望的字段名不匹配。
   */
  public static void ensureCurrentName(final JsonParser jp, final String expectedName)
      throws IOException {
    final String fieldName = jp.currentName();
    if (!fieldName.equals(expectedName)) {
      throw new IllegalStateException("Expected field name '" + expectedName
          + "', got: " + fieldName);
    }
  }
}