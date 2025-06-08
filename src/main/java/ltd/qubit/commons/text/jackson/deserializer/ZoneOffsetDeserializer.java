////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.ZoneOffset;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.ZoneOffsetCodec;

@Immutable
public class ZoneOffsetDeserializer extends DecoderDeserializer<ZoneOffset> {

  private static final long serialVersionUID = 1108390597460523182L;

  public static final ZoneOffsetDeserializer INSTANCE =
      new ZoneOffsetDeserializer();

  public ZoneOffsetDeserializer() {
    super(ZoneOffset.class, ZoneOffsetCodec.INSTANCE);
  }
}