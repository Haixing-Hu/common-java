////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.Year;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.YearCodec;

/**
 * The JACKSON key deserializer of a {@link Year} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class YearKeyDeserializer extends DecoderKeyDeserializer<Year> {

  private static final long serialVersionUID = -3081548615222217255L;

  public static final YearKeyDeserializer INSTANCE = new YearKeyDeserializer();

  public YearKeyDeserializer() {
    super(Year.class, YearCodec.INSTANCE);
  }
}