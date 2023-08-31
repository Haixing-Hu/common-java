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
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import ltd.qubit.commons.text.CaseFormat;

import static ltd.qubit.commons.text.jackson.CustomizeJacksonUtils.customizeFeature;
import static ltd.qubit.commons.text.jackson.CustomizeJacksonUtils.getNormalizedConfig;

/**
 * 自定义的 Jackson JSON ObjectMapper。
 *
 * @author Haixing Hu
 */
public class CustomizedJsonMapper extends JsonMapper {

  private static final long serialVersionUID = 2077067730796046421L;

  public static final CaseFormat DEFAULT_NAMING_STRATEGY = CaseFormat.LOWER_UNDERSCORE;

  public static CustomizedJsonMapper createNormalized() {
    return new CustomizedJsonMapper(true);
  }

  private boolean prettyPrint = false;

  private boolean normalized = false;

  private CaseFormat namingStrategy = DEFAULT_NAMING_STRATEGY;

  public CustomizedJsonMapper() {
    init();
  }

  public CustomizedJsonMapper(final boolean normalized) {
    this.normalized = normalized;
    init();
  }

  public CustomizedJsonMapper(final CustomizedJsonMapper other) {
    super(other);
    this.prettyPrint = other.prettyPrint;
    this.normalized = other.normalized;
    this.namingStrategy = other.namingStrategy;
    init();
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
    // 处理 JDK8 的 Optional, Stream 等类型
    this.registerModule(new Jdk8Module());
    // 增加自定义类型注册模块
    this.registerModule(new TypeRegistrationModule());
  }

  public final boolean isPrettyPrint() {
    return prettyPrint;
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

  public void setPrettyPrint(final boolean prettyPrint) {
    this.prettyPrint = prettyPrint;
    // 是否 pretty print
    this.configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);
  }

  public final CaseFormat getNamingStrategy() {
    return namingStrategy;
  }

  public void setNamingStrategy(final CaseFormat namingStrategy) {
    this.namingStrategy = namingStrategy;
    // 设置序列化和反序列化时字段属性命名策略
    this.setPropertyNamingStrategy(namingStrategy.toPropertyNamingStrategy());
  }

  @Override
  public CustomizedJsonMapper copy() {
    return new CustomizedJsonMapper(this);
  }
}
