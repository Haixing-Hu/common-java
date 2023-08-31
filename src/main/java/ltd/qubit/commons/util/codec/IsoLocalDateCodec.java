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
 * 符合 ISO-8601 的本地日期编码器，其编码格式为 "yyyy-mm-dd"。
 *
 * @author Haixing Hu
 * @see <a href="https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era">
 * What is the difference between year and year-of-era?</a>
 */
public class IsoLocalDateCodec extends LocalDateCodec {

  public IsoLocalDateCodec() {
    // NOTE that 'u' represent the year, while 'y' represent the year-of-era.
    // see: https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era
    super("uuuu-MM-dd", "uuuu-M-d[[' ']['T']HH:mm[':'ss[.SSS]]]", false, true);
  }
}
