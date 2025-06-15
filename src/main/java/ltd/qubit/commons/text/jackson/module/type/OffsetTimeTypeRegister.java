////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoOffsetTimeDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoOffsetTimeKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoOffsetTimeSerializer;

/**
 * OffsetTime类型注册器，用于注册OffsetTime的ISO格式序列化和反序列化器。
 * <p>
 * 该注册器为OffsetTime类型提供了ISO 8601格式的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class OffsetTimeTypeRegister implements TypeRegister<OffsetTime> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<OffsetTime> getType() {
    return OffsetTime.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<OffsetTime> getSerializer() {
    return IsoOffsetTimeSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<OffsetTime> getDeserializer() {
    return IsoOffsetTimeDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<OffsetTime> getKeySerializer() {
    return IsoOffsetTimeSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoOffsetTimeKeyDeserializer.INSTANCE;
  }
}