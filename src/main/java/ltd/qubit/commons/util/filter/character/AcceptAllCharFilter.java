////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

/**
 * 一个接受所有字符的字符过滤器。
 *
 * @author 胡海星
 */
public class AcceptAllCharFilter implements CharFilter {

  /**
   * 此类的单例实例。
   */
  public static final AcceptAllCharFilter INSTANCE = new AcceptAllCharFilter();

  private AcceptAllCharFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Character ch) {
    return true;
  }
}