////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.ZoneId;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.ZoneIdCodec;

@Immutable
public class ZoneIdDeserializer extends DecoderDeserializer<ZoneId> {

  private static final long serialVersionUID = -2316865805523121328L;

  public static final ZoneIdDeserializer INSTANCE = new ZoneIdDeserializer();

  public ZoneIdDeserializer() {
    super(ZoneId.class, ZoneIdCodec.INSTANCE);
  }
}
