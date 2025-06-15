////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.RawEnumDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.RawEnumKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.RawEnumSerializer;

/**
 * Enum类型注册器，用于注册枚举的原始值序列化和反序列化器。
 * <p>
 * 该注册器为Enum类型提供了原始值的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@SuppressWarnings("rawtypes")
@Immutable
public class EnumTypeRegister implements TypeRegister<Enum> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Enum> getType() {
    return Enum.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Enum> getSerializer() {
    return RawEnumSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<Enum> getDeserializer() {
    return RawEnumDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Enum> getKeySerializer() {
    return RawEnumSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return RawEnumKeyDeserializer.INSTANCE;
  }
}