////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.util.HashMap;
import java.util.Map;

/**
 * JDK支持的加密填充模式枚举。
 * <p>
 * 有两种主要类型的密码：分组密码和流密码。分组密码一次处理整个块，通常长度为许多字节。
 * 如果没有足够的数据构成完整的输入块，则必须填充数据：即在加密之前，
 * 必须添加虚拟字节以构成密码块大小的倍数。然后在解密阶段删除这些字节。
 * 填充可以由应用程序完成，也可以通过将密码初始化为使用填充类型（如"PKCS5PADDING"）来完成。
 * 相比之下，流密码一次处理传入数据的一个小单位（通常是一个字节甚至一个位）。
 * 这允许密码处理任意数量的数据而无需填充。
 *
 * @author 胡海星
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#Cipher">Stream vs. Block Ciphers</a>
 */
public enum CryptoPadding {

  /**
   * 无填充。
   */
  NONE("NoPadding"),

  /**
   * 此分组密码填充在W3C的"XML加密语法和处理"文档的5.2分组加密算法中描述。
   */
  ISO_10126("ISO10126Padding"),

  /**
   * PKCS #1 v2.2中描述的填充方案，与RSA算法一起使用。
   */
  PKCS1("PKCS1Padding"),

  /**
   * PKCS #5：基于密码的加密规范2.1版中描述的填充方案。
   */
  PKCS5("PKCS5Padding"),

  /**
   * SSL协议版本3.0（1996年11月18日）第5.2.3.2节（CBC分组密码）中定义的填充方案。
   * <p>
   * GenericBlockCipher实例的大小必须是分组密码块长度的倍数。
   * <p>
   * 总是存在的填充长度有助于填充，这意味着如果：
   *
   * <pre><code>
   * sizeof(content) + sizeof(MAC) % block_length = 0,
   * </code></pre>
   *
   * <p>
   * 由于padding_length的存在，填充必须是（block_length - 1）字节长。
   * <p>
   * 这使得填充方案类似于（但不完全是）PKCS5Padding，
   * 其中填充长度在填充中编码（范围从1到block_length）。
   * 使用SSL方案，sizeof(padding)在总是存在的padding_length中编码，
   * 因此范围从0到block_length-1。
   */
  SSL3("SSL3Padding");

  /**
   * 加密填充模式名称与枚举的映射。
   */
  private static final Map<String, CryptoPadding> NAME_MAP = new HashMap<>();
  static {
    for (final CryptoPadding padding: values()) {
      NAME_MAP.put(padding.name().toUpperCase(), padding);
      NAME_MAP.put(padding.code().toUpperCase(), padding);
    }
  }

  /**
   * 通过枚举名称或代码（即JDK中的标准名称）获取加密填充模式。
   *
   * @param nameOrCode
   *     指定加密填充模式的名称或代码，忽略大小写。
   * @return
   *     对应的{@link CryptoPadding}，如果没有此类填充模式则返回{@code null}。
   */
  public static CryptoPadding forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  /**
   * 加密填充模式的代码，即JDK中的标准名称。
   */
  private final String code;

  /**
   * 构造一个{@link CryptoPadding}对象。
   *
   * @param code
   *     加密填充模式的代码，即JDK中的标准名称。
   */
  CryptoPadding(final String code) {
    this.code = code;
  }

  /**
   * 获取此加密填充模式的代码，即JDK中的标准名称。
   *
   * @return
   *     此加密填充模式的代码，即JDK中的标准名称。
   */
  String code() {
    return code;
  }
}