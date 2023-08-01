////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.Base64Codec;

import com.fasterxml.jackson.core.JsonGenerator;

import javax.annotation.concurrent.Immutable;

/**
 * The JSON serializer of a {@code byte[]} object, which serialize the
 * {@code byte[]} to the string encoded in the BASE-64 format.
 *
 * @author Haixing Hu
 */
@Immutable
public class Base64ByteArraySerializer extends EncoderSerializer<byte[]> {

  private static final long serialVersionUID = 3804753080990851417L;

  public static final Base64ByteArraySerializer INSTANCE = new Base64ByteArraySerializer();

  public Base64ByteArraySerializer() {
    super(byte[].class, Base64Codec.INSTANCE, JsonGenerator::writeString);
  }
}
