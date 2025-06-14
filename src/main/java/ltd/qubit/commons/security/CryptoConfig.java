////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 表示加密配置的类。
 *
 * <p>这是{@link Encryptor}和{@link Decryptor}的基类。</p>
 *
 * @author 胡海星
 */
public class CryptoConfig {

  /**
   * 包装后缀。
   */
  private static final String WRAP_SUFFIX = "Wrap";

  /**
   * 加密算法。
   */
  protected final CryptoAlgorithm algorithm;

  /**
   * 加密模式。
   */
  protected final CryptoMode mode;

  /**
   * 填充方式。
   */
  protected final CryptoPadding padding;

  /**
   * 加密器。
   */
  protected final transient Cipher cipher;

  /**
   * 构造一个{@link CryptoConfig}。
   *
   * @param algorithm
   *     加密算法。
   * @param mode
   *     加密模式。
   * @param padding
   *     填充方式。
   * @throws NoSuchPaddingException
   *     如果不支持指定的填充方式。
   * @throws NoSuchAlgorithmException
   *     如果不支持指定的算法。
   */
  protected CryptoConfig(final CryptoAlgorithm algorithm, final CryptoMode mode,
      final CryptoPadding padding) throws NoSuchPaddingException, NoSuchAlgorithmException {
    this.algorithm = requireNonNull("algorithm", algorithm);
    this.mode = requireNonNull("mode", mode);
    this.padding = requireNonNull("padding", padding);
    final String cipherName = algorithm.code() + "/" + mode.code() + "/" + padding.code();
    this.cipher = Cipher.getInstance(cipherName);
  }

  /**
   * 获取加密算法。
   *
   * @return 加密算法。
   */
  public CryptoAlgorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * 获取加密模式。
   *
   * @return 加密模式。
   */
  public CryptoMode getMode() {
    return mode;
  }

  /**
   * 获取填充方式。
   *
   * @return 填充方式。
   */
  public CryptoPadding getPadding() {
    return padding;
  }

  /**
   * 测试加密配置是否具有加密参数。
   *
   * @return
   *     如果此解密器的配置具有加密参数则返回{@code true}；否则返回{@code false}。
   */
  public boolean hasParameters() {
    switch (algorithm) {
      case AES:
      case DES_EDE:
      case BLOWFISH:
        switch (mode) {
          case CFB:
          case CFB8:
          case CFB16:
          case CFB32:
          case CFB64:
          case CFB128:
          case CFB256:
          case CFB512:
          case CFB1024:
          case OFB:
          case OFB8:
          case OFB16:
          case OFB32:
          case OFB64:
          case OFB128:
          case OFB256:
          case OFB512:
          case OFB1024:
          case CBC:
          case CTR:
          case PCBC:
            return true;
          default:
            return false;
        }
      default:
        return false;
    }
  }

  /**
   * 获取此算法的密钥生成器。
   *
   * @return
   *     此算法的密钥生成器。
   * @throws NoSuchAlgorithmException
   *     如果系统不支持此算法。
   */
  protected KeyGenerator getKeyGenerator() throws NoSuchAlgorithmException {
    final String name = getKeyGeneratorName();
    return KeyGenerator.getInstance(name);
  }

  /**
   * 获取密钥生成器的名称。
   *
   * @return 密钥生成器的名称。
   */
  protected String getKeyGeneratorName() {
    final String code = algorithm.code();
    if (code.endsWith(WRAP_SUFFIX)) {
      return code.substring(0, code.length() - WRAP_SUFFIX.length());
    } else {
      return code;
    }
  }

  /**
   * 获取此算法的不透明加密参数。
   *
   * @param parameters
   *     以此算法参数的主要编码格式编码的加密参数。如果存在此类型参数的ASN.1规范，
   *     则参数的主要编码格式是ASN.1。
   * @return
   *     此算法对应的不透明加密参数。
   * @throws NoSuchAlgorithmException
   *     如果系统不支持此算法。
   * @throws IOException
   *     如果在解码参数时发生任何I/O错误。
   */
  protected AlgorithmParameters getAlgorithmParameters(final byte[] parameters)
      throws NoSuchAlgorithmException, IOException {
    requireNonNull("parameters", parameters);
    final String code = algorithm.code();
    final AlgorithmParameters ap = AlgorithmParameters.getInstance(code);
    ap.init(parameters);
    return ap;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Encryptor other = (Encryptor) o;
    return Equality.equals(algorithm, other.algorithm)
        && Equality.equals(mode, other.mode)
        && Equality.equals(padding, other.padding);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, algorithm);
    result = Hash.combine(result, multiplier, mode);
    result = Hash.combine(result, multiplier, padding);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("algorithm", algorithm)
        .append("mode", mode)
        .append("padding", padding)
        .toString();
  }
}