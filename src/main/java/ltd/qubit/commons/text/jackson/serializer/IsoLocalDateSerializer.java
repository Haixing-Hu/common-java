////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

/**
 * The JACKSON serializer of a {@link LocalDate} object, in the ISO-8601
 * format of "yyyy-mm-dd".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalDateSerializer extends LocalDateSerializer {

  private static final long serialVersionUID = 7554711342122229142L;

  public static final IsoLocalDateSerializer INSTANCE =
      new IsoLocalDateSerializer();

  public IsoLocalDateSerializer() {
    super(new IsoLocalDateCodec());
  }
}
