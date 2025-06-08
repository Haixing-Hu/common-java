////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.pair;

import javax.annotation.Nullable;

/**
 * A builder for {@link KeyValuePairList}.
 *
 * @author Haixing Hu
 */
public class KeyValuePairListBuilder {
  private final KeyValuePairList list = new KeyValuePairList();

  public <E extends Enum<E>>
  KeyValuePairListBuilder add(final E key, @Nullable final Object value) {
    list.add(key, value);
    return this;
  }

  public KeyValuePairListBuilder add(final String key, @Nullable final Object value) {
    list.add(key, value);
    return this;
  }

  public KeyValuePairListBuilder addNonNull(final String key, @Nullable final Object value) {
    list.addNonNull(key, value);
    return this;
  }

  public KeyValuePairList build() {
    return list;
  }
}