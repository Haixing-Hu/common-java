////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.ZoneId;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.ZoneIdDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.ZoneIdKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.ZoneIdSerializer;

@Immutable
public class ZoneIdTypeRegister implements TypeRegister<ZoneId> {

  @Override
  public Class<ZoneId> getType() {
    return ZoneId.class;
  }

  @Override
  public JsonSerializer<ZoneId> getSerializer() {
    return ZoneIdSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<ZoneId> getDeserializer() {
    return ZoneIdDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<ZoneId> getKeySerializer() {
    return ZoneIdSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return ZoneIdKeyDeserializer.INSTANCE;
  }
}
