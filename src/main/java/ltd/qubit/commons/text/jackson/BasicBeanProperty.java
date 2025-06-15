////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

/**
 * Jackson的{@link BeanProperty}的简单实现。
 *
 * @author 胡海星
 */
public class BasicBeanProperty implements BeanProperty {
  /**
   * 对应的字段。
   */
  private final Field field;

  /**
   * 字段的Java类型。
   */
  private final JavaType type;

  /**
   * 属性名称。
   */
  private final PropertyName name;

  /**
   * 包装器属性名称。
   */
  private final PropertyName wrapperName;

  /**
   * 注解字段。
   */
  private final AnnotatedField member;

  /**
   * 属性元数据。
   */
  private final PropertyMetadata metadata;

  /**
   * 构造一个基本的Bean属性。
   *
   * @param mapper 对象映射器
   * @param operation 操作类型
   * @param field 字段对象
   */
  public BasicBeanProperty(final ObjectMapper mapper, final Operation operation,
      final Field field) {
    final MapperConfig<?> config = operation.getConfig(mapper);
    this.field = field;
    this.type = config.constructType(field.getType());
    if (this.type.isContainerType()) {
      this.wrapperName = JacksonUtils.getWrapperPropertyName(mapper, field);
      this.name = JacksonUtils.getWrappedPropertyName(mapper, field);
    } else {
      this.name = JacksonUtils.getPropertyName(mapper, field);
      this.wrapperName = null;
    }
    member = JacksonUtils.getAnnotatedField(mapper, operation, field);
    metadata = JacksonUtils.getMetadata(mapper, operation, member);
  }

  /**
   * 获取对应的字段。
   *
   * @return 字段对象
   */
  public Field getField() {
    return field;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name.getSimpleName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PropertyName getFullName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JavaType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PropertyName getWrapperName() {
    return wrapperName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PropertyMetadata getMetadata() {
    return metadata;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRequired() {
    return metadata.isRequired();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVirtual() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AnnotatedField getMember() {
    return member;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <A extends Annotation> A getAnnotation(final Class<A> acls) {
    return (member == null ? null : member.getAnnotation(acls));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <A extends Annotation> A getContextAnnotation(final Class<A> acls) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public Value findFormatOverrides(final AnnotationIntrospector introspector) {
    if ((member != null) && (introspector != null)) {
      final JsonFormat.Value value = introspector.findFormat(member);
      if (value != null) {
        return value;
      }
    }
    return EMPTY_FORMAT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Value findPropertyFormat(final MapperConfig<?> config,
      final Class<?> baseType) {
    final JsonFormat.Value value = config.getDefaultPropertyFormat(baseType);
    final AnnotationIntrospector introspector = config.getAnnotationIntrospector();
    if ((introspector == null) || (member == null)) {
      return value;
    }
    final JsonFormat.Value v = introspector.findFormat(member);
    if (v == null) {
      return value;
    }
    return value.withOverrides(v);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonInclude.Value findPropertyInclusion(final MapperConfig<?> config,
      final Class<?> baseType) {
    final JsonInclude.Value value = config.getDefaultInclusion(baseType, type.getRawClass());
    final AnnotationIntrospector introspector = config.getAnnotationIntrospector();
    if ((introspector == null) || (member == null)) {
      return value;
    }
    final JsonInclude.Value v = introspector.findPropertyInclusion(member);
    if (v == null) {
      return value;
    }
    return value.withOverrides(v);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PropertyName> findAliases(final MapperConfig<?> config) {
    return Collections.emptyList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void depositSchemaProperty(final JsonObjectFormatVisitor objectVisitor,
      final SerializerProvider provider) throws JsonMappingException {
    throw new UnsupportedOperationException("Instances of "
        + getClass().getName() + " should not get visited");
  }
}