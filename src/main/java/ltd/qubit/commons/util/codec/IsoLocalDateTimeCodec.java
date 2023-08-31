////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * 符合 ISO-8601 的本地日期时间编码器，其编码格式为 "yyyy-mm-dd HH:mm:ss"。
 *
 * @author Haixing Hu
 * @see <a href="https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era">
 * What is the difference between year and year-of-era?</a>
 */
public class IsoLocalDateTimeCodec extends LocalDateTimeCodec {

  // NOTE that 'u' represent the year, while 'y' represent the year-of-era.
  // see: https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era
  public IsoLocalDateTimeCodec() {
    super("uuuu-MM-dd HH:mm:ss", "uuuu-MM-dd[' ']['T']HH:mm:ss[.SSS]", false);
  }
}
