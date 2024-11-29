////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.Period;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.PeriodDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.PeriodKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.PeriodSerializer;

@Immutable
public class PeriodTypeRegister implements TypeRegister<Period> {

  @Override
  public Class<Period> getType() {
    return Period.class;
  }

  @Override
  public JsonSerializer<Period> getSerializer() {
    return PeriodSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<Period> getDeserializer() {
    return PeriodDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<Period> getKeySerializer() {
    return PeriodSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return PeriodKeyDeserializer.INSTANCE;
  }
}
