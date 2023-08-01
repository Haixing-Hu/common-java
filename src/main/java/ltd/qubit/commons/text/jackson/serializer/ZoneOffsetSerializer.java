////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.ZoneOffsetCodec;

import com.fasterxml.jackson.core.JsonGenerator;

import java.time.ZoneOffset;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ZoneOffsetSerializer extends EncoderSerializer<ZoneOffset> {

  private static final long serialVersionUID = -171168624211059720L;

  public static final ZoneOffsetSerializer INSTANCE =
      new ZoneOffsetSerializer();

  public ZoneOffsetSerializer() {
    super(ZoneOffset.class, ZoneOffsetCodec.INSTANCE, JsonGenerator::writeString);
  }
}
