////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.MonthDay;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.MonthDayDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.MonthDayKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.MonthDaySerializer;

/**
 * MonthDay类型注册器，用于注册MonthDay的序列化和反序列化器。
 * <p>
 * 该注册器为MonthDay类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class MonthDayTypeRegister implements TypeRegister<MonthDay> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<MonthDay> getType() {
    return MonthDay.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<MonthDay> getSerializer() {
    return MonthDaySerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<MonthDay> getDeserializer() {
    return MonthDayDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<MonthDay> getKeySerializer() {
    return MonthDaySerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return MonthDayKeyDeserializer.INSTANCE;
  }
}