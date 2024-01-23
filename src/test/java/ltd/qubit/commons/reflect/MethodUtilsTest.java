////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.reflect.testbed.ChildBean;
import ltd.qubit.commons.reflect.testbed.Country;
import ltd.qubit.commons.reflect.testbed.CustomList;
import ltd.qubit.commons.reflect.testbed.Deletable;
import ltd.qubit.commons.reflect.testbed.GenericMethod;
import ltd.qubit.commons.reflect.testbed.GenericMethodParent;
import ltd.qubit.commons.reflect.testbed.Info;
import ltd.qubit.commons.reflect.testbed.MethodRefBean;
import ltd.qubit.commons.reflect.testbed.ParentBean;
import ltd.qubit.commons.reflect.testbed.WithInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static ltd.qubit.commons.reflect.MethodUtils.getAllMethods;
import static ltd.qubit.commons.reflect.MethodUtils.getFullyQualifiedMethodName;
import static ltd.qubit.commons.reflect.MethodUtils.getMethod;
import static ltd.qubit.commons.reflect.MethodUtils.getMethodByName;
import static ltd.qubit.commons.reflect.MethodUtils.getMethodByReference;
import static ltd.qubit.commons.reflect.MethodUtils.getMethodUri;
import static ltd.qubit.commons.reflect.Option.ALL;
import static ltd.qubit.commons.reflect.Option.ALL_EXCLUDE_BRIDGE;
import static ltd.qubit.commons.reflect.Option.ALL_EXCLUDE_PRIVATE_NATIVE;
import static ltd.qubit.commons.reflect.Option.BEAN_METHOD;

public class MethodUtilsTest {

  interface Identifiable<ID extends Serializable, T> extends Serializable {

    ID getId();

    T setId(ID id);
  }

  public static class Foo implements Identifiable<Long, Foo> {
    private static final long serialVersionUID = -3393139839709382696L;
    private Long id;

    public final Long getId() {
      return id;
    }

    public final Foo setId(final Long id) {
      this.id = id;
      return this;
    }
  }

  @Test
  public void testGetAllMethods_ExcludeBridgeMethods() {
    final List<Method> methods = getAllMethods(Foo.class, ALL_EXCLUDE_BRIDGE);
    final Object[] result = methods.stream()
                                   .filter((m) -> m.getName().equals("getId"))
                                   .toArray();
    assertEquals(2, result.length);
  }

  @Test
  public void testGetFullyQualifiedMethodName() {
    Method method = getMethod(String.class, ALL, "substring", new Class<?>[]{int.class});
    assertEquals("java.lang.String#substring(int)", getFullyQualifiedMethodName(method));
    method = getMethod(String.class, ALL, "substring", new Class<?>[]{int.class, int.class});
    assertEquals("java.lang.String#substring(int,int)", getFullyQualifiedMethodName(method));
  }

  @Test
  public void testGetMethodUri() {
    Method method = getMethod(String.class, ALL, "substring", new Class<?>[]{int.class});
    assertEquals("method:java.lang.String#substring(int)", getMethodUri(method).toString());

    method = getMethod(String.class, ALL, "substring", new Class<?>[]{int.class, int.class});
    assertEquals("method:java.lang.String#substring(int,int)", getMethodUri(method).toString());
  }

  @Test
  public void testGetAllMethodsExcludeOverridden() throws Exception {
    final List<Method> a1 = getAllMethods(ParentBean.class, BEAN_METHOD);
    assertEquals(13, a1.size());
    assertArrayEquals(new Method[]{
        ParentBean.class.getDeclaredMethod("equals", Object.class),
        Object.class.getDeclaredMethod("getClass"),
        ParentBean.class.getDeclaredMethod("getId"),
        ParentBean.class.getDeclaredMethod("getNumber"),
        ParentBean.class.getDeclaredMethod("hashCode"),
        Object.class.getDeclaredMethod("notify"),
        Object.class.getDeclaredMethod("notifyAll"),
        ParentBean.class.getDeclaredMethod("setId", Long.class),
        ParentBean.class.getDeclaredMethod("setNumber", Number.class),
        ParentBean.class.getDeclaredMethod("toString"),
        Object.class.getDeclaredMethod("wait"),
        Object.class.getDeclaredMethod("wait", long.class),
        Object.class.getDeclaredMethod("wait", long.class, int.class),
    }, a1.toArray(new Method[0]));

    final List<Method> a2 = getAllMethods(ChildBean.class, BEAN_METHOD);
    assertEquals(25, a2.size());
    assertArrayEquals(new Method[]{
        ChildBean.class.getDeclaredMethod("equals", Object.class),
        Object.class.getDeclaredMethod("getClass"),
        ChildBean.class.getDeclaredMethod("getCode"),
        ChildBean.class.getDeclaredMethod("getDescription"),
        ParentBean.class.getDeclaredMethod("getId"),
        ChildBean.class.getDeclaredMethod("getInfo"),
        ChildBean.class.getDeclaredMethod("getName"),
        ChildBean.class.getDeclaredMethod("getNumber"),
        ChildBean.class.getDeclaredMethod("getParentId"),
        ChildBean.class.getDeclaredMethod("hashCode"),
        ChildBean.class.getDeclaredMethod("isDeleted"),
        Object.class.getDeclaredMethod("notify"),
        Object.class.getDeclaredMethod("notifyAll"),
        ChildBean.class.getDeclaredMethod("setCode", String.class),
        ChildBean.class.getDeclaredMethod("setDeleted", boolean.class),
        ChildBean.class.getDeclaredMethod("setDescription", String.class),
        ParentBean.class.getDeclaredMethod("setId", Long.class),
        ChildBean.class.getDeclaredMethod("setName", String.class),
        ChildBean.class.getDeclaredMethod("setNumber", Long.class),
        ParentBean.class.getDeclaredMethod("setNumber", Number.class),
        ChildBean.class.getDeclaredMethod("setParentId", Long.class),
        ChildBean.class.getDeclaredMethod("toString"),
        Object.class.getDeclaredMethod("wait"),
        Object.class.getDeclaredMethod("wait", long.class),
        Object.class.getDeclaredMethod("wait", long.class, int.class),
    }, a2.toArray(new Method[0]));

    final List<Method> a3 = getAllMethods(Country.class, BEAN_METHOD);
    System.out.println("a3 = " + a3);
    assertArrayEquals(new Method[]{
        Country.class.getDeclaredMethod("assign", Country.class),
        Country.class.getDeclaredMethod("clone"),
        Country.class.getDeclaredMethod("equals", Object.class),
        Object.class.getDeclaredMethod("getClass"),
        Country.class.getDeclaredMethod("getCode"),
        Country.class.getDeclaredMethod("getCreateTime"),
        Country.class.getDeclaredMethod("getDeleteTime"),
        Country.class.getDeclaredMethod("getDescription"),
        Country.class.getDeclaredMethod("getIcon"),
        Country.class.getDeclaredMethod("getId"),
        WithInfo.class.getDeclaredMethod("getInfo"),
        Country.class.getDeclaredMethod("getModifyTime"),
        Country.class.getDeclaredMethod("getName"),
        Country.class.getDeclaredMethod("getPhoneArea"),
        Country.class.getDeclaredMethod("getPostalcode"),
        Country.class.getDeclaredMethod("getUrl"),
        Country.class.getDeclaredMethod("hashCode"),
        Deletable.class.getDeclaredMethod("isDeleted"),
        Object.class.getDeclaredMethod("notify"),
        Object.class.getDeclaredMethod("notifyAll"),
        Country.class.getDeclaredMethod("setCode", String.class),
        Country.class.getDeclaredMethod("setCreateTime", Instant.class),
        Country.class.getDeclaredMethod("setDeleteTime", Instant.class),
        Country.class.getDeclaredMethod("setDescription", String.class),
        Country.class.getDeclaredMethod("setIcon", String.class),
        Country.class.getDeclaredMethod("setId", Long.class),
        Country.class.getDeclaredMethod("setModifyTime", Instant.class),
        Country.class.getDeclaredMethod("setName", String.class),
        Country.class.getDeclaredMethod("setPhoneArea", String.class),
        Country.class.getDeclaredMethod("setPostalcode", String.class),
        Country.class.getDeclaredMethod("setUrl", String.class),
        Country.class.getDeclaredMethod("toString"),
        Object.class.getDeclaredMethod("wait"),
        Object.class.getDeclaredMethod("wait", long.class),
        Object.class.getDeclaredMethod("wait", long.class, int.class),
    }, a3.toArray(new Method[0]));
  }

  @Test
  public void testGetAllMethods_GenericMethod_ALL() throws Exception {
    final List<Method> methods = getAllMethods(GenericMethod.class, ALL_EXCLUDE_PRIVATE_NATIVE);
    System.out.println("======================================================");
    System.out.println("JDK " + SystemUtils.JAVA_VERSION_FLOAT);
    System.out.println("All methods of the GenericMethod are:");
    methods.forEach((m) -> System.out.println(m.toGenericString()));
    System.out.println("======================================================");
    // methods.removeIf((m) -> m.getName().equals("registerNatives"));
    assertArrayEquals(new Method[] {
        Object.class.getDeclaredMethod("clone"),
        GenericMethod.class.getDeclaredMethod("createArray", Class.class, int.class),
        Object.class.getDeclaredMethod("equals", Object.class),
        Object.class.getDeclaredMethod("finalize"),
        GenericMethod.class.getDeclaredMethod("foo", Enum.class),
        GenericMethod.class.getDeclaredMethod("foo", Object.class),
        GenericMethodParent.class.getDeclaredMethod("foo", Object.class),
        Object.class.getDeclaredMethod("getClass"),
        Object.class.getDeclaredMethod("hashCode"),
        Object.class.getDeclaredMethod("notify"),
        Object.class.getDeclaredMethod("notifyAll"),
        Object.class.getDeclaredMethod("toString"),
        Object.class.getDeclaredMethod("wait"),
        Object.class.getDeclaredMethod("wait", long.class),
        Object.class.getDeclaredMethod("wait", long.class, int.class),
    }, methods.toArray(new Method[0]));
  }

  @Test
  public void testGetAllMethods_GenericMethod_BEAN_METHOD() throws Exception {
    final List<Method> methods = getAllMethods(GenericMethod.class, BEAN_METHOD);
    assertArrayEquals(new Method[] {
        GenericMethod.class.getDeclaredMethod("createArray", Class.class, int.class),
        Object.class.getDeclaredMethod("equals", Object.class),
        GenericMethod.class.getDeclaredMethod("foo", Enum.class),
        GenericMethod.class.getDeclaredMethod("foo", Object.class),
        Object.class.getDeclaredMethod("getClass"),
        Object.class.getDeclaredMethod("hashCode"),
        Object.class.getDeclaredMethod("notify"),
        Object.class.getDeclaredMethod("notifyAll"),
        Object.class.getDeclaredMethod("toString"),
        Object.class.getDeclaredMethod("wait"),
        Object.class.getDeclaredMethod("wait", long.class),
        Object.class.getDeclaredMethod("wait", long.class, int.class),
    }, methods.toArray(new Method[0]));
  }

  @Test
  public void testGetAllMethods_CustomList_ALL() throws Exception {
    final List<Method> methods = getAllMethods(CustomList.class, BEAN_METHOD);
    System.out.println("======================================================");
    System.out.println("JDK " + SystemUtils.JAVA_VERSION_FLOAT);
    System.out.println("All methods of the CustomList are:");
    methods.forEach((m) -> System.out.println(m.toGenericString()));
    System.out.println("======================================================");
    final float jdkVersion = SystemUtils.JAVA_VERSION_FLOAT;
    final int expected;
    if (jdkVersion >= 21) {
      expected = 49;
    } else if (jdkVersion >= 11) {
      expected = 42;
    } else {
      expected = 41;
    }
    assertEquals(expected, methods.size());
  }

  @Test
  public void testGetMethodByReference_NonVoidMethod0() throws NoSuchMethodException {
    assertEquals(Info.class.getDeclaredMethod("getId"),
        getMethodByReference(Info.class, Info::getId));
    assertEquals(Info.class.getDeclaredMethod("getCode"),
        getMethodByReference(Info.class, Info::getCode));
    assertEquals(Info.class.getDeclaredMethod("getName"),
        getMethodByReference(Info.class, Info::getName));
    assertEquals(Info.class.getDeclaredMethod("toString"),
        getMethodByReference(Info.class, Info::toString));

    assertEquals(MethodRefBean.class.getDeclaredMethod("nonVoidMethod0"),
        getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod0));

    assertThrows(IllegalArgumentException.class,
        () -> getMethodByReference(Info.class, Info::getClass));
  }

  @Test
  public void testGetMethodByReference_NonVoidMethod1() {
    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod1"));

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1PrimitiveInt))
        .withMessage("The method must have non-primitive arguments.");
  }

  @Test
  public void testGetMethodByReference_NonVoidMethod2_9() {
    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod2))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod2"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod3))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod3"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod4))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod4"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod5))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod5"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod6))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod6"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod7))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod7"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod8))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod8"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod9))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod9"));
  }

  @Test
  public void testGetMethodByReference_VoidMethod0() throws NoSuchMethodException {
    assertEquals(MethodRefBean.class.getDeclaredMethod("voidMethod0"),
        getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod0));
  }

  @Test
  public void testGetMethodByReference_VoidMethod1() {
    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod1))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod1"));

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod1PrimitiveInt))
        .withMessage("The method must have non-primitive arguments.");

    assertThat(getMethodByReference(Info.class, Info::setId))
        .isEqualTo(getMethodByName(Info.class, Option.DEFAULT, "setId"));
  }

  @Test
  public void testGetMethodByReference_VoidMethod2_9() {
    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod2))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod2"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod3))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod3"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod4))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod4"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod5))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod5"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod6))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod6"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod7))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod7"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod8))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod8"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod9))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod9"));
  }
}
