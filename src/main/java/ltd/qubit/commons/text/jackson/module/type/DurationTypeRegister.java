////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.DurationDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.DurationKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.DurationSerializer;

/**
 * Duration类型注册器，用于注册Duration的序列化和反序列化器。
 * <p>
 * 该注册器为Duration类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class DurationTypeRegister implements TypeRegister<Duration> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Duration> getType() {
    return Duration.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Duration> getSerializer() {
    return DurationSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<Duration> getDeserializer() {
    return DurationDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Duration> getKeySerializer() {
    return DurationSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return DurationKeyDeserializer.INSTANCE;
  }
}