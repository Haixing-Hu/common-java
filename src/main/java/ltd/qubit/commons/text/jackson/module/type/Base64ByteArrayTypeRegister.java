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

@Immutable
public class Base64ByteArrayTypeRegister implements TypeRegister<byte[]> {

  @Override
  public Class<byte[]> getType() {
    return byte[].class;
  }

  @Override
  public JsonSerializer<byte[]> getSerializer() {
    return Base64ByteArraySerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<byte[]> getDeserializer() {
    return Base64ByteArrayDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<byte[]> getKeySerializer() {
    return Base64ByteArraySerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return Base64ByteArrayKeyDeserializer.INSTANCE;
  }
}