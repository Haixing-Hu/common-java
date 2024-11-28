////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import com.google.common.base.Ascii;

import ltd.qubit.commons.lang.CharUtils;

/**
 * The interface defining the constants used in CSV files.
 *
 * @author Haixing Hu
 */
public interface CsvConstant {

  /**
   * Comma character.
   */
  char CSV_DELIMITER = ',';

  /**
   * Quote character.
   */
  char CSV_QUOTE = '"';

  /**
   * Quote character converted to string.
   */
  String CSV_QUOTE_STR = CharUtils.toString(CSV_QUOTE);

  /**
   * Escaped quote string.
   */
  String CSV_ESCAPED_QUOTE_STR = CSV_QUOTE_STR + CSV_QUOTE_STR;

  /**
   * CSV key characters in an array.
   */
  char[] CSV_SEARCH_CHARS = {
      CSV_DELIMITER,
      CSV_QUOTE,
      Ascii.CR,
      Ascii.LF,
  };
}
