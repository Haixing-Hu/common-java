////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.ZoneOffset;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.ZoneOffsetCodec;

@Immutable
public class ZoneOffsetSerializer extends EncoderSerializer<ZoneOffset> {

  private static final long serialVersionUID = -171168624211059720L;

  public static final ZoneOffsetSerializer INSTANCE =
      new ZoneOffsetSerializer();

  public ZoneOffsetSerializer() {
    super(ZoneOffset.class, ZoneOffsetCodec.INSTANCE, JsonGenerator::writeString);
  }
}
