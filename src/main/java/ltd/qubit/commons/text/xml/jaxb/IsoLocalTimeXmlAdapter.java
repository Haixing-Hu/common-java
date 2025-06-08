////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.time.LocalTime;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

/**
 * The customized JAXB data type adaptor for the {@link LocalTime} type.
 *
 * <p>This adaptor will print the {@link LocalTime} type in the ISO-8601 format,
 * i.e., "12:32:43".
 *
 * @author Haixing Hu
 */
public class IsoLocalTimeXmlAdapter extends LocalTimeXmlAdapter {

  public IsoLocalTimeXmlAdapter() {
    super(new IsoLocalTimeCodec());
  }
}