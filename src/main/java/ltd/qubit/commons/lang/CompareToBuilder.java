////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import javax.annotation.Nullable;

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;

/**
 * A builder used to build the {@link Comparable#compareTo(Object)} functions.
 *
 * <p>This class provides methods to build a good {@code compareTo} method for any
 * class. It follows rules laid out in "Effective Java", by Joshua Bloch.
 *
 * <p>The implementation of this class is similar to the implementation of
 * {@code CompareToBuilder} in the Apache commons-lang, but this one is extended
 * to support more commonly used objects.
 *
 * @author Haixing
 */
public final class CompareToBuilder {

  private int result = 0;

  public CompareToBuilder append(final boolean lhs, final boolean rhs) {
    if (result == 0) {
      result = Boolean.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final boolean[] lhs, @Nullable final boolean[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Boolean lhs, @Nullable final Boolean rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Boolean[] lhs,
      @Nullable final Boolean[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(final char lhs, final char rhs) {
    if (result == 0) {
      result = Character.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final char[] lhs, @Nullable final char[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Character lhs, @Nullable final Character rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Character[] lhs,
      @Nullable final Character[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(final byte lhs, final byte rhs) {
    if (result == 0) {
      result = Byte.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final byte[] lhs, @Nullable final byte[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Byte lhs, @Nullable final Byte rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Byte[] lhs, @Nullable final Byte[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(final short lhs, final short rhs) {
    if (result == 0) {
      result = Short.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final short[] lhs, @Nullable final short[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Short lhs, @Nullable final Short rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Short[] lhs, @Nullable final Short[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(final int lhs, final int rhs) {
    if (result == 0) {
      result = Integer.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final int[] lhs, @Nullable final int[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Integer lhs, @Nullable final Integer rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Integer[] lhs,
      @Nullable final Integer[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(final long lhs, final long rhs) {
    if (result == 0) {
      result = Long.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final long[] lhs, @Nullable final long[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Long lhs, @Nullable final Long rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Long[] lhs, @Nullable final Long[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(final float lhs, final float rhs) {
    if (result == 0) {
      result = Float.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final float[] lhs, @Nullable final float[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Float lhs, @Nullable final Float rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Float[] lhs, @Nullable final Float[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(final double lhs, final double rhs) {
    if (result == 0) {
      result = Double.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final double[] lhs, @Nullable final double[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Double lhs, @Nullable final Double rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Double[] lhs, @Nullable final Double[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public <E extends Enum<E>> CompareToBuilder append(@Nullable final E lhs,
      @Nullable final E rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public <E extends Enum<E>> CompareToBuilder append(@Nullable final E[] lhs,
      @Nullable final E[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final String lhs, @Nullable final String rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final String[] lhs,
      @Nullable final String[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Class<?> lhs, @Nullable final Class<?> rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(final Class<?>[] lhs, final Class<?>[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Type lhs, @Nullable final Type rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(final Type[] lhs, final Type[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final BigInteger lhs,
      @Nullable final BigInteger rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final BigInteger[] lhs,
      @Nullable final BigInteger[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final BigDecimal lhs,
      @Nullable final BigDecimal rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final BigDecimal[] lhs,
      @Nullable final BigDecimal[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Date lhs, @Nullable final Date rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Date[] lhs, @Nullable final Date[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Instant lhs, @Nullable final Instant rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Instant[] lhs,
      @Nullable final Instant[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final LocalTime lhs,
      @Nullable final LocalTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final LocalTime[] lhs,
      @Nullable final LocalTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final LocalDate lhs,
      @Nullable final LocalDate rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final LocalDate[] lhs,
      @Nullable final LocalDate[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final LocalDateTime lhs,
      @Nullable final LocalDateTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final LocalDateTime[] lhs,
      @Nullable final LocalDateTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final ZonedDateTime lhs,
      @Nullable final ZonedDateTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final ZonedDateTime[] lhs,
      @Nullable final ZonedDateTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final OffsetDateTime lhs,
      @Nullable final OffsetDateTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final OffsetDateTime[] lhs,
      @Nullable final OffsetDateTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final OffsetTime lhs,
      @Nullable final OffsetTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final OffsetTime[] lhs,
      @Nullable final OffsetTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final BooleanCollection lhs,
      @Nullable final BooleanCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final CharCollection lhs,
      @Nullable final CharCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final ByteCollection lhs,
      @Nullable final ByteCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final ShortCollection lhs,
      @Nullable final ShortCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final IntCollection lhs,
      @Nullable final IntCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final LongCollection lhs,
      @Nullable final LongCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final FloatCollection lhs,
      @Nullable final FloatCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final DoubleCollection lhs,
      @Nullable final DoubleCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public <T> CompareToBuilder append(@Nullable final Collection<T> lhs,
      @Nullable final Collection<T> rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public <T> CompareToBuilder append(@Nullable final Collection<T> lhs,
      @Nullable final Collection<T> rhs, @Nullable final Comparator<T> comparator) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs, comparator);
    }
    return this;
  }

  public CompareToBuilder append(@Nullable final Object lhs, @Nullable final Object rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public <T> CompareToBuilder append(@Nullable final T lhs, @Nullable final T rhs,
      @Nullable final Comparator<T> comparator) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs, comparator);
    }
    return this;
  }

  public <T> CompareToBuilder append(@Nullable final T[] lhs, @Nullable final T[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  public <T> CompareToBuilder append(@Nullable final T[] lhs, @Nullable final T[] rhs,
      @Nullable final Comparator<T> comparator) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs, comparator);
    }
    return this;
  }

  public CompareToBuilder appendSuper(final int superResult) {
    if (result == 0) {
      result = superResult;
    }
    return this;
  }

  public int compare() {
    return result;
  }
}
