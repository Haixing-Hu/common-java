////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;
import java.io.UncheckedIOException;

import static ltd.qubit.commons.lang.CharUtils.UPPERCASE_DIGITS;
import static ltd.qubit.commons.text.ErrorCode.BUFFER_OVERFLOW;
import static ltd.qubit.commons.text.ErrorCode.INCOMPLETE_UNICODE;
import static ltd.qubit.commons.text.ErrorCode.MALFORMED_UNICODE;
import static ltd.qubit.commons.text.Unicode.HIGH_SURROGATE_MASK;
import static ltd.qubit.commons.text.Unicode.HIGH_SURROGATE_MIN;
import static ltd.qubit.commons.text.Unicode.HIGH_SURROGATE_SHIFT;
import static ltd.qubit.commons.text.Unicode.LOW_SURROGATE_MASK;
import static ltd.qubit.commons.text.Unicode.LOW_SURROGATE_MIN;
import static ltd.qubit.commons.text.Unicode.SUPPLEMENTARY_MIN;
import static ltd.qubit.commons.text.Unicode.SURROGATE_COMPOSE_OFFSET;
import static ltd.qubit.commons.text.Unicode.SURROGATE_DECOMPOSE_MASK;
import static ltd.qubit.commons.text.Unicode.SURROGATE_DECOMPOSE_OFFSET;
import static ltd.qubit.commons.text.Unicode.SURROGATE_MASK;
import static ltd.qubit.commons.text.Unicode.SURROGATE_MIN;
import static ltd.qubit.commons.text.Unicode.UNICODE_MAX;
import static ltd.qubit.commons.text.Unicode.composeSurrogatePair;
import static ltd.qubit.commons.text.Unicode.decomposeHighSurrogate;
import static ltd.qubit.commons.text.Unicode.decomposeLowSurrogate;
import static ltd.qubit.commons.text.Unicode.isSupplementary;

/**
 * 提供UTF-16编码方案的实用工具。
 *
 * @author 胡海星
 */
public final class Utf16 {

  /**
   * 编码单个Unicode码点所需的最大代码单元数量。
   */
  public static final int MAX_CODE_UNIT_COUNT = 2;

  /**
   * 判断指定的UTF-16代码单元是否为编码有效码点的单个代码单元。
   *
   * <p>有效的Unicode码点可以编码为1到2个UTF-16代码单元，当且仅当UTF-16代码单元不是代理时，
   * 它是编码Unicode码点的单个代码单元。
   *
   * @param ch UTF-16代码单元
   * @return 如果指定的UTF-16代码单元是编码有效Unicode码点的单个代码单元则返回{@code true}；否则返回{@code false}
   */
  public static boolean isSingle(final char ch) {
    return (ch & SURROGATE_MASK) != SURROGATE_MIN;
  }

  /**
   * 判断指定的UTF-16代码单元是否为有效Unicode码点的前导代码单元。
   *
   * <p>有效的Unicode码点可以编码为1到2个UTF-16代码单元，当且仅当UTF-16代码单元是高代理时，
   * 它是Unicode码点的前导代码单元。
   *
   * @param ch UTF-16代码单元
   * @return 如果指定的UTF-16代码单元是有效Unicode码点的前导代码单元则返回{@code true}；否则返回{@code false}
   */
  public static boolean isLeading(final char ch) {
    return (ch & HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN;
  }

  /**
   * 判断指定的UTF-16代码单元是否为有效Unicode码点的尾随代码单元。
   *
   * <p>有效的Unicode码点可以编码为1到2个UTF-16代码单元，当且仅当UTF-16代码单元是低代理时，
   * 它是Unicode码点的尾随代码单元。
   *
   * @param ch UTF-16代码单元
   * @return 如果指定的UTF-16代码单元是有效Unicode码点的尾随代码单元则返回{@code true}；否则返回{@code false}
   */
  public static boolean isTrailing(final char ch) {
    return (ch & LOW_SURROGATE_MASK) == LOW_SURROGATE_MIN;
  }

  /**
   * 测试UTF-16代码单元是否为代理字符。
   *
   * @param ch 要测试的UTF-16代码单元
   * @return 如果UTF-16代码单元是代理字符则返回{@code true}；否则返回{@code false}
   */
  public static boolean isSurrogate(final char ch) {
    return (ch & SURROGATE_MASK) == SURROGATE_MIN;
  }

  /**
   * 测试两个UTF-16代码单元是否组成代理对。
   *
   * @param high 要当作高代理的UTF-16代码单元
   * @param low 要当作低代理的UTF-16代码单元
   * @return 如果两个UTF-16代码单元组成代理对则返回{@code true}；否则返回{@code false}
   */
  public static boolean isSurrogatePair(final char high, final char low) {
    return isLeading(high) && isTrailing(low);
  }

  /**
   * 组合UTF-16代码单元的代理对。
   *
   * @param high 表示高代理的UTF-16代码单元
   * @param low 表示低代理的UTF-16代码单元
   * @return 由指定代理对组成的Unicode字符的码点
   */
  public static int compose(final char high, final char low) {
    assert (isLeading(high) && isTrailing(low));
    return (high << HIGH_SURROGATE_SHIFT) + low - SURROGATE_COMPOSE_OFFSET;
  }

  /**
   * 分解补充Unicode码点的高代理UTF-16代码单元。
   *
   * @param codePoint 要分解的补充Unicode码点
   * @return 表示补充Unicode码点高代理的UTF-16代码单元
   */
  public static char decomposeHigh(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (char) ((codePoint >> HIGH_SURROGATE_SHIFT) + SURROGATE_DECOMPOSE_OFFSET);
  }

  /**
   * 分解补充Unicode码点的低代理UTF-16代码单元。
   *
   * @param codePoint 要分解的补充Unicode码点
   * @return 表示补充Unicode码点低代理的UTF-16代码单元
   */
  public static int decomposeLow(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (char) ((codePoint & SURROGATE_DECOMPOSE_MASK) | LOW_SURROGATE_MIN);
  }

  /**
   * 根据前导代码单元计算组成有效Unicode码点所需的尾随UTF-16代码单元的数量。
   *
   * @param ch UTF-16代码单元，必须是有效码点的前导代码单元
   * @return 组成有效Unicode码点所需的尾随代码单元数量（不包括前导代码单元本身）
   */
  public static int getTrailingCount(final char ch) {
    return ((ch & HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN ? 1 : 0);
  }

  /**
   * 获取编码指定Unicode码点所需的UTF-16代码单元数量。
   *
   * @param codePoint 码点，必须是有效的Unicode码点且不能是代理码点
   * @return 编码指定Unicode码点所需的UTF-16代码单元数量，范围在[1, MaxCount]内
   */
  public static int getCodeUnitCount(final int codePoint) {
    return (codePoint >= SUPPLEMENTARY_MIN ? 2 : 1);
  }

  /**
   * 将UTF-16代码单元序列中的随机访问偏移量调整到当前码点的起始位置。
   *
   * <p>更准确地说，如果偏移量指向尾随代码单元，则将偏移量向后移动到相应的前导代码单元；
   * 否则，不进行修改。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *          字符数组。
   * @param startIndex
   *          字符数组的开始索引。
   * @return 解析位置的减少量，如果解析位置未修改则返回0。成功调用此函数后，
   *         缓冲区的位置将减少此函数返回的值。如果缓冲区的当前位置指向非法代码单元序列
   *         或不完整的代码单元序列，函数将返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示非法代码单元序列；
   *         {@link ErrorCode#INCOMPLETE_UNICODE}表示不完整的代码单元序列，
   *         缓冲区的位置不会改变。
   * @throws IndexOutOfBoundsException
   *           如果startIndex &lt; 0或pos.getIndex() &lt; startIndex。
   */
  public static int setToStart(final ParsingPosition pos, final char[] buffer,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (! isTrailing(buffer[index])) {
      // buffer[current] is not a lower surrogate.
      return 0;
    } else if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    } else {
      --index;
      if (isLeading(buffer[index])) {
        // buffer[current] is a high surrogate
        pos.setIndex(index);
        return 1;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 将UTF-16代码单元序列中的随机访问偏移量调整到当前码点的起始位置。
   *
   * <p>更准确地说，如果偏移量指向尾随代码单元，则将偏移量向后移动到相应的前导代码单元；
   * 否则，不进行修改。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param str
   *          字符序列。
   * @param startIndex
   *          字符序列的开始索引。
   * @return 解析位置的减少量，如果解析位置未修改则返回0。成功调用此函数后，
   *         缓冲区的位置将减少此函数返回的值。如果缓冲区的当前位置指向非法代码单元序列
   *         或不完整的代码单元序列，函数将返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示非法代码单元序列；
   *         {@link ErrorCode#INCOMPLETE_UNICODE}表示不完整的代码单元序列，
   *         缓冲区的位置不会改变。
   * @throws IndexOutOfBoundsException
   *           如果startIndex &lt; 0或pos.getIndex() &lt; startIndex。
   */
  public static int setToStart(final ParsingPosition pos, final CharSequence str,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (! isTrailing(str.charAt(index))) {
      // buffer[current] is not a lower surrogate.
      return 0;
    } else if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    } else {
      --index;
      if (isLeading(str.charAt(index))) {
        // buffer[current] is a high surrogate
        pos.setIndex(index);
        return 1;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 将UTF-16代码单元序列中的随机访问偏移量调整到当前码点的结束位置。
   *
   * <p>更准确地说，如果偏移量指向码点的尾随代码单元，则将偏移量增加到
   * 该码点的整个代码单元序列之后；否则，不进行修改。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *          字符数组。
   * @param endIndex
   *          字符数组的结束索引。
   * @return 解析位置的增加量，如果解析位置未修改则返回0。成功调用此函数后，
   *         缓冲区的位置将增加此函数返回的值。如果缓冲区的当前位置指向非法代码单元序列
   *         或不完整的代码单元序列，函数将返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示非法代码单元序列；
   *         {@link ErrorCode#INCOMPLETE_UNICODE}表示不完整的代码单元序列。
   * @throws IndexOutOfBoundsException
   *           如果endIndex &gt; buffer.length或pos.getIndex() &gt; endIndex。
   *           注意，如果pos.getIndex() == endIndex，函数不执行任何操作并返回0。
   */
  public static int setToTerminal(final ParsingPosition pos, final char[] buffer,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    } else if (! isLeading(buffer[index])) {
      // buffer[current] is not a high surrogate
      return 0;
    } else {
      ++index;
      if (index >= endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      } else if (isTrailing(buffer[index])) {
        // buffer[current] is a low surrogate
        pos.setIndex(index);
        return 1;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 将UTF-16代码单元序列中的随机访问偏移量调整到当前码点的结束位置。
   *
   * <p>更准确地说，如果偏移量指向码点的尾随代码单元，则将偏移量增加到
   * 该码点的整个代码单元序列之后；否则，不进行修改。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param str
   *          字符序列。
   * @param endIndex
   *          字符序列的结束索引。
   * @return 解析位置的增加量，如果解析位置未修改则返回0。成功调用此函数后，
   *         缓冲区的位置将增加此函数返回的值。如果缓冲区的当前位置指向非法代码单元序列
   *         或不完整的代码单元序列，函数将返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示非法代码单元序列；
   *         {@link ErrorCode#INCOMPLETE_UNICODE}表示不完整的代码单元序列。
   * @throws IndexOutOfBoundsException
   *           如果endIndex &gt; str.length()或pos.getIndex() &gt; endIndex。
   *           注意，如果pos.getIndex() == endIndex，函数不执行任何操作并返回0。
   */
  public static int setToTerminal(final ParsingPosition pos,
      final CharSequence str, final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > str.length()) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    } else if (! isLeading(str.charAt(index))) {
      // buffer[current] is not a high surrogate
      return 0;
    } else {
      ++index;
      if (index >= endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      } else if (isTrailing(str.charAt(index))) {
        // buffer[current] is a low surrogate
        pos.setIndex(index);
        return 1;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 将UTF-16代码单元序列中的偏移量从一个码点边界向前推进到下一个码点边界。
   *
   * <p>偏移量必须指向有效码点开始的代码单元（即要么指向单个代码单元，要么指向前导代码单元），
   * 函数将偏移量推进到下一个代码单元的开始位置，返回跳过的代码单元数量。如果偏移量不指向
   * 有效码点开始的代码单元，或指向非法代码单元序列，函数将不执行任何操作但返回表示错误的负整数。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *          字符数组。
   * @param endIndex
   *          字符数组的结束索引。
   * @return 如果函数成功从指定偏移量的代码单元序列中跳过一个码点，返回组成被跳过码点的
   *         代码单元数量，并将缓冲区的位置向前移动到新位置；否则，如果指定偏移量不指向
   *         有效码点的前导代码单元，不执行任何操作并返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示指定偏移量不指向组成有效码点的
   *         合法代码单元序列的开始位置；{@link ErrorCode#INCOMPLETE_UNICODE}表示
   *         输入代码单元序列不完整，无法形成有效码点。
   * @throws IndexOutOfBoundsException
   *           如果endIndex &gt; buffer.length或pos.getIndex() &gt; endIndex。
   *           注意，如果pos.getIndex() == endIndex，函数不执行任何操作并返回0。
   */
  public static int forward(final ParsingPosition pos, final char[] buffer,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final char ch = buffer[index];
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return 1;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else {
      ++index;
      if (index == endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      } else if (isTrailing(buffer[index])) {
        pos.setIndex(index + 1);
        return 2;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 将UTF-16代码单元序列中的偏移量从一个码点边界向前推进到下一个码点边界。
   *
   * <p>偏移量必须指向有效码点开始的代码单元（即要么指向单个代码单元，要么指向前导代码单元），
   * 函数将偏移量推进到下一个代码单元的开始位置，返回跳过的代码单元数量。如果偏移量不指向
   * 有效码点开始的代码单元，或指向非法代码单元序列，函数将不执行任何操作但返回表示错误的负整数。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param str
   *          字符序列。
   * @param endIndex
   *          字符序列的结束索引。
   * @return 如果函数成功从指定偏移量的代码单元序列中跳过一个码点，返回组成被跳过码点的
   *         代码单元数量，并将缓冲区的位置向前移动到新位置；否则，如果指定偏移量不指向
   *         有效码点的前导代码单元，不执行任何操作并返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示指定偏移量不指向组成有效码点的
   *         合法代码单元序列的开始位置；{@link ErrorCode#INCOMPLETE_UNICODE}表示
   *         输入代码单元序列不完整，无法形成有效码点。
   * @throws IndexOutOfBoundsException
   *           如果endIndex &gt; buffer.length或pos.getIndex() &gt; endIndex。
   *           注意，如果pos.getIndex() == endIndex，函数不执行任何操作并返回0。
   */
  public static int forward(final ParsingPosition pos, final CharSequence str,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > str.length()) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final char ch = str.charAt(index);
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return 1;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else {
      ++index;
      if (index == endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      } else if (isTrailing(str.charAt(index))) {
        pos.setIndex(index + 1);
        return 2;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 将UTF-16代码单元序列中的偏移量从一个码点边界向后退到前一个码点边界。
   *
   * <p>偏移量必须指向有效码点结束后的位置（即要么指向单个代码单元，要么指向前导代码单元，
   * 要么指向整个代码单元序列最后一个代码单元之后的位置），函数将偏移量后退到前一个代码单元的
   * 开始位置，返回经过的代码单元数量。如果偏移量不指向有效码点结束后的位置，
   * 或前一个代码单元序列非法，函数将不执行任何操作但返回表示错误的负整数。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *          字符数组。
   * @param startIndex
   *          字符数组的开始索引。
   * @return 如果函数成功从指定偏移量的代码单元序列中向后经过一个码点，返回组成经过码点的
   *         代码单元数量，并将缓冲区的位置向后移动到新位置；否则，如果指定偏移量不指向
   *         有效码点的前导代码单元，不执行任何操作并返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示指定偏移量不指向有效码点结束的
   *         代码单元的下一个位置；{@link ErrorCode#INCOMPLETE_UNICODE}表示输入
   *         代码单元序列不完整，无法形成有效码点。
   * @throws IndexOutOfBoundsException
   *           如果startIndex &lt; 0或pos.getIndex() &lt; startIndex。
   *           注意，如果pos.getIndex() == startIndex，函数不执行任何操作并返回0。
   */
  public static int backward(final ParsingPosition pos, final char[] buffer,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    --index;
    final char ch = buffer[index];
    if (isSingle(ch)) {
      pos.setIndex(index);
      return 1;
    } else if (! isTrailing(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
    if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    }
    --index;
    if (isLeading(buffer[index])) {
      pos.setIndex(index);
      return 2;
    } else {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
  }

  /**
   * 将UTF-16代码单元序列中的偏移量从一个码点边界向后退到前一个码点边界。
   *
   * <p>偏移量必须指向有效码点结束后的位置（即要么指向单个代码单元，要么指向前导代码单元，
   * 要么指向整个代码单元序列最后一个代码单元之后的位置），函数将偏移量后退到前一个代码单元的
   * 开始位置，返回经过的代码单元数量。如果偏移量不指向有效码点结束后的位置，
   * 或前一个代码单元序列非法，函数将不执行任何操作但返回表示错误的负整数。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param str
   *          字符序列。
   * @param startIndex
   *          字符序列的开始索引。
   * @return 如果函数成功从指定偏移量的代码单元序列中向后经过一个码点，返回组成经过码点的
   *         代码单元数量，并将缓冲区的位置向后移动到新位置；否则，如果指定偏移量不指向
   *         有效码点的前导代码单元，不执行任何操作并返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示指定偏移量不指向有效码点结束的
   *         代码单元的下一个位置；{@link ErrorCode#INCOMPLETE_UNICODE}表示输入
   *         代码单元序列不完整，无法形成有效码点。
   * @throws IndexOutOfBoundsException
   *           如果startIndex &lt; 0或pos.getIndex() &lt; startIndex。
   *           注意，如果pos.getIndex() == startIndex，函数不执行任何操作并返回0。
   */
  public static int backward(final ParsingPosition pos, final CharSequence str,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    --index;
    final char ch = str.charAt(index);
    if (isSingle(ch)) {
      pos.setIndex(index);
      return 1;
    } else if (! isTrailing(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
    if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    }
    --index;
    if (isLeading(str.charAt(index))) {
      pos.setIndex(index);
      return 2;
    } else {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
  }

  /**
   * 从码点边界偏移量处的UTF-16代码单元序列中获取一个码点。
   *
   * <p>偏移量必须指向有效码点开始的代码单元（即单个代码单元，或有效码点的前导代码单元）。
   * 函数读取组成该码点的所有代码单元，并返回已读取的代码单元数量。如果偏移量不指向
   * 有效码点的起始代码单元，或指向非法代码单元序列，函数将返回表示错误的负整数。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *          字符数组。
   * @param endIndex
   *          字符数组的结束索引。
   * @return 如果函数成功从指定偏移量的代码单元序列中获取一个码点，返回码点的值
   *         （非负数）；否则，返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示指定偏移量不指向组成有效码点的
   *         合法代码单元序列的开始位置；{@link ErrorCode#INCOMPLETE_UNICODE}表示
   *         输入代码单元序列不完整，无法形成有效码点。
   * @throws IndexOutOfBoundsException
   *           如果endIndex &gt; buffer.length或pos.getIndex() &gt; endIndex。
   *           注意，如果pos.getIndex() == endIndex，函数不执行任何操作并返回0。
   */
  public static int getNext(final ParsingPosition pos, final char[] buffer,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final char ch = buffer[index];
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return ch;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else {
      ++index;
      if (index >= endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      }
      final char next_ch = buffer[index];
      if (isTrailing(next_ch)) {
        final int codePoint = composeSurrogatePair(ch, next_ch);
        pos.setIndex(index + 1);
        return codePoint;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 从码点边界偏移量处的UTF-16代码单元序列中获取一个码点。
   *
   * <p>偏移量必须指向有效码点开始的代码单元（即单个代码单元，或有效码点的前导代码单元）。
   * 函数读取组成该码点的所有代码单元，并返回已读取的代码单元数量。如果偏移量不指向
   * 有效码点的起始代码单元，或指向非法代码单元序列，函数将返回表示错误的负整数。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param str
   *          字符序列。
   * @param endIndex
   *          字符序列的结束索引。
   * @return 如果函数成功从指定偏移量的代码单元序列中获取一个码点，返回码点的值
   *         （非负数）；否则，返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示指定偏移量不指向组成有效码点的
   *         合法代码单元序列的开始位置；{@link ErrorCode#INCOMPLETE_UNICODE}表示
   *         输入代码单元序列不完整，无法形成有效码点。
   * @throws IndexOutOfBoundsException
   *           如果endIndex &gt; buffer.length或pos.getIndex() &gt; endIndex。
   *           注意，如果pos.getIndex() == endIndex，函数不执行任何操作并返回0。
   */
  public static int getNext(final ParsingPosition pos, final CharSequence str,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > str.length()) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final char ch = str.charAt(index);
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return ch;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else {
      ++index;
      if (index >= endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      }
      final char next_ch = str.charAt(index);
      if (isTrailing(next_ch)) {
        final int codePoint = composeSurrogatePair(ch, next_ch);
        pos.setIndex(index + 1);
        return codePoint;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 将UTF-16代码单元序列的偏移量从一个码点边界移动到前一个码点边界，并获取它们之间的码点。
   *
   * <p>输入偏移量必须指向有效码点结束代码单元后的位置（要么是下一个码点的起始代码单元，
   * 要么是整个代码单元序列的末尾）。函数将偏移量移动到前一个码点的起始代码单元，
   * 读取该码点，然后返回组成该码点的代码单元数量（也是偏移量减少的量）。如果偏移量不指向
   * 有效码点结束代码单元后的位置，或前一个代码单元序列非法，函数将返回表示错误的负整数。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *          字符数组。
   * @param startIndex
   *          字符数组的开始索引。
   * @return 如果函数成功从指定索引的代码单元序列中获取一个码点，返回码点的值
   *         （非负数）；否则，返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示指定偏移量不指向组成有效码点的
   *         合法代码单元序列结束位置的下一个位置；{@link ErrorCode#INCOMPLETE_UNICODE}
   *         表示输入代码单元序列不完整，无法形成有效的Unicode码点。
   * @throws IndexOutOfBoundsException
   *           如果startIndex &lt; 0或pos.getIndex() &lt; startIndex。
   *           注意，如果pos.getIndex() == startIndex，函数不执行任何操作并返回0。
   */
  public static int getPrevious(final ParsingPosition pos, final char[] buffer,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    --index;
    final char ch1 = buffer[index];
    if (isSingle(ch1)) {
      pos.setIndex(index);
      return ch1;
    } else if (! isTrailing(ch1)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    } else {
      --index;
      final char ch2 = buffer[index];
      if (isLeading(ch2)) {
        final int codePoint = composeSurrogatePair(ch2, ch1);
        pos.setIndex(index);
        return codePoint;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 将UTF-16代码单元序列的偏移量从一个码点边界移动到前一个码点边界，并获取它们之间的码点。
   *
   * <p>输入偏移量必须指向有效码点结束代码单元后的位置（要么是下一个码点的起始代码单元，
   * 要么是整个代码单元序列的末尾）。函数将偏移量移动到前一个码点的起始代码单元，
   * 读取该码点，然后返回组成该码点的代码单元数量（也是偏移量减少的量）。如果偏移量不指向
   * 有效码点结束代码单元后的位置，或前一个代码单元序列非法，函数将返回表示错误的负整数。
   *
   * @param pos
   *          索引解析位置；调用此函数后，将设置为新位置。
   * @param str
   *          字符序列。
   * @param startIndex
   *          字符序列的开始索引。
   * @return 如果函数成功从指定索引的代码单元序列中获取一个码点，返回码点的值
   *         （非负数）；否则，返回表示错误的负整数：
   *         ErrorCode::Malformed表示指定偏移量不指向组成有效码点的合法代码单元序列
   *         结束位置的下一个位置；ErrorCode::Incomplete表示输入代码单元序列不完整，
   *         无法形成有效的Unicode码点。
   * @throws IndexOutOfBoundsException
   *           如果startIndex &lt; 0或pos.getIndex() &lt; startIndex。
   *           注意，如果pos.getIndex() == startIndex，函数不执行任何操作并返回0。
   */
  public static int getPrevious(final ParsingPosition pos,
      final CharSequence str, final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    --index;
    final char ch1 = str.charAt(index);
    if (isSingle(ch1)) {
      pos.setIndex(index);
      return ch1;
    } else if (! isTrailing(ch1)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    } else {
      --index;
      final char ch2 = str.charAt(index);
      if (isLeading(ch2)) {
        final int codePoint = composeSurrogatePair(ch2, ch1);
        pos.setIndex(index);
        return codePoint;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * 将一个码点放入UTF-16代码单元缓冲区，写入1到2个代码单元。
   *
   * <p>current指向放置码点的位置，放置码点后向前推进1到2个位置。如果码点无效
   * 或缓冲区空间不足，函数返回0。
   *
   * @param codePoint
   *          要放入的码点。
   * @param index
   *          缓冲区中放置码点的索引。必须在[0, buffer.length)范围内。
   * @param buffer
   *          UTF-16代码单元缓冲区。
   * @param endIndex
   *          字符数组的结束索引。
   * @return 如果码点成功放入缓冲区，返回放入缓冲区的代码单元数量；
   *         否则，返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示指定的码点不是有效的Unicode码点，
   *         或是代理字符；{@link ErrorCode#BUFFER_OVERFLOW}表示代码单元缓冲区
   *         没有足够的空间来保存结果。
   * @throws IndexOutOfBoundsException
   *           如果endIndex &gt; buffer.length或current &gt; endIndex。
   *           注意，如果current == endIndex，函数将返回ErrorCode.OVERFLOW。
   */
  public static int put(final int codePoint, final int index,
      final char[] buffer, final int endIndex) {
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return BUFFER_OVERFLOW;
    }
    if (codePoint < SUPPLEMENTARY_MIN) {
      buffer[index] = (char) codePoint;
      return 1;
    } else if (codePoint > UNICODE_MAX) {
      return MALFORMED_UNICODE;
    } else {
      final int next_index = index + 1;
      if (next_index >= endIndex) {
        return BUFFER_OVERFLOW;
      } else {
        buffer[index] = (char) decomposeHighSurrogate(codePoint);
        buffer[next_index] = (char) decomposeLowSurrogate(codePoint);
        return 2;
      }
    }
  }

  /**
   * 将一个码点放入UTF-16代码单元缓冲区，写入1到2个代码单元。
   *
   * <p>current指向放置码点的位置，放置码点后向前推进1到2个位置。如果码点无效
   * 或缓冲区空间不足，函数返回0。
   *
   * @param codePoint
   *          要放入的码点。
   * @param buffer
   *          用于存储UTF-16代码单元的{@link Appendable}对象。
   * @return 如果码点成功放入缓冲区，返回放入缓冲区的代码单元数量；
   *         否则，返回表示错误的负整数：
   *         {@link ErrorCode#MALFORMED_UNICODE}表示指定的码点不是有效的Unicode码点，
   *         或是代理字符；{@link ErrorCode#BUFFER_OVERFLOW}表示代码单元缓冲区
   *         没有足够的空间来保存结果。
   * @throws IOException
   *           如果发生任何I/O错误。
   */
  public static int put(final int codePoint, final Appendable buffer)
      throws IOException {
    if (codePoint < SUPPLEMENTARY_MIN) {
      buffer.append((char) codePoint);
      return 1;
    } else if (codePoint > UNICODE_MAX) {
      return MALFORMED_UNICODE;
    } else {
      buffer.append((char) decomposeHighSurrogate(codePoint));
      buffer.append((char) decomposeLowSurrogate(codePoint));
      return 2;
    }
  }

  /**
   * 将Unicode码点转义为Java/JavaScript风格的Unicode转义序列。
   *
   * <p>对于基本多文种平面（BMP）中的码点，生成形如{@code \\uXXXX}的转义序列；
   * 对于补充字符，生成两个转义序列，分别对应高、低代理字符。
   *
   * @param codePoint
   *          要转义的Unicode码点。
   * @param appendable
   *          用于输出转义序列的{@link Appendable}对象。
   * @throws IOException
   *           如果写入过程中发生I/O错误。
   */
  public static void escape(final int codePoint, final Appendable appendable)
      throws IOException {
    if (codePoint < SUPPLEMENTARY_MIN) {
      appendable.append("\\u");
      toHex(codePoint, appendable);
    } else {
      appendable.append("\\u");
      toHex(decomposeHighSurrogate(codePoint), appendable);
      appendable.append("\\u");
      toHex(decomposeLowSurrogate(codePoint), appendable);
    }
  }

  /**
   * 将Unicode码点转义为Java/JavaScript风格的Unicode转义序列字符串。
   *
   * <p>对于基本多文种平面（BMP）中的码点，生成形如{@code \\uXXXX}的转义序列；
   * 对于补充字符，生成两个转义序列，分别对应高、低代理字符。
   *
   * @param codePoint
   *          要转义的Unicode码点。
   * @return 包含转义序列的字符串。
   */
  public static String escape(final int codePoint) {
    final StringBuilder builder = new StringBuilder();
    try {
      escape(codePoint, builder);
    } catch (final IOException e) {
      // should never throw
      throw new UncheckedIOException(e);
    }
    return builder.toString();
  }

  /**
   * 将整数值转换为4位十六进制字符串。
   *
   * @param value
   *          要转换的整数值。
   * @param appendable
   *          用于输出十六进制字符的{@link Appendable}对象。
   * @throws IOException
   *           如果写入过程中发生I/O错误。
   */
  private static void toHex(final int value, final Appendable appendable)
      throws IOException {
    // the last 4 HEX digits should always be output
    // stop checkstyle: MagicNumber
    appendable.append(UPPERCASE_DIGITS[(value >>> 12) & 0xF]);
    appendable.append(UPPERCASE_DIGITS[(value >>> 8) & 0xF]);
    appendable.append(UPPERCASE_DIGITS[(value >>> 4) & 0xF]);
    appendable.append(UPPERCASE_DIGITS[value & 0x0F]);
    // resume checkstyle: MagicNumber
  }
}