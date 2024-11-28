////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;


import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.PosixLocaleCodec;

/**
 * The JACKSON key deserializer of a {@link Locale} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class PosixLocaleKeyDeserializer extends DecoderKeyDeserializer<Locale> {

  private static final long serialVersionUID = -4310833323826743748L;

  public static final PosixLocaleKeyDeserializer INSTANCE = new PosixLocaleKeyDeserializer();

  public PosixLocaleKeyDeserializer() {
    super(Locale.class, PosixLocaleCodec.INSTANCE);
  }
}
