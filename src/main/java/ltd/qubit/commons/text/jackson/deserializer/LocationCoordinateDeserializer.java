////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.model.Location;
import ltd.qubit.commons.util.codec.LocationCodec;
import ltd.qubit.commons.util.codec.LocationCoordinateCodec;

/**
 * JSON deserializer for geolocation coordinates.
 *
 * @author Haixing Hu
 * @see Location
 * @see LocationCoordinateCodec
 * @see LocationCodec
 */
@Immutable
public class LocationCoordinateDeserializer extends BigDecimalDeserializer {

  @Serial
  private static final long serialVersionUID = 7166442348959619965L;

  public LocationCoordinateDeserializer() {
    super(new LocationCoordinateCodec());
  }
}
