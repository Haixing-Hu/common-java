////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
