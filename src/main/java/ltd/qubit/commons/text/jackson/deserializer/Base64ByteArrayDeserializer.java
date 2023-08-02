////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Base64Codec;

/**
 * The JSON deserializer of a {@code byte[]} object, which deserialize the
 * {@code byte[]} from the string encoded in the BASE-64 format.
 *
 * @author Haixing Hu
 */
@Immutable
public class Base64ByteArrayDeserializer extends DecoderDeserializer<byte[]> {

  private static final long serialVersionUID = 4060958725438976043L;

  public static final Base64ByteArrayDeserializer INSTANCE =
      new Base64ByteArrayDeserializer();

  public Base64ByteArrayDeserializer() {
    super(byte[].class, Base64Codec.INSTANCE);
  }
}
