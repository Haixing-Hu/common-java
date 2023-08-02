////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.ZoneId;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.ZoneIdCodec;

import com.fasterxml.jackson.core.JsonGenerator;

@Immutable
public class ZoneIdSerializer extends EncoderSerializer<ZoneId> {

  private static final long serialVersionUID = 6231057212284169151L;

  public static final ZoneIdSerializer INSTANCE = new ZoneIdSerializer();

  public ZoneIdSerializer() {
    super(ZoneId.class, ZoneIdCodec.INSTANCE, JsonGenerator::writeString);
  }
}
