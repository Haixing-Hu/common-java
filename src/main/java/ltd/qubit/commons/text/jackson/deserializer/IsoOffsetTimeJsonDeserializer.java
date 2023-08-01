////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoOffsetTimeCodec;

@Immutable
public class IsoOffsetTimeJsonDeserializer extends DecoderDeserializer<OffsetTime> {

  private static final long serialVersionUID = -2608618436291577502L;

  public static final IsoOffsetTimeJsonDeserializer INSTANCE =
      new IsoOffsetTimeJsonDeserializer();

  public IsoOffsetTimeJsonDeserializer() {
    super(OffsetTime.class, IsoOffsetTimeCodec.INSTANCE);
  }
}
