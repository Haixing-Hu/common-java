////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.type;

import java.time.YearMonth;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.YearMonthDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.YearMonthKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.YearMonthSerializer;

@Immutable
public class YearMonthTypeRegister implements TypeRegister<YearMonth> {

  @Override
  public Class<YearMonth> getType() {
    return YearMonth.class;
  }

  @Override
  public JsonSerializer<YearMonth> getSerializer() {
    return YearMonthSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<YearMonth> getDeserializer() {
    return YearMonthDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<YearMonth> getKeySerializer() {
    return YearMonthSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return YearMonthKeyDeserializer.INSTANCE;
  }
}
