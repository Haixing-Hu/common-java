////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.module.SimpleModule;

import ltd.qubit.commons.text.jackson.deserializer.StripStringDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.StripStringKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.StripStringSerializer;

/**
 * 字符串去空格模块，用于在序列化和反序列化时自动去除字符串的前后空格。
 * <p>
 * 该模块注册了字符串的序列化器、反序列化器以及键序列化器和键反序列化器，
 * 在处理字符串时会自动调用 {@link String#strip()} 方法去除前后空格。
 *
 * @author 胡海星
 */
@Immutable
public class StripStringModule extends SimpleModule {
  @Serial
  private static final long serialVersionUID = -7300221967161392446L;

  /**
   * 模块的单例实例。
   */
  public static final StripStringModule INSTANCE = new StripStringModule();

  /**
   * 构造字符串去空格模块。
   * <p>
   * 注册String类型的序列化器和反序列化器。
   */
  public StripStringModule() {
    this.addSerializer(String.class, StripStringSerializer.INSTANCE);
    this.addDeserializer(String.class, StripStringDeserializer.INSTANCE);
    this.addKeySerializer(String.class, StripStringSerializer.INSTANCE);
    this.addKeyDeserializer(String.class, StripStringKeyDeserializer.INSTANCE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getModuleName() {
    return this.getClass().getSimpleName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupModule(final SetupContext context) {
    super.setupModule(context);
  }
}