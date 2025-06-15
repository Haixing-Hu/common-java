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

import ltd.qubit.commons.text.jackson.deserializer.FloatDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.FloatKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.FloatSerializer;

/**
 * 原始float类型注册器，用于注册原始float类型的序列化和反序列化器。
 * <p>
 * 该注册器为原始float类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class PrimitiveFloatTypeRegister implements TypeRegister<Float> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Float> getType() {
    return Float.TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Float> getSerializer() {
    return FloatSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<Float> getDeserializer() {
    return FloatDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Float> getKeySerializer() {
    return FloatSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return FloatKeyDeserializer.INSTANCE;
  }
}