////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

import java.util.Collection;
import java.util.HashSet;

import ltd.qubit.commons.lang.CloneableEx;

/**
 * A set of {@link HyperLink} objects.
 * <p>
 * This class is presented to simplified the serialization of set of hyper
 * links.
 * </p>
 *
 * @author Haixing Hu
 */
public final class HyperLinkSet extends HashSet<HyperLink> implements
        CloneableEx<HyperLinkSet> {

  private static final long serialVersionUID = 4345600463485787291L;

  public HyperLinkSet() {
  }

  public HyperLinkSet(final Collection<HyperLink> c) {
    super(c);
  }

  public HyperLinkSet(final int initialCapacity, final float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public HyperLinkSet(final int initialCapacity) {
    super(initialCapacity);
  }

  @Override
  public HyperLinkSet cloneEx() {
    return new HyperLinkSet(this);
  }
}