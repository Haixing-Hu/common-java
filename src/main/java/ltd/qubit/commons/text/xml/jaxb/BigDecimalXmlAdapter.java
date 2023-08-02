////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.util.codec.BigDecimalCodec;
import ltd.qubit.commons.util.codec.Codec;

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
