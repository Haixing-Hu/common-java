////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * The codec which encode/decode {@link TimestampCodec} objects to/from strings.
 *
 * @author Haixing Hu
 */
public class TimestampCodec implements Codec<Timestamp, String> {

  public static final TimestampCodec INSTANCE = new TimestampCodec();

  private final IsoInstantCodec delegate;

  public TimestampCodec() {
    delegate = new IsoInstantCodec();
  }

  @Override
  public Timestamp decode(final String source) throws DecodingException {
    final Instant instant = delegate.decode(source);
    if (instant == null) {
      return null;
    }
    return Timestamp.from(instant);
  }

  @Override
  public String encode(final Timestamp source) throws EncodingException {
    if (source == null) {
      return null;
    }
    return delegate.encode(source.toInstant());
  }
}