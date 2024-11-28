////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter;

/**
 * This abstract class provides an easier way to implements the {@link Filter}
 * interface using an anonymous class.
 *
 * @author Haixing Hu
 */
public abstract class AbstractFilter<T> implements Filter<T> {

  @Override
  public abstract boolean accept(T t);

}
