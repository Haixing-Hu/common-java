////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.message;

import javax.annotation.Nullable;

/**
 * Sub-interface of MessageSource to be implemented by objects that
 * can resolve messages hierarchically.
 * <p>
 * This class is a copy of {@code HierarchicalMessageSource}. It is used to
 * avoid the dependency of Spring Framework.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Haixing Hu
 */
public interface HierarchicalMessageSource extends MessageSource {

  /**
   * Set the parent that will be used to try to resolve messages that this
   * object can't resolve.
   *
   * @param parent
   *     the parent {@link MessageSource} that will be used to resolve messages
   *     that this object can't resolve. May be {@code null}, in which case no
   *     further resolution is possible.
   */
  void setParentMessageSource(@Nullable MessageSource parent);

  /**
   * Return the parent of this MessageSource, or {@code null} if none.
   */
  @Nullable
  MessageSource getParentMessageSource();
}