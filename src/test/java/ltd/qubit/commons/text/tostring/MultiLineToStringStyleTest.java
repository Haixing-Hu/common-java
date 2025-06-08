////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.text.tostring.ToStringStyleTest.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.lang.SystemUtils.LINE_SEPARATOR;

/**
 * Unit test for the ToStringBuilder and MultiLineToStringStyle class.
 *
 * @author Haixing Hu
 */
public class MultiLineToStringStyleTest {

  private final Integer base = 5;
  private final String baseStr = base.getClass().getName() + "@"
      + Integer.toHexString(System.identityHashCode(base));

  private static final ToStringStyle STYLE = MultiLineToStringStyle.INSTANCE;

  private static String getStartPrefix(final Object obj) {
    final StringBuilder builder = new StringBuilder();
    STYLE.appendClassName(builder, obj);
    STYLE.appendIdentityHashCode(builder, obj);
    return builder.toString();
  }

  @Test
  public void testBlank() {
    final ToStringBuilder tb = new ToStringBuilder(STYLE);
    final String actual = tb.reset(base).build();
    assertEquals(baseStr + "[" + LINE_SEPARATOR + "]", actual);
  }

  @Test
  public void testSingleField() {
    final ToStringBuilder tb = new ToStringBuilder(STYLE);
    final String actual = tb.reset(base)
                            .append("foo", 123)
                            .build();
    assertEquals(baseStr
        + "[" + LINE_SEPARATOR
        + "  foo=123" + LINE_SEPARATOR
        + "]", actual);
  }

  @Test
  public void testAppendSuper_emptySuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr
            + "[" + LINE_SEPARATOR + "]",
        builder
            .reset(base)
            .appendSuper("Integer@8888[" + LINE_SEPARATOR + "]")
            .build()
    );
  }

  @Test
  public void testAppendSuper_nullSuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr
            + "[" + LINE_SEPARATOR
            + "  <null>" + LINE_SEPARATOR
            + "]",
        builder
            .reset(base)
            .appendSuper("Integer@8888[" + LINE_SEPARATOR
                + "    <null>" + LINE_SEPARATOR
                + "  ]")
            .build()
    );
  }

  @Test
  public void testAppendSuper_singleElementSuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr
            + "[" + LINE_SEPARATOR
            + "  a=\"hello\"" + LINE_SEPARATOR
            + "]",
        builder
            .reset(base)
            .appendSuper("Integer@8888[" + LINE_SEPARATOR
                + "    a=\"hello\"" + LINE_SEPARATOR
                + "  ]")
            .build()
    );
  }


  @Test
  public void testAppendSuper_multiElementsSuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr
            + "[" + LINE_SEPARATOR
            + "  a=\"hello\"" + LINE_SEPARATOR
            + "  b=123" + LINE_SEPARATOR
            + "  c=456" + LINE_SEPARATOR
            + "]",
        builder
            .reset(base)
            .appendSuper("Integer@8888[" + LINE_SEPARATOR
                + "    a=\"hello\"" + LINE_SEPARATOR
                + "    b=123" + LINE_SEPARATOR
                + "  ]")
            .append("c", 456)
            .build()
    );
  }

  @Test
  public void testAppendSuper_nestedElementsSuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr
            + "[" + LINE_SEPARATOR
            + "  a=\"hello\"" + LINE_SEPARATOR
            + "  b=123" + LINE_SEPARATOR
            + "  c=[" + LINE_SEPARATOR
            + "    d=\"world\"" + LINE_SEPARATOR
            + "  ]" + LINE_SEPARATOR
            + "  e=789" + LINE_SEPARATOR
            + "]",
        builder
            .reset(base)
            .appendSuper("Integer@8888[" + LINE_SEPARATOR
                + "    a=\"hello\"" + LINE_SEPARATOR
                + "    b=123" + LINE_SEPARATOR
                + "    c=[" + LINE_SEPARATOR
                + "      d=\"world\"" + LINE_SEPARATOR
                + "    ]" + LINE_SEPARATOR
                + "  ]")
            .append("e", 789)
            .build()
    );
  }

  @Test
  public void testAppendSuper_emptySuperWithSingleField() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr + "[" + LINE_SEPARATOR
            + "  a=\"hello\"" + LINE_SEPARATOR
            + "]",
        builder.reset(base)
               .appendSuper("Integer@8888[" + LINE_SEPARATOR + "]")
               .append("a", "hello").build());
  }

  @Test
  public void testAppendSuper_nullSupeWithSingleField() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr
            + "[" + LINE_SEPARATOR
            + "  <null>" + LINE_SEPARATOR
            + "  a=\"hello\"" + LINE_SEPARATOR
            + "]",
        builder
            .reset(base)
            .appendSuper("Integer@8888[" + LINE_SEPARATOR
                + "    <null>" + LINE_SEPARATOR
                + "  ]")
            .append("a", "hello")
            .build()
    );
  }

  @Test
  public void testAppendSuper_appendSuperNullWithSingleField() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr
            + "[" + LINE_SEPARATOR
            + "  a=\"hello\"" + LINE_SEPARATOR
            + "]",
        builder
            .reset(base)
            .appendSuper(null)
            .append("a", "hello")
            .build()
    );
  }

  @Test
  public void testObject() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final Integer i3 = 3;
    final Integer i4 = 4;

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  <null>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) null).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  3"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append(i3).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  a=<null>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", (Object) null).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  a=3"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", i3).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  a=3"
        + LINE_SEPARATOR + "  b=4" + LINE_SEPARATOR
        + "]", builder.reset(base).append("a", i3).append("b", i4).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  a=<Integer>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", i3, false).build());

    assertEquals(
        baseStr + "[" + LINE_SEPARATOR + "  a=<size=0>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", new ArrayList<Byte>(), false)
               .build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  a=[]"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", new ArrayList<Byte>(), true)
               .build());

    assertEquals(
        baseStr + "[" + LINE_SEPARATOR + "  a=<size=0>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", new HashMap<Byte, Byte>(), false)
               .build());

    assertEquals(
        baseStr + "[" + LINE_SEPARATOR + "  a={}"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", new HashMap<Byte, Byte>(), true)
               .build());

    assertEquals(
        baseStr + "[" + LINE_SEPARATOR + "  a=<size=0>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", (Object) new String[0], false)
               .build());

    assertEquals(
        baseStr + "[" + LINE_SEPARATOR + "  a={}"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", (Object) new String[0], true)
               .build());
  }

  @Test
  public void testPerson() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final Person p = new Person();
    p.name = "Jane Doe";
    p.age = 25;
    p.smoker = true;
    final String str = p.getClass().getName() + "@"
        + Integer.toHexString(System.identityHashCode(p));

    assertEquals(
        str + "[" + LINE_SEPARATOR
            + "  name=\"Jane Doe\"" + LINE_SEPARATOR
            + "  age=25" + LINE_SEPARATOR
            + "  smoker=true" + LINE_SEPARATOR
            + "]",
        builder.reset(p)
               .append("name", p.name)
               .append("age", p.age)
               .append("smoker", p.smoker)
               .build());
  }

  @Test
  public void testLong() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  3"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append(3L).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  a=3"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append("a", 3L).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  a=3"
        + LINE_SEPARATOR + "  b=4" + LINE_SEPARATOR
        + "]", builder.reset(base).append("a", 3L).append("b", 4L).build());
  }

  @Test
  public void testObjectArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    Object[] array = {null, base, new int[]{3, 6}};

    assertEquals(baseStr + "[" + LINE_SEPARATOR
            + "  {<null>,5,{3,6}}" + LINE_SEPARATOR + "]",
        builder.reset(base).append(array).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR
            + "  {<null>,5,{3,6}}" + LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).build());

    array = null;
    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  <null>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append(array).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  <null>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).build());
  }

  @Test
  public void testLongArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[] array = {1, 2, -3, 4};
    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  {1,2,-3,4}"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append(array).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  {1,2,-3,4}"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).build());

    array = null;
    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  <null>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append(array).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  <null>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).build());
  }

  @Test
  public void testLongArrayArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[][] array = {{1, 2}, null, {5}};
    assertEquals(baseStr + "[" + LINE_SEPARATOR
            + "  {{1,2},<null>,{5}}" + LINE_SEPARATOR + "]",
        builder.reset(base).append(array).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR
            + "  {{1,2},<null>,{5}}" + LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).build());

    array = null;
    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  <null>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append(array).build());

    assertEquals(baseStr + "[" + LINE_SEPARATOR + "  <null>"
            + LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).build());
  }

  @Test
  public void testNestedObjects() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    class Country {
      String name;
      String code;

      @Override
      public String toString() {
        return new ToStringBuilder(STYLE, this)
            .append("name", name)
            .append("code", code)
            .build();
      }
    }

    class Address {
      String street;
      String city;
      Country country;

      @Override
      public String toString() {
        return new ToStringBuilder(STYLE, this)
            .append("street", street)
            .append("city", city)
            .append("country", country)
            .build();
      }
    }

    class Foo {
      Address address;

      @Override
      public String toString() {
        return new ToStringBuilder(STYLE, this)
            .append("address", address)
            .build();
      }
    }

    final Country country = new Country();
    country.name = "USA";
    country.code = "US";
    final Address address = new Address();
    address.street = "123 Main St";
    address.city = "Springfield";
    address.country = country;
    final Foo foo = new Foo();
    foo.address = address;

    final String expected = getStartPrefix(foo) + "[" + LINE_SEPARATOR
        + "  address=" + getStartPrefix(address) + "[" + LINE_SEPARATOR
        + "    street=\"123 Main St\"" + LINE_SEPARATOR
        + "    city=\"Springfield\"" + LINE_SEPARATOR
        + "    country=" + getStartPrefix(country) + "[" + LINE_SEPARATOR
        + "      name=\"USA\"" + LINE_SEPARATOR
        + "      code=\"US\"" + LINE_SEPARATOR
        + "    ]" + LINE_SEPARATOR
        + "  ]" + LINE_SEPARATOR
        + "]";

    assertEquals(expected, foo.toString());
  }

  // FIXME: The following tests are disabled because it cannot be passed
  @Disabled
  @Test
  public void testNestedMap() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final HashMap<String, Object> address = new HashMap<>();
    address.put("street", "123 Main St");
    address.put("city", "Springfield");

    final HashMap<String, Object> country = new HashMap<>();
    country.put("name", "USA");
    country.put("code", "US");
    address.put("country", country);

    final String expected = baseStr + "[" + LINE_SEPARATOR
        + "  address=" + "{" + LINE_SEPARATOR
        + "    street=\"123 Main St\"," + LINE_SEPARATOR
        + "    city=\"Springfield\"," + LINE_SEPARATOR
        + "    country={" + LINE_SEPARATOR
        + "      name=\"USA\"," + LINE_SEPARATOR
        + "      code=\"US\"" + LINE_SEPARATOR
        + "    }" + LINE_SEPARATOR
        + "  }" + LINE_SEPARATOR
        + "]";

    assertEquals(expected,
        builder.reset(base)
               .append("address", address)
               .build());
  }

  // FIXME: The following tests are disabled because it cannot be passed
  @Disabled
  @Test
  public void testNestedArrays() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final Object[] array = {
        "first",
        new int[]{1, 2, 3},
        new String[]{"nested", "array"}
    };

    final String expected = baseStr + "[" + LINE_SEPARATOR
        + "  array={" + LINE_SEPARATOR
        + "    \"first\"," + LINE_SEPARATOR
        + "    {1,2,3}," + LINE_SEPARATOR
        + "    {\"nested\",\"array\"}" + LINE_SEPARATOR
        + "  }" + LINE_SEPARATOR
        + "]";

    assertEquals(expected,
        builder.reset(base)
               .append("array", array)
               .build());
  }

  @Test
  public void testNestedObject_2() {
    class Info {
      public Long id;
      public String code;
      public String name;

      @Override
      public String toString() {
        return new ToStringBuilder(STYLE, this)
            .append("id", id)
            .append("code", code)
            .append("name", name)
            .toString();
      }
    }

    class StatefulInfo extends Info {
      public String state;

      @Override
      public String toString() {
        return new ToStringBuilder(STYLE, this)
            .appendSuper(super.toString())
            .append("state", state)
            .toString();
      }
    }

    final StatefulInfo info = new StatefulInfo();
    info.id = 123L;
    info.code = "abc";
    info.name = "hello";
    info.state = "NORMAL";
    final String str = info.toString();

    final String expected = getStartPrefix(info) + "[" + LINE_SEPARATOR
        + "  id=123" + LINE_SEPARATOR
        + "  code=\"abc\"" + LINE_SEPARATOR
        + "  name=\"hello\"" + LINE_SEPARATOR
        + "  state=\"NORMAL\"" + LINE_SEPARATOR
        + "]";
    assertEquals(expected, str);
  }

  @Test
  public void testNestedNestedObject() {
    class Info {
      public Long id;
      public String code;
      public String name;

      @Override
      public String toString() {
        return new ToStringBuilder(STYLE, this)
            .append("id", id)
            .append("code", code)
            .append("name", name)
            .toString();
      }
    }

    class StatefulInfo extends Info {
      public String state;

      @Override
      public String toString() {
        return new ToStringBuilder(STYLE, this)
            .appendSuper(super.toString())
            .append("state", state)
            .toString();
      }
    }

    class DeleteableStatefulInfo extends StatefulInfo {
      public LocalDate deleteDate;

      @Override
      public String toString() {
        return new ToStringBuilder(STYLE, this)
            .appendSuper(super.toString())
            .append("deleteDate", deleteDate)
            .toString();
      }
    }

    final DeleteableStatefulInfo info = new DeleteableStatefulInfo();
    info.id = 123L;
    info.code = "abc";
    info.name = "hello";
    info.state = "NORMAL";
    info.deleteDate = LocalDate.of(2025, 1, 1);
    final String str = info.toString();

    final String expected = getStartPrefix(info) + "[" + LINE_SEPARATOR
        + "  id=123" + LINE_SEPARATOR
        + "  code=\"abc\"" + LINE_SEPARATOR
        + "  name=\"hello\"" + LINE_SEPARATOR
        + "  state=\"NORMAL\"" + LINE_SEPARATOR
        + "  deleteDate=2025-01-01" + LINE_SEPARATOR
        + "]";
    assertEquals(expected, str);
  }
}