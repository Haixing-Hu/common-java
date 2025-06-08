////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoInstantDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoInstantKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoInstantSerializer;

@Immutable
public class InstantTypeRegister implements TypeRegister<Instant> {

  @Override
  public Class<Instant> getType() {
    return Instant.class;
  }

  @Override
  public JsonSerializer<Instant> getSerializer() {
    return IsoInstantSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<Instant> getDeserializer() {
    return IsoInstantDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<Instant> getKeySerializer() {
    return IsoInstantSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoInstantKeyDeserializer.INSTANCE;
  }
}