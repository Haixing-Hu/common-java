////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.Year;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.YearDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.YearKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.YearSerializer;

/**
 * Year类型注册器，用于注册Year的序列化和反序列化器。
 * <p>
 * 该注册器为Year类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class YearTypeRegister implements TypeRegister<Year> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Year> getType() {
    return Year.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Year> getSerializer() {
    return YearSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<Year> getDeserializer() {
    return YearDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<Year> getKeySerializer() {
    return YearSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return YearKeyDeserializer.INSTANCE;
  }
}