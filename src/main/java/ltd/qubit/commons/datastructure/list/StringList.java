////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list;

import ltd.qubit.commons.annotation.TypeCodec;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.StringListCodec;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 此模型表示字符串列表，继承了JDK的原生{@link ArrayList}，但提供了自定义的{@link Codec}
 * 和MyBatis Type Handler，并实现了{@link Assignable}接口。
 *
 * @author Haixing Hu
 */
@TypeCodec(StringListCodec.class)
public class StringList extends ArrayList<String> implements Assignable<StringList> {

  private static final long serialVersionUID = 7510619465554403307L;

  public StringList() {
    //  empty
  }

  public StringList(final Collection<String> col) {
    super(col);
  }

  public StringList(final StringList other) {
    assign(other);
  }

  @Override
  public void assign(final StringList other) {
    this.clear();
    this.addAll(other);
  }

  @Override
  public StringList clone() {
    return new StringList(this);
  }
}
