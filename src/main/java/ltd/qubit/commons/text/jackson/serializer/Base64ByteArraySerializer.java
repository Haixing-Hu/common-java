////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Base64Codec;

/**
 * The JACKSON serializer of a {@code byte[]} object, which serialize the
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