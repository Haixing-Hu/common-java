////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

/**
 * 此枚举表示政治面貌。
 *
 * @author 胡海星
 * @see "《GB/T 4762-1984 政治面貌代码》"
 */
public enum Politics {

  /**
   * 中国共产党党员，简称中共党员。
   */
  COMMUNIST_PARTY_MEMBER("01"),

  /**
   * 中国共产党预备党员，简称中共预备党员。
   */
  COMMUNIST_PARTY_PROBATIONARY_MEMBER("02"),

  /**
   * 中国共产注意青年团团员，简称共青团员。
   */
  COMMUNIST_YOUTH_LEAGUE_MEMBER("03"),

  /**
   * 中国国民党革命委员会会员，简称民革会员。
   */
  KUOMINTANG_MEMBER("04"),

  /**
   * 中国民主同盟盟员，简称民盟盟员。
   */
  DEMOCRATIC_LEAGUE_MEMBER("05"),

  /**
   * 中国民主建国会会员，简称民建会员。
   */
  NATIONAL_DEMOCRATIC_CONSTRUCTION_ASSOCIATION_MEMBER("06"),

  /**
   * 中国民主促进会会员，简称民进会员。
   */
  PROMOTING_DEMOCRACY_ASSOCIATION_MEMBER("07"),

  /**
   * 中国农工民主党党员，简称农工党党员。
   */
  PEASANTS_WORKERS_DEMOCRATIC_PARTY_MEMBER("08"),

  /**
   * 中国致公党党员，简称致公党党员。
   */
  ZHI_GONG_PARTY_MEMBER("09"),

  /**
   * 九三学社社员。
   */
  NINE_THREE_ACADEMIC_SOCIETY_MEMBER("10"),

  /**
   * 台湾民主自治同盟盟员，简称台盟盟员。
   */
  TAIWAN_DEMOCRATIC_SELF_GOVERNMENT_LEAGUE_MEMBER("11"),

  /**
   * 无党派民主人士。
   */
  INDEPENDENT_POLITICIAN("12"),

  /**
   * 群众。
   */
  MASSES("13");

  private final String code;

  Politics(final String code) {
    this.code = code;
  }

  /**
   * 获取政治面貌的国标编码。
   *
   * @return
   *     此政治面貌的国标编码。
   */
  String code() {
    return this.code;
  }
}