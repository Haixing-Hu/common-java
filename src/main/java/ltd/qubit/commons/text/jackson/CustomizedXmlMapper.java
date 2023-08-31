////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;
import ltd.qubit.commons.text.CaseFormat;
import org.codehaus.stax2.XMLOutputFactory2;

import javax.xml.stream.XMLOutputFactory;

import static ltd.qubit.commons.text.jackson.CustomizeJacksonUtils.customizeFeature;
import static ltd.qubit.commons.text.jackson.CustomizeJacksonUtils.getNormalizedConfig;

/**
 * 自定义的 Jackson XmlMapper。
 *
 * @author Haixing Hu
 */
public class CustomizedXmlMapper extends XmlMapper {

  private static final long serialVersionUID = 2353632581927861218L;

  public static final CaseFormat DEFAULT_NAMING_STRATEGY = CaseFormat.LOWER_HYPHEN;

  public static CustomizedXmlMapper createNormalized() {
    return new CustomizedXmlMapper(true);
  }

  private CaseFormat namingStrategy = DEFAULT_NAMING_STRATEGY;

  private boolean normalized = false;

  private boolean prettyPrint = false;

  // private final XmlNameConversionIntrospector nameConversionIntrospector =
  //     new XmlNameConversionIntrospector(namingStrategy.toPropertyNamingStrategy());

  public CustomizedXmlMapper() {
    super(getXmlModule());
    init();
  }

  public CustomizedXmlMapper(final boolean normalized) {
    super(getXmlModule());
    this.normalized = normalized;
    init();
  }

  public CustomizedXmlMapper(final CustomizedXmlMapper other) {
    super(other);
    this.namingStrategy = other.namingStrategy;
    this.prettyPrint = other.prettyPrint;
    this.normalized = other.normalized;
    init();
  }

  private static JacksonXmlModule getXmlModule() {
    final JacksonXmlModule module = new JacksonXmlModule();
    module.setDefaultUseWrapper(true);
    return module;
  }

  private void init() {
    customizeFeature(this);
    // 是否 pretty print
    this.configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);
    // 是否正则化序列化结果
    if (normalized) {
      final SerializationConfig config = getNormalizedConfig(this);
      this.setConfig(config);
    }
    // 设置序列化和反序列化时字段属性命名策略
    this.setPropertyNamingStrategy(namingStrategy.toPropertyNamingStrategy());
    // 注意：默认的 XmlMapper 是同时支持 JacksonXmlAnnotation 和 JacksonAnnotation 的
    // 但这样的话，如果某个字段被同时标注了 Jackson 的 JSON annotation (例如 @JsonSerialize)
    // 和 JAXB 的 XML annotation (例如 @XmlJavaTypeAdapter)，就会发生冲突
    // 因此我们需要重置此 mapper 的 AnnotationIntrospector 为 JaxbAnnotationIntrospector
    // 即移除了默认的 JacksonXmlAnnotation 和 JacksonAnnotation 构成的 AnnotationIntrospectorPair
    // 注意：不能使用 JacksonXmlAnnotationIntrospector，因为它继承自 JacksonAnnotationIntrospector
    // 会默认加上对 JacksonAnnotation 的支持，例如 Instant 字段标注了 @XmlJavaTypeAdapter
    // 就导致类型报错，因为 JacksonAnnotationIntrospector 会根据 JavaTimeModule 注册的
    // IsoInstantSerializer 处理被 JavaTypeAdaptor 转换后的数据，从而导致类型错误
    this.setAnnotationIntrospector(
        new JakartaXmlBindAnnotationIntrospector(this.getTypeFactory()));
    // 处理 JDK8 的 Optional, Stream 等类型
    this.registerModule(new Jdk8Module());
    // 增加自定义类型注册模块
    this.registerModule(new TypeRegistrationModule());
    // 增加XML序列化自定义模块
    this.registerModule(
        new CustomizedXmlModule(namingStrategy.toPropertyNamingStrategy()));
    // 设置XML输出转义
    final XMLOutputFactory factory = this.getFactory().getXMLOutputFactory();
    factory.setProperty(XMLOutputFactory2.P_TEXT_ESCAPER,
        XmlEscapingWriterFactory.INSTANCE);
    factory.setProperty(XMLOutputFactory2.P_ATTR_VALUE_ESCAPER,
        XmlEscapingWriterFactory.INSTANCE);
  }

  // private AnnotationIntrospector buildAnnotationIntrospector() {
  //   // 使用 JakartaXmlBindAnnotationIntrospector 为首选的 introspector
  //   final JakartaXmlBindAnnotationIntrospector primary =
  //       new JakartaXmlBindAnnotationIntrospector(this.getTypeFactory());
  //   // 使用 NameConversionIntrospector 为后备 introspector，它的作用是处理
  //   // root元素名转换，以及容器内部 wrapped 元素名称的单复数转换。
  //   return new AnnotationIntrospectorPair(primary, nameConversionIntrospector);
  // }

  public final boolean isPrettyPrint() {
    return prettyPrint;
  }

  public void setPrettyPrint(final boolean prettyPrint) {
    this.prettyPrint = prettyPrint;
    // 是否 pretty print
    this.configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);
  }

  public final boolean isNormalized() {
    return normalized;
  }

  public void setNormalized(final boolean normalized) {
    this.normalized = normalized;
    if (normalized) {
      final SerializationConfig config = getNormalizedConfig(this);
      this.setConfig(config);
    }
  }

  public final CaseFormat getNamingStrategy() {
    return namingStrategy;
  }

  public void setNamingStrategy(final CaseFormat namingStrategy) {
    this.namingStrategy = namingStrategy;
    // 设置序列化和反序列化时字段属性命名策略
    this.setPropertyNamingStrategy(namingStrategy.toPropertyNamingStrategy());
    // this.nameConversionIntrospector.setNamingStrategy(namingStrategy.toPropertyNamingStrategy());
  }

  @Override
  public CustomizedXmlMapper copy() {
    return new CustomizedXmlMapper(this);
  }

}
