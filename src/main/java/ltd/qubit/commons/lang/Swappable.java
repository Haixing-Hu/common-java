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
 * This interface impose a swap operation of a class T.
 *
 * @author Haixing Hu
 */
public interface Swappable<T> {

  void swap(T other);
}