////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoDateDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoDateKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoDateSerializer;

/**
 * Date类型注册器，用于注册Date的ISO格式序列化和反序列化器。
 * <p>
 * 该注册器为Date类型提供了ISO 8601格式的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class DateTypeRegister implements TypeRegister<Date> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Date> getType() {
    return Date.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Date> getSerializer() {
    return IsoDateSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<Date> getDeserializer() {
    return IsoDateDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Date> getKeySerializer() {
    return IsoDateSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoDateKeyDeserializer.INSTANCE;
  }
}