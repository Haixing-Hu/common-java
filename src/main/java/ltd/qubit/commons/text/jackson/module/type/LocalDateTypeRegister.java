////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoLocalDateDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoLocalDateKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoLocalDateSerializer;

/**
 * LocalDate类型注册器，用于注册LocalDate的ISO格式序列化和反序列化器。
 * <p>
 * 该注册器为LocalDate类型提供了ISO 8601格式的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class LocalDateTypeRegister implements TypeRegister<LocalDate> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<LocalDate> getType() {
    return LocalDate.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<LocalDate> getSerializer() {
    return IsoLocalDateSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<LocalDate> getDeserializer() {
    return IsoLocalDateDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<LocalDate> getKeySerializer() {
    return IsoLocalDateSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoLocalDateKeyDeserializer.INSTANCE;
  }
}