////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoDateCodec;

/**
 * The JACKSON key deserializer of a {@link Date} object, in the ISO-8601
 * format of "yyyy-MM-dd'T'HH:mm:ss'Z'".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoDateKeyDeserializer extends DateKeyDeserializer {

  private static final long serialVersionUID = 5491058965489717729L;

  public static final IsoDateKeyDeserializer INSTANCE = new IsoDateKeyDeserializer();

  public IsoDateKeyDeserializer() {
    super(new IsoDateCodec());
  }
}