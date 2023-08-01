////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

import java.time.LocalDate;

/**
 * The customized JAXB data type adaptor for the {@link LocalDate} type.
 *
 * <p>This adaptor will print the {@link LocalDate} type in the ISO-8601 format,
 * i.e., "1970-01-01".
 *
 * @author Haixing Hu
 */
public class IsoLocalDateXmlAdapter extends LocalDateXmlAdapter {

  public IsoLocalDateXmlAdapter() {
    super(new IsoLocalDateCodec());
  }
}
