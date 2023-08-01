////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import ltd.qubit.commons.util.codec.BigDecimalCodec;
import ltd.qubit.commons.util.codec.Codec;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.math.BigDecimal;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The customized JAXB data type adaptor for the {@link BigDecimal} type.
 *
 * @author Haixing Hu
 */
public class BigDecimalXmlAdapter extends XmlAdapter<String, BigDecimal> {

  private final Codec<BigDecimal, String> codec;

  public BigDecimalXmlAdapter() {
    this(new BigDecimalCodec());
  }

  public BigDecimalXmlAdapter(final Codec<BigDecimal, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public BigDecimal unmarshal(final String v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.decode(v);
    }
  }

  @Override
  public String marshal(final BigDecimal v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.encode(v);
    }
  }
}
