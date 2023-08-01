////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import java.util.Arrays;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.util.range.CloseRange;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test of the {@link RandomEx} class.
 *
 * @author Haixing Hu
 */
public class RandomExTest {

  static final int TEST_COUNT = 1000;

  private final RandomEx random = new RandomEx();

  @Test
  public void testNextLong_bound() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final long bound = random.nextLong();
      if (bound <= 0) {
        // System.out.printf("bound = %d\n", bound);
        assertThrows(IllegalArgumentException.class, () -> random.nextLong(bound));
      } else {
        final long value = random.nextLong(bound);
        // System.out.printf("bound = %d, value = %d\n", bound, value);
        assertThat(value).isGreaterThanOrEqualTo(0)
                         .isLessThan(bound);
      }
    }
  }

  @Test
  public void testNextLong_lower_upper() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final long lower = random.nextLong();
      final long upper = random.nextLong();
      if (lower >= upper) {
        // System.out.printf("lower = %d, upper = %d\n", lower, upper);
        assertThrows(IllegalArgumentException.class, () -> random.nextLong(lower, upper));
      } else {
        final long value = random.nextLong(lower, upper);
        // System.out.printf("lower = %d, upper = %d, value = %d\n", lower, upper, value);
        assertThat(value).isGreaterThanOrEqualTo(lower)
                         .isLessThan(upper);
      }
    }
    for (int i = 0; i < TEST_COUNT; ++i) {
      final long lower = Long.MIN_VALUE;
      final long upper = Long.MAX_VALUE;
      final long value = random.nextLong(lower, upper);
      // System.out.printf("lower = %d, upper = %d, value = %d\n", lower, upper, value);
      assertThat(value).isGreaterThanOrEqualTo(lower)
                       .isLessThan(upper);
    }
  }

  @Test
  public void testNextLong_range() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final long min = random.nextLong();
      final long max = random.nextLong();
      if (min > max) {
        // System.out.printf("min = %d, max = %d\n", min, max);
        assertThrows(IllegalArgumentException.class,
            () -> random.nextLong(new CloseRange<>(min, max)));
      } else {
        final long value = random.nextLong(new CloseRange<>(min, max));
        // System.out.printf("min = %d, max = %d, value = %d\n", min, max, value);
        assertThat(value).isGreaterThanOrEqualTo(min)
                         .isLessThanOrEqualTo(max);
      }
    }
    for (int i = 0; i < TEST_COUNT; ++i) {
      final long min = Long.MIN_VALUE;
      final long max = Long.MAX_VALUE;
      final long value = random.nextLong(new CloseRange<>(min, max));
      // System.out.printf("min = %d, max = %d, value = %d\n", min, max, value);
      assertThat(value).isGreaterThanOrEqualTo(min)
                       .isLessThanOrEqualTo(max);
    }
  }

  @Test
  public void testNextInt_lower_upper() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final int lower = random.nextInt();
      final int upper = random.nextInt();
      if (lower >= upper) {
        // System.out.printf("lower = %d, upper = %d\n", lower, upper);
        assertThrows(IllegalArgumentException.class,
            () -> random.nextInt(lower, upper));
      } else {
        final int value = random.nextInt(lower, upper);
        // System.out.printf("lower = %d, upper = %d, value = %d\n", lower, upper, value);
        assertThat(value).isGreaterThanOrEqualTo(lower)
                         .isLessThan(upper);
      }
    }
    for (int i = 0; i < TEST_COUNT; ++i) {
      final int lower = Integer.MIN_VALUE;
      final int upper = Integer.MAX_VALUE;
      final int value = random.nextInt(lower, upper);
      // System.out.printf("lower = %d, upper = %d, value = %d\n", lower, upper, value);
      assertThat(value).isGreaterThanOrEqualTo(lower)
                       .isLessThan(upper);
    }
  }

  @Test
  public void testNextInt_range() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final int min = random.nextInt();
      final int max = random.nextInt();
      if (min > max) {
        // System.out.printf("min = %d, max = %d\n", min, max);
        assertThrows(IllegalArgumentException.class,
            () -> random.nextInt(new CloseRange<>(min, max)));
      } else {
        final int value = random.nextInt(new CloseRange<>(min, max));
        // System.out.printf("min = %d, max = %d, value = %d\n", min, max, value);
        assertThat(value).isGreaterThanOrEqualTo(min)
                         .isLessThanOrEqualTo(max);
      }
    }
    for (int i = 0; i < TEST_COUNT; ++i) {
      final int min = Integer.MIN_VALUE;
      final int max = Integer.MAX_VALUE;
      final int value = random.nextInt(new CloseRange<>(min, max));
      // System.out.printf("min = %d, max = %d, value = %d\n", min, max, value);
      assertThat(value).isGreaterThanOrEqualTo(min)
                       .isLessThanOrEqualTo(max);
    }
  }

  private static final int MAX_COL_SIZE = 100;

  @Test
  public void testChoose_int_array() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final int[] array = random.nextIntArray(MAX_COL_SIZE, false);
      final int value = random.choose(array);
      assertTrue(ArrayUtils.contains(array, value));
    }
    final int[] array = new int[0];
    assertThrows(IllegalArgumentException.class, () -> random.choose(array));
  }

  @Test
  public void testChoose_int_array_k() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final int[] array = random.nextIntArray(MAX_COL_SIZE, false);
      final int k = random.nextInt(array.length + 1);
      final int[] values = random.choose(array, k);
      assertNotNull(values);
      assertEquals(k, values.length);
      final boolean[] used = new boolean[array.length];
      Arrays.fill(used, false);
      for (int t = 0; t < values.length; ++t) {
        final int val = values[t];
        boolean found = false;
        for (int j = 0; j < array.length; ++j) {
          if (array[j] == val && !used[j]) {
            found = true;
            used[j] = true;
            break;
          }
        }
        assertTrue(found, "the " + t + "-th random chosen values cannot be found.");
      }
    }
  }
}
