////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.IsoOffsetTimeCodec;

import com.fasterxml.jackson.core.JsonGenerator;

import java.time.OffsetTime;
import javax.annotation.concurrent.Immutable;

@Immutable
public class IsoOffsetTimeJsonSerializer extends EncoderSerializer<OffsetTime> {

  private static final long serialVersionUID = -8607540977638353617L;

  public static final IsoOffsetTimeJsonSerializer INSTANCE =
      new IsoOffsetTimeJsonSerializer();

  public IsoOffsetTimeJsonSerializer() {
    super(OffsetTime.class, IsoOffsetTimeCodec.INSTANCE, JsonGenerator::writeString);
  }
}
