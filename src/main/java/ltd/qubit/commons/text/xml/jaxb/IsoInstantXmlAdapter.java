////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import ltd.qubit.commons.util.codec.IsoInstantCodec;

import java.time.Instant;

/**
 * The customized JAXB data type adaptor for the {@link Instant} type.
 *
 * <p>This adaptor will print the {@link Instant} type in the ISO-8601 format,
 * i.e., "1970-01-01T12:33:12.123Z".
 *
 * @author Haixing Hu
 */
public class IsoInstantXmlAdapter extends InstantXmlAdapter {

  public IsoInstantXmlAdapter() {
    super(new IsoInstantCodec());
  }
}
