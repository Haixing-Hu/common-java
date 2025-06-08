////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.PosixLocaleCodec;

@Immutable
public class PosixLocaleSerializer extends EncoderSerializer<Locale> {

  private static final long serialVersionUID = 8709094403572085333L;

  public static final PosixLocaleSerializer INSTANCE = new PosixLocaleSerializer();

  public PosixLocaleSerializer() {
    super(Locale.class, PosixLocaleCodec.INSTANCE, JsonGenerator::writeString);
  }
}