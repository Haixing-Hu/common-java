////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer;

/**
 * 此抽象类提供了一种更简单的方法来实现{@link Transformer}接口,即使用匿名类。
 *
 * @author 胡海星
 */
public abstract class AbstractTransformer<T> implements Transformer<T> {

  @Override
  public abstract T transform(T t);

}