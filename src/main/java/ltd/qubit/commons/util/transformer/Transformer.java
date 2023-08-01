////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
