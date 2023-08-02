////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.RawEnumCodec;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * The JSON serializer of the enumeration class.
 *
 * @author Haixing Hu
 */
@SuppressWarnings("rawtypes")
@Immutable
public class RawEnumSerializer extends EncoderSerializer<Enum> {

  private static final long serialVersionUID = -2540120793385986043L;

  public static final RawEnumSerializer INSTANCE = new RawEnumSerializer();

  public RawEnumSerializer() {
    super(Enum.class, new RawEnumCodec(), JsonGenerator::writeString);
  }
}
