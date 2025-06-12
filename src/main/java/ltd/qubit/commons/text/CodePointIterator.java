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
 * {@link CodePointIterator}是一个迭代器，用于遍历字符串的代码点。
 *
 * @author 胡海星
 */
public abstract class CodePointIterator {

  protected int start;
  protected int end;
  protected int left;
  protected int right;
  protected int current;

  protected CodePointIterator() {
    start = 0;
    end = 0;
    left = 0;
    right = 0;
    current = 0;
  }

  protected CodePointIterator(final int start, final int end) {
    this.start = start;
    this.end = end;
    left = start;
    right = start;
    current = 0;
  }

  /**
   * 获取要迭代的字符串范围的起始索引。
   *
   * @return 要迭代的字符串范围的起始索引。
   */
  public final int start() {
    return start;
  }

  /**
   * 获取要迭代的字符串范围的结束索引。
   *
   * @return 要迭代的字符串范围的结束索引。
   */
  public final int end() {
    return end;
  }

  /**
   * 获取当前代码点左边界的索引。
   *
   * @return 当前代码点左边界的索引。
   */
  public final int left() {
    return left;
  }

  /**
   * 获取当前代码点右边界的索引。
   *
   * @return 当前代码点右边界的索引。
   */
  public final int right() {
    return right;
  }

  /**
   * 测试迭代器是否在迭代区域的起始位置。
   *
   * @return 如果迭代器在迭代区域的起始位置则返回true；否则返回false。
   */
  public final boolean atStart() {
    return left <= start;
  }

  /**
   * 测试迭代器是否在迭代区域的结束位置。
   *
   * @return 如果迭代器在迭代区域的结束位置则返回true；否则返回false。
   */
  public final boolean atEnd() {
    return left >= end;
  }

  /**
   * 测试迭代器是否在迭代区域的最后一个代码点位置。
   *
   * @return 如果迭代器在迭代区域的最后一个代码点位置则返回true；否则返回false。
   */
  public final boolean atLast() {
    return (left < end) && (right >= end);
  }

  /**
   * 测试迭代器是否在最后一个代码点位置或迭代区域的结束位置。
   *
   * @return 如果迭代器在最后一个代码点位置或迭代区域的结束位置则返回true；否则返回false。
   */
  public final boolean atLastOrEnd() {
    return right >= end;
  }

  /**
   * 获取当前代码点。
   *
   * @return 当前代码点。
   */
  public final int current() {
    return current;
  }

  /**
   * 将迭代器设置到起始位置。
   */
  public abstract void setToStart();

  /**
   * 将迭代器设置到结束位置。
   */
  public abstract void setToEnd();

  /**
   * 将迭代器设置到最后一个代码点的位置。
   */
  public abstract void setToLast();

  /**
   * 将迭代器设置到指定索引处的代码点。
   *
   * <p>也就是说，调用此函数后，迭代器将指向范围为[x, y)的代码点，使得x ≤ current < y。
   *
   * @param index 指定的索引。
   * @throws IndexOutOfBoundsException 如果索引超出起始和结束索引的范围。
   */
  public abstract void setTo(int index);

  /**
   * 将迭代器移动到下一个代码点。
   *
   * <p>如果迭代器已经在结束位置，调用此函数无效果。
   */
  public abstract void forward();

  /**
   * 将迭代器移动到上一个代码点。
   *
   * <p>如果迭代器已经在起始位置，调用此函数无效果。
   */
  public abstract void backward();

}