////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.reflect.FieldUtils.getFieldInfo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FieldInfoTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  static class Base<T> {
    protected T value;
    protected T[] array;
    protected List<T> list;
  }

  static class Sub<T, F> extends Base<T> {
    protected String name;
    protected F[] data;
  }

  static class SubSub<F> extends Sub<Integer, F> {
    protected String id;
  }

  @Test
  public void testGetFieldInfo() {
    final FieldInfo f1 = getFieldInfo(SubSub.class, Option.BEAN_FIELD, "value");
    assertNotNull(f1);
    logger.info("f1 = {}", f1);
    assertEquals(Integer.class, f1.getActualType());

    final FieldInfo f2 = getFieldInfo(SubSub.class, Option.BEAN_FIELD, "array");
    assertNotNull(f2);
    logger.info("f2 = {}", f2);
    assertEquals(Integer[].class, f2.getActualType());
    assertEquals(Integer.class, f2.getActualComponentType());

    final FieldInfo f3 = getFieldInfo(SubSub.class, Option.BEAN_FIELD, "list");
    assertNotNull(f3);
    logger.info("f3 = {}", f3);
    assertEquals(List.class, f3.getActualType());
    final Type g3 = f3.getField().getGenericType();
    logger.info("g3 = {}", g3);
    logger.info("g3.actualTypeArguments = {}", (Object)((ParameterizedType) g3).getActualTypeArguments());
    assertArrayEquals(new Class<?>[]{ Integer.class }, f3.getActualTypeArguments());

    final FieldInfo f4 = getFieldInfo(SubSub.class, Option.BEAN_FIELD, "data");
    assertNotNull(f4);
    logger.info("f4 = {}", f4);
    assertEquals(Object[].class, f4.getActualType());
  }
}
