////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;
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

import ltd.qubit.commons.reflect.ConstructorUtils;
import ltd.qubit.commons.text.Remover;
import ltd.qubit.commons.util.transformer.string.PluralToSingularTransformer;
import ltd.qubit.commons.util.transformer.string.SingularToPluralTransformer;

import static ltd.qubit.commons.lang.StringUtils.defaultToNull;
import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;
import static ltd.qubit.commons.text.jackson.Operation.SERIALIZE;

/**
 * Provide Jackson related utilities.
 *
 * @author Haixing Hu
 */
public class JacksonUtils {

  private JacksonUtils() {}

  public static final String JAXB_DEFAULT_VALUE = "##default";

  public static final String JACKSON_DEFAULT_VALUE = "";

  @SuppressWarnings("deprecation")
  public static String translateName(final ObjectMapper mapper, final String name) {
    final PropertyNamingStrategy namingStrategy = mapper.getPropertyNamingStrategy();
    if (namingStrategy instanceof PropertyNamingStrategyBase) {
      return ((PropertyNamingStrategyBase) namingStrategy).translate(name);
    } else if (namingStrategy instanceof NamingBase) {
      return ((NamingBase) namingStrategy).translate(name);
    } else {
      return name;
    }
  }

  public static PropertyName getRootWrapperName(final ObjectMapper mapper,
      final Class<?> type) {
    final PropertyName rootName = getRootName(mapper,type);
    final SingularToPluralTransformer transformer = SingularToPluralTransformer.INSTANCE;
    final String name = transformer.transform(rootName.getSimpleName());
    return new PropertyName(name);
  }

  public static PropertyName getRootName(final ObjectMapper mapper, final Class<?> type) {
    if (mapper instanceof XmlMapper) {
      if (type.isAnnotationPresent(XmlRootElement.class)) {
        final XmlRootElement rootElement = type.getAnnotation(XmlRootElement.class);
        final String name = defaultToNull(JAXB_DEFAULT_VALUE, rootElement.name());
        final String namespace = defaultToNull(JAXB_DEFAULT_VALUE, rootElement.namespace());
        if (name != null) {
          return new PropertyName(name, namespace);
        }
      } else if (type.isAnnotationPresent(JacksonXmlRootElement.class)) {
        final JacksonXmlRootElement rootElement = type.getAnnotation(
            JacksonXmlRootElement.class);
        final String name = defaultToNull(JACKSON_DEFAULT_VALUE, rootElement.localName());
        final String namespace = defaultToNull(JACKSON_DEFAULT_VALUE, rootElement.namespace());
        if (name != null) {
          return new PropertyName(name, namespace);
        }
      }
    } else if (mapper instanceof JsonMapper) {
      if (type.isAnnotationPresent(JsonRootName.class)) {
        final JsonRootName rootName = type.getAnnotation(JsonRootName.class);
        final String name = defaultToNull(JACKSON_DEFAULT_VALUE, rootName.value());
        final String namespace = defaultToNull(JACKSON_DEFAULT_VALUE, rootName.namespace());
        if (name != null) {
          return new PropertyName(name, namespace);
        }
      }
    }
    final String name = translateName(mapper, type.getSimpleName());
    return new PropertyName(name);
  }

  public static PropertyName getPropertyName(final ObjectMapper mapper,
      final Field field) {
    if (mapper instanceof JsonMapper) {
        if (isAnnotationPresent(field, JsonProperty.class)) {
          final JsonProperty annotation = getAnnotation(field, JsonProperty.class);
          final String name = defaultToNull(JACKSON_DEFAULT_VALUE, annotation.value());
          final String namespace = defaultToNull(JACKSON_DEFAULT_VALUE, annotation.namespace());
          if (name != null) {
            return new PropertyName(name, namespace);
          }
        }
    } else if (mapper instanceof XmlMapper) {
      if (isAnnotationPresent(field, XmlElement.class)) {
        final XmlElement annotation = getAnnotation(field, XmlElement.class);
        final String name = defaultToNull(JAXB_DEFAULT_VALUE, annotation.name());
        final String namespace = defaultToNull(JAXB_DEFAULT_VALUE, annotation.namespace());
        if (name != null) {
          return new PropertyName(name, namespace);
        }
      }
      if (isAnnotationPresent(field, XmlAttribute.class)) {
        final XmlAttribute annotation = getAnnotation(field, XmlAttribute.class);
        final String name = defaultToNull(JAXB_DEFAULT_VALUE, annotation.name());
        final String namespace = defaultToNull(JAXB_DEFAULT_VALUE, annotation.namespace());
        if (name != null) {
          return new PropertyName("@" + name, namespace);
        } else {
          return new PropertyName("@" + translateName(mapper, field.getName()));
        }
      }
    }
    final String name = translateName(mapper, field.getName());
    return new PropertyName(name);
  }

  @NotNull
  public static PropertyName getPropertyWrapperName(final ObjectMapper mapper,
      final Field field) {
    if (mapper instanceof XmlMapper) {
      if (isAnnotationPresent(field, XmlElementWrapper.class)) {
        final XmlElementWrapper annotation = getAnnotation(field,
            XmlElementWrapper.class);
        final String name = defaultToNull(JAXB_DEFAULT_VALUE, annotation.name());
        final String namespace = defaultToNull(JAXB_DEFAULT_VALUE, annotation.namespace());
        if (name != null) {
          return new PropertyName(name, namespace);
        }
      } else if (isAnnotationPresent(field, JacksonXmlElementWrapper.class)) {
        final JacksonXmlElementWrapper annotation = getAnnotation(field,
            JacksonXmlElementWrapper.class);
        if (!annotation.useWrapping()) {
          return null;
        }
        final String name = defaultToNull(JACKSON_DEFAULT_VALUE, annotation.localName());
        final String namespace = defaultToNull(JACKSON_DEFAULT_VALUE, annotation.namespace());
        if (name != null) {
          return new PropertyName(name, namespace);
        }
      }
    }
    final String name = translateName(mapper, field.getName());
    return new PropertyName(name);
  }

  public static PropertyName getPropertyWrappedName(final ObjectMapper mapper,
      final Field field) {
    if (mapper instanceof JsonMapper) {
        if (isAnnotationPresent(field, JsonProperty.class)) {
          final JsonProperty annotation = getAnnotation(field, JsonProperty.class);
          final String name = defaultToNull(JACKSON_DEFAULT_VALUE, annotation.value());
          final String namespace = defaultToNull(JACKSON_DEFAULT_VALUE, annotation.namespace());
          if (name != null) {
            return new PropertyName(name, namespace);
          }
        }
    } else if (mapper instanceof XmlMapper) {
      if (isAnnotationPresent(field, XmlElement.class)) {
        final XmlElement annotation = getAnnotation(field, XmlElement.class);
        final String name = defaultToNull(JAXB_DEFAULT_VALUE, annotation.name());
        final String namespace = defaultToNull(JAXB_DEFAULT_VALUE, annotation.namespace());
        if (name != null) {
          return new PropertyName(name, namespace);
        }
      } else if (isAnnotationPresent(field, JacksonXmlProperty.class)) {
        final JacksonXmlProperty annotation = getAnnotation(field, JacksonXmlProperty.class);
        final String name = defaultToNull(JACKSON_DEFAULT_VALUE, annotation.localName());
        final String namespace = defaultToNull(JACKSON_DEFAULT_VALUE, annotation.namespace());
        if (name != null) {
          return new PropertyName(name, namespace);
        }
      }
    }
    final String name = translateName(mapper, field.getName());
    // translate the plural form of the field name to its singular form
    final PluralToSingularTransformer transformer = PluralToSingularTransformer.INSTANCE;
    return new PropertyName(transformer.transform(name));
  }

  @SuppressWarnings("rawtypes")
  public static String serializeWithAdapter(final ObjectMapper mapper,
      final XmlJavaTypeAdapter annotation, final Field field, final Object fieldValue)
      throws Exception {
    final Class<? extends XmlAdapter> adapterClass = annotation.value();
    final XmlAdapter adapter = ConstructorUtils.newInstance(adapterClass);
    return serializeWithAdapter(mapper, adapter, field, fieldValue);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static String serializeWithAdapter(final ObjectMapper mapper,
      final XmlAdapter adapter, @Nullable final Field field, final Object fieldValue)
      throws Exception {
    return (String) adapter.marshal(fieldValue);
  }

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

  public static AnnotatedField getAnnotatedField(final ObjectMapper mapper,
      final Operation operation, final Field field) {
    // algorithm comes from
    // com.fasterxml.jackson.databind.introspect.AnnotatedFieldCollector._findFields()
    final MapperConfig<?> config = operation.getConfig(mapper);
    final Class<?> beanClass = field.getDeclaringClass();
    final JavaType beanType = config.constructType(beanClass);
    final AnnotatedClass annotatedClass = AnnotatedClassResolver.resolve(config, beanType, config);
    final AnnotationIntrospector introspector = config.getAnnotationIntrospector();
    final AnnotationMap annotations = new AnnotationMap();
    collectAnnotations(field.getAnnotations(), introspector, annotations);
    // TODO: deal with mixins
    return new AnnotatedField(annotatedClass, field, annotations);
  }

  public static AnnotatedMethod getAnnotatedMethod(final ObjectMapper mapper,
      final Operation operation, final Method method) {
    final MapperConfig<?> config = operation.getConfig(mapper);
    final Class<?> beanClass = method.getDeclaringClass();
    final JavaType beanType = config.constructType(beanClass);
    final AnnotatedClass annotatedClass = AnnotatedClassResolver.resolve(config, beanType, config);
    final AnnotationIntrospector introspector = config.getAnnotationIntrospector();
    final AnnotationMap annotations = new AnnotationMap();
    collectAnnotations(method.getAnnotations(), introspector, annotations);
    // TODO: deal with mixins
    final AnnotationMap[] paramAnnotations =
        collectAnnotations(method.getParameterAnnotations(), introspector);
    // TODO: deal with mixins
    return new AnnotatedMethod(annotatedClass, method, annotations, paramAnnotations);
  }

  private static void collectAnnotations(final Annotation[] annotations,
      final AnnotationIntrospector introspector, final AnnotationMap result) {
    // algorithm comes from
    // com.fasterxml.jackson.databind.introspect.AnnotatedFieldCollector.collectAnnotations()
    for (final Annotation annotation : annotations) {
      result.add(annotation);
      if (introspector.isAnnotationBundle(annotation)) {
        collectAnnotationsFromBundle(annotation, introspector, result);
      }
    }
  }

  private static AnnotationMap[] collectAnnotations(final Annotation[][] annotationsList,
      final AnnotationIntrospector introspector) {
    final int n = annotationsList.length;
    final AnnotationMap[] result = new AnnotationMap[n];
    for (int i = 0; i < n; ++i) {
      result[i] = new AnnotationMap();
      collectAnnotations(annotationsList[i], introspector, result[i]);
    }
    return result;
  }

  private static void collectAnnotationsFromBundle(final Annotation bundle,
      final AnnotationIntrospector introspector, final AnnotationMap result) {
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
      if (introspector.isAnnotationBundle(a)) {
        collectAnnotationsFromBundle(a, introspector, result);
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
    if (member instanceof AnnotatedMethod) {
      final AnnotatedMethod method = (AnnotatedMethod) member;
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
}
