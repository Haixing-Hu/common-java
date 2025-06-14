////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.LongCodec;

/**
 * The JACKSON serializer which encode {@link Long} values to strings.
 *
 * @author Haixing Hu
 */
public class LongAsStringSerializer extends EncoderSerializer<Long> {

  private static final long serialVersionUID = 7392297823492881718L;

  public LongAsStringSerializer() {
    this(LongCodec.INSTANCE);
  }

  public LongAsStringSerializer(final Encoder<Long, String> encoder) {
    super(Long.class, encoder, JsonGenerator::writeString);
  }
}