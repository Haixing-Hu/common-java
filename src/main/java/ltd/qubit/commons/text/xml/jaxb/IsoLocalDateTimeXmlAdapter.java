////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.time.LocalDateTime;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

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