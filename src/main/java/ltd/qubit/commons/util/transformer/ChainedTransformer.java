////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * A {@code ChainedTransformer} object is a instance of
 * {@code Transformer} consists of a list of {@code Transformer}
 * objects.
 *
 * <p>A ChainedTransformer object will transform the input object using all the
 * transformers in its chain one by one, until the object become
 * {@code null}.
 *
 * <p>Note that the order of the transformers in the chain is crucial.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class ChainedTransformer<T> extends AbstractTransformer<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChainedTransformer.class);

  protected List<Transformer<T>> transformers;

  public ChainedTransformer() {
    transformers = null;
  }

  public List<Transformer<T>> getTransformers() {
    return transformers;
  }

  public void setTransformers(final List<Transformer<T>> transformers) {
    this.transformers = transformers;
  }

  public void addTransformer(final Transformer<T> transformer) {
    if (transformers == null) {
      transformers = new LinkedList<>();
    }
    transformers.add(transformer);
  }

  public int size() {
    return (transformers == null ? 0 : transformers.size());
  }

  public boolean isEmpty() {
    return (transformers == null) || transformers.isEmpty();
  }

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
        LOGGER.debug("Applying the transformer {} ...", tr.getClass());
        final T result = tr.transform(target);
        if (result == null) {
          return null;
        }
        LOGGER.debug("Transform {} to {} ", target, result);
        target = result;
      }
      return target;
    }
  }
}
