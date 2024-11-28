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

import ltd.qubit.commons.text.jackson.deserializer.DoubleDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.DoubleKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.DoubleSerializer;

@Immutable
public class DoubleTypeRegister implements TypeRegister<Double> {

  @Override
  public Class<Double> getType() {
    return Double.class;
  }

  @Override
  public JsonSerializer<Double> getSerializer() {
    return DoubleSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<Double> getDeserializer() {
    return DoubleDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<Double> getKeySerializer() {
    return DoubleSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return DoubleKeyDeserializer.INSTANCE;
  }
}
