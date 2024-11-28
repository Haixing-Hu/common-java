////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.compare;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * The default comparators for {@link LocalDate} objects.
 *
 * @author Haixing Hu
 */
public final class LocalDateComparator implements Comparator<LocalDate> {

  public static final LocalDateComparator INSTANCE = new LocalDateComparator();

  @Override
  public int compare(final LocalDate o1, final LocalDate o2) {
    return o1.compareTo(o2);
  }
}
