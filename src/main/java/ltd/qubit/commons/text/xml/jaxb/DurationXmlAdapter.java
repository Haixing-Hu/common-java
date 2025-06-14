////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.time.Duration;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.DurationCodec;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The customized JAXB data type adaptor for the {@link Duration} type.
 *
 * @author Haixing Hu
 */
public class DurationXmlAdapter extends XmlAdapter<String, Duration> {

  private final Codec<Duration, String> codec;

  public DurationXmlAdapter() {
    this(new DurationCodec());
  }

  public DurationXmlAdapter(final Codec<Duration, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public Duration unmarshal(final String v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.decode(v);
    }
  }

  @Override
  public String marshal(final Duration v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.encode(v);
    }
  }
}