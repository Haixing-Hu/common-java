////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code ChainedTransformer}对象是{@code Transformer}的实例,由{@code Transformer}对象列表组成。
 *
 * <p>ChainedTransformer对象将使用其链中的所有转换器逐个转换输入对象,直到对象变为{@code null}。
 *
 * <p>请注意,链中转换器的顺序至关重要。
 *
 * @author 胡海星
 */
@NotThreadSafe
public class ChainedTransformer<T> extends AbstractTransformer<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChainedTransformer.class);

  protected List<Transformer<T>> transformers;

  /**
   * 构造一个{@link ChainedTransformer}。
   */
  public ChainedTransformer() {
    transformers = null;
  }

  /**
   * 获取此链中的转换器列表。
   *
   * @return
   *     此链中的转换器列表。
   */
  public List<Transformer<T>> getTransformers() {
    return transformers;
  }

  /**
   * 设置此链中的转换器列表。
   *
   * @param transformers
   *     新的转换器列表。
   */
  public void setTransformers(final List<Transformer<T>> transformers) {
    this.transformers = transformers;
  }

  /**
   * 将转换器添加到此链的末尾。
   *
   * @param transformer
   *     要添加的转换器。
   */
  public void addTransformer(final Transformer<T> transformer) {
    if (transformers == null) {
      transformers = new LinkedList<>();
    }
    transformers.add(transformer);
  }

  /**
   * 返回此链中的转换器数量。
   *
   * @return
   *     此链中的转换器数量。
   */
  public int size() {
    return (transformers == null ? 0 : transformers.size());
  }

  /**
   * 测试此链是否不包含任何转换器。
   *
   * @return
   *     如果此链不包含任何转换器，则返回{@code true}；否则返回{@code false}。
   */
  public boolean isEmpty() {
    return (transformers == null) || transformers.isEmpty();
  }

  /**
   * 从此链中删除所有转换器。
   */
  public void clear() {
    if (transformers != null) {
      transformers.clear();
    }
  }

  @Override
  public T transform(final T obj) {
    if (obj == null) {
      return null;
    }
    if (transformers == null) {
      return obj;
    } else {
      T target = obj;
      for (final Transformer<T> tr : transformers) {
        LOGGER.debug("应用转换器 {} ...", tr.getClass());
        final T result = tr.transform(target);
        if (result == null) {
          return null;
        }
        LOGGER.debug("将 {} 转换为 {} ", target, result);
        target = result;
      }
      return target;
    }
  }
}