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

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.Instant;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The customized JAXB data type adaptor for the {@link Instant} type.
 *
 * @author Haixing Hu
 */
public class InstantXmlAdapter extends XmlAdapter<String, Instant> {

  private final Codec<Instant, String> codec;

  public InstantXmlAdapter(final Codec<Instant, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public Instant unmarshal(final String v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.decode(v);
    }
  }

  @Override
  public String marshal(final Instant v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.encode(v);
    }
  }
}
