////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.time.Period;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.PeriodCodec;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The customized JAXB data type adaptor for the {@link Period} type.
 *
 * @author Haixing Hu
 */
public class PeriodXmlAdapter extends XmlAdapter<String, Period> {

  private final Codec<Period, String> codec;

  public PeriodXmlAdapter() {
    this(new PeriodCodec());
  }

  public PeriodXmlAdapter(final Codec<Period, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public Period unmarshal(final String v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.decode(v);
    }
  }

  @Override
  public String marshal(final Period v) throws Exception {
    if (v == null) {
      return null;
    } else {
      return codec.encode(v);
    }
  }
}
