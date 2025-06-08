////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.IsoOffsetTimeDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.IsoOffsetTimeKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.IsoOffsetTimeSerializer;

@Immutable
public class OffsetTimeTypeRegister implements TypeRegister<OffsetTime> {

  @Override
  public Class<OffsetTime> getType() {
    return OffsetTime.class;
  }

  @Override
  public JsonSerializer<OffsetTime> getSerializer() {
    return IsoOffsetTimeSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<OffsetTime> getDeserializer() {
    return IsoOffsetTimeDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<OffsetTime> getKeySerializer() {
    return IsoOffsetTimeSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return IsoOffsetTimeKeyDeserializer.INSTANCE;
  }
}