////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.PosixLocaleCodec;

import com.fasterxml.jackson.core.JsonGenerator;

import java.util.Locale;
import javax.annotation.concurrent.Immutable;

@Immutable
public class PosixLocaleSerializer extends EncoderSerializer<Locale> {

  private static final long serialVersionUID = 8709094403572085333L;

  public static final PosixLocaleSerializer INSTANCE = new PosixLocaleSerializer();

  public PosixLocaleSerializer() {
    super(Locale.class, PosixLocaleCodec.INSTANCE, JsonGenerator::writeString);
  }
}
