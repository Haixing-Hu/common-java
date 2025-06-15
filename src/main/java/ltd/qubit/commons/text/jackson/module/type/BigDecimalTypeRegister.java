////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.BigDecimalDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.BigDecimalKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.BigDecimalSerializer;

/**
 * BigDecimal类型注册器，用于注册BigDecimal的序列化和反序列化器。
 * <p>
 * 该注册器为BigDecimal类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class BigDecimalTypeRegister implements TypeRegister<BigDecimal> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<BigDecimal> getType() {
    return BigDecimal.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<BigDecimal> getSerializer() {
    return BigDecimalSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<BigDecimal> getDeserializer() {
    return BigDecimalDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<BigDecimal> getKeySerializer() {
    return BigDecimalSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return BigDecimalKeyDeserializer.INSTANCE;
  }
}