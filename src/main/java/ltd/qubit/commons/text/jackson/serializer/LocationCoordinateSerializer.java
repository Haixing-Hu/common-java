////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.model.Location;
import ltd.qubit.commons.util.codec.LocationCodec;
import ltd.qubit.commons.util.codec.LocationCoordinateCodec;

/**
 * JSON serializer for geolocation coordinates.
 *
 * @author Haixing Hu
 * @see Location
 * @see LocationCoordinateCodec
 * @see LocationCodec
 */
@Immutable
public class LocationCoordinateSerializer extends BigDecimalSerializer {

  @Serial
  private static final long serialVersionUID = 2115578678289300490L;

  public LocationCoordinateSerializer() {
    super(new LocationCoordinateCodec());
  }
}
