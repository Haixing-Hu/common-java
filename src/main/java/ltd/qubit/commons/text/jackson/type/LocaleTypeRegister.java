////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.type;


import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.PosixLocaleDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.PosixLocaleKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.PosixLocaleSerializer;

@Immutable
public class LocaleTypeRegister implements TypeRegister<Locale> {

  @Override
  public Class<Locale> getType() {
    return Locale.class;
  }

  @Override
  public JsonSerializer<Locale> getSerializer() {
    return PosixLocaleSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<Locale> getDeserializer() {
    return PosixLocaleDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<Locale> getKeySerializer() {
    return PosixLocaleSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return PosixLocaleKeyDeserializer.INSTANCE;
  }
}
