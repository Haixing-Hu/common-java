////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoInstantDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoInstantKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoInstantSerializer;

/**
 * Instant类型注册器，用于注册Instant的ISO格式序列化和反序列化器。
 * <p>
 * 该注册器为Instant类型提供了ISO 8601格式的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class InstantTypeRegister implements TypeRegister<Instant> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Instant> getType() {
    return Instant.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Instant> getSerializer() {
    return IsoInstantSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<Instant> getDeserializer() {
    return IsoInstantDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Instant> getKeySerializer() {
    return IsoInstantSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoInstantKeyDeserializer.INSTANCE;
  }
}