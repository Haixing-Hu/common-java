////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.reflect.FieldUtils.getActualType;

public class FieldUtilsGetActualTypeTest {

  public static class Wrapper<T> {
    public T data;
    public int value = 0;
  }

  public static class Foo {
    public int id = 1;
    public String name = "abc";
  }

  public static class Goo extends Wrapper<Foo> {
    public String code = "goo";
  }

  public static class Too extends Goo {
    //  empty
  }

  public static class WrapperData<A, B> {
    public A a;
    public B b;
  }

  public static class Data extends WrapperData<Foo, Goo> {
    // empty
  }

  @Test
  public void testGetActualType() throws NoSuchFieldException {
    final Field f1 = Goo.class.getField("data");
    assertEquals(Foo.class, getActualType(Goo.class, f1));

    final Field f2 = Goo.class.getField("value");
    assertEquals(int.class, getActualType(Goo.class, f2));

    final Field f3 = Too.class.getField("data");
    assertEquals(Foo.class, getActualType(Too.class, f3));

    assertEquals(Object.class, getActualType(Wrapper.class, f1));

    final Field f4 = Data.class.getField("a");
    assertEquals(Foo.class, getActualType(Data.class, f4));

    final Field f5 = Data.class.getField("b");
    assertEquals(Goo.class, getActualType(Data.class, f5));

    final Field f6 = Goo.class.getField("code");
    assertEquals(String.class, getActualType(Goo.class, f6));
  }


  public static class HasCollectionField {
    private List<Integer> list;
    private Map<String, Integer> map;
  }

}
