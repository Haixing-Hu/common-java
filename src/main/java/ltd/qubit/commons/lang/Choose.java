////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * 提供选择函数。
 *
 * @author 胡海星
 */
public class Choose {

  /**
   * 从两个 Character 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  public static Character choose(final Character v1, final Character v2) {
    if (v1 != null) {
      return v1;
    } else {
      return v2;
    }
  }

  /**
   * 从三个 Character 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；否则返回 v3
   */
  public static Character choose(final Character v1, final Character v2, final Character v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }

  /**
   * 从两个 Boolean 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  public static Boolean choose(final Boolean v1, final Boolean v2) {
    if (v1 != null) {
      return v1;
    } else {
      return v2;
    }
  }

  /**
   * 从三个 Boolean 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；否则返回 v3
   */
  public static Boolean choose(final Boolean v1, final Boolean v2, final Boolean v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }

  /**
   * 从两个 Byte 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  public static Byte choose(final Byte v1, final Byte v2) {
    if (v1 != null) {
      return v1;
    } else {
      return v2;
    }
  }

  /**
   * 从三个 Byte 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；否则返回 v3
   */
  public static Byte choose(final Byte v1, final Byte v2, final Byte v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }

  /**
   * 从两个 Short 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  public static Short choose(final Short v1, final Short v2) {
    if (v1 != null) {
      return v1;
    } else {
      return v2;
    }
  }

  /**
   * 从三个 Short 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；否则返回 v3
   */
  public static Short choose(final Short v1, final Short v2, final Short v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }

  /**
   * 从两个 Integer 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  public static Integer choose(final Integer v1, final Integer v2) {
    if (v1 != null) {
      return v1;
    } else {
      return v2;
    }
  }

  /**
   * 从三个 Integer 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；否则返回 v3
   */
  public static Integer choose(final Integer v1, final Integer v2, final Integer v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }

  /**
   * 从两个 Long 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  public static Long choose(final Long v1, final Long v2) {
    if (v1 != null) {
      return v1;
    } else {
      return v2;
    }
  }

  /**
   * 从三个 Long 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；否则返回 v3
   */
  public static Long choose(final Long v1, final Long v2, final Long v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }

  /**
   * 从两个 Float 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  public static Float choose(final Float v1, final Float v2) {
    if (v1 != null) {
      return v1;
    } else {
      return v2;
    }
  }

  /**
   * 从三个 Float 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；否则返回 v3
   */
  public static Float choose(final Float v1, final Float v2, final Float v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }

  /**
   * 从两个 Double 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  public static Double choose(final Double v1, final Double v2) {
    if (v1 != null) {
      return v1;
    } else {
      return v2;
    }
  }

  /**
   * 从三个 Double 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；否则返回 v3
   */
  public static Double choose(final Double v1, final Double v2, final Double v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }

  /**
   * 从两个 String 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  public static String choose(final String v1, final String v2) {
    if (v1 != null) {
      return v1;
    } else {
      return v2;
    }
  }

  /**
   * 从三个 String 值中选择第一个非 null 的值。
   *
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；否则返回 v3
   */
  public static String choose(final String v1, final String v2, final String v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }
}