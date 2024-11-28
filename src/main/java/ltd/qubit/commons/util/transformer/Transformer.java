////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer;

/**
 * This interface provides a function to translate an object to another.
 *
 * @author Haixing Hu
 */
@FunctionalInterface
public interface Transformer<T> {

  T transform(T t);

}
