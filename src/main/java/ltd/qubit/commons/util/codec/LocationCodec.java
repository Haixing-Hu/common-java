////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.model.Location;

/**
 * The encoder and decoder of geolocation locations.
 * <p>
 * This class provides functions for converting between {@link Location} objects
 * and strings.
 * <p>
 * A {@link Location} object will be represented by a string of the following form:
 * <pre><code>
 *   &lt;longitude&gt; ',' &lt;latitude&gt;
 * </code></pre>
 * <p>
 * For example: "116.482086,39.990496"
 *
 * @author Haixing Hu
 */
@Immutable
public class LocationCodec implements Codec<Location, String> {

  /**
   * The separator between latitude and longitude coordinates.
   */
  public static final String SPLITTER = ",";

  @Override
  public Location decode(final String source) throws DecodingException {
    if (source == null || source.length() == 0) {
      return null;
    }
    final String[] values = source.split(SPLITTER);
    if (values.length != 2) {
      throw new DecodingException("Invalid location format: " + source);
    }
    try {
      return new Location(values[0], values[1]);
    } catch (final DecodingException e) {
      throw new DecodingException("Invalid location format: " + source);
    }
  }

  @Override
  public String encode(final Location source) {
    if (source == null) {
      return null;
    }
    final LocationCoordinateCodec codec = LocationCoordinateCodec.INSTANCE;
    return codec.encode(source.getLongitude())
        + SPLITTER
        + codec.encode(source.getLatitude());
  }
}
