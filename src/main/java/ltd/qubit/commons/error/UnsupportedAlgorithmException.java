////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.Serial;
import java.security.GeneralSecurityException;

import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 当使用不支持的算法时抛出此异常。
 *
 * @author 胡海星
 */
public class UnsupportedAlgorithmException extends GeneralSecurityException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 2973072118457122125L;

  private final String algorithm;

  /**
   * 构造一个新的不支持算法异常。
   *
   * @param algorithm
   *     不支持的算法名称。
   */
  public UnsupportedAlgorithmException(final String algorithm) {
    super("Unsupported algorithm: " + algorithm);
    this.algorithm = algorithm;
  }

  /**
   * 获取不支持的算法名称。
   *
   * @return
   *     不支持的算法名称。
   */
  public final String getAlgorithm() {
    return algorithm;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "UNSUPPORTED_ALGORITHM",
        KeyValuePairList.of("algorithm", algorithm));
  }
}