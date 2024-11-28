////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides utility function for filters.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class FilterUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilterUtils.class);

  public static <T> T filter(@Nullable final Filter<T> filter,
      @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (filter != null) {
      final boolean accept;
      synchronized (filter) {
        accept = filter.accept(value);
      }
      if (!accept) {
        LOGGER.debug("Rejected: filter = {}, value = {}", filter, value);
        return null;
      }
    }
    return value;
  }

  public static <T> T filter(@Nullable final Filter<T> filter1,
      @Nullable final Filter<T> filter2, @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (filter1 != null) {
      final boolean accept;
      synchronized (filter1) {
        accept = filter1.accept(value);
      }
      if (!accept) {
        LOGGER.debug("Rejected: filter = {}, value = {}", filter1, value);
        return null;
      }
    }
    if (filter2 != null) {
      final boolean accept;
      synchronized (filter2) {
        accept = filter2.accept(value);
      }
      if (!accept) {
        LOGGER.debug("Rejected: filter = {}, value = {}", filter2, value);
        return null;
      }
    }
    return value;
  }

  public static <T> T filter(@Nullable final Filter<T> filter1,
      @Nullable final Filter<T> filter2,
      @Nullable final Filter<T> filter3,
      @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (filter1 != null) {
      final boolean accept;
      synchronized (filter1) {
        accept = filter1.accept(value);
      }
      if (!accept) {
        LOGGER.debug("Rejected: filter = {}, value = {}", filter1, value);
        return null;
      }
    }
    if (filter2 != null) {
      final boolean accept;
      synchronized (filter2) {
        accept = filter2.accept(value);
      }
      if (!accept) {
        LOGGER.debug("Rejected: filter = {}, value = {}", filter2, value);
        return null;
      }
    }
    if (filter3 != null) {
      final boolean accept;
      synchronized (filter3) {
        accept = filter3.accept(value);
      }
      if (!accept) {
        LOGGER.debug("Rejected: filter = {}, value = {}", filter3, value);
        return null;
      }
    }
    return value;
  }

  public static <T> boolean allAccept(final List<? extends Filter<T>> filters,
      final T value) {
    for (final Filter<T> filter : filters) {
      final boolean accept;
      synchronized (filter) {
        accept = filter.accept(value);
      }
      if (!accept) {
        LOGGER.debug("Rejected: filter = {}, value = {}", filter, value);
        return false;
      }
    }
    return true;
  }

  public static <T> boolean anyAccept(final List<? extends Filter<T>> filters,
      final T value) {
    for (final Filter<T> filter : filters) {
      final boolean accept;
      synchronized (filter) {
        accept = filter.accept(value);
      }
      if (accept) {
        LOGGER.debug("Accepted: filter = {}, value = {}", filter, value);
        return true;
      }
    }
    return false;
  }

  public static <T> boolean noneAccept(final List<? extends Filter<T>> filters,
      final T value) {
    for (final Filter<T> filter : filters) {
      final boolean accept;
      synchronized (filter) {
        accept = filter.accept(value);
      }
      if (accept) {
        LOGGER.debug("Accepted: filter = {}, value = {}", filter, value);
        return false;
      }
    }
    return true;
  }
}
