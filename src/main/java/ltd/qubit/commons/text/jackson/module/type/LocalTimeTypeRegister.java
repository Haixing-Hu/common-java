////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoLocalTimeDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoLocalTimeKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoLocalTimeSerializer;

@Immutable
public class LocalTimeTypeRegister implements TypeRegister<LocalTime> {

  @Override
  public Class<LocalTime> getType() {
    return LocalTime.class;
  }

  @Override
  public JsonSerializer<LocalTime> getSerializer() {
    return IsoLocalTimeSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<LocalTime> getDeserializer() {
    return IsoLocalTimeDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<LocalTime> getKeySerializer() {
    return IsoLocalTimeSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoLocalTimeKeyDeserializer.INSTANCE;
  }
}
