////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;

import ltd.qubit.commons.reflect.testbed.IntegerList;
import ltd.qubit.commons.reflect.testbed.MyIntegerList;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TypeInfoTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void testGetGenericSuperClass() {
    final Type t1 = IntegerList.class.getGenericSuperclass();
    logger.info("t1 = {}", t1);
    final Type t2 = MyIntegerList.class.getGenericSuperclass();
    logger.info("t2 = {}", t2);
  }

  @Test
  public void testGetGenericInterfaces() {
    final Type[] types1 = IntegerList.class.getGenericInterfaces();
    logger.info("types1 = {}", (Object) types1);
    final Type[] types2 = MyIntegerList.class.getGenericInterfaces();
    logger.info("types2 = {}", (Object) types2);
  }

  static class Base<T> {
    protected T value;
  }

  static class Sub<T, F> extends Base<T> {
    protected String name;
    protected F[] data;
  }

  static class SubSub<F> extends Sub<Integer, F> {
    protected String id;
  }

  @Test
  public void testResolveActualType() {
    final TypeInfo i1 = new TypeInfo(SubSub.class);
    logger.info("i1.parameters = {}", (Object) i1.getParameters());
    assertArrayEquals(new String[]{"F"},
        Arrays.stream(i1.getParameters())
              .map(Type::getTypeName)
              .toArray());
    logger.info("i1.actualArguments = {}", (Object) i1.getActualArguments());
    assertArrayEquals(new Type[0], i1.getActualArguments());
    final FieldInfo fi1 = FieldUtils.getFieldInfo(SubSub.class, Option.BEAN_FIELD, "value");
    assertNotNull(fi1);
    logger.info("fi1 = {}", fi1);
    assertEquals(Integer.class, fi1.getActualType());
    final Field f1 = FieldUtils.getField(SubSub.class, Option.BEAN_FIELD, "value");
    assertNotNull(f1);
    final Type t1 = f1.getGenericType();
    logger.info("t1 = {}", t1);
    assertEquals("T", t1.getTypeName());
    final Class<?> c1 = i1.resolveActualType(Object.class, t1);
    logger.info("c1 = {}", c1);
    assertEquals(Object.class, c1);
  }
}
