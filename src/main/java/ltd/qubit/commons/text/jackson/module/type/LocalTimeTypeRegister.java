////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoLocalTimeDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoLocalTimeKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoLocalTimeSerializer;

/**
 * LocalTime类型注册器，用于注册LocalTime的ISO格式序列化和反序列化器。
 * <p>
 * 该注册器为LocalTime类型提供了ISO 8601格式的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class LocalTimeTypeRegister implements TypeRegister<LocalTime> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<LocalTime> getType() {
    return LocalTime.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<LocalTime> getSerializer() {
    return IsoLocalTimeSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<LocalTime> getDeserializer() {
    return IsoLocalTimeDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<LocalTime> getKeySerializer() {
    return IsoLocalTimeSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoLocalTimeKeyDeserializer.INSTANCE;
  }
}