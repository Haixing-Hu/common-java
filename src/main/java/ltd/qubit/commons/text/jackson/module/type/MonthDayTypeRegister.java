////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.MonthDay;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.MonthDayDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.MonthDayKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.MonthDaySerializer;

@Immutable
public class MonthDayTypeRegister implements TypeRegister<MonthDay> {

  @Override
  public Class<MonthDay> getType() {
    return MonthDay.class;
  }

  @Override
  public JsonSerializer<MonthDay> getSerializer() {
    return MonthDaySerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<MonthDay> getDeserializer() {
    return MonthDayDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<MonthDay> getKeySerializer() {
    return MonthDaySerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return MonthDayKeyDeserializer.INSTANCE;
  }
}
