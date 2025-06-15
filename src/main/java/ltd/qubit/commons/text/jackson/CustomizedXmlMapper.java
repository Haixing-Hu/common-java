////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 自定义的Jackson XmlMapper。
 *
 * <p>此类扩展了Jackson的{@link XmlMapper}，提供了额外的XML处理功能和定制化配置：
 * <ul>
 * <li>支持自定义命名策略（默认为小写连字符命名）</li>
 * <li>支持美化打印输出</li>
 * <li>支持正则化序列化配置</li>
 * <li>集成XML转义处理</li>
 * <li>支持Jakarta XML Bind注解</li>
 * <li>集成JDK8模块和各种自定义模块</li>
 * </ul>
 * </p>
 *
 * @author 胡海星
 */
public class CustomizedXmlMapper extends XmlMapper {

  /**
   * 序列化版本号。
   */
  @Serial
  private static final long serialVersionUID = 2353632581927861218L;

  /**
   * 默认的命名策略为小写连字符格式。
   */
  public static final CaseFormat DEFAULT_NAMING_STRATEGY = CaseFormat.LOWER_HYPHEN;

  /**
   * 创建一个正则化的自定义XML映射器。
   *
   * @return 正则化的自定义XML映射器实例
   */
  public static CustomizedXmlMapper createNormalized() {
    return new CustomizedXmlMapper(true);
  }

  /**
   * 命名策略。
   */
  private CaseFormat namingStrategy = DEFAULT_NAMING_STRATEGY;

  /**
   * 是否启用正则化。
   */
  private boolean normalized = false;

  /**
   * 是否启用美化打印。
   */
  private boolean prettyPrint = true;

  /**
   * 构造一个默认的自定义XML映射器。
   */
  public CustomizedXmlMapper() {
    super(getXmlModule());
    init();
  }

  /**
   * 构造一个自定义XML映射器。
   *
   * @param normalized 是否启用正则化
   */
  public CustomizedXmlMapper(final boolean normalized) {
    super(getXmlModule());
    this.normalized = normalized;
    init();
  }

  /**
   * 基于另一个自定义XML映射器创建副本。
   *
   * @param other 要复制的映射器
   */
  public CustomizedXmlMapper(final CustomizedXmlMapper other) {
    super(other);
    this.namingStrategy = other.namingStrategy;
    this.prettyPrint = other.prettyPrint;
    this.normalized = other.normalized;
    init();
  }

  /**
   * 获取配置好的Jackson XML模块。
   *
   * @return 配置好的XML模块
   */
  private static JacksonXmlModule getXmlModule() {
    final JacksonXmlModule module = new JacksonXmlModule();
    module.setDefaultUseWrapper(true);
    return module;
  }

  /**
   * 初始化映射器配置。
   */
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

  /**
   * 判断是否启用了美化打印。
   *
   * @return 如果启用了美化打印则返回true
   */
  public final boolean isPrettyPrint() {
    return prettyPrint;
  }

  /**
   * 设置是否启用美化打印。
   *
   * @param prettyPrint 是否启用美化打印
   */
  public void setPrettyPrint(final boolean prettyPrint) {
    this.prettyPrint = prettyPrint;
    // 是否 pretty print
    this.configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);
  }

  /**
   * 判断是否启用了正则化。
   *
   * @return 如果启用了正则化则返回true
   */
  public final boolean isNormalized() {
    return normalized;
  }

  /**
   * 设置是否启用正则化。
   *
   * @param normalized 是否启用正则化
   */
  public void setNormalized(final boolean normalized) {
    this.normalized = normalized;
    if (normalized) {
      final SerializationConfig config = getNormalizedConfig(this);
      this.setConfig(config);
    }
  }

  /**
   * 获取当前的命名策略。
   *
   * @return 当前的命名策略
   */
  public final CaseFormat getNamingStrategy() {
    return namingStrategy;
  }

  /**
   * 设置命名策略。
   *
   * @param namingStrategy 要设置的命名策略
   */
  public void setNamingStrategy(final CaseFormat namingStrategy) {
    this.namingStrategy = namingStrategy;
    // 设置序列化和反序列化时字段属性命名策略
    this.setPropertyNamingStrategy(namingStrategy.toPropertyNamingStrategy());
    // this.nameConversionIntrospector.setNamingStrategy(namingStrategy.toPropertyNamingStrategy());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CustomizedXmlMapper copy() {
    return new CustomizedXmlMapper(this);
  }

}