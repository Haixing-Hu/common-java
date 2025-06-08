////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.lang.DateUtils;

/**
 * 符合 ISO-8601 的时间戳编码器，其编码格式为 "uuuu-mm-dd'T'HH:mm:ss.SSS'Z'"。
 *
 * @author 胡海星
 * @see <a href="https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era">
 * What is the difference between year and year-of-era?</a>
 */
public class IsoDateCodec extends DateCodec {

  public static final IsoDateCodec INSTANCE = new IsoDateCodec();

  public IsoDateCodec() {
    // NOTE that 'u' represent the year, while 'y' represent the year-of-era.
    // see: https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era
    super("uuuu-MM-dd'T'HH:mm:ss.SSS'Z'",
            "uuuu-MM-dd[[' ']['T']HH:mm[':'ss[.SSS]]][' ']['Z'][Z][z]",
            DateUtils.UTC_ZONE_ID, false);
  }
}