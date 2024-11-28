////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * An extended version of the {@link java.lang.Cloneable} interface,
 * which explicitly provides the {@link #cloneEx()} method.
 *
 * @author Haixing Hu
 */
public interface CloneableEx<T> {

  T cloneEx();
}
