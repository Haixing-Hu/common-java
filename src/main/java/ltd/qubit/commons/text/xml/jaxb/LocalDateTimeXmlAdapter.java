////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.LocalDateTimeCodec;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The customized JAXB data type adaptor for the {@link LocalDateTime} type.
 *
 * @author Haixing Hu
 */
public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

  private final Codec<LocalDateTime, String> codec;

  public LocalDateTimeXmlAdapter() {
    this(new LocalDateTimeCodec());
  }

  public LocalDateTimeXmlAdapter(final Codec<LocalDateTime, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public LocalDateTime unmarshal(final String v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.decode(v);
    }
  }

  @Override
  public String marshal(final LocalDateTime v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.encode(v);
    }
  }
}
