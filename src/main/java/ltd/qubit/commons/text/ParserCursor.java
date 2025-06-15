////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

/**
 * 解析器游标类，用于跟踪解析过程中的位置。
 *
 * @author 胡海星
 */
public class ParserCursor {
  private final int lowerBound;
  private final int upperBound;
  private int pos;

  /**
   * 构造一个解析器游标。
   *
   * @param lowerBound
   *     下界位置，不能为负数。
   * @param upperBound
   *     上界位置，不能小于下界。
   * @throws IndexOutOfBoundsException
   *     如果下界为负数或下界大于上界。
   */
  public ParserCursor(final int lowerBound, final int upperBound) {
    if (lowerBound < 0) {
      throw new IndexOutOfBoundsException("Lower bound cannot be negative");
    } else if (lowerBound > upperBound) {
      throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
    } else {
      this.lowerBound = lowerBound;
      this.upperBound = upperBound;
      this.pos = lowerBound;
    }
  }

  /**
   * 获取下界位置。
   *
   * @return
   *     下界位置。
   */
  public int getLowerBound() {
    return this.lowerBound;
  }

  /**
   * 获取上界位置。
   *
   * @return
   *     上界位置。
   */
  public int getUpperBound() {
    return this.upperBound;
  }

  /**
   * 获取当前位置。
   *
   * @return
   *     当前位置。
   */
  public int getPos() {
    return this.pos;
  }

  /**
   * 更新当前位置。
   *
   * @param pos
   *     新的位置，必须在下界和上界之间。
   * @throws IndexOutOfBoundsException
   *     如果新位置超出范围。
   */
  public void updatePos(final int pos) {
    if (pos < this.lowerBound) {
      throw new IndexOutOfBoundsException("pos: " + pos + " < lowerBound: " + this.lowerBound);
    } else if (pos > this.upperBound) {
      throw new IndexOutOfBoundsException("pos: " + pos + " > upperBound: " + this.upperBound);
    } else {
      this.pos = pos;
    }
  }

  /**
   * 判断是否已到达末尾。
   *
   * @return
   *     如果当前位置已到达或超过上界则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean atEnd() {
    return this.pos >= this.upperBound;
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append('[')
        .append(this.lowerBound)
        .append('>')
        .append(this.pos)
        .append('>')
        .append(this.upperBound)
        .append(']');
    return builder.toString();
  }
}