////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.math.ByteBit;
import ltd.qubit.commons.math.IntBit;

/**
 * 提供UTF-8编码方案的实用工具。
 *
 * @author 胡海星
 */
public final class Utf8 {
  // stop checkstyle: MagicNumberCheck

  /**
   * 编码单个Unicode码点所需的最大代码单元数量。
   */
  public static final int MAX_CODE_UNIT_COUNT = 4;

  /**
   * 最小前导UTF-8代码单元。
   *
   * <p>所有前导UTF-8代码单元都具有以下形式：
   * <pre>
   *   11000010-11011111 C2-DF 194-223 开始2字节序列
   *   11100000-11101111 E0-EF 224-239 开始3字节序列
   *   11110000-11110100 F0-F4 240-244 开始4字节序列
   * </pre>
   *
   * <p>因此，最小前导UTF-8代码单元为0xC2。
   *
   * @see <a href="http://en.wikipedia.org/wiki/UTF-8">UTF-8</a>
   */
  public static final int MIN_LEADING = 0xC2;

  /**
   * 最大前导UTF-8代码单元。
   */
  public static final int MAX_LEADING = 0xF4;

  /**
   * 尾随UTF-8代码单元的掩码。
   *
   * <p>所有尾随UTF-8代码单元都具有以下形式：
   * <pre>
   *   10xxxxxx
   * </pre>
   *
   * <p>因此，尾随UTF-8代码单元的掩码为0xC0。
   *
   * @see <a href="http://en.wikipedia.org/wiki/UTF-8">UTF-8</a>
   */
  public static final int TRAILING_MASK = 0xC0;

  /**
   * 尾随UTF-8代码单元的模式。
   */
  public static final int TRAILING_PATTERN = 0x80;

  /**
   * 尾随UTF-8代码单元数量减数。
   *
   * <p>尾随UTF-8代码单元数量减数为6，即尾随UTF-8代码单元数量为6 - k。因此，尾随UTF-8代码单元数量减数为6。
   *
   * 令k为从最高有效位开始第一个0的当前值（最高有效位有当前值7），则：
   * <ul>
   *   <li>如果k = 7，ch是一个单个代码单元，尾随UTF-8代码单元数量为0。</li>
   *   <li>如果k = 6，ch不是一个有效的UTF-8前导代码单元。</li>
   *   <li>如果k = 5, 4, 或3，尾随UTF-8代码单元数量为6 - k。</li>
   *   <li>否则，ch不是一个有效的UTF-8前导代码单元。</li>
   * </ul>
   *
   * @see <a href="http://en.wikipedia.org/wiki/UTF-8">UTF-8</a>
   */
  public static final int TRAILING_COUNT_MINUEND = 6;

  /**
   * 最大单个UTF-8代码单元。
   */
  public static final int MAX_ONE_CODE_UNIT = 0x7F;

  /**
   * 最大两个UTF-8代码单元。
   */
  public static final int MAX_TWO_CODE_UNIT = 0x7FF;

  /**
   * 最大三个UTF-8代码单元。
   */
  public static final int MAX_THREE_CODE_UNIT = 0xFFFF;

  /**
   * 最大四个UTF-8代码单元。
   */
  public static final int MAX_FOUR_CODE_UNIT = 0x10FFFF;

  /**
   * 代理码点的掩码。
   * <p>
   * 如果ch是Unicode码点，则ch是代理码点当且仅当
   * {@code (ch & SurrogateMask) == SurrogatePattern}。
   */
  public static final int SURROGATE_MASK = 0xFFFFF800;

  /**
   * 判断指定的UTF-8代码单元是否为编码有效码点的单个代码单元。
   *
   * <p>有效的Unicode码点可以编码为1到4个UTF-8代码单元，当且仅当UTF-8代码单元的值小于0x80时，
   * 它是编码Unicode码点的单个代码单元。
   *
   * @param ch UTF-8代码单元
   * @return 如果指定的UTF-8代码单元是编码有效Unicode码点的单个代码单元则返回{@code true}；否则返回{@code false}
   */
  public static boolean isSingle(final byte ch) {
    final int codePoint = (ch & 0xFF);
    return codePoint <= MAX_ONE_CODE_UNIT;
  }

  /**
   * 判断指定的UTF-8代码单元是否为编码有效码点的单个代码单元。
   *
   * <p>有效的Unicode码点可以编码为1到4个UTF-8代码单元，当且仅当UTF-8代码单元的值小于0x80时，
   * 它是编码Unicode码点的单个代码单元。
   *
   * @param ch 作为无符号整数的UTF-8代码单元
   * @return 如果指定的UTF-8代码单元是编码有效Unicode码点的单个代码单元则返回{@code true}；否则返回{@code false}
   */
  public static boolean isSingle(final int ch) {
    return ch <= MAX_ONE_CODE_UNIT;
  }

  /**
   * 判断指定的UTF-8代码单元是否为有效Unicode码点的前导代码单元。
   *
   * <p>有效的Unicode码点可以编码为1到4个UTF-8代码单元，当且仅当UTF-8代码单元的位以110、1110或11110为前缀时
   * （即它的二进制形式为110xxxxx、1110xxxx或11110xxx），它是Unicode码点的前导代码单元。
   *
   * @param ch UTF-8代码单元
   * @return 如果指定的UTF-8代码单元是有效Unicode码点的前导代码单元则返回{@code true}；否则返回{@code false}
   */
  public static boolean isLeading(final byte ch) {
    // http://en.wikipedia.org/wiki/UTF-8
    //
    // 11000010-11011111 C2-DF 194-223 Start of 2-byte sequence
    // 11100000-11101111 E0-EF 224-239 Start of 3-byte sequence
    // 11110000-11110100 F0-F4 240-244 Start of 4-byte sequence
    //
    final int codePoint = (ch & 0xFF);
    return (codePoint - MIN_LEADING) <= (MAX_LEADING - MIN_LEADING);
  }

  /**
   * 判断指定的UTF-8代码单元是否为有效Unicode码点的前导代码单元。
   *
   * <p>有效的Unicode码点可以编码为1到4个UTF-8代码单元，当且仅当UTF-8代码单元的位以110、1110或11110为前缀时
   * （即它的二进制形式为110xxxxx、1110xxxx或11110xxx），它是Unicode码点的前导代码单元。
   *
   * @param ch 作为无符号整数的UTF-8代码单元
   * @return 如果指定的UTF-8代码单元是有效Unicode码点的前导代码单元则返回{@code true}；否则返回{@code false}
   */
  public static boolean isLeading(final int ch) {
    // http://en.wikipedia.org/wiki/UTF-8
    //
    // 11000010-11011111 C2-DF 194-223 Start of 2-byte sequence
    // 11100000-11101111 E0-EF 224-239 Start of 3-byte sequence
    // 11110000-11110100 F0-F4 240-244 Start of 4-byte sequence
    //
    return (ch - MIN_LEADING) <= (MAX_LEADING - MIN_LEADING);
  }

  /**
   * 判断指定的UTF-8代码单元是否为有效Unicode码点的尾随代码单元。
   *
   * <p>有效的Unicode码点可以编码为1到4个UTF-8代码单元，当且仅当UTF-8代码单元的位以10为前缀时
   * （即它的二进制形式为10xxxxxx，或等价地，它在[0x80, 0xBF]之间），它是Unicode码点的尾随代码单元。
   *
   * @param ch UTF-8代码单元
   * @return 如果指定的UTF-8代码单元是有效Unicode码点的尾随代码单元则返回{@code true}；否则返回{@code false}
   */
  public static boolean isTrailing(final byte ch) {
    // http://en.wikipedia.org/wiki/UTF-8
    //
    // All trailing UTF-8 code units has the form of
    //
    // 10xxxxxx
    //
    // i.e., the most significant 2 bits are 1 and 0.
    //
    final int codePoint = (ch & 0xFF);
    return (codePoint & TRAILING_MASK) == TRAILING_PATTERN;
  }

  /**
   * 判断指定的UTF-8代码单元是否为有效Unicode码点的尾随代码单元。
   *
   * <p>有效的Unicode码点可以编码为1到4个UTF-8代码单元，当且仅当UTF-8代码单元的位以10为前缀时
   * （即它的二进制形式为10xxxxxx，或等价地，它在[0x80, 0xBF]之间），它是Unicode码点的尾随代码单元。
   *
   * @param ch 作为无符号整数的UTF-8代码单元
   * @return 如果指定的UTF-8代码单元是有效Unicode码点的尾随代码单元则返回{@code true}；否则返回{@code false}
   */
  public static boolean isTrailing(final int ch) {
    // http://en.wikipedia.org/wiki/UTF-8
    //
    // All trailing UTF-8 code units has the form of
    //
    // 10xxxxxx
    //
    // i.e., the most significant 2 bits are 1 and 0.
    //
    return (ch & TRAILING_MASK) == TRAILING_PATTERN;
  }

  /**
   * 根据前导代码单元计算组成有效Unicode码点所需的尾随UTF-8代码单元的数量。
   *
   * @param ch UTF-8代码单元，必须是有效码点的前导代码单元
   * @return 组成有效Unicode码点所需的尾随代码单元数量（不包括前导代码单元本身）
   */
  public static int getTrailingCount(final byte ch) {
    // let k be the current of the first 0 of ch from the most significant bit
    // (the most significant bit has the current of 7), then
    //
    // - if k = 7, the leadingCodeUnit is a single code unit, and the number of
    // trailing UTF-8 code units is 0.
    // - if k = 6, the leadingCodeUnit is NOT a valid leading UTF-8 code unit.
    // - if k = 5, 4, or 3, the number of trailing UTF-8 code units is 6 - k.
    // - otherwise, the leadingCodeUnit is NOT a valid leading UTF-8 code unit.
    //
    assert isLeading(ch);
    final int lastZero = ByteBit.findLastUnset(ch);
    return (TRAILING_COUNT_MINUEND - lastZero);
  }

  /**
   * 根据前导代码单元计算组成有效Unicode码点所需的尾随UTF-8代码单元的数量。
   *
   * @param ch 作为无符号整数的UTF-8代码单元，必须是有效码点的前导代码单元
   * @return 组成有效Unicode码点所需的尾随代码单元数量（不包括前导代码单元本身）
   */
  public static int getTrailingCount(final int ch) {
    // let k be the current of the first 0 of ch from the most significant bit
    // (the most significant bit has the current of 7), then
    //
    // - if k = 7, the leadingCodeUnit is a single code unit, and the number of
    // trailing UTF-8 code units is 0.
    // - if k = 6, the leadingCodeUnit is NOT a valid leading UTF-8 code unit.
    // - if k = 5, 4, or 3, the number of trailing UTF-8 code units is 6 - k.
    // - otherwise, the leadingCodeUnit is NOT a valid leading UTF-8 code unit.
    //
    assert isLeading(ch);
    final int lastZero = IntBit.findLastUnset(ch);
    return (TRAILING_COUNT_MINUEND - lastZero);
  }

  /**
   * 获取编码指定Unicode码点所需的UTF-8代码单元数量。
   *
   * @param codePoint 码点，必须是有效的Unicode码点且不能是代理码点
   * @return 编码指定Unicode码点所需的UTF-8代码单元数量，范围在[1, MaxCount]内
   */
  public static int getCodeUnitCount(final int codePoint) {
    // the codePoint must be a valid Unicode code point and not a surrogate.
    assert ((codePoint <= Unicode.UNICODE_MAX)
        && ((codePoint & SURROGATE_MASK) != Unicode.SURROGATE_MIN));
    if (codePoint <= MAX_ONE_CODE_UNIT) {
      return 1;
    } else if (codePoint <= MAX_TWO_CODE_UNIT) {
      return 2;
    } else if (codePoint <= MAX_THREE_CODE_UNIT) {
      return 3;
    } else {
      return 4;
    }
  }

  /**
   * 将UTF-8代码单元序列中的随机访问偏移量调整到当前码点的起始位置。
   *
   * <p>更准确地说，如果偏移量指向尾随代码单元，则将偏移量向后移动到相应的前导代码单元；
   * 否则，不进行修改。
   *
   * @param pos
   *     索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *     字节数组。
   * @param startIndex
   *     字节数组的开始索引。
   * @return 参数位置的减少量，如果参数位置未修改则返回0。成功调用此函数后，
   *     缓冲区的位置将减少此函数返回的值。如果缓冲区的当前位置指向非法代码单元序列
   *     或不完整的代码单元序列，函数将返回表示错误的负整数：
   *     {@link ErrorCode#MALFORMED_UNICODE}表示非法代码单元序列；
   *     {@link ErrorCode#INCOMPLETE_UNICODE}表示不完整的代码单元序列，
   *     缓冲区的位置不会改变。
   * @throws IndexOutOfBoundsException
   *     如果startIndex &lt; 0或current.value &lt; startIndex。如果current.value
   *     == startIndex，函数将检查startIndex处的字节是否为尾随字节，如果是，
   *     将返回INCOMPLETE_UNICODE；否则返回0。
   */
  public static int setToStart(final ParsingPosition pos, final byte[] buffer,
      final int startIndex) {
    final int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (! isTrailing(buffer[index])) {
      return 0;
    }
    for (int i = index - 1; i >= startIndex; --i) {
      final byte ch = buffer[i];
      if (isLeading(ch)) {
        final int count = index - i;
        if (count > getTrailingCount(ch)) {
          pos.setErrorIndex(i);
          pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
          return ErrorCode.MALFORMED_UNICODE;
        } else {
          pos.setIndex(i);
          return count;
        }
      } else if (! isTrailing(ch)) {
        pos.setErrorIndex(i);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      } else if (index - i >= (MAX_CODE_UNIT_COUNT - 1)) {
        // the code unit sequence is too long
        pos.setErrorIndex(i);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
    }
    pos.setErrorIndex(startIndex);
    pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
    return ErrorCode.INCOMPLETE_UNICODE;
  }

  /**
   * 将UTF-8代码单元序列中的随机访问偏移量调整到当前码点的结束位置。
   *
   * <p>更准确地说，如果偏移量指向码点的尾随代码单元，则将偏移量增加到
   * 该码点的整个代码单元序列之后；否则，不进行修改。
   *
   * @param pos
   *     索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *     字节数组。
   * @param endIndex
   *     字节数组的结束索引。
   * @return 参数位置的增加量，如果参数位置未修改则返回0。成功调用此函数后，
   *     缓冲区的位置将增加此函数返回的值。如果缓冲区的当前位置指向非法代码单元序列
   *     或不完整的代码单元序列，函数将返回表示错误的负整数：
   *     {@link ErrorCode#MALFORMED_UNICODE}表示非法代码单元序列；
   *     {@link ErrorCode#INCOMPLETE_UNICODE}表示不完整的代码单元序列。
   * @throws IndexOutOfBoundsException
   *     如果endIndex &gt; buffer.length或current.value &gt; endIndex。
   *     注意，如果current.value == endIndex，函数不执行任何操作并返回0。
   */
  public static int setToTerminal(final ParsingPosition pos, final byte[] buffer,
      final int endIndex) {
    final int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == buffer.length) {
      return 0;
    }
    final byte ch = buffer[index];
    if (! isLeading(ch)) {
      return 0;
    }
    final int trailingCount = getTrailingCount(ch);
    for (int i = index + 1; i < endIndex; ++i) {
      final int count = i - index;
      if (count >= trailingCount) {
        // the code unit sequence is too long
        pos.setErrorIndex(i);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
      if (! isTrailing(buffer[i])) {
        pos.setIndex(i);
        return count;
      }
    }
    pos.setErrorIndex(endIndex);
    pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
    return ErrorCode.INCOMPLETE_UNICODE;
  }

  /**
   * 将UTF-8代码单元序列的偏移量从一个码点边界推进到下一个码点边界。
   *
   * <p>偏移量必须指向有效码点开始的代码单元（即，它要么指向单个代码单元，
   * 要么指向前导代码单元），该函数将偏移量推进到下一个代码单元的开始位置，
   * 返回跳过的代码单元数量。如果偏移量没有指向有效码点开始的代码单元，
   * 或者它指向非法代码单元序列，函数不执行任何操作并返回表示错误的负整数。
   *
   * @param pos
   *     索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *     字节数组。
   * @param endIndex
   *     字节数组的结束索引。
   * @return 如果函数成功从指定偏移量的代码单元序列中跳过一个码点，
   *     返回组成被跳过码点的代码单元数量，并将缓冲区位置向前移动到新位置；
   *     否则，如果指定偏移量没有指向有效码点的前导代码单元，不执行任何操作
   *     并返回表示错误的负整数：{@link ErrorCode#MALFORMED_UNICODE}
   *     表示指定偏移量没有指向组成有效码点的合法代码单元序列的开始；
   *     {@link ErrorCode#INCOMPLETE_UNICODE}表示输入代码单元序列
   *     不完整，无法形成有效码点。
   * @throws IndexOutOfBoundsException
   *     如果endIndex &gt; buffer.length或current.value &gt; endIndex。
   *     注意，如果current.value == endIndex，函数不执行任何操作并返回0。
   */
  public static int forward(final ParsingPosition pos, final byte[] buffer,
      final int endIndex) {
    final int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    byte ch = buffer[index];
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return 1;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
      return ErrorCode.MALFORMED_UNICODE;
    }
    final int count = 1 + getTrailingCount(ch);
    final int nextIndex = index + count;
    if (nextIndex > endIndex) {
      pos.setErrorIndex(endIndex);
      pos.setErrorCode(ErrorCode.INCOMPLETE_UNICODE);
      return ErrorCode.INCOMPLETE_UNICODE;
    }
    for (int i = index + 1; i < nextIndex; ++i) {
      ch = buffer[i];
      if (! isTrailing(ch)) {
        pos.setErrorIndex(i);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
    }
    pos.setIndex(nextIndex);
    return count;
  }

  /**
   * 将UTF-8代码单元序列的偏移量从一个码点边界递减到前一个码点边界。
   *
   * <p>偏移量必须指向有效码点结束后的下一个位置（即，它要么指向单个代码单元，
   * 要么指向前导代码单元，要么指向整个代码单元序列最后一个代码单元之后的位置），
   * 该函数将偏移量递减到前一个代码单元的开始位置，返回经过的代码单元数量。
   * 如果偏移量没有指向有效码点结束后的下一个位置，或者前一个代码单元序列是非法的，
   * 函数不执行任何操作并返回表示错误的负整数。
   *
   * @param pos
   *     索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *     字节数组。
   * @param startIndex
   *     字节数组的开始索引。
   * @return 如果函数成功从指定偏移量的代码单元序列中越过前一个码点，
   *     返回组成被越过码点的代码单元数量，并将缓冲区位置向后移动到新位置；
   *     否则，如果指定偏移量没有指向有效码点的前导代码单元，不执行任何操作
   *     并返回表示错误的负整数：{@link ErrorCode#MALFORMED_UNICODE}
   *     表示指定偏移量没有指向有效码点结束的代码单元的下一个位置；
   *     {@link ErrorCode#INCOMPLETE_UNICODE}表示输入代码单元序列
   *     不完整，无法形成有效码点。
   * @throws IndexOutOfBoundsException
   *     如果startIndex &lt; 0或current.value &lt; startIndex。
   *     注意，如果current.value == startIndex，函数不执行任何操作并返回0。
   */
  public static int backward(final ParsingPosition pos, final byte[] buffer,
      final int startIndex) {
    final int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    final int prev_index = index - 1;
    assert (prev_index >= startIndex);
    byte ch = buffer[prev_index];
    if (isSingle(ch)) {
      pos.setIndex(prev_index);
      return 1;
    } else if (! isTrailing(ch)) {
      pos.setErrorIndex(prev_index);
      pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
      return ErrorCode.MALFORMED_UNICODE;
    }
    for (int i = prev_index - 1; i >= startIndex; --i) {
      ch = buffer[i];
      if (isLeading(ch)) {
        final int count = index - i;
        if (count != getTrailingCount(ch) + 1) {
          pos.setErrorIndex(i);
          pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
          return ErrorCode.MALFORMED_UNICODE;
        } else {
          pos.setIndex(i);
          return count;
        }
      } else if (! isTrailing(ch)) {
        pos.setErrorIndex(i);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      } else if (index - i >= MAX_CODE_UNIT_COUNT) {
        // the code unit sequence is too long
        pos.setErrorIndex(i);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
    }
    pos.setErrorIndex(startIndex);
    pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
    return ErrorCode.INCOMPLETE_UNICODE;
  }

  /**
   * 从UTF-8代码单元序列的码点边界偏移量处获取一个码点。
   *
   * <p>偏移量必须指向有效码点开始的代码单元（即，要么是单个代码单元，
   * 要么是有效码点的前导代码单元）。该函数读取组成该码点的所有代码单元，
   * 并返回已读取的代码单元数量。如果偏移量没有指向有效码点的起始代码单元，
   * 或者指向非法代码单元序列，函数将返回表示错误的负整数。
   *
   * @param pos
   *     索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *     字节数组。
   * @param endIndex
   *     字节数组的结束索引。
   * @return 如果函数成功从指定偏移量的代码单元序列中获取一个码点，
   *     返回该码点的值（非负值）；否则，返回表示错误的负整数：
   *     {@link ErrorCode#MALFORMED_UNICODE}表示指定偏移量没有指向
   *     组成有效码点的合法代码单元序列的开始；
   *     {@link ErrorCode#INCOMPLETE_UNICODE}表示输入代码单元序列
   *     不完整，无法形成有效码点。
   * @throws IndexOutOfBoundsException
   *     如果endIndex &gt; buffer.length或current.value &gt; endIndex。
   *     注意，如果current.value == endIndex，函数不执行任何操作并返回0。
   */
  public static int getNext(final ParsingPosition pos, final byte[] buffer,
      final int endIndex) {
    final int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final int p0 = index;
    final int c0 = (buffer[p0] & 0xFF);
    if (isSingle(c0)) {
      pos.setIndex(p0 + 1);
      return c0;
    } else if (! isLeading(c0)) {
      pos.setErrorIndex(p0);
      pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
      return ErrorCode.MALFORMED_UNICODE;
    }
    if (c0 <= 0xC1) {
      // Over long encoding: c0 is the start of a 2-byte sequence, but code
      // point <= 127. This MUST be considered as an ERROR, for the security
      // reason. (for example, the over long UTF-8 sequence 0xC0 0x80 will be
      // decoded into the character U+0000, which would cause an buffer
      // overflow.)
      pos.setErrorIndex(p0);
      pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
      return ErrorCode.MALFORMED_UNICODE;
    } else if (c0 <= 0xDF) {
      // c0 is the start of a 2-byte sequence, encoding U+0080..U+07FF
      final int p2 = p0 + 2;
      if (p2 > endIndex) {
        pos.setErrorIndex(endIndex);
        pos.setErrorCode(ErrorCode.INCOMPLETE_UNICODE);
        return ErrorCode.INCOMPLETE_UNICODE;
      }
      final int p1 = p0 + 1;
      int c1 = buffer[p1] & 0xFF;
      c1 ^= 0x80;
      if (c1 >= 0x40) {
        pos.setErrorIndex(p1);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
      int codePoint = (c0 & 0x1F);
      codePoint <<= 6;
      codePoint |= c1;
      pos.setIndex(p2);
      return codePoint;
    } else if (c0 <= 0xEF) {
      // c0 is the start of a 3-byte sequence, encoding U+0800..U+FFFF
      final int p3 = p0 + 3;
      if (p3 > endIndex) {
        pos.setErrorIndex(endIndex);
        pos.setErrorCode(ErrorCode.INCOMPLETE_UNICODE);
        return ErrorCode.INCOMPLETE_UNICODE;
      }
      final int p1 = p0 + 1;
      int c1 = (buffer[p1] & 0xFF);
      if ((c0 < 0xE1) && (c1 < 0xA0)) {
        pos.setErrorIndex(p1);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
      c1 ^= 0x80;
      if (c1 >= 0x40) {
        return ErrorCode.MALFORMED_UNICODE;
      }
      final int p2 = p0 + 2;
      int c2 = (buffer[p2] & 0xFF);
      c2 ^= 0x80;
      if (c2 >= 0x40) {
        pos.setErrorIndex(p2);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
      int codePoint = c0 & 0x0F;
      codePoint <<= 12;
      codePoint |= (c1 << 6);
      codePoint |= c2;
      pos.setIndex(p3);
      return codePoint;
    } else if (c0 <= 0xF4) {
      // c0 is the start of a 4-byte sequence, encoding U+10000..U+10FFFF
      final int p4 = p0 + 4;
      if (p4 > endIndex) {
        pos.setErrorIndex(endIndex);
        pos.setErrorCode(ErrorCode.INCOMPLETE_UNICODE);
        return ErrorCode.INCOMPLETE_UNICODE;
      }
      final int p1 = p0 + 1;
      int c1 = (buffer[p1] & 0xFF);
      if ((c0 < 0xF1) && (c1 < 0x90)) {
        pos.setErrorIndex(p1);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
      c1 ^= 0x80;
      if (c1 >= 0x40) {
        return ErrorCode.MALFORMED_UNICODE;
      }
      final int p2 = p0 + 2;
      int c2 = (buffer[p2] & 0xFF);
      c2 ^= 0x80;
      if (c2 >= 0x40) {
        pos.setErrorIndex(p2);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
      final int p3 = p0 + 3;
      int c3 = (buffer[p3] & 0xFF);
      c3 ^= 0x80;
      if (c3 >= 0x40) {
        pos.setErrorIndex(p3);
        pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
        return ErrorCode.MALFORMED_UNICODE;
      }
      int codePoint = (c0 & 0x07);
      codePoint <<= 18;
      codePoint |= (c1 << 12);
      codePoint |= (c2 << 6);
      codePoint |= c3;
      pos.setIndex(p4);
      return codePoint;
    } else {
      pos.setErrorIndex(p0);
      pos.setErrorCode(ErrorCode.MALFORMED_UNICODE);
      return ErrorCode.MALFORMED_UNICODE;
    }
  }

  /**
   * 将UTF-8代码单元序列的偏移量从一个码点边界移动到前一个码点边界并获取它们之间的码点。
   *
   * <p>输入偏移量必须指向有效码点结束代码单元的下一个位置（这要么是下一个码点的
   * 起始代码单元，要么是整个代码单元序列的结尾）。该函数将把偏移量移动到前一个码点的
   * 起始代码单元，读取该码点，然后返回组成该码点的代码单元数量（这也是偏移量的减少量）。
   * 如果偏移量没有指向有效码点结束代码单元的下一个位置，或者前一个代码单元序列是非法的，
   * 函数将返回表示错误的负整数。
   *
   * @param pos
   *     索引解析位置；调用此函数后，将设置为新位置。
   * @param buffer
   *     字节数组。
   * @param startIndex
   *     字节数组的开始索引。
   * @return 如果函数成功从指定索引的代码单元序列中获取一个码点，
   *     返回该码点的值（非负值）；否则，返回表示错误的负整数：
   *     {@link ErrorCode#MALFORMED_UNICODE}表示指定偏移量没有指向
   *     组成有效码点的合法代码单元序列结尾的下一个位置；
   *     {@link ErrorCode#INCOMPLETE_UNICODE}表示输入代码单元序列
   *     不完整，无法形成有效的Unicode码点。
   * @throws IndexOutOfBoundsException
   *     如果startIndex &lt; 0或current.value &lt; startIndex。
   *     注意，如果current.value == startIndex，函数不执行任何操作并返回0。
   */
  public static int getPrevious(final ParsingPosition pos, final byte[] buffer,
      final int startIndex) {
    final int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    // remember the old current
    final int oldIndex = index;
    int result = backward(pos, buffer, startIndex);
    if (result < 0) {
      return result;
    } else {
      // remember the new current
      final int newIndex = pos.getIndex();
      result = getNext(pos, buffer, oldIndex);
      if (result < 0) {
        // on error restore the old current
        pos.reset(oldIndex);
        return result;
      } else {
        // on success set the new current
        pos.setIndex(newIndex);
        return result;
      }
    }
  }

  /**
   * 将一个码点放入UTF-8代码单元缓冲区，写入1到4个代码单元。
   *
   * <p>当前位置指向放置码点的位置，在放置码点后会推进1到4个位置。
   * 如果码点无效或缓冲区空间不足，函数返回0。
   *
   * @param codePoint
   *     要放置的码点。
   * @param index
   *     缓冲区中放置码点的索引。必须在[0, buffer.length)范围内。
   * @param buffer
   *     UTF-8代码单元缓冲区。
   * @param endIndex
   *     字节数组的结束索引。
   * @return 如果码点成功放入缓冲区，返回放入缓冲区的代码单元数量；
   *     否则，返回表示错误的负整数：{@link ErrorCode#MALFORMED_UNICODE}
   *     表示指定码点不是有效的Unicode码点或者是代理码点；
   *     {@link ErrorCode#BUFFER_OVERFLOW}表示代码单元缓冲区没有足够的空间
   *     来保存结果。
   * @throws IndexOutOfBoundsException
   *     如果endIndex &gt; buffer.length或current &gt; endIndex。
   *     注意，如果current == endIndex，函数将返回BUFFER_OVERFLOW。
   */
  public static int put(final int codePoint, final int index,
      final byte[] buffer, final int endIndex) {
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (codePoint <= MAX_ONE_CODE_UNIT) {
      buffer[index] = (byte) (codePoint & 0xFF);
      return 1;
    } else if (codePoint < 0x0800) {
      if (index + 2 > endIndex) {
        return ErrorCode.BUFFER_OVERFLOW;
      }
      final int c1 = ((codePoint & 0x3F) | 0x80);
      final int c0 = ((codePoint >>> 6) | 0xC0);
      buffer[index + 1] = (byte) (c1 & 0xFF);
      buffer[index] = (byte) (c0 & 0xFF);
      return 2;
    } else if (codePoint < 0x10000) {
      if (index + 3 > endIndex) {
        return ErrorCode.BUFFER_OVERFLOW;
      }
      // Starting with Unicode 3.2, surrogate code points must not be encoded
      // in UTF-8.
      if ((codePoint & 0xFFFFF800) == 0xD800) {
        return ErrorCode.MALFORMED_UNICODE;
      }
      int cp = codePoint;
      final int c2 = ((cp & 0x3F) | 0x80);
      cp = ((cp >>> 6) | 0x800);
      final int c1 = ((cp & 0x3F) | 0x80);
      final int c0 = ((cp >>> 6) | 0xC0);
      buffer[index + 2] = (byte) (c2 & 0xFF);
      buffer[index + 1] = (byte) (c1 & 0xFF);
      buffer[index] = (byte) (c0 & 0xFF);
      return 3;
    } else if (codePoint < 0x110000) {
      if (index + 4 > endIndex) {
        return ErrorCode.BUFFER_OVERFLOW;
      }
      int cp = codePoint;
      final int c3 = ((cp & 0x3F) | 0x80);
      cp = ((cp >>> 6) | 0x10000);
      final int c2 = ((cp & 0x3F) | 0x80);
      cp = ((cp >>> 6) | 0x800);
      final int c1 = ((cp & 0x3F) | 0x80);
      final int c0 = ((cp >>> 6) | 0xC0);
      buffer[index + 3] = (byte) (c3 & 0xFF);
      buffer[index + 2] = (byte) (c2 & 0xFF);
      buffer[index + 1] = (byte) (c1 & 0xFF);
      buffer[index] = (byte) (c0 & 0xFF);
      return 4;
    } else {
      return ErrorCode.MALFORMED_UNICODE;
    }
  }
  // resume checkstyle: MagicNumberCheck
}