////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer;

/**
 * This abstract class provides an easier way to implements the
 * {@link Transformer} interface using an anonymous class.
 *
 * @author Haixing Hu
 */
public abstract class AbstractTransformer<T> implements Transformer<T> {

  @Override
  public abstract T transform(T t);

}
