////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.nio.charset.Charset;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.util.codec.CharsetCodec;
import ltd.qubit.commons.util.codec.Codec;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The customized JAXB data type adaptor for the {@link Charset} type.
 *
 * @author Haixing Hu
 */
public class CharsetXmlAdapter extends XmlAdapter<String, Charset> {

  private final Codec<Charset, String> codec;

  public CharsetXmlAdapter() {
    this(new CharsetCodec());
  }

  public CharsetXmlAdapter(final Codec<Charset, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public Charset unmarshal(final String v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.decode(v);
    }
  }

  @Override
  public String marshal(final Charset v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.encode(v);
    }
  }
}