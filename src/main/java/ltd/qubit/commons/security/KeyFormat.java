////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 密钥编码格式的枚举。
 *
 * @author 胡海星
 */
public enum KeyFormat {

  /**
   * PKCS#8 编码格式，通常用于私钥。
   */
  PKCS8("PKCS#8"),

  /**
   * X.509 编码格式，通常用于公钥。
   */
  X509("X.509");

  private static final Map<String, KeyFormat> NAME_MAP = new HashMap<>();
  static {
    for (final KeyFormat format: values()) {
      NAME_MAP.put(format.name().toUpperCase(), format);
      NAME_MAP.put(format.code().toUpperCase(), format);
    }
  }

  /**
   * 根据名称或代码，获取指定的密钥编码格式枚举值。
   *
   * @param nameOrCode
   *     指定的密钥编码格式的枚举值名称或代码（JDK系统中的格式名称），不区分大小写。
   * @return
   *     该名称或代码对应的密钥编码格式枚举值。
   */
  public static KeyFormat forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;

  KeyFormat(final String code) {
    this.code = code;
  }

  /**
   * 获取此密钥编码格式的代码，即该密钥编码格式在JDK系统中的格式名称。
   *
   * @return
   *     此密钥编码格式的代码，即该密钥编码格式在JDK系统中的格式名称。
   */
  public String code() {
    return code;
  }

  /**
   * 获取此密钥编码格式对应的编码规范。
   *
   * @param encodedKey
   *     依据此格式编码后的密钥。
   * @return
   *     指定密钥对应的编码规范。
   */
  EncodedKeySpec getEncodedKeySpec(final byte[] encodedKey) {
    switch (this) {
      case PKCS8:
        return new PKCS8EncodedKeySpec(encodedKey);
      case X509:
        return new X509EncodedKeySpec(encodedKey);
      default:
        throw new RuntimeException("Unsupported key format: " + this.name());
    }
  }
}