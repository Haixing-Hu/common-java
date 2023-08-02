////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import java.util.ArrayList;

import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;

/**
 * 此模型表示时间范围集合。
 *
 * @author pino
 */
public class LocalTimeRangeList extends ArrayList<LocalTimeRange> implements
    Assignable<LocalTimeRangeList> {

  private static final long serialVersionUID = -4177321765027710164L;

  public LocalTimeRangeList() {}

  public LocalTimeRangeList(final LocalTimeRangeList other) {
    assign(other);
  }

  @Override
  public void assign(final LocalTimeRangeList other) {
    if (this != other) {
      clear();
      for (final LocalTimeRange lr : other) {
        add(Assignment.clone(lr));
      }
    }
  }

  public LocalTimeRangeList clone() {
    return new LocalTimeRangeList(this);
  }

}
