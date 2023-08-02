////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * 此枚举表示系统支持的安全随机数算法。
 *
 * @author 胡海星
 * @see SecureRandom
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecureRandom">SecureRandom Number Generation Algorithms</a>
 */
public enum SecureRandomAlgorithm {

  NATIVE_PRNG("NativePRNG"),

  NATIVE_PRNG_BLOCKING("NativePRNGBlocking"),

  NATIVE_PRNG_NON_BLOCKING("NativePRNGNonBlocking"),

  PKCS11("PKCS11"),

  SHA1_PRNG("SHA1PRNG"),

  WINDOWS_PRNG("Windows-PRNG");

  private static final Map<String, SecureRandomAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final SecureRandomAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * 根据名称或编码，获取指定的算法枚举值。
   *
   * @param nameOrCode
   *     指定的算法的枚举值名称或编码（系统算法名），不区分大小写。
   * @return
   *     该名称或编码对应的算法枚举值。
   */
  public static SecureRandomAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;

  SecureRandomAlgorithm(final String code) {
    this.code = code;
  }

  /**
   * 获取此算法的编码，即该算法在系统中的算法名称。
   *
   * @return
   *     此算法的编码，即该算法在系统中的算法名称。
   */
  String code() {
    return code;
  }
}
