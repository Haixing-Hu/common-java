////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoDateCodec;

/**
 * The JACKSON deserializer of a {@link Date} object, in the ISO-8601
 * format of "yyyy-MM-dd'T'HH:mm:ss'Z'".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoDateDeserializer extends DateDeserializer {

  private static final long serialVersionUID = 8254014955826356806L;

  public static final IsoDateDeserializer INSTANCE = new IsoDateDeserializer();

  public IsoDateDeserializer() {
    super(new IsoDateCodec());
  }
}
