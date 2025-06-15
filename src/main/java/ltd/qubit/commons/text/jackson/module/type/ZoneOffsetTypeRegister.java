////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.ZoneOffset;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.ZoneOffsetDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.ZoneOffsetKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.ZoneOffsetSerializer;

/**
 * ZoneOffset类型注册器，用于注册ZoneOffset的序列化和反序列化器。
 * <p>
 * 该注册器为ZoneOffset类型提供了标准的序列化和反序列化处理。
 *
 * @author 胡海星
 */
@Immutable
public class ZoneOffsetTypeRegister implements TypeRegister<ZoneOffset> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<ZoneOffset> getType() {
    return ZoneOffset.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<ZoneOffset> getSerializer() {
    return ZoneOffsetSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<ZoneOffset> getDeserializer() {
    return ZoneOffsetDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<ZoneOffset> getKeySerializer() {
    return ZoneOffsetSerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return ZoneOffsetKeyDeserializer.INSTANCE;
  }
}