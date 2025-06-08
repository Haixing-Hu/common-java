////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import java.time.ZoneOffset;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.jackson.deserializer.ZoneOffsetDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.ZoneOffsetKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.ZoneOffsetSerializer;

@Immutable
public class ZoneOffsetTypeRegister implements TypeRegister<ZoneOffset> {

  @Override
  public Class<ZoneOffset> getType() {
    return ZoneOffset.class;
  }

  @Override
  public JsonSerializer<ZoneOffset> getSerializer() {
    return ZoneOffsetSerializer.INSTANCE;
  }

  @Override
  public JsonDeserializer<ZoneOffset> getDeserializer() {
    return ZoneOffsetDeserializer.INSTANCE;
  }

  @Override
  public JsonSerializer<ZoneOffset> getKeySerializer() {
    return ZoneOffsetSerializer.INSTANCE;
  }

  @Override
  public KeyDeserializer getKeyDeserializer() {
    return ZoneOffsetKeyDeserializer.INSTANCE;
  }
}