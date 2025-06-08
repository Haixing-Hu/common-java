////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Base64Codec;

/**
 * The JACKSON key deserializer of a {@code byte[]} object, which deserialize
 * the {@code byte[]} from the string encoded in the BASE-64 format.
 *
 * @author Haixing Hu
 */
@Immutable
public class Base64ByteArrayKeyDeserializer extends DecoderKeyDeserializer<byte[]> {

  private static final long serialVersionUID = -7037446321933808102L;

  public static final Base64ByteArrayKeyDeserializer INSTANCE =
      new Base64ByteArrayKeyDeserializer();

  protected Base64ByteArrayKeyDeserializer() {
    super(byte[].class, Base64Codec.INSTANCE);
  }
}