////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * Defaults the constants of splitting operations.
 *
 * @author Haixing Hu
 */
public interface SplitOption {

  /**
   * Neither trim nor ignore empty.
   */
  int NONE = 0;

  /**
   * Trim the splitted substrings.
   */
  int TRIM = 0x0001;

  /**
   * Ignore the empty splitted substrings.
   */
  int IGNORE_EMPTY = 0x0002;

  /**
   * Use so-called "camel-case" for letter types.
   */
  int CAMEL_CASE = 0x0004;

  /**
   * Comparing the substring and separator ignoring the case.
   */
  int IGNORE_CASE = 0x0008;

  /**
   * Default options, which will trim the splitted substrings and ignore the
   * empty substrings.
   */
  int DEFAULT = TRIM | IGNORE_EMPTY;
}