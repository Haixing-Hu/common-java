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
 * The base class for the unit test of Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsTestBase {

  public static final String WHITESPACE;
  public static final String NON_WHITESPACE;
  public static final String TRIMMABLE;
  public static final String NON_TRIMMABLE;
  public static final String BLANK;
  public static final String NON_BLANK;
  public static final String NON_WHITE_SPACE_BUT_BLANK;

  static {
    final StringBuilder whitespace = new StringBuilder();
    final StringBuilder nonWhitespace = new StringBuilder();
    final StringBuilder blank = new StringBuilder();
    final StringBuilder nonBlank = new StringBuilder();
    final StringBuilder trimmable = new StringBuilder();
    final StringBuilder nonTrimmable = new StringBuilder();
    final StringBuilder nonWhitespaceButBlank = new StringBuilder();

    //  stop checkstyle: MagicNumberCheck
    for (int i = 0; i < Character.MAX_VALUE; i++) {
      final boolean isWhitespace;
      final boolean isBlank;
      if (Character.isWhitespace(i)) {
        isWhitespace = true;
        whitespace.appendCodePoint(i);
        if (i > 32) {
          nonTrimmable.appendCodePoint(i);
        }
      } else {
        isWhitespace = false;
        if (i < 40) {
          nonWhitespace.appendCodePoint(i);
        }
      }
      if (CharUtils.isBlank(i)) {
        isBlank = true;
        // append an extra space to avoid the successive surrogate pairs
        blank.appendCodePoint(i).append(' ');
      } else {
        isBlank = false;
        nonBlank.appendCodePoint(i);
      }
      if ((!isWhitespace) && isBlank) {
        // append an extra space to avoid the successive surrogate pairs
        nonWhitespaceButBlank.appendCodePoint(i).append(' ');
      }
    }
    for (int i = 0; i <= 32; i++) {
      trimmable.appendCodePoint(i);
    }
    //  resume checkstyle: MagicNumberCheck
    WHITESPACE = whitespace.toString();
    NON_WHITESPACE = nonWhitespace.toString();
    TRIMMABLE = trimmable.toString();
    NON_TRIMMABLE = nonTrimmable.toString();
    BLANK = blank.toString();
    NON_BLANK = nonBlank.toString();
    NON_WHITE_SPACE_BUT_BLANK = nonWhitespaceButBlank.toString();
  }

  protected static final String[] ARRAY_LIST = {"foo", "bar", "baz"};
  protected static final String[] EMPTY_ARRAY_LIST = {};
  protected static final String[] NULL_ARRAY_LIST = {null};
  protected static final String[] MIXED_ARRAY_LIST = {null, "", "foo"};
  protected static final Object[] MIXED_TYPE_LIST = {"foo", 2L};

  protected static final String SEPARATOR = ",";
  protected static final char SEPARATOR_CHAR = ';';

  protected static final String TEXT_LIST = "foo,bar,baz";
  protected static final String TEXT_LIST_CHAR = "foo;bar;baz";
  protected static final String TEXT_LIST_NOSEP = "foobarbaz";

  protected static final String FOO_UNCAP = "foo";
  protected static final String FOO_CAP = "Foo";

  protected static final String SENTENCE_UNCAP = "foo bar baz";
  protected static final String SENTENCE_CAP = "Foo Bar Baz";

  protected static final String FOO = "foo";
  protected static final String BAR = "bar";
  protected static final String FOOBAR = "foobar";
  protected static final String CAP_FOO = "FOO";
  protected static final String CAP_BAR = "BAR";
  protected static final String CAP_FOOBAR = "FOOBAR";

  protected static final String[] FOOBAR_SUB_ARRAY = {"ob", "ba"};
  protected static final String BAZ = "baz";
  protected static final String SENTENCE = "foo bar baz";

}