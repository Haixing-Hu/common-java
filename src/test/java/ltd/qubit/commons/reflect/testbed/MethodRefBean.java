////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

public class MethodRefBean {

  public String lastCalledMethod = null;

  public void voidMethod0() {
    lastCalledMethod = "voidMethod0";
  }

  public void voidMethod1(final String p1) {
    lastCalledMethod = "voidMethod1";
  }

  public void setPrimitiveBoolean(final boolean p1) {
    lastCalledMethod = "setPrimitiveBoolean";
  }

  public void setPrimitiveChar(final char p1) {
    lastCalledMethod = "setPrimitiveChar";
  }

  public void setPrimitiveByte(final byte p1) {
    lastCalledMethod = "setPrimitiveByte";
  }

  public void setPrimitiveShort(final short p1) {
    lastCalledMethod = "setPrimitiveShort";
  }

  public void setPrimitiveInt(final int p1) {
    lastCalledMethod = "setPrimitiveInt";
  }

  public void setPrimitiveLong(final long p1) {
    lastCalledMethod = "setPrimitiveLong";
  }

  public void setPrimitiveFloat(final float p1) {
    lastCalledMethod = "setPrimitiveFloat";
  }

  public void setPrimitiveDouble(final double p1) {
    lastCalledMethod = "setPrimitiveDouble";
  }

  public void setBoolean(final Boolean p1) {
    lastCalledMethod = "setBoolean";
  }

  public void setCharacter(final Character p1) {
    lastCalledMethod = "setCharacter";
  }

  public void setByte(final Byte p1) {
    lastCalledMethod = "setByte";
  }

  public void setShort(final Short p1) {
    lastCalledMethod = "setShort";
  }

  public void setInteger(final Integer p1) {
    lastCalledMethod = "setInteger";
  }

  public void setLong(final Long p1) {
    lastCalledMethod = "setLong";
  }

  public void setFloat(final Float p1) {
    lastCalledMethod = "setFloat";
  }

  public void setDouble(final Double p1) {
    lastCalledMethod = "setDouble";
  }

  public int setString(final String p1) {
    lastCalledMethod = "setString";
    return 0;
  }

  public void voidMethod2(final String p1, final String p2) {
    lastCalledMethod = "voidMethod2";
  }

  public void voidMethod3(final String p1, final String p2, final String p3) {
    lastCalledMethod = "voidMethod3";
  }

  public void voidMethod4(final String p1, final String p2, final String p3, final String p4) {
    lastCalledMethod = "voidMethod4";
  }

  public void voidMethod5(final String p1, final String p2, final String p3, final String p4, final String p5) {
    lastCalledMethod = "voidMethod5";
  }

  public void voidMethod6(final String p1, final String p2, final String p3, final String p4, final String p5, final String p6) {
    lastCalledMethod = "voidMethod6";
  }

  public void voidMethod7(final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final String p7) {
    lastCalledMethod = "voidMethod7";
  }

  public void voidMethod8(final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final String p7, final String p8) {
    lastCalledMethod = "voidMethod8";
  }

  public void voidMethod9(final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final String p7, final String p8, final String p9) {
    lastCalledMethod = "voidMethod9";
  }

  public int nonVoidMethod0() {
    lastCalledMethod = "nonVoidMethod0";
    return 0;
  }

  public int nonVoidMethod1(final String p1) {
    lastCalledMethod = "nonVoidMethod1";
    return 0;
  }

  public int nonVoidMethod1PrimitiveBoolean(final boolean p1) {
    lastCalledMethod = "nonVoidMethod1PrimitiveBoolean";
    return 0;
  }

  public int nonVoidMethod1PrimitiveChar(final char p1) {
    lastCalledMethod = "nonVoidMethod1PrimitiveChar";
    return 0;
  }

  public int nonVoidMethod1PrimitiveByte(final byte p1) {
    lastCalledMethod = "nonVoidMethod1PrimitiveByte";
    return 0;
  }

  public int nonVoidMethod1PrimitiveShort(final short p1) {
    lastCalledMethod = "nonVoidMethod1PrimitiveShort";
    return 0;
  }

  public int nonVoidMethod1PrimitiveInt(final int p1) {
    lastCalledMethod = "nonVoidMethod1PrimitiveInt";
    return 0;
  }

  public int nonVoidMethod1PrimitiveLong(final long p1) {
    lastCalledMethod = "nonVoidMethod1PrimitiveLong";
    return 0;
  }

  public int nonVoidMethod1PrimitiveFloat(final float p1) {
    lastCalledMethod = "nonVoidMethod1PrimitiveFloat";
    return 0;
  }

  public int nonVoidMethod1PrimitiveDouble(final double p1) {
    lastCalledMethod = "nonVoidMethod1PrimitiveDouble";
    return 0;
  }

  public int nonVoidMethod1Integer(final Integer p1) {
    lastCalledMethod = "nonVoidMethod1Integer";
    return 0;
  }

  public int nonVoidMethod1String(final String p1) {
    lastCalledMethod = "nonVoidMethod1String";
    return 0;
  }

  public int nonVoidMethod2(final String p1, final String p2) {
    lastCalledMethod = "nonVoidMethod2";
    return 0;
  }

  public int nonVoidMethod3(final String p1, final String p2, final String p3) {
    lastCalledMethod = "nonVoidMethod3";
    return 0;
  }

  public int nonVoidMethod4(final String p1, final String p2, final String p3, final String p4) {
    lastCalledMethod = "nonVoidMethod4";
    return 0;
  }

  public int nonVoidMethod5(final String p1, final String p2, final String p3, final String p4, final String p5) {
    lastCalledMethod = "nonVoidMethod5";
    return 0;
  }

  public int nonVoidMethod6(final String p1, final String p2, final String p3, final String p4, final String p5, final String p6) {
    lastCalledMethod = "nonVoidMethod6";
    return 0;
  }

  public int nonVoidMethod7(final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final String p7) {
    lastCalledMethod = "nonVoidMethod7";
    return 0;
  }

  public int nonVoidMethod8(final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final String p7, final String p8) {
    lastCalledMethod = "nonVoidMethod8";
    return 0;
  }

  public int nonVoidMethod9(final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final String p7, final String p8, final String p9) {
    lastCalledMethod = "nonVoidMethod9";
    return 0;
  }
}