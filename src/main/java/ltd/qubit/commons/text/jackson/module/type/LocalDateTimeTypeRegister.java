////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoLocalDateTimeDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoLocalDateTimeKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoLocalDateTimeSerializer;

/**
 * LocalDateTime类型注册器，用于注册LocalDateTime的ISO格式序列化和反序列化器。
 * <p>
 * 该注册器为LocalDateTime类型提供了ISO 8601格式的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class LocalDateTimeTypeRegister implements TypeRegister<LocalDateTime> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<LocalDateTime> getType() {
    return LocalDateTime.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<LocalDateTime> getSerializer() {
    return IsoLocalDateTimeSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<LocalDateTime> getDeserializer() {
    return IsoLocalDateTimeDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<LocalDateTime> getKeySerializer() {
    return IsoLocalDateTimeSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoLocalDateTimeKeyDeserializer.INSTANCE;
  }
}