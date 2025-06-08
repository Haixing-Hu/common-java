////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.DurationDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.DurationKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.DurationSerializer;

@Immutable
public class DurationTypeRegister implements TypeRegister<Duration> {

  @Override
  public Class<Duration> getType() {
    return Duration.class;
  }

  @Override
  public JsonSerializer<Duration> getSerializer() {
    return DurationSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<Duration> getDeserializer() {
    return DurationDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<Duration> getKeySerializer() {
    return DurationSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return DurationKeyDeserializer.INSTANCE;
  }
}