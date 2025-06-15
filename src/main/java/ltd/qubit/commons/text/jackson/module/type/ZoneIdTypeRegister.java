////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.ZoneId;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.ZoneIdDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.ZoneIdKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.ZoneIdSerializer;

/**
 * ZoneId类型注册器，用于注册ZoneId的序列化和反序列化器。
 * <p>
 * 该注册器为ZoneId类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class ZoneIdTypeRegister implements TypeRegister<ZoneId> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<ZoneId> getType() {
    return ZoneId.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<ZoneId> getSerializer() {
    return ZoneIdSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<ZoneId> getDeserializer() {
    return ZoneIdDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<ZoneId> getKeySerializer() {
    return ZoneIdSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return ZoneIdKeyDeserializer.INSTANCE;
  }
}