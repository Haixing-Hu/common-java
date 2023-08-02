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
 * An extended version of the {@link java.lang.Cloneable} interface,
 * which explicitly provides the {@link #clone()} method.
 *
 * @author Haixing Hu
 */
public interface CloneableEx<T> extends java.lang.Cloneable {

  T clone();
}
