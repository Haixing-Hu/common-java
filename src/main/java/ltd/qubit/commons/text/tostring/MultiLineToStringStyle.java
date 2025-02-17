////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.StringUtils;

import static ltd.qubit.commons.lang.SystemUtils.LINE_SEPARATOR;

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

  @Serial
  private static final long serialVersionUID = 4592558914484643637L;

  private static final ThreadLocal<String> CURRENT_INDENT = ThreadLocal.withInitial(() -> "");

  private static final String INDENT_UNIT = "  ";

  public static final MultiLineToStringStyle INSTANCE = new MultiLineToStringStyle();

  private final String originalContentStart;

  private final String originalContentEnd;

  public MultiLineToStringStyle() {
    originalContentStart = getContentStart();
    originalContentEnd = getContentEnd();
    setContentStart(originalContentStart);
    setFieldSeparator("");
    setFieldSeparatorAtStart(false);
    setFieldSeparatorAtEnd(false);
    setContentEnd(LINE_SEPARATOR + originalContentEnd);
  }

  private void increaseIndent() {
    CURRENT_INDENT.set(CURRENT_INDENT.get() + INDENT_UNIT);
  }

  private void decreaseIndent() {
    final String current = CURRENT_INDENT.get();
    if (current.length() >= 2) {
      CURRENT_INDENT.set(current.substring(2));
    }
  }

  private String getCurrentIndent() {
    return CURRENT_INDENT.get();
  }

  @Override
  public void appendStart(final StringBuilder builder, final Object object) {
    super.appendStart(builder, object);
    increaseIndent();
    setContentEnd(LINE_SEPARATOR + getCurrentIndent() + originalContentEnd);
  }

  @Override
  public void appendEnd(final StringBuilder builder, final Object object) {
    decreaseIndent();
    setContentEnd(LINE_SEPARATOR + getCurrentIndent() + originalContentEnd);
    super.appendEnd(builder, object);
  }

  /**
   * Append to the {@code toString} the field start.
   *
   * @param builder
   *     the {@code StringBuilder} to populate
   * @param fieldName
   *     the field name
   */
  @Override
  protected void appendFieldStart(final StringBuilder builder,
      final String fieldName) {
    builder.append(LINE_SEPARATOR)
           .append(getCurrentIndent());
    super.appendFieldStart(builder, fieldName);
  }

  @Override
  public void appendSuper(final StringBuilder builder,
      final String superToString) {
    if (superToString != null) {
      final int pos1 = superToString.indexOf(contentStart) + contentStart.length();
      final int pos2 = superToString.lastIndexOf(contentEnd);
      if ((pos1 != pos2) && (pos1 >= 0) && (pos2 >= 0)) {
        String data = superToString.substring(pos1, pos2);
        data = StringUtils.removePrefixFromEachLine(data, INDENT_UNIT);
        builder.append(data);
      }
    }
  }

  @Override
  public String toString() {
    return "MultiLineToStringStyle";
  }
}
