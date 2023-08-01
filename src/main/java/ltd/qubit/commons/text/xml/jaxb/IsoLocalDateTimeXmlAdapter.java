////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

import java.time.LocalDateTime;

/**
 * The customized JAXB data type adaptor for the {@link LocalDateTime} type.
 *
 * <p>This adaptor will print the {@link LocalDateTime} type in the ISO-8601
 * format, i.e., "1970-01-01 12:33:12".
 *
 * @author Haixing Hu
 */
public class IsoLocalDateTimeXmlAdapter extends LocalDateTimeXmlAdapter {

  public IsoLocalDateTimeXmlAdapter() {
    super(new IsoLocalDateTimeCodec());
  }
}
