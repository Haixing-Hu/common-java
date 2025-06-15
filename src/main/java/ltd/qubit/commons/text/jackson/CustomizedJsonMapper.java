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

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import ltd.qubit.commons.text.CaseFormat;
import ltd.qubit.commons.text.jackson.module.ForceCreatorDeserializerModule;
import ltd.qubit.commons.text.jackson.module.TypeRegistrationModule;

import static ltd.qubit.commons.text.jackson.CustomizeJacksonUtils.customizeFeature;
import static ltd.qubit.commons.text.jackson.CustomizeJacksonUtils.getNormalizedConfig;

/**
 * 自定义的Jackson JSON ObjectMapper。
 *
 * <p>此类扩展了Jackson的{@link JsonMapper}，提供了额外的功能和定制化配置：
 * <ul>
 * <li>支持自定义命名策略（默认为下划线命名）</li>
 * <li>支持美化打印输出</li>
 * <li>支持正则化序列化配置</li>
 * <li>集成JDK8模块，支持Optional、Stream等类型</li>
 * <li>包含自定义类型注册模块和强制创建器反序列化模块</li>
 * </ul>
 * </p>
 *
 * @author 胡海星
 */
public class CustomizedJsonMapper extends JsonMapper {

  @Serial
  private static final long serialVersionUID = 2077067730796046421L;

  /**
   * 默认的命名策略为小写下划线格式。
   */
  public static final CaseFormat DEFAULT_NAMING_STRATEGY = CaseFormat.LOWER_UNDERSCORE;

  /**
   * 创建一个正则化的自定义JSON映射器。
   *
   * @return 正则化的自定义JSON映射器实例
   */
  public static CustomizedJsonMapper createNormalized() {
    return new CustomizedJsonMapper(true);
  }

  /**
   * 是否启用美化打印。
   */
  private boolean prettyPrint = false;

  /**
   * 是否启用正则化。
   */
  private boolean normalized = false;

  /**
   * 命名策略。
   */
  private CaseFormat namingStrategy = DEFAULT_NAMING_STRATEGY;

  /**
   * 构造一个默认的自定义JSON映射器。
   */
  public CustomizedJsonMapper() {
    init();
  }

  /**
   * 构造一个自定义JSON映射器。
   *
   * @param normalized 是否启用正则化
   */
  public CustomizedJsonMapper(final boolean normalized) {
    this.normalized = normalized;
    init();
  }

  /**
   * 基于另一个自定义JSON映射器创建副本。
   *
   * @param other 要复制的映射器
   */
  public CustomizedJsonMapper(final CustomizedJsonMapper other) {
    super(other);
    this.prettyPrint = other.prettyPrint;
    this.normalized = other.normalized;
    this.namingStrategy = other.namingStrategy;
    init();
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
    // 设置序列化和反序列化时字段属性命名策略
    this.setPropertyNamingStrategy(namingStrategy.toPropertyNamingStrategy());
    // 处理 JDK8 的 Optional, Stream 等类型
    this.registerModule(new Jdk8Module());
    // 增加自定义类型注册模块
    this.registerModule(TypeRegistrationModule.INSTANCE);
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
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CustomizedJsonMapper copy() {
    return new CustomizedJsonMapper(this);
  }
}