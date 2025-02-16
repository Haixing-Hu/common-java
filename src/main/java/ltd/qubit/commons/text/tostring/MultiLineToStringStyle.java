////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.SystemUtils;

/**
 * The multi-line {@code toString()} style.
 *
 * <p>Using the {@code Person} example from {@link ToStringBuilder}, the
 * output would look like this:
 *
 * <pre>
 * Person@182f0db[
 *   name=John Doe
 *   age=33
 *   smoker=false
 * ]
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class MultiLineToStringStyle extends ToStringStyle {

  private static final long serialVersionUID = 4592558914484643637L;

  public static final MultiLineToStringStyle INSTANCE = new MultiLineToStringStyle();

  private static final ThreadLocal<String> CURRENT_INDENT = ThreadLocal.withInitial(() -> "");

  private static final String INDENT_UNIT = "  ";

  protected void increaseIndent() {
    CURRENT_INDENT.set(CURRENT_INDENT.get() + INDENT_UNIT);
  }

  protected void decreaseIndent() {
    String current = CURRENT_INDENT.get();
    if (current.length() >= 2) {
      CURRENT_INDENT.set(current.substring(2));
    }
  }

  protected String getCurrentIndent() {
    return CURRENT_INDENT.get();
  }

  @Override
  public void appendStart(StringBuilder builder, Object object) {
    super.appendStart(builder, object);
    increaseIndent();
  }

  @Override
  public void appendEnd(StringBuilder builder, Object object) {
    decreaseIndent();
    super.appendEnd(builder, object);
  }

  public MultiLineToStringStyle() {
    setContentStart(contentStart);
    setFieldSeparator(SystemUtils.LINE_SEPARATOR + getCurrentIndent() + INDENT_UNIT);
    setFieldSeparatorAtStart(true);
    setContentEnd(SystemUtils.LINE_SEPARATOR + getCurrentIndent() + contentEnd);
  }

  @Override
  protected void appendFieldSeparator(StringBuilder builder) {
    builder.append(SystemUtils.LINE_SEPARATOR)
          .append(getCurrentIndent())
          .append(INDENT_UNIT);
  }

  @Override
  protected void appendContentEnd(StringBuilder builder) {
    builder.append(SystemUtils.LINE_SEPARATOR)
          .append(getCurrentIndent())
          .append(getContentEnd());
  }

  @Override
  public String toString() {
    return "MultiLineToStringStyle";
  }
}
