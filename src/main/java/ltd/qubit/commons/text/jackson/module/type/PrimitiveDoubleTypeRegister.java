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

import ltd.qubit.commons.text.jackson.deserializer.DoubleDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.DoubleKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.DoubleSerializer;

/**
 * 原始double类型注册器，用于注册原始double类型的序列化和反序列化器。
 * <p>
 * 该注册器为原始double类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class PrimitiveDoubleTypeRegister implements TypeRegister<Double> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Double> getType() {
    return Double.TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Double> getSerializer() {
    return DoubleSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<Double> getDeserializer() {
    return DoubleDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Double> getKeySerializer() {
    return DoubleSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return DoubleKeyDeserializer.INSTANCE;
  }
}