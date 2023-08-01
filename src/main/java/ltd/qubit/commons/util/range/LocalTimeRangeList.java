////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;

import java.util.ArrayList;

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
