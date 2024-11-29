////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.Serial;

import javax.xml.stream.XMLOutputFactory;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;

import ltd.qubit.commons.text.CaseFormat;
import ltd.qubit.commons.text.jackson.module.ForceCreatorDeserializerModule;
import ltd.qubit.commons.text.jackson.module.StripStringModule;
import ltd.qubit.commons.text.jackson.module.TypeRegistrationModule;
import ltd.qubit.commons.text.jackson.module.XmlMapModule;
import ltd.qubit.commons.text.xml.XmlEscapingWriterFactory;

import static org.codehaus.stax2.XMLOutputFactory2.P_ATTR_VALUE_ESCAPER;
import static org.codehaus.stax2.XMLOutputFactory2.P_TEXT_ESCAPER;

import static ltd.qubit.commons.text.jackson.CustomizeJacksonUtils.customizeFeature;
import static ltd.qubit.commons.text.jackson.CustomizeJacksonUtils.getNormalizedConfig;

/**
 * 自定义的 Jackson XmlMapper。
 *
 * @author 胡海星
 */
public class CustomizedXmlMapper extends XmlMapper {

  @Serial
  private static final long serialVersionUID = 2353632581927861218L;

  public static final CaseFormat DEFAULT_NAMING_STRATEGY = CaseFormat.LOWER_HYPHEN;

  public static CustomizedXmlMapper createNormalized() {
    return new CustomizedXmlMapper(true);
  }

  private CaseFormat namingStrategy = DEFAULT_NAMING_STRATEGY;

  private boolean normalized = false;

  private boolean prettyPrint = true;

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
    // 设置XML输出转义
    final XMLOutputFactory factory = this.getFactory().getXMLOutputFactory();
    factory.setProperty(P_TEXT_ESCAPER, XmlEscapingWriterFactory.INSTANCE);
    factory.setProperty(P_ATTR_VALUE_ESCAPER, XmlEscapingWriterFactory.INSTANCE);
    // 设置序列化和反序列化时字段属性命名策略
    final PropertyNamingStrategies.NamingBase naming = namingStrategy.toPropertyNamingStrategy();
    this.setPropertyNamingStrategy(naming);
    // 注意：默认的 XmlMapper 是同时支持 JacksonXmlAnnotation 和 JacksonAnnotation 的
    // 但这样的话，如果某个字段被同时标注了 Jackson 的 JSON annotation (例如 @JsonSerialize)
    // 和 JAXB 的 XML annotation (例如 @XmlJavaTypeAdapter)，就会发生冲突
    // 因此我们需要重置此 mapper 的 AnnotationIntrospector 为 JakartaXmlBindAnnotationIntrospector
    // 即移除了默认的 JacksonXmlAnnotation 和 JacksonAnnotation 构成的 AnnotationIntrospectorPair
    // 注意：不能使用 JacksonXmlAnnotationIntrospector，因为它继承自 JacksonAnnotationIntrospector
    // 会默认加上对 JacksonAnnotation 的支持，例如 Instant 字段标注了 @XmlJavaTypeAdapter
    // 就导致类型报错，因为 JacksonAnnotationIntrospector 会根据 JavaTimeModule 注册的
    // IsoInstantSerializer 处理被 JavaTypeAdaptor 转换后的数据，从而导致类型错误
    //
    // 事实上，我们先使用了一个自定义的 XmlNameConversionIntrospector
    // 它可以根据指定的命名策略转换根元素名称，并且自动处理集合类属性内部元素名称，
    // 同时通过内置的 JakartaXmlBindAnnotationIntrospector 获得 Jakarata XML Bind Annotation 注解所指定的属性名称
    // 再对其通过指定的命名策略进行转换。
    // 接下来如果 XmlNameConversionIntrospector 没有处理指定的标签，再调用
    // JakartaXmlBindAnnotationIntrospector 进行处理。
    final TypeFactory typeFactory = this.getTypeFactory();
    final AnnotationIntrospector nameAi = new XmlNameConversionIntrospector(naming, typeFactory);
    final AnnotationIntrospector xmlAi = new JakartaXmlBindAnnotationIntrospector(typeFactory);
    final AnnotationIntrospector ai = AnnotationIntrospectorPair.create(nameAi, xmlAi);
    this.setAnnotationIntrospector(ai);
    // 处理 JDK8 的 Optional, Stream 等类型
    this.registerModule(new Jdk8Module());
    // 增加自定义类型注册模块
    this.registerModule(TypeRegistrationModule.INSTANCE);
    // 增加自定义模块，对字符串字段值需要 strip 前后空白字符
    this.registerModule(StripStringModule.INSTANCE);
    // 增加自定义模块，对 map 类型特殊处理
    this.registerModule(XmlMapModule.INSTANCE);
    // 增加强制创建对象的模块
    this.registerModule(ForceCreatorDeserializerModule.INSTANCE);
  }

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
