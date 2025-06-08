////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import java.util.Locale;

import ltd.qubit.commons.lang.EnumUtils;

import static ltd.qubit.commons.lang.EnumUtils.registerLocalizedNames;

/**
 * 此枚举表示民族。
 *
 * @author 胡海星
 * @since 1.0
 * @see <a href="https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%9C%8B%E6%B0%91%E6%97%8F%E5%88%97%E8%A1%A8">中国民族列表</a>
 */
public enum Ethnic {

  /**
   * 汉族。
   */
  HAN(1, "HA"),

  /**
   * 蒙古族。
   */
  MONGOL(2, "MG"),

  /**
   * 回族。
   */
  HUI(3, "HU"),

  /**
   * 藏族。
   */
  TIBETAN(4, "ZA"),

  /**
   * 维吾尔族。
   */
  UYGHUR(5, "UG"),

  /**
   * 苗族。
   */
  MIAO(6, "MH"),

  /**
   * 彝族。
   */
  YI(7, "YI"),

  /**
   * 壮族。
   */
  ZHUANG(8, "ZH"),

  /**
   * 布依族。
   */
  BUYEI(9, "BY"),

  /**
   * 朝鲜族。
   */
  CHOSEN(10, "CS"),

  /**
   * 满族。
   */
  MAN(11, "MA"),

  /**
   * 侗族。
   */
  DONG(12, "DO"),

  /**
   * 瑶族。
   */
  YAO(13, "YA"),

  /**
   * 白族。
   */
  BAI(14, "BA"),

  /**
   * 土家族。
   */
  TUJIA(15, "TJ"),

  /**
   * 哈尼族。
   */
  HANI(16, "HN"),

  /**
   * 哈萨克族。
   */
  KAZAK(17, "KZ"),

  /**
   * 傣族。
   */
  DAI(18, "DA"),

  /**
   * 黎族。
   */
  LI(19, "LI"),

  /**
   * 傈僳族。
   */
  LISU(20, "LS"),

  /**
   * 佤族。
   */
  VA(21, "VA"),

  /**
   * 畲族。
   */
  SHE(22, "SH"),

  /**
   * 拉祜族。
   */
  LAHU(23, "LH"),

  /**
   * 水族。
   */
  SUI(24, "SU"),

  /**
   * 东乡族。
   */
  DONGXIANG(25, "DX"),

  /**
   * 纳西族。
   */
  NAXI(26, "NX"),

  /**
   * 景颇族。
   */
  JINGPO(27, "JP"),

  /**
   * 柯尔克孜族。
   */
  KIRGIZ(28, "KG"),

  /**
   * 土族。
   */
  TU(29, "TU"),

  /**
   * 达斡尔族。
   */
  DAUR(30, "DU"),

  /**
   * 仫佬族。
   */
  MULAO(31, "ML"),

  /**
   * 羌族。
   */
  QIANG(32, "QI"),

  /**
   * 布朗族。
   */
  BLANG(33, "BL"),

  /**
   * 撒拉族。
   */
  SALAR(34, "SL"),

  /**
   * 毛南族。
   */
  MAONAN(35, "MN"),

  /**
   * 仡佬族。
   */
  GELAO(36, "GL"),

  /**
   * 锡伯族。
   */
  XIBE(37, "XB"),

  /**
   * 阿昌族。
   */
  ACHANG(38, "AC"),

  /**
   * 普米族。
   */
  PUMI(39, "PM"),

  /**
   * 塔吉克族。
   */
  TAJIK(40, "TA"),

  /**
   * 怒族。
   */
  NU(41, "NU"),

  /**
   * 乌兹别克族。
   */
  UZBEK(42, "UZ"),

  /**
   * 俄罗斯族。
   */
  RUSS(43, "RS"),

  /**
   * 鄂温克族。
   */
  EWENKI(44, "EW"),

  /**
   * 德昂族。
   */
  DEANG(45, "DE"),

  /**
   * 保安族。
   */
  BONAN(46, "BO"),

  /**
   * 裕固族。
   */
  YUGUR(47, "YG"),

  /**
   * 京族。
   */
  GIN(48, "GI"),

  /**
   * 塔塔尔族。
   */
  TATAR(49, "TT"),

  /**
   * 独龙族。
   */
  DERUNG(50, "DR"),

  /**
   * 鄂伦春族。
   */
  OROQEN(51, "OR"),

  /**
   * 赫哲族。
   */
  HEZHEN(52, "HZ"),

  /**
   * 门巴族。
   */
  MONBA(53, "MB"),

  /**
   * 珞巴族。
   */
  LHOBA(54, "LB"),

  /**
   * 基诺族。
   */
  JINO(55, "JN"),

  /**
   * 高山族。
   */
  GAOSHAN(56, "GS"),

  /**
   * 外国人，包括外籍人士，以及中国籍外国人。
   */
  FOREIGNER(-1, "ZZ"),

  /**
   * 其他。
   */
  OTHER(0, "XX");

  private final int id;
  private final String code;

  Ethnic(final int id, final String code) {
    this.id = id;
    this.code = code;
  }

  public int id() {
    return id;
  }

  public String code() {
    return code;
  }

  static {
    registerLocalizedNames(Ethnic.class, "i18n.ethnic");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }
}