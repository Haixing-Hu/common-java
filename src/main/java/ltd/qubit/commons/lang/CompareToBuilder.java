////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 用于构建 {@link Comparable#compareTo(Object)} 函数的构建器。
 *
 * <p>该类提供方法来构建任何类的良好 {@code compareTo} 方法。它遵循了
 * Joshua Bloch 在"Effective Java"中制定的规则。
 *
 * <p>该类的实现类似于 Apache commons-lang 中的 {@code CompareToBuilder} 实现，
 * 但本类扩展为支持更多常用对象。
 *
 * @author 胡海星
 */
public final class CompareToBuilder {

  private int result = 0;

  /**
   * 测试两个 {@code boolean} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code boolean} 值。
   * @param rhs
   *     右侧的 {@code boolean} 值。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final boolean lhs, final boolean rhs) {
    if (result == 0) {
      result = Boolean.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code boolean} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code boolean} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code boolean} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final boolean[] lhs, @Nullable final boolean[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Boolean} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Boolean} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Boolean} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Boolean lhs, @Nullable final Boolean rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Boolean} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Boolean} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Boolean} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Boolean[] lhs,
      @Nullable final Boolean[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code char} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code char} 值。
   * @param rhs
   *     右侧的 {@code char} 值。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final char lhs, final char rhs) {
    if (result == 0) {
      result = Character.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code char} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code char} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code char} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final char[] lhs, @Nullable final char[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Character} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Character} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Character} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Character lhs, @Nullable final Character rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Character} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Character} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Character} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Character[] lhs, @Nullable final Character[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code byte} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code byte} 值。
   * @param rhs
   *     右侧的 {@code byte} 值。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final byte lhs, final byte rhs) {
    if (result == 0) {
      result = Byte.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code byte} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code byte} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code byte} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final byte[] lhs, @Nullable final byte[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Byte} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Byte} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Byte} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Byte lhs, @Nullable final Byte rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Byte} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Byte} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Byte} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Byte[] lhs, @Nullable final Byte[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code short} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code short} 值。
   * @param rhs
   *     右侧的 {@code short} 值。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final short lhs, final short rhs) {
    if (result == 0) {
      result = Short.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code short} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code short} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code short} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final short[] lhs, @Nullable final short[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Short} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Short} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Short} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Short lhs, @Nullable final Short rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Short} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Short} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Short} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Short[] lhs, @Nullable final Short[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code int} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code int} 值。
   * @param rhs
   *     右侧的 {@code int} 值。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final int lhs, final int rhs) {
    if (result == 0) {
      result = Integer.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code int} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code int} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code int} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final int[] lhs, @Nullable final int[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Integer} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Integer} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Integer} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Integer lhs, @Nullable final Integer rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Integer} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Integer} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Integer} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Integer[] lhs,
      @Nullable final Integer[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code long} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code long} 值。
   * @param rhs
   *     右侧的 {@code long} 值。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final long lhs, final long rhs) {
    if (result == 0) {
      result = Long.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code long} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code long} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code long} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final long[] lhs, @Nullable final long[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Long} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Long} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Long} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Long lhs, @Nullable final Long rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Long} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Long} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Long} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Long[] lhs, @Nullable final Long[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code float} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code float} 值。
   * @param rhs
   *     右侧的 {@code float} 值。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final float lhs, final float rhs) {
    if (result == 0) {
      result = Float.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code float} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code float} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code float} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final float[] lhs, @Nullable final float[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Float} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Float} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Float} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Float lhs, @Nullable final Float rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Float} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Float} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Float} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Float[] lhs, @Nullable final Float[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code double} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code double} 值。
   * @param rhs
   *     右侧的 {@code double} 值。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final double lhs, final double rhs) {
    if (result == 0) {
      result = Double.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code double} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code double} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code double} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final double[] lhs, @Nullable final double[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Double} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Double} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Double} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Double lhs, @Nullable final Double rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Double} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Double} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Double} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Double[] lhs, @Nullable final Double[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个枚举值并将它们的比较结果添加到构建器中。
   *
   * @param <E>
   *     枚举类型。
   * @param lhs
   *     左侧的枚举值，可以为 {@code null}。
   * @param rhs
   *     右侧的枚举值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public <E extends Enum<E>> CompareToBuilder append(@Nullable final E lhs,
      @Nullable final E rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个枚举数组并将它们的比较结果添加到构建器中。
   *
   * @param <E>
   *     枚举类型。
   * @param lhs
   *     左侧的枚举数组，可以为 {@code null}。
   * @param rhs
   *     右侧的枚举数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public <E extends Enum<E>> CompareToBuilder append(@Nullable final E[] lhs,
      @Nullable final E[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code String} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的字符串，可以为 {@code null}。
   * @param rhs
   *     右侧的字符串，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final String lhs, @Nullable final String rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code String} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的字符串数组，可以为 {@code null}。
   * @param rhs
   *     右侧的字符串数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final String[] lhs,
      @Nullable final String[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code String} 值并将它们的比较结果添加到构建器中，忽略大小写。
   *
   * @param lhs
   *     左侧的字符串，可以为 {@code null}。
   * @param rhs
   *     右侧的字符串，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder appendIgnoreCase(@Nullable final String lhs,
      @Nullable final String rhs) {
    if (result == 0) {
      result = Comparison.compareIgnoreCase(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code String} 数组并将它们的比较结果添加到构建器中，忽略大小写。
   *
   * @param lhs
   *     左侧的字符串数组，可以为 {@code null}。
   * @param rhs
   *     右侧的字符串数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder appendIgnoreCase(@Nullable final String[] lhs,
      @Nullable final String[] rhs) {
    if (result == 0) {
      result = Comparison.compareIgnoreCase(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Class} 对象并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的类对象，可以为 {@code null}。
   * @param rhs
   *     右侧的类对象，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Class<?> lhs, @Nullable final Class<?> rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Class} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的类数组，不能为 {@code null}。
   * @param rhs
   *     右侧的类数组，不能为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final Class<?>[] lhs, final Class<?>[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Type} 对象并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的类型，可以为 {@code null}。
   * @param rhs
   *     右侧的类型，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Type lhs, @Nullable final Type rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Type} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的类型数组，不能为 {@code null}。
   * @param rhs
   *     右侧的类型数组，不能为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(final Type[] lhs, final Type[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code BigInteger} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code BigInteger} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code BigInteger} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final BigInteger lhs,
      @Nullable final BigInteger rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code BigInteger} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code BigInteger} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code BigInteger} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final BigInteger[] lhs,
      @Nullable final BigInteger[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code BigDecimal} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code BigDecimal} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code BigDecimal} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final BigDecimal lhs,
      @Nullable final BigDecimal rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code BigDecimal} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code BigDecimal} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code BigDecimal} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final BigDecimal[] lhs,
      @Nullable final BigDecimal[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Date} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的日期，可以为 {@code null}。
   * @param rhs
   *     右侧的日期，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Date lhs, @Nullable final Date rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Date} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的日期数组，可以为 {@code null}。
   * @param rhs
   *     右侧的日期数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Date[] lhs, @Nullable final Date[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Instant} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Instant} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Instant} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Instant lhs, @Nullable final Instant rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Instant} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code Instant} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code Instant} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Instant[] lhs,
      @Nullable final Instant[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code LocalTime} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code LocalTime} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code LocalTime} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final LocalTime lhs,
      @Nullable final LocalTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code LocalTime} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code LocalTime} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code LocalTime} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final LocalTime[] lhs,
      @Nullable final LocalTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code LocalDate} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code LocalDate} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code LocalDate} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final LocalDate lhs,
      @Nullable final LocalDate rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code LocalDate} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code LocalDate} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code LocalDate} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final LocalDate[] lhs,
      @Nullable final LocalDate[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code LocalDateTime} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code LocalDateTime} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code LocalDateTime} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final LocalDateTime lhs,
      @Nullable final LocalDateTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code LocalDateTime} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code LocalDateTime} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code LocalDateTime} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final LocalDateTime[] lhs,
      @Nullable final LocalDateTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code ZonedDateTime} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code ZonedDateTime} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code ZonedDateTime} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final ZonedDateTime lhs,
      @Nullable final ZonedDateTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code ZonedDateTime} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code ZonedDateTime} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code ZonedDateTime} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final ZonedDateTime[] lhs,
      @Nullable final ZonedDateTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code OffsetDateTime} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code OffsetDateTime} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code OffsetDateTime} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final OffsetDateTime lhs,
      @Nullable final OffsetDateTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code OffsetDateTime} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code OffsetDateTime} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code OffsetDateTime} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final OffsetDateTime[] lhs,
      @Nullable final OffsetDateTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code OffsetTime} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code OffsetTime} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code OffsetTime} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final OffsetTime lhs,
      @Nullable final OffsetTime rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code OffsetTime} 数组并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code OffsetTime} 数组，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code OffsetTime} 数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final OffsetTime[] lhs,
      @Nullable final OffsetTime[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code BooleanCollection} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code BooleanCollection} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code BooleanCollection} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final BooleanCollection lhs,
      @Nullable final BooleanCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code CharCollection} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code CharCollection} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code CharCollection} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final CharCollection lhs,
      @Nullable final CharCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code ByteCollection} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code ByteCollection} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code ByteCollection} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final ByteCollection lhs,
      @Nullable final ByteCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code ShortCollection} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code ShortCollection} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code ShortCollection} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final ShortCollection lhs,
      @Nullable final ShortCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code IntCollection} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code IntCollection} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code IntCollection} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final IntCollection lhs,
      @Nullable final IntCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code LongCollection} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code LongCollection} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code LongCollection} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final LongCollection lhs,
      @Nullable final LongCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code FloatCollection} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code FloatCollection} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code FloatCollection} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final FloatCollection lhs,
      @Nullable final FloatCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code DoubleCollection} 值并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的 {@code DoubleCollection} 值，可以为 {@code null}。
   * @param rhs
   *     右侧的 {@code DoubleCollection} 值，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final DoubleCollection lhs,
      @Nullable final DoubleCollection rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Collection} 值并将它们的比较结果添加到构建器中。
   *
   * @param <T>
   *     集合元素类型。
   * @param lhs
   *     左侧的集合，可以为 {@code null}。
   * @param rhs
   *     右侧的集合，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public <T> CompareToBuilder append(@Nullable final Collection<T> lhs, @Nullable final Collection<T> rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个 {@code Collection} 值并将它们的比较结果添加到构建器中。
   *
   * @param <T>
   *     集合元素类型。
   * @param lhs
   *     左侧的集合，可以为 {@code null}。
   * @param rhs
   *     右侧的集合，可以为 {@code null}。
   * @param comparator
   *     比较器，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public <T> CompareToBuilder append(@Nullable final Collection<T> lhs,
      @Nullable final Collection<T> rhs, @Nullable final Comparator<T> comparator) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs, comparator);
    }
    return this;
  }

  /**
   * 测试两个对象并将它们的比较结果添加到构建器中。
   *
   * @param lhs
   *     左侧的对象，可以为 {@code null}。
   * @param rhs
   *     右侧的对象，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public CompareToBuilder append(@Nullable final Object lhs, @Nullable final Object rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个对象并将它们的比较结果添加到构建器中。
   *
   * @param <T>
   *     对象类型。
   * @param lhs
   *     左侧的对象，可以为 {@code null}。
   * @param rhs
   *     右侧的对象，可以为 {@code null}。
   * @param comparator
   *     比较器，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public <T> CompareToBuilder append(@Nullable final T lhs, @Nullable final T rhs,
      @Nullable final Comparator<T> comparator) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs, comparator);
    }
    return this;
  }

  /**
   * 测试两个对象数组并将它们的比较结果添加到构建器中。
   *
   * @param <T>
   *     对象类型。
   * @param lhs
   *     左侧的对象数组，可以为 {@code null}。
   * @param rhs
   *     右侧的对象数组，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public <T> CompareToBuilder append(@Nullable final T[] lhs, @Nullable final T[] rhs) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs);
    }
    return this;
  }

  /**
   * 测试两个对象数组并将它们的比较结果添加到构建器中。
   *
   * @param <T>
   *     对象类型。
   * @param lhs
   *     左侧的对象数组，可以为 {@code null}。
   * @param rhs
   *     右侧的对象数组，可以为 {@code null}。
   * @param comparator
   *     比较器，可以为 {@code null}。
   * @return 此构建器实例。
   */
  public <T> CompareToBuilder append(@Nullable final T[] lhs, @Nullable final T[] rhs,
      @Nullable final Comparator<T> comparator) {
    if (result == 0) {
      result = Comparison.compare(lhs, rhs, comparator);
    }
    return this;
  }

  /**
   * 添加来自超类 {@code compareTo} 的结果。
   *
   * @param superResult
   *     来自超类的比较结果。
   * @return 此构建器实例。
   */
  public CompareToBuilder appendSuper(final int superResult) {
    if (result == 0) {
      result = superResult;
    }
    return this;
  }

  /**
   * 返回到目前为止累积的比较结果。
   *
   * @return 比较结果：负数、零或正数，分别表示小于、等于或大于。
   */
  public int compare() {
    return result;
  }
}