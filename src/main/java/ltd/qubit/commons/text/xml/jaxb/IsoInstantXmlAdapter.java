////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.time.Instant;

import ltd.qubit.commons.util.codec.IsoInstantCodec;

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
