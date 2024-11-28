////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.test;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.json.JsonMapper;

import net.javacrumbs.jsonunit.assertj.JsonAssert;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

/**
 * Some utilities functions for JSON unit.
 *
 * @author Haixing Hu
 */
public class JsonUnitUtils {

  public static <T> void assertJsonArrayEquals(final String json,
      final String field, @Nullable final T[] expected,
      final JsonMapper mapper) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(field).isAbsent();
    } else {
      final JsonAssert assertion = assertThatJson(json);
      assertion.node(field).isArray().hasSize(expected.length);
      for (int i = 0; i < expected.length; ++i) {
        final String str = mapper.writerWithDefaultPrettyPrinter()
                           .writeValueAsString(expected[i]);
        assertion.node(field + "[" + i + "]").isEqualTo(str);
      }
    }
  }

  public static <T> void assertJsonArrayEquals(final String json,
      final String field, @Nullable final List<T> expected,
      final JsonMapper mapper) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(field).isAbsent();
    } else {
      final JsonAssert assertion = assertThatJson(json);
      assertion.node(field).isArray().hasSize(expected.size());
      for (int i = 0; i < expected.size(); ++i) {
        final String str = mapper.writerWithDefaultPrettyPrinter()
                           .writeValueAsString(expected.get(i));
        assertion.node(field + "[" + i + "]").isEqualTo(str);
      }
    }
  }

  public static void assertJsonNodeNull(final String json, final String nodePath)
          throws Exception {
    assertThatJson(json).node(nodePath).isNull();
  }

  public static void assertJsonNodeAbsent(final String json, final String nodePath)
          throws Exception {
    assertThatJson(json).node(nodePath).isAbsent();
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final Object expected, final JsonMapper mapper) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      final String expectedJson = mapper.writerWithDefaultPrettyPrinter()
                              .writeValueAsString(expected);
      assertThatJson(json).node(nodePath).isEqualTo(expectedJson);
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final Boolean expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isBoolean().isEqualTo(expected);
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final String expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isString().isEqualTo(expected);
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final Character expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isString().isEqualTo(expected.toString());
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final Long expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(expected.toString());
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final Integer expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(expected.toString());
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final Short expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(expected.toString());
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final Byte expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(expected.toString());
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final Float expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(expected.toString());
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final Double expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(expected.toString());
    }
  }

  public static <E extends Enum<E>> void assertJsonNodeEquals(final String json,
          final String nodePath, @Nullable final E expected) throws Exception {
    if (expected == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isString().isEqualTo(expected.name());
    }
  }

  public static void assertJsonNodeEquals(final String json, final String nodePath,
          @Nullable final BigDecimal value) throws Exception {
    if (value == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      // 必须把 BigDecimal 转换为字符串在比较，否则可能丧失精度，
      // 例如原来的值是 4.1230 若直接比较则会和 4.123 比较，导致不同
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(value.toString());
    }
  }

  public static void assertJsonNodeEqualsFloat(final String json, final String nodePath,
          @Nullable final String floatText) throws Exception {
    if (floatText == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(floatText);
    }
  }

  public static void assertJsonNodeEqualsDouble(final String json, final String nodePath,
          @Nullable final String doubleText) throws Exception {
    if (doubleText == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(doubleText);
    }
  }

  public static void assertJsonNodeEqualsBigDecimal(final String json, final String nodePath,
          @Nullable final String bigDecimalText) throws Exception {
    if (bigDecimalText == null) {
      assertThatJson(json).node(nodePath).isAbsent();
    } else {
      assertThatJson(json).node(nodePath).isNumber().isEqualByComparingTo(bigDecimalText);
    }
  }
}
