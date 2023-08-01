////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * This interface impose a swap operation of a class T.
 *
 * @author Haixing Hu
 */
public interface Swapable<T> {

  void swap(T other);
}
