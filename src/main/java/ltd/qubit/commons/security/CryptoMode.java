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
 * JDK支持的加密模式枚举。
 * <p>
 * 当使用简单分组密码进行加密时，两个相同的明文块总是会产生相同的密文块。
 * 如果密码分析师在破解密文时发现重复的文本块，其工作会变得更容易。为了增加文本的复杂性，
 * 反馈模式使用前一个输出块来改变输入块，然后再应用加密算法。第一个块需要一个初始值，
 * 这个值称为初始化向量（IV）。由于IV只是在任何加密之前改变数据，IV应该是随机的，
 * 但不一定需要保密。有多种模式，如CBC（密码块链接）、CFB（密码反馈模式）和
 * OFB（输出反馈模式）。ECB（电子密码本模式）是一种不受块位置或其他密文块影响的模式。
 * 由于ECB密文在使用相同明文/密钥时是相同的，此模式通常不适用于加密应用，不应使用。
 * <p>
 * 某些算法（如AES和RSA）允许不同长度的密钥，但其他算法是固定的，如3DES。
 * 使用更长密钥的加密通常意味着对消息恢复有更强的抵抗力。通常，安全性和时间之间需要权衡，
 * 因此请适当选择密钥长度。
 * <p>
 * 大多数算法使用二进制密钥。大多数人没有能力记住长序列的二进制数字，即使以十六进制表示也是如此。
 * 字符密码更容易回忆。由于字符密码通常从少数字符中选择（例如[a-zA-Z0-9]），
 * 已经定义了诸如"基于密码的加密"（PBE）等协议，它们采用字符密码并生成强二进制密钥。
 * 为了使攻击者从密码到密钥的过程非常耗时（通过所谓的"字典攻击"，其中常见的字典词->值映射被预计算），
 * 大多数PBE实现会混入一个称为盐的随机数，以增加密钥的随机性。
 * <p>
 * 较新的密码模式，如带关联数据的认证加密（AEAD）（例如，伽罗瓦/计数器模式（GCM））
 * 同时加密数据并认证生成的消息。附加关联数据（AAD）可在计算生成的AEAD标签（Mac）时使用，
 * 但此AAD数据不作为密文输出。（例如，某些数据可能不需要保密，但应参与标签计算以检测修改。）
 * Cipher.updateAAD()方法可用于在标签计算中包含AAD。
 *
 * @author 胡海星
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#Cipher">Modes Of Operation</a>
 */
public enum CryptoMode {
  /**
   * 无模式。
   */
  NONE("NONE"),

  /**
   * 密码块链接模式，如FIPS PUB 81中定义。
   */
  CBC("CBC"),

  //  /**
  //   * Counter/CBC Mode, as defined in NIST Special Publication SP 800-38C:
  //   * Recommendation for Block Cipher Modes of Operation: the CCM Mode for
  //   * Authentication and Confidentiality.
  //   */
  //  CCM("CCM"), // NOT SUPPORTED

  /**
   * 密码反馈模式，如FIPS PUB 81中定义。
   * <p>
   * 使用CFB和OFB等模式，分组密码可以以小于密码实际块大小的单位加密数据。
   * 当请求此类模式时，您可以通过将此数字附加到模式名称来可选地指定一次要处理的位数，
   * 如"DES/CFB8/NoPadding"和"DES/OFB32/PKCS5Padding"转换中所示。
   * 如果未指定此类数字，则使用提供商特定的默认值。（例如，SunJCE提供商对DES使用64位的默认值。）
   * 因此，通过使用8位模式（如CFB8或OFB8），分组密码可以转换为面向字节的流密码。
   */
  CFB("CFB"),

  /**
   * 块大小为8位的密码反馈模式。
   */
  CFB8("CFB8"),

  /**
   * 块大小为16位的密码反馈模式。
   */
  CFB16("CFB16"),

  /**
   * 块大小为32位的密码反馈模式。
   */
  CFB32("CFB32"),

  /**
   * 块大小为64位的密码反馈模式。
   */
  CFB64("CFB64"),

  /**
   * 块大小为128位的密码反馈模式。
   */
  CFB128("CFB128"),

  /**
   * 块大小为256位的密码反馈模式。
   */
  CFB256("CFB256"),

  /**
   * 块大小为512位的密码反馈模式。
   */
  CFB512("CFB512"),

  /**
   * 块大小为1024位的密码反馈模式。
   */
  CFB1024("CFB1024"),

  /**
   * OFB的简化，计数器模式将输入块作为计数器更新。
   */
  CTR("CTR"),

  /**
   * 密文偷取，如Bruce Schneier的《应用密码学-第二版》，John Wiley and Sons，1996年所述。
   */
  CTS("CTS"),

  /**
   * 电子密码本模式，如FIPS PUB 81中定义（通常此模式不应用于多个数据块）。
   */
  ECB("ECB"),

  //  /**
  //   * Galois/Counter Mode, as defined in NIST Special Publication SP 800-38D
  //   * Recommendation for Block Cipher Modes of Operation: Galois/Counter Mode
  //   * (GCM) and GMAC.
  //   */
  //  GCM("GCM"),   // NOT SUPPORTED

  /**
   * 输出反馈模式，如FIPS PUB 81中定义。
   * <p>
   * 使用CFB和OFB等模式，分组密码可以以小于密码实际块大小的单位加密数据。
   * 当请求此类模式时，您可以通过将此数字附加到模式名称来可选地指定一次要处理的位数，
   * 如"DES/CFB8/NoPadding"和"DES/OFB32/PKCS5Padding"转换中所示。
   * 如果未指定此类数字，则使用提供商特定的默认值。（例如，SunJCE提供商对DES使用64位的默认值。）
   * 因此，通过使用8位模式（如CFB8或OFB8），分组密码可以转换为面向字节的流密码。
   */
  OFB("OFB"),

  /**
   * 块大小为8位的输出反馈模式。
   */
  OFB8("OFB8"),

  /**
   * 块大小为16位的输出反馈模式。
   */
  OFB16("OFB16"),

  /**
   * 块大小为32位的输出反馈模式。
   */
  OFB32("OFB32"),

  /**
   * 块大小为64位的输出反馈模式。
   */
  OFB64("OFB64"),

  /**
   * 块大小为128位的输出反馈模式。
   */
  OFB128("OFB128"),

  /**
   * 块大小为256位的输出反馈模式。
   */
  OFB256("OFB256"),

  /**
   * 块大小为512位的输出反馈模式。
   */
  OFB512("OFB512"),

  /**
   * 块大小为1024位的输出反馈模式。
   */
  OFB1024("OFB1024"),

  /**
   * 传播密码块链接，如Kerberos V4定义。
   */
  PCBC("PCBC");

  /**
   * 加密模式名称与枚举的映射。
   */
  private static final Map<String, CryptoMode> NAME_MAP = new HashMap<>();
  static {
    for (final CryptoMode mode: values()) {
      NAME_MAP.put(mode.name().toUpperCase(), mode);
      NAME_MAP.put(mode.code().toUpperCase(), mode);
    }
  }

  /**
   * 通过枚举名称或代码（即JDK中的标准名称）获取加密模式。
   *
   * @param nameOrCode
   *     指定加密模式的名称或代码，忽略大小写。
   * @return
   *     对应的{@link CryptoMode}，如果没有此类模式则返回{@code null}。
   */
  public static CryptoMode forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  /**
   * 加密模式的代码，即JDK中的标准名称。
   */
  private final String code;

  /**
   * 构造一个{@link CryptoMode}对象。
   *
   * @param code
   *     加密模式的代码，即JDK中的标准名称。
   */
  CryptoMode(final String code) {
    this.code = code;
  }

  /**
   * 获取此加密模式的代码，即JDK中的标准名称。
   *
   * @return
   *     此加密模式的代码，即JDK中的标准名称。
   */
  String code() {
    return code;
  }
}