////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.ZoneOffsetCodec;

import java.time.ZoneOffset;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ZoneOffsetDeserializer extends DecoderDeserializer<ZoneOffset> {

  private static final long serialVersionUID = 1108390597460523182L;

  public static final ZoneOffsetDeserializer INSTANCE =
      new ZoneOffsetDeserializer();

  public ZoneOffsetDeserializer() {
    super(ZoneOffset.class, ZoneOffsetCodec.INSTANCE);
  }
}
