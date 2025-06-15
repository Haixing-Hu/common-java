////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.BigIntegerDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.BigIntegerKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.BigIntegerSerializer;

/**
 * BigInteger类型注册器，用于注册BigInteger的序列化和反序列化器。
 * <p>
 * 该注册器为BigInteger类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class BigIntegerTypeRegister implements TypeRegister<BigInteger> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<BigInteger> getType() {
    return BigInteger.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<BigInteger> getSerializer() {
    return BigIntegerSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<BigInteger> getDeserializer() {
    return BigIntegerDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<BigInteger> getKeySerializer() {
    return BigIntegerSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return BigIntegerKeyDeserializer.INSTANCE;
  }
}