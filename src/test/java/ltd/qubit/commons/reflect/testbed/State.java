////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.util.Locale;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * 此枚举表示实体类型的状态。
 *
 * @author 胡海星
 */
@XmlRootElement(name = "state")
public enum State {

  /**
   * 未激活。
   */
  INACTIVE,

  /**
   * 正常。
   */
  NORMAL,

  /**
   * 锁定/冻结。
   */
  LOCKED,

  /**
   * 屏蔽/封杀。
   */
  BLOCKED,

  /**
   * 已废弃。
   */
  OBSOLETED,

  /**
   * 禁用。
   */
  DISABLED;

  public String getLocalizedName() {
    switch (this) {
      case INACTIVE:
        return "未激活";
      case NORMAL:
        return "正常";
      case LOCKED:
        return "锁定";
      case BLOCKED:
        return "封杀";
      case OBSOLETED:
        return "废弃";
      case DISABLED:
        return "禁用";
      default:
        return "未知";
    }
  }

  public String getLocalizedNameFor(final Locale locale) {
    return getLocalizedName();
  }

  public String method1(final String p1) {
    return "method1: " + p1;
  }

  public String method1boolean(final boolean p1) {
    return "method1: " + p1;
  }

  public String method1char(final char p1) {
    return "method1: " + p1;
  }

  public String method1byte(final byte p1) {
    return "method1: " + p1;
  }

  public String method1short(final short p1) {
    return "method1: " + p1;
  }

  public String method1int(final int p1) {
    return "method1: " + p1;
  }

  public String method1long(final long p1) {
    return "method1: " + p1;
  }

  public String method1float(final float p1) {
    return "method1: " + p1;
  }

  public String method1double(final double p1) {
    return "method1: " + p1;
  }

  public String method2(final String p1, final int p2) {
    return "method2: " + p1 + ", " + p2;
  }

  public String method3(final String p1, final int p2, final boolean p3) {
    return "method3: " + p1 + ", " + p2 + ", " + p3;
  }

  public String method4(final String p1, final int p2, final boolean p3, final long p4) {
    return "method4: " + p1 + ", " + p2 + ", " + p3 + ", " + p4;
  }

  public String method5(final String p1, final int p2, final boolean p3, final long p4, final double p5) {
    return "method5: " + p1 + ", " + p2 + ", " + p3 + ", " + p4 + ", " + p5;
  }

  public String method6(final String p1, final int p2, final boolean p3, final long p4, final double p5, final float p6) {
    return "method6: " + p1 + ", " + p2 + ", " + p3 + ", " + p4 + ", " + p5 + ", " + p6;
  }

  public String method7(final String p1, final int p2, final boolean p3, final long p4, final double p5, final float p6, final short p7) {
    return "method7: " + p1 + ", " + p2 + ", " + p3 + ", " + p4 + ", " + p5 + ", " + p6 + ", " + p7;
  }

  public String method8(final String p1, final int p2, final boolean p3, final long p4, final double p5, final float p6, final short p7, final byte p8) {
    return "method8: " + p1 + ", " + p2 + ", " + p3 + ", " + p4 + ", " + p5 + ", " + p6 + ", " + p7 + ", " + p8;
  }

  public String method9(final String p1, final int p2, final boolean p3, final long p4, final double p5, final float p6, final short p7, final byte p8, final char p9) {
    return "method9: " + p1 + ", " + p2 + ", " + p3 + ", " + p4 + ", " + p5 + ", " + p6 + ", " + p7 + ", " + p8 + ", " + p9;
  }
}
