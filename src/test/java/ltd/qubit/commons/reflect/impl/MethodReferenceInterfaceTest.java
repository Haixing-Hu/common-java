////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.reflect.testbed.MethodRefBean;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodReferenceInterfaceTest {

  @Test
  public void testMethodReferenceVoidMethod() {
    final MethodRefBean bean = new MethodRefBean();

    call(bean, MethodRefBean::voidMethod0);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod0");

    call(bean, MethodRefBean::voidMethod1, null);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod1");

    call(bean, MethodRefBean::voidMethod2, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod2");

    call(bean, MethodRefBean::voidMethod3, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod3");

    call(bean, MethodRefBean::voidMethod4, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod4");

    call(bean, MethodRefBean::voidMethod5, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod5");

    call(bean, MethodRefBean::voidMethod6, null, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod6");

    call(bean, MethodRefBean::voidMethod7, null, null, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod7");

    call(bean, MethodRefBean::voidMethod8, null, null, null, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod8");

    call(bean, MethodRefBean::voidMethod9, null, null, null, null, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("voidMethod9");
  }

  @Test
  public void testMethodReferenceNonVoidMethod() {
    final MethodRefBean bean = new MethodRefBean();

    call(bean, MethodRefBean::nonVoidMethod0);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod0");

    call(bean, MethodRefBean::nonVoidMethod1, null);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1");

    call(bean, MethodRefBean::nonVoidMethod2, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod2");

    call(bean, MethodRefBean::nonVoidMethod3, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod3");

    call(bean, MethodRefBean::nonVoidMethod4, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod4");

    call(bean, MethodRefBean::nonVoidMethod5, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod5");

    call(bean, MethodRefBean::nonVoidMethod6, null, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod6");

    call(bean, MethodRefBean::nonVoidMethod7, null, null, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod7");

    call(bean, MethodRefBean::nonVoidMethod8, null, null, null, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod8");

    call(bean, MethodRefBean::nonVoidMethod9, null, null, null, null, null, null, null, null, null);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod9");
  }

  private <T>
  void call(final T bean, final VoidMethod0<T> ref) {
    ref.invoke(bean);
  }

  private <T, P1>
  void call(final T bean, final VoidMethod1<T, P1> ref, final P1 p1) {
    ref.invoke(bean, p1);
  }

  private <T, P1, P2>
  void call(final T bean, final VoidMethod2<T, P1, P2> ref, final P1 p1, final P2 p2) {
    ref.invoke(bean, p1, p2);
  }

  private <T, P1, P2, P3>
  void call(final T bean, final VoidMethod3<T, P1, P2, P3> ref, final P1 p1, final P2 p2, final P3 p3) {
    ref.invoke(bean, p1, p2, p3);
  }

  private <T, P1, P2, P3, P4>
  void call(final T bean, final VoidMethod4<T, P1, P2, P3, P4> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4) {
    ref.invoke(bean, p1, p2, p3, p4);
  }

  private <T, P1, P2, P3, P4, P5>
  void call(final T bean, final VoidMethod5<T, P1, P2, P3, P4, P5> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5) {
    ref.invoke(bean, p1, p2, p3, p4, p5);
  }

  private <T, P1, P2, P3, P4, P5, P6>
  void call(final T bean, final VoidMethod6<T, P1, P2, P3, P4, P5, P6> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5, final P6 p6) {
    ref.invoke(bean, p1, p2, p3, p4, p5, p6);
  }

  private <T, P1, P2, P3, P4, P5, P6, P7>
  void call(final T bean, final VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5, final P6 p6, final P7 p7) {
    ref.invoke(bean, p1, p2, p3, p4, p5, p6, p7);
  }

  private <T, P1, P2, P3, P4, P5, P6, P7, P8>
  void call(final T bean, final VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5, final P6 p6, final P7 p7, final P8 p8) {
    ref.invoke(bean, p1, p2, p3, p4, p5, p6, p7, p8);
  }

  private <T, P1, P2, P3, P4, P5, P6, P7, P8, P9>
  void call(final T bean, final VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5, final P6 p6, final P7 p7, final P8 p8, final P9 p9) {
    ref.invoke(bean, p1, p2, p3, p4, p5, p6, p7, p8, p9);
  }

  private <T, R>
  void call(final T bean, final NonVoidMethod0<T, R> ref) {
    ref.invoke(bean);
  }

  private <T, R, P1>
  void call(final T bean, final NonVoidMethod1<T, R, P1> ref, final P1 p1) {
    ref.invoke(bean, p1);
  }

  private <T, R, P1, P2>
  void call(final T bean, final NonVoidMethod2<T, R, P1, P2> ref, final P1 p1, final P2 p2) {
    ref.invoke(bean, p1, p2);
  }

  private <T, R, P1, P2, P3>
  void call(final T bean, final NonVoidMethod3<T, R, P1, P2, P3> ref, final P1 p1, final P2 p2, final P3 p3) {
    ref.invoke(bean, p1, p2, p3);
  }

  private <T, R, P1, P2, P3, P4>
  void call(final T bean, final NonVoidMethod4<T, R, P1, P2, P3, P4> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4) {
    ref.invoke(bean, p1, p2, p3, p4);
  }

  private <T, R, P1, P2, P3, P4, P5>
  void call(final T bean, final NonVoidMethod5<T, R, P1, P2, P3, P4, P5> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5) {
    ref.invoke(bean, p1, p2, p3, p4, p5);
  }

  private <T, R, P1, P2, P3, P4, P5, P6>
  void call(final T bean, final NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5, final P6 p6) {
    ref.invoke(bean, p1, p2, p3, p4, p5, p6);
  }

  private <T, R, P1, P2, P3, P4, P5, P6, P7>
  void call(final T bean, final NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5, final P6 p6, final P7 p7) {
    ref.invoke(bean, p1, p2, p3, p4, p5, p6, p7);
  }

  private <T, R, P1, P2, P3, P4, P5, P6, P7, P8>
  void call(final T bean, final NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5, final P6 p6, final P7 p7, final P8 p8) {
    ref.invoke(bean, p1, p2, p3, p4, p5, p6, p7, p8);
  }

  private <T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9>
  void call(final T bean, final NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref,
      final P1 p1, final P2 p2, final P3 p3, final P4 p4, final P5 p5, final P6 p6, final P7 p7, final P8 p8, final P9 p9) {
    ref.invoke(bean, p1, p2, p3, p4, p5, p6, p7, p8, p9);
  }


  @Test
  public void testMethodReferenceNonVoidMethodPrimitiveParameters() {
    final MethodRefBean bean = new MethodRefBean();


//    invoke(bean, MethodRefBean::nonVoidMethod1Integer);
//    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1Integer");
//
//    invoke(bean, MethodRefBean::nonVoidMethod1String);
//    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1String");

    invoke(bean, MethodRefBean::nonVoidMethod1PrimitiveByte);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1PrimitiveByte");
    assertThat(lastInvoke).isEqualTo("invoke(T, NonVoidMethod1Byte)");

    invoke(bean, MethodRefBean::nonVoidMethod1PrimitiveShort);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1PrimitiveShort");
    assertThat(lastInvoke).isEqualTo("invoke(T, NonVoidMethod1Byte)");

    invoke(bean, MethodRefBean::nonVoidMethod1PrimitiveInt);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1PrimitiveInt");
    assertThat(lastInvoke).isEqualTo("invoke(T, NonVoidMethod1Byte)");

    invoke(bean, MethodRefBean::nonVoidMethod1PrimitiveLong);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1PrimitiveLong");
    assertThat(lastInvoke).isEqualTo("invoke(T, NonVoidMethod1Byte)");

    invoke(bean, MethodRefBean::nonVoidMethod1PrimitiveFloat);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1PrimitiveFloat");
    assertThat(lastInvoke).isEqualTo("invoke(T, NonVoidMethod1Byte)");

    invoke(bean, MethodRefBean::nonVoidMethod1PrimitiveDouble);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1PrimitiveDouble");
    assertThat(lastInvoke).isEqualTo("invoke(T, NonVoidMethod1Byte)");

    invoke(bean, MethodRefBean::nonVoidMethod1PrimitiveBoolean);
    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1PrimitiveBoolean");
    assertThat(lastInvoke).isEqualTo("invoke(T, NonVoidMethod1Boolean)");

    //    invoke(bean, MethodRefBean::nonVoidMethod1PrimitiveChar);
    //    assertThat(bean.lastCalledMethod).isEqualTo("nonVoidMethod1PrimitiveChar");
    //    assertThat(lastInvoke).isEqualTo("invoke(T, NonVoidMethod1Char)");
  }

  private String lastInvoke;


  @SuppressWarnings("overloads")
  private <T, R, P1>
  void invoke(final T bean, final NonVoidMethod1<T, R, P1> ref) {
    ref.invoke(bean, null);
    lastInvoke = "invoke(T, NonVoidMethod1)";
  }


  @SuppressWarnings("overloads")
  private <T, R>
  void invoke(final T bean, final NonVoidMethod1Byte<T, R> ref) {
    ref.invoke(bean, (byte) 0);
    lastInvoke = "invoke(T, NonVoidMethod1Byte)";
  }

  //  private <T, R>
  //  void invoke(T bean, NonVoidMethod1Short<T, R> ref) {
  //    ref.invoke(bean, (short) 0);
  //    lastInvoke = "invoke(T, NonVoidMethod1Short)";
  //  }
  //
  //  private <T, R>
  //  void invoke(T bean, NonVoidMethod1Int<T, R> ref) {
  //    ref.invoke(bean, 0);
  //    lastInvoke = "invoke(T, NonVoidMethod1Int)";
  //  }
  ////
  //  private <T, R>
  //  void invoke(T bean, NonVoidMethod1Long<T, R> ref) {
  //    ref.invoke(bean, 0L);
  //    lastInvoke = "invoke(T, NonVoidMethod1Long)";
  //  }

  @SuppressWarnings("overloads")
  private <T, R>
  void invoke(final T bean, final NonVoidMethod1Boolean<T, R> ref) {
    ref.invoke(bean, false);
    lastInvoke = "invoke(T, NonVoidMethod1Boolean)";
  }

  //  private <T, R>
  //  void invoke(T bean, NonVoidMethod1Char<T, R> ref) {
  //    ref.invoke(bean, '\0');
  //  }
  //
  //  private <T, R>
  //  void invoke(T bean, NonVoidMethod1Float<T, R> ref) {
  //    ref.invoke(bean, 0.0f);
  //    lastInvoke = "invoke(T, NonVoidMethod1Float)";
  //  }
  //
  //  private <T, R>
  //  void invoke(T bean, NonVoidMethod1Double<T, R> ref) {
  //    ref.invoke(bean, 0.0);
  //    lastInvoke = "invoke(T, NonVoidMethod1Double)";
  //  }
}