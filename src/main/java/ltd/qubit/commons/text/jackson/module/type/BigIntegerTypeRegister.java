////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.BigIntegerDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.BigIntegerKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.BigIntegerSerializer;

@Immutable
public class BigIntegerTypeRegister implements TypeRegister<BigInteger> {

  @Override
  public Class<BigInteger> getType() {
    return BigInteger.class;
  }

  @Override
  public JsonSerializer<BigInteger> getSerializer() {
    return BigIntegerSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<BigInteger> getDeserializer() {
    return BigIntegerDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<BigInteger> getKeySerializer() {
    return BigIntegerSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return BigIntegerKeyDeserializer.INSTANCE;
  }
}
