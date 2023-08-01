////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import ltd.qubit.commons.util.transformer.string.PluralToSingularTransformer;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;

import static ltd.qubit.commons.text.jackson.JacksonUtils.JAXB_DEFAULT_VALUE;

/**
 * 此 {@link AnnotationIntrospector} 用于根据指定的命名策略转换根元素名称，并且自动处理 集合类属性内部元素名称。
 *
 * @author 胡海星
 */
public class XmlNameConversionIntrospector extends AnnotationIntrospector {

  private static final long serialVersionUID = 6574159265535613840L;

  private NamingBase namingStrategy;

  private final PluralToSingularTransformer transformer = PluralToSingularTransformer.INSTANCE;

  public XmlNameConversionIntrospector(final NamingBase namingStrategy) {
    this.namingStrategy = namingStrategy;
  }

  public final NamingBase getNamingStrategy() {
    return namingStrategy;
  }

  public void setNamingStrategy(final NamingBase namingStrategy) {
    this.namingStrategy = namingStrategy;
  }

  @Override
  public Version version() {
    return Version.unknownVersion();
  }

  public PropertyName findRootName(final AnnotatedClass annotatedClass) {
    // 根据类名和指定的命名策略生成根元素名称
    final Class<?> type = annotatedClass.getRawType();
    final String name = type.getSimpleName();
    final String convertedName = namingStrategy.translate(name);
    return PropertyName.construct(convertedName);
  }

  public PropertyName findWrapperName(final Annotated annotated) {
    if ((annotated instanceof AnnotatedField) && (annotated.getType().isContainerType())) {
      // 容器字段的 Wrapper name 为该字段名称根据指定的命名策略转换的结果。
      final String name = annotated.getName();
      final String convertedName = namingStrategy.translate(name);
      return PropertyName.construct(convertedName);
    }
    return null;
  }

  @Override
  public PropertyName findNameForDeserialization(final Annotated annotated) {
    if ((annotated instanceof AnnotatedField)
        && (annotated.getType().isContainerType())) {
      return getWrappedElementName(annotated);
    }
    return null;
  }

  @Override
  public PropertyName findNameForSerialization(final Annotated annotated) {
    if ((annotated instanceof AnnotatedField)
        && (annotated.getType().isContainerType())) {
      return getWrappedElementName(annotated);
    }
    return null;
  }

  private PropertyName getWrappedElementName(final Annotated annotated) {
    if (annotated.hasAnnotation(XmlElements.class)) {
      // do not provide wrapped element name for @XmlElements annotated fields,
      // since it will use the customized name provided by the internal @XmlElement
      // annotation.
      return getWrappedElementNameFromXmlElements(annotated);
    } else {
      return getWrappedElementNameFromFieldName(annotated);
    }
  }

  private PropertyName getWrappedElementNameFromFieldName(final Annotated annotated) {
    // 容器字段的 name 为该字段名称的“单数形式”根据指定的命名策略转换的结果。
    final String name = annotated.getName();
    final String singularName = transformer.transform(name);
    final String convertedName = namingStrategy.translate(singularName);
    return PropertyName.construct(convertedName);
  }

  private PropertyName getWrappedElementNameFromXmlElements(final Annotated annotated) {
    final XmlElements annotation = annotated.getAnnotation(XmlElements.class);
    final XmlElement[] elements = annotation.value();
    final Class<?> annotatedType = annotated.getRawType();
    for (final XmlElement element : elements) {
      if (annotatedType.equals(element.type())) {
        final String name = element.name();
        if (JAXB_DEFAULT_VALUE.equals(name)) {
          return getWrappedElementNameFromFieldName(annotated);
        } else {
          return new PropertyName(name);
        }
      }
    }
    return getWrappedElementNameFromFieldName(annotated);
  }
}
