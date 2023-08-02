////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.util.Date;

import ltd.qubit.commons.util.codec.IsoDateCodec;

/**
 * The customized JAXB data type adaptor for the {@link Date} type.
 *
 * <p>This adaptor will print the {@link Date} type in the xsd:dateTime format
 * using the UTC time zone, i.e., the epoch ({@code new Date(0)}) will be
 * printed in the form of "1970-01-01T00:00:00Z".
 *
 * @author Haixing Hu
 */
public class IsoDateXmlAdapter extends DateXmlAdapter {

  public IsoDateXmlAdapter() {
    super(new IsoDateCodec());
  }
}
