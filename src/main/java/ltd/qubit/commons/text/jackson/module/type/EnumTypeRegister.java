////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.RawEnumDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.RawEnumKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.RawEnumSerializer;

@SuppressWarnings("rawtypes")
@Immutable
public class EnumTypeRegister implements TypeRegister<Enum> {

  @Override
  public Class<Enum> getType() {
    return Enum.class;
  }

  @Override
  public JsonSerializer<Enum> getSerializer() {
    return RawEnumSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<Enum> getDeserializer() {
    return RawEnumDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<Enum> getKeySerializer() {
    return RawEnumSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return RawEnumKeyDeserializer.INSTANCE;
  }
}