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
import ltd.qubit.commons.reflect.impl.GetMethodByReferenceThroughSerialization;
import ltd.qubit.commons.reflect.testbed.ChildBean;
import ltd.qubit.commons.reflect.testbed.Country;
import ltd.qubit.commons.reflect.testbed.CustomList;
import ltd.qubit.commons.reflect.testbed.Deletable;
import ltd.qubit.commons.reflect.testbed.GenericMethod;
import ltd.qubit.commons.reflect.testbed.GenericMethodParent;
import ltd.qubit.commons.reflect.testbed.Info;
import ltd.qubit.commons.reflect.testbed.MethodRefBean;
import ltd.qubit.commons.reflect.testbed.MyRecord;
import ltd.qubit.commons.reflect.testbed.ParentBean;
import ltd.qubit.commons.reflect.testbed.State;
import ltd.qubit.commons.reflect.testbed.WithInfo;

import static org.assertj.core.api.Assertions.assertThat;
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
        Country.class.getDeclaredMethod("cloneEx"),
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
  }

  @Test
  public void testGetMethodByReference_NonVoidMethod1_primitive() {
    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1PrimitiveBoolean))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod1PrimitiveBoolean"));

    // assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1PrimitiveChar))
    //     .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod1PrimitiveChar"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1PrimitiveByte))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod1PrimitiveByte"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1PrimitiveShort))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod1PrimitiveShort"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1PrimitiveInt))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod1PrimitiveInt"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1PrimitiveLong))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod1PrimitiveLong"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1PrimitiveFloat))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod1PrimitiveFloat"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::nonVoidMethod1PrimitiveDouble))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "nonVoidMethod1PrimitiveDouble"));
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
    assertThat(getMethodByReference(Info.class, Info::setId))
        .isEqualTo(getMethodByName(Info.class, Option.DEFAULT, "setId"));
  }

  @Test
  public void testGetMethodByReference_VoidMethod1_primitive() {
    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setPrimitiveBoolean))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setPrimitiveBoolean"));

    // FIXME: Current primitive char is not supported
    // assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::voidMethod1PrimitiveChar))
    //     .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "voidMethod1PrimitiveChar"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setPrimitiveByte))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setPrimitiveByte"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setPrimitiveShort))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setPrimitiveShort"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setPrimitiveInt))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setPrimitiveInt"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setPrimitiveLong))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setPrimitiveLong"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setPrimitiveFloat))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setPrimitiveFloat"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setPrimitiveDouble))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setPrimitiveDouble"));
  }

  @Test
  public void testGetMethodByReference_VoidMethod1_primitive_boxing() {
    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setBoolean))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setBoolean"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setCharacter))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setCharacter"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setByte))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setByte"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setShort))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setShort"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setInteger))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setInteger"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setLong))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setLong"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setFloat))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setFloat"));

    assertThat(getMethodByReference(MethodRefBean.class, MethodRefBean::setDouble))
        .isEqualTo(getMethodByName(MethodRefBean.class, Option.DEFAULT, "setDouble"));
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

  @Test
  public void testGetMethodByReferenceForRecord() {
    final Method m = getMethodByReference(MyRecord.class, MyRecord::name);
    assertEquals("name", m.getName());
  }

  @Test
  public void testGetMethodByReferenceForEnumClass() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::getLocalizedName);
    final Method expected = getMethodByName(State.class, "getLocalizedName");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass1() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method1);
    final Method expected = getMethodByName(State.class, "method1");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass1Boolean() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method1boolean);
    final Method expected = getMethodByName(State.class, "method1boolean");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass1Char() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method1char);
    final Method expected = getMethodByName(State.class, "method1char");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass1Byte() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method1byte);
    final Method expected = getMethodByName(State.class, "method1byte");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass1Short() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method1short);
    final Method expected = getMethodByName(State.class, "method1short");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass1Int() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method1int);
    final Method expected = getMethodByName(State.class, "method1int");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass1Long() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method1long);
    final Method expected = getMethodByName(State.class, "method1long");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass1Float() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method1float);
    final Method expected = getMethodByName(State.class, "method1float");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass1Double() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method1double);
    final Method expected = getMethodByName(State.class, "method1double");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass2() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method2);
    final Method expected = getMethodByName(State.class, "method2");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass3() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method3);
    final Method expected = getMethodByName(State.class, "method3");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass4() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method4);
    final Method expected = getMethodByName(State.class, "method4");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass5() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method5);
    final Method expected = getMethodByName(State.class, "method5");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass6() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method6);
    final Method expected = getMethodByName(State.class, "method6");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass7() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method7);
    final Method expected = getMethodByName(State.class, "method7");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass8() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method8);
    final Method expected = getMethodByName(State.class, "method8");
    assertEquals(expected, m);
  }

  @Test
  public void testGetMethodByReferenceForEnumClass9() throws NoSuchMethodException {
    final Method m = getMethodByReference(State.class, State::method9);
    final Method expected = getMethodByName(State.class, "method9");
    assertEquals(expected, m);
  }

  @Test
  public void test2() throws Exception {
    final String name = GetMethodByReferenceThroughSerialization.getMethodNameBySerialization(State::getLocalizedName);
    assertEquals("getLocalizedName", name);
  }
}
