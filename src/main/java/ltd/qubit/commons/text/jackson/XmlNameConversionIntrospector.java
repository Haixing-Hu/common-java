////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;

import static ltd.qubit.commons.text.jackson.JacksonUtils.getXmlRootName;
import static ltd.qubit.commons.text.jackson.JacksonUtils.getXmlWrappedPropertyName;
import static ltd.qubit.commons.text.jackson.JacksonUtils.getXmlWrapperPropertyName;

/**
 * 此 {@link AnnotationIntrospector} 用于根据指定的命名策略转换根元素名称，并且自动处理
 * 集合类属性内部元素名称。
 *
 * @author 胡海星
 */
public class XmlNameConversionIntrospector extends AnnotationIntrospector
    implements AnnotationIntrospector.XmlExtensions {

  private static final long serialVersionUID = 6574159265535613840L;

  private final static String MARKER_FOR_DEFAULT = "##default";

  private final NamingBase namingStrategy;
  // private final PluralToSingularTransformer transformer = PluralToSingularTransformer.INSTANCE;
  private final JakartaXmlBindAnnotationIntrospector xmlBindAi;

  public XmlNameConversionIntrospector(final NamingBase namingStrategy, final TypeFactory typeFactory) {
    this.namingStrategy = namingStrategy;
    // 使用一个内置的 JakartaXmlBindAnnotationIntrospector
    this.xmlBindAi = new JakartaXmlBindAnnotationIntrospector(typeFactory);
  }

  public NamingBase getNamingStrategy() {
    return namingStrategy;
  }

  @Override
  public Version version() {
    return Version.unknownVersion();
  }

  @Override
  public PropertyName findRootName(final AnnotatedClass annotatedClass) {
    return getXmlRootName(namingStrategy, annotatedClass.getAnnotated());
  }

  @Override
  public PropertyName findWrapperName(final Annotated annotated) {
    if (annotated instanceof final AnnotatedField af) {
      return getXmlWrapperPropertyName(namingStrategy, af.getAnnotated());
    } else if (annotated instanceof final AnnotatedMethod am) {
      return getXmlWrapperPropertyName(namingStrategy, am.getAnnotated());
    } else {
      throw new IllegalArgumentException("Unsupported annotated: " + annotated);
    }
  }

  @Override
  public PropertyName findNameForDeserialization(final Annotated annotated) {
    if ((annotated instanceof AnnotatedField) && annotated.getType().isContainerType()) {
      return getWrappedElementName(annotated);
    }
    // 先通过 JakartaXmlBindAnnotationIntrospector 获取此属性的名称，然后根据命名策略转换
    final PropertyName pn = xmlBindAi.findNameForDeserialization(annotated);
    if (pn != null && (!pn.equals(PropertyName.NO_NAME))) {
      return translatePropertyName(pn);
    }
    return null;
  }

  @Override
  public PropertyName findNameForSerialization(final Annotated annotated) {
    if ((annotated instanceof AnnotatedField)
        && annotated.getType().isContainerType()
        && (!annotated.getType().isMapLikeType())) {  // 对于Map类型特殊处理，因为它不存在wrapper，所以不需要转换名字单复数
      return getWrappedElementName(annotated);
    }
    // 先通过 JakartaXmlBindAnnotationIntrospector 获取此属性的名称，然后根据命名策略转换
    final PropertyName pn = xmlBindAi.findNameForSerialization(annotated);
    if (pn != null && (!pn.equals(PropertyName.NO_NAME))) {
      return translatePropertyName(pn);
    }
    return null;
  }

  private PropertyName translatePropertyName(final PropertyName pn) {
    final String name = pn.getSimpleName();
    final String convertedName = namingStrategy.translate(name);
    return PropertyName.construct(convertedName, pn.getNamespace());
  }

  private PropertyName getWrappedElementName(final Annotated annotated) {
    if (annotated instanceof final AnnotatedField af) {
      return getXmlWrappedPropertyName(namingStrategy, af.getAnnotated());
    } else if (annotated instanceof final AnnotatedMethod am) {
      return getXmlWrappedPropertyName(namingStrategy, am.getAnnotated());
    } else {
      throw new IllegalArgumentException("Unsupported annotated: " + annotated);
    }
  }

  @Override
  public String findNamespace(final MapperConfig<?> config, final Annotated ann) {
    return xmlBindAi.findNamespace(config, ann);
  }

  @Override
  public Boolean isOutputAsAttribute(final MapperConfig<?> config, final Annotated ann) {
    return xmlBindAi.isOutputAsAttribute(config, ann);
  }

  @Override
  public Boolean isOutputAsText(final MapperConfig<?> config, final Annotated ann) {
    return xmlBindAi.isOutputAsText(config, ann);
  }

  @Override
  public Boolean isOutputAsCData(final MapperConfig<?> config, final Annotated ann) {
    return xmlBindAi.isOutputAsCData(config, ann);
  }
}
