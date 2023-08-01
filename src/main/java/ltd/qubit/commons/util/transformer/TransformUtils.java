////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides utility function for transformers.
 *
 * @author Haixing Hu
 */
public final class TransformUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransformUtils.class);

  private static <T> void logTransform(final T oldValue, final T newValue) {
    if (LOGGER.isDebugEnabled()) {
      if (! Equality.equals(oldValue, newValue)) {
        LOGGER.debug("Transform: {} --> {}", oldValue, newValue);
      }
    }
  }

  public static <T> T transform(@Nullable final Transformer<T> tr,
      @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (tr == null) {
      return value;
    }
    final T newValue;
    synchronized (tr) {
      newValue = tr.transform(value);
    }
    logTransform(value, newValue);
    return newValue;
  }

  public static <T> T transform(@Nullable final Transformer<T> tr1,
        @Nullable final Transformer<T> tr2, @Nullable final T value) {
    if (value == null) {
      return null;
    }
    T result = value;
    if (tr1 != null) {
      final T newValue;
      synchronized (tr1) {
        newValue = tr1.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    if (tr2 != null) {
      final T newValue;
      synchronized (tr2) {
        newValue = tr2.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    return result;
  }

  public static <T> T transform(@Nullable final Transformer<T> tr1,
        @Nullable final Transformer<T> tr2, @Nullable final Transformer<T> tr3,
        @Nullable final T value) {
    if (value == null) {
      return null;
    }
    T result = value;
    if (tr1 != null) {
      final T newValue;
      synchronized (tr1) {
        newValue = tr1.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    if (tr2 != null) {
      final T newValue;
      synchronized (tr2) {
        newValue = tr2.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    if (tr3 != null) {
      final T newValue;
      synchronized (tr3) {
        newValue = tr3.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    return result;
  }

  public static <T> T transform(@Nullable final List<Transformer<T>> trs,
          @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (trs == null || trs.isEmpty()) {
      return value;
    }
    T result = value;
    for (final Transformer<T> tr: trs) {
      result = transform(tr, result);
    }
    return result;
  }

  public static <T> List<T> transformList(@Nullable final Transformer<T> tr,
      @Nullable final List<T> list, final boolean ignoreNull) {
    if (list == null) {
      return null;
    }
    if (tr == null) {
      return list;
    }
    final List<T> result = new ArrayList<>();
    for (final T value : list) {
      if (value == null) {
        if (! ignoreNull) {
          result.add(null);
        }
      } else {
        final T newValue;
        synchronized (tr) {
          newValue = tr.transform(value);
        }
        logTransform(value, newValue);
        if (newValue == null && ignoreNull) {
          continue;
        }
        result.add(newValue);
      }
    }
    return result;
  }

  public static <T> List<T> transformList(@Nullable final List<Transformer<T>> trs,
          @Nullable final List<T> list, final boolean ignoreNull) {
    if (list == null) {
      return null;
    }
    if (trs == null || trs.isEmpty()) {
      return list;
    }
    List<T> result = list;
    for (final Transformer<T> tr : trs) {
      result = transformList(tr, result, ignoreNull);
    }
    return result;
  }

}
