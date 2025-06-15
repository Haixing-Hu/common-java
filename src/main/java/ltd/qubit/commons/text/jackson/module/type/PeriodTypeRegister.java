////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.Period;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.PeriodDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.PeriodKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.PeriodSerializer;

/**
 * Period类型注册器，用于注册Period的序列化和反序列化器。
 * <p>
 * 该注册器为Period类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class PeriodTypeRegister implements TypeRegister<Period> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Period> getType() {
    return Period.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Period> getSerializer() {
    return PeriodSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<Period> getDeserializer() {
    return PeriodDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Period> getKeySerializer() {
    return PeriodSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return PeriodKeyDeserializer.INSTANCE;
  }
}