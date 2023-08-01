////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.LongCodec;

/**
 * The JSON deserializer which decodes {@link Long} values from strings.
 *
 * @author Haixing Hu
 */
@Immutable
public class LongAsStringDeserializer extends DecoderDeserializer<Long> {

  private static final long serialVersionUID = -5333449846330022646L;

  public static final LongAsStringDeserializer INSTANCE =
      new LongAsStringDeserializer();

  public LongAsStringDeserializer() {
    super(Long.class, new LongCodec());
  }

  public LongAsStringDeserializer(final Decoder<String, Long> decoder) {
    super(Long.class, decoder);
  }
}
