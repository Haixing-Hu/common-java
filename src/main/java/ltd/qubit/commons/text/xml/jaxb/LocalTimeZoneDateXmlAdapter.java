////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import ltd.qubit.commons.util.codec.DateCodec;

import java.time.ZoneId;
import java.util.Date;

/**
 * The customized JAXB data type adaptor for the {@link Date} type with local
 * time zone.
 *
 * <p>This adaptor will print the {@link Date} type in the xsd:dateTime format
 * using the local time zone, i.e., the epoch ({@code new Date(0)}) will be
 * printed in the form of "1970-01-01 00:00:00".
 *
 * @author Haixing Hu
 */
public class LocalTimeZoneDateXmlAdapter extends DateXmlAdapter {

  public LocalTimeZoneDateXmlAdapter() {
    super(new DateCodec("yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd[ HH:mm[:ss]]", ZoneId.systemDefault(), false));
  }
}
