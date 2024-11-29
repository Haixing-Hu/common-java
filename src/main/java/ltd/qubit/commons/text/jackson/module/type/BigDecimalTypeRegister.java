////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.BigDecimalDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.BigDecimalKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.BigDecimalSerializer;

@Immutable
public class BigDecimalTypeRegister implements TypeRegister<BigDecimal> {

  @Override
  public Class<BigDecimal> getType() {
    return BigDecimal.class;
  }

  @Override
  public JsonSerializer<BigDecimal> getSerializer() {
    return BigDecimalSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<BigDecimal> getDeserializer() {
    return BigDecimalDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<BigDecimal> getKeySerializer() {
    return BigDecimalSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return BigDecimalKeyDeserializer.INSTANCE;
  }
}
