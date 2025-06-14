////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.PosixLocaleCodec;

@Immutable
public class PosixLocaleDeserializer extends DecoderDeserializer<Locale> {

  private static final long serialVersionUID = 5696200805930749178L;

  public static final PosixLocaleDeserializer INSTANCE =
      new PosixLocaleDeserializer();

  public PosixLocaleDeserializer() {
    super(Locale.class, PosixLocaleCodec.INSTANCE);
  }
}