////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.time.LocalTime;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.LocalTimeCodec;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The customized JAXB data type adaptor for the {@link LocalTime} type.
 *
 * @author Haixing Hu
 */
public class LocalTimeXmlAdapter extends XmlAdapter<String, LocalTime> {

  private final Codec<LocalTime, String> codec;

  public LocalTimeXmlAdapter() {
    this(new LocalTimeCodec());
  }

  public LocalTimeXmlAdapter(final Codec<LocalTime, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public LocalTime unmarshal(final String v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.decode(v);
    }
  }

  @Override
  public String marshal(final LocalTime v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.encode(v);
    }
  }
}
