////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql.mybatis;

/**
 * Handle the local date without a timezone. The format of the local data satisfies
 * the ISO-8601 standard "yyyy-MM-dd".
 *
 * @author Haixing Hu
 */
public class IsoLocalDateHandler extends LocalDateTimePatternHandler {

  public IsoLocalDateHandler() {
    super("yyyy-MM-dd");
  }
}
