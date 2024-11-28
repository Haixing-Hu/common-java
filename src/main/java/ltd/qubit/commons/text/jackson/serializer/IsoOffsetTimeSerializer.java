////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.IsoOffsetTimeCodec;

/**
 * The JACKSON serializer of a {@link OffsetTime} object, in the ISO-8601
 * format.
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoOffsetTimeSerializer extends EncoderSerializer<OffsetTime> {

  private static final long serialVersionUID = -8607540977638353617L;

  public static final IsoOffsetTimeSerializer INSTANCE = new IsoOffsetTimeSerializer();

  public IsoOffsetTimeSerializer() {
    super(OffsetTime.class, IsoOffsetTimeCodec.INSTANCE, JsonGenerator::writeString);
  }
}
