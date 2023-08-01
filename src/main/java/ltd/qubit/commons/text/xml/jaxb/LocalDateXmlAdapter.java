////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.LocalDateCodec;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The customized JAXB data type adaptor for the {@link LocalDate} type.
 *
 * @author Haixing Hu
 */
public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {

  private final Codec<LocalDate, String> codec;

  public LocalDateXmlAdapter() {
    this(new LocalDateCodec());
  }

  public LocalDateXmlAdapter(final Codec<LocalDate, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public LocalDate unmarshal(final String v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.decode(v);
    }
  }

  @Override
  public String marshal(final LocalDate v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.encode(v);
    }
  }
}
