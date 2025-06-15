////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.YearMonth;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.YearMonthDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.YearMonthKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.YearMonthSerializer;

/**
 * YearMonth类型注册器，用于注册YearMonth的序列化和反序列化器。
 * <p>
 * 该注册器为YearMonth类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class YearMonthTypeRegister implements TypeRegister<YearMonth> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<YearMonth> getType() {
    return YearMonth.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<YearMonth> getSerializer() {
    return YearMonthSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<YearMonth> getDeserializer() {
    return YearMonthDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<YearMonth> getKeySerializer() {
    return YearMonthSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return YearMonthKeyDeserializer.INSTANCE;
  }
}