////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.Year;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.YearDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.YearKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.YearSerializer;

@Immutable
public class YearTypeRegister implements TypeRegister<Year> {

  @Override
  public Class<Year> getType() {
    return Year.class;
  }

  @Override
  public JsonSerializer<Year> getSerializer() {
    return YearSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<Year> getDeserializer() {
    return YearDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<Year> getKeySerializer() {
    return YearSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return YearKeyDeserializer.INSTANCE;
  }
}
