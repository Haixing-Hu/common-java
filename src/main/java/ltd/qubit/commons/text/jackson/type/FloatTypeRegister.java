////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.type;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.FloatDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.FloatKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.FloatSerializer;

@Immutable
public class FloatTypeRegister implements TypeRegister<Float> {

  @Override
  public Class<Float> getType() {
    return Float.class;
  }

  @Override
  public JsonSerializer<Float> getSerializer() {
    return FloatSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<Float> getDeserializer() {
    return FloatDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<Float> getKeySerializer() {
    return FloatSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return FloatKeyDeserializer.INSTANCE;
  }
}
