////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.Base64ByteArrayDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.Base64ByteArrayKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.Base64ByteArraySerializer;

/**
 * Base64字节数组类型注册器，用于注册字节数组的Base64序列化和反序列化器。
 * <p>
 * 该注册器将字节数组转换为Base64字符串进行序列化，并在反序列化时将Base64字符串转换回字节数组。
 *
 * @author 胡海星
 */
@Immutable
public class Base64ByteArrayTypeRegister implements TypeRegister<byte[]> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<byte[]> getType() {
    return byte[].class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<byte[]> getSerializer() {
    return Base64ByteArraySerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<byte[]> getDeserializer() {
    return Base64ByteArrayDeserializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<byte[]> getKeySerializer() {
    return Base64ByteArraySerializer.INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer getKeyDeserializer() {
    return Base64ByteArrayKeyDeserializer.INSTANCE;
  }
}