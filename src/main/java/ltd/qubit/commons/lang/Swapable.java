////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
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
