////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoLocalDateTimeDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoLocalDateTimeKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoLocalDateTimeSerializer;

@Immutable
public class LocalDateTimeTypeRegister implements TypeRegister<LocalDateTime> {

  @Override
  public Class<LocalDateTime> getType() {
    return LocalDateTime.class;
  }

  @Override
  public JsonSerializer<LocalDateTime> getSerializer() {
    return IsoLocalDateTimeSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<LocalDateTime> getDeserializer() {
    return IsoLocalDateTimeDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<LocalDateTime> getKeySerializer() {
    return IsoLocalDateTimeSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoLocalDateTimeKeyDeserializer.INSTANCE;
  }
}