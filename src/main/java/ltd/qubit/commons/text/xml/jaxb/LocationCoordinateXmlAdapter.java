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

import javax.annotation.concurrent.Immutable;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.util.codec.LocationCoordinateCodec;

/**
 * The XML Adapter for geographical location coordinates.
 *
 * @author Haixing Hu
 */
@Immutable
public class LocationCoordinateXmlAdapter extends XmlAdapter<String, BigDecimal> {

  @Override
  public BigDecimal unmarshal(final String value) throws Exception {
    final LocationCoordinateCodec codec = LocationCoordinateCodec.INSTANCE;
    return (value == null ? null : codec.decode(value));
  }

  @Override
  public String marshal(final BigDecimal value) throws Exception {
    final LocationCoordinateCodec codec = LocationCoordinateCodec.INSTANCE;
    return (value == null ? null : codec.encode(value));
  }
}
