////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoDateDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoDateKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoDateSerializer;

@Immutable
public class DateTypeRegister implements TypeRegister<Date> {

  @Override
  public Class<Date> getType() {
    return Date.class;
  }

  @Override
  public JsonSerializer<Date> getSerializer() {
    return IsoDateSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<Date> getDeserializer() {
    return IsoDateDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<Date> getKeySerializer() {
    return IsoDateSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoDateKeyDeserializer.INSTANCE;
  }
}
