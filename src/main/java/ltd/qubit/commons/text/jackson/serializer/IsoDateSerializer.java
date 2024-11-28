////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoDateCodec;

/**
 * The JACKSON serializer of a {@link Date} object, in the ISO-8601
 * format of "yyyy-MM-dd'T'HH:mm:ss'Z'".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoDateSerializer extends DateSerializer {

  private static final long serialVersionUID = 4166452457184587852L;

  public static final IsoDateSerializer INSTANCE = new IsoDateSerializer();

  public IsoDateSerializer() {
    super(new IsoDateCodec());
  }
}
