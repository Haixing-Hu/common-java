////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.RawEnumCodec;

import com.fasterxml.jackson.core.JsonGenerator;

import javax.annotation.concurrent.Immutable;

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
