////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.RawEnumCodec;

/**
 * The JACKSON serializer of the enumeration class.
 *
 * @author Haixing Hu
 */
@SuppressWarnings("rawtypes")
@Immutable
public class RawEnumSerializer extends EncoderSerializer<Enum> {

  @Serial
  private static final long serialVersionUID = -2540120793385986043L;

  public static final RawEnumSerializer INSTANCE = new RawEnumSerializer();

  public RawEnumSerializer() {
    // make the RawEnumCodec support @JsonValue annotation
    super(Enum.class, new RawEnumCodec(true), JsonGenerator::writeString);
  }
}