////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link StripEndTransformer}通过剥离尾随的空白和不可打印字符来转换字符串。
 *
 * @author 胡海星
 */
@Immutable
public final class StripEndTransformer extends AbstractStringTransformer {

  public static final StripEndTransformer INSTANCE = new StripEndTransformer();

  public StripEndTransformer() {
    // empty
  }

  @Override
  public String transform(final String str) {
    return new Stripper().fromEnd().strip(str);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    return (o != null) && (getClass() == o.getClass());
  }

  @Override
  public int hashCode() {
    return 2;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }

  @Override
  public StripEndTransformer cloneEx() {
    return this;
  }
}