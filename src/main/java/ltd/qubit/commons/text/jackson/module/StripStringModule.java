/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.module.SimpleModule;

import ltd.qubit.commons.text.jackson.deserializer.StripStringDeserializer;
import ltd.qubit.commons.text.jackson.keydeserializer.StripStringKeyDeserializer;
import ltd.qubit.commons.text.jackson.serializer.StripStringSerializer;

@Immutable
public class StripStringModule extends SimpleModule {

  public static final StripStringModule INSTANCE = new StripStringModule();

  public StripStringModule() {
    this.addSerializer(String.class, StripStringSerializer.INSTANCE);
    this.addDeserializer(String.class, StripStringDeserializer.INSTANCE);
    this.addKeySerializer(String.class, StripStringSerializer.INSTANCE);
    this.addKeyDeserializer(String.class, StripStringKeyDeserializer.INSTANCE);
  }

  @Override
  public String getModuleName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public void setupModule(final SetupContext context) {
    super.setupModule(context);
  }
}
