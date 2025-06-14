////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.Year;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.YearCodec;

/**
 * The JACKSON serializer of a {@link Year} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class YearSerializer extends EncoderSerializer<Year> {

  private static final long serialVersionUID = 1725253327137411054L;

  public static final YearSerializer INSTANCE = new YearSerializer();

  public YearSerializer() {
    this(YearCodec.INSTANCE);
  }

  public YearSerializer(final Encoder<Year, String> encoder) {
    super(Year.class, encoder, JsonGenerator::writeString);
  }
}