////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoLocalDateDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoLocalDateKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoLocalDateSerializer;

@Immutable
public class LocalDateTypeRegister implements TypeRegister<LocalDate> {

  @Override
  public Class<LocalDate> getType() {
    return LocalDate.class;
  }

  @Override
  public JsonSerializer<LocalDate> getSerializer() {
    return IsoLocalDateSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<LocalDate> getDeserializer() {
    return IsoLocalDateDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<LocalDate> getKeySerializer() {
    return IsoLocalDateSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoLocalDateKeyDeserializer.INSTANCE;
  }
}