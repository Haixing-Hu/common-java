////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.io.Serializable;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The {@link CharSequenceCodePointIterator} is used to iterate through the code
 * points of a string.
 *
 * @author Haixing Hu
 */
public final class CharSequenceCodePointIterator extends CodePointIterator
    implements CloneableEx<CharSequenceCodePointIterator>, Serializable {

  private static final long serialVersionUID = -5929301593382742173L;

  private CharSequence text;

  public CharSequenceCodePointIterator() {
    this.text = StringUtils.EMPTY;
    this.start = 0;
    this.end = 0;
    setToStart();
  }

  public CharSequenceCodePointIterator(final CharSequence str) {
    this.text = requireNonNull("str", str);
    this.start = 0;
    this.end = str.length();
    setToStart();
  }

  public CharSequenceCodePointIterator(final CharSequence str, final int start,
      final int end) {
    this.text = requireNonNull("str", str);
    this.start = Math.max(0, start);
    this.end = Math.min(str.length(), end);
    setToStart();
  }

  public CharSequence getText() {
    return text.subSequence(start, end);
  }

  public void reset(final CharSequence str) {
    this.text = requireNonNull("str", str);
    this.start = 0;
    this.end = str.length();
    setToStart();
  }

  public void reset(final CharSequence str, final int index) {
    requireNonNull("str", str);
    if ((index < 0) || (index > str.length())) {
      throw new IndexOutOfBoundsException();
    }
    this.text = str;
    this.start = 0;
    this.end = str.length();
    setTo(index);
  }

  public void reset(final CharSequence str, final int start, final int end) {
    this.text = requireNonNull("str", str);
    this.start = Math.max(0, start);
    this.end = Math.min(str.length(), end);
    setToStart();
  }

  public void reset(final CharSequence str, final int start, final int end,
      final int index) {
    requireNonNull("str", str);
    final int s = Math.max(0, start);
    final int e = Math.min(str.length(), end);
    if (index < s || index > e) {
      throw new IndexOutOfBoundsException();
    }
    this.text = str;
    this.start = s;
    this.end = e;
    setTo(index);
  }

  @Override
  public void setToStart() {
    current = Character.codePointAt(text, start);
    left = start;
    if (current < Unicode.SUPPLEMENTARY_MIN) {
      right = left + 1;
    } else {
      right = left + 2;
    }
  }

  @Override
  public void setToEnd() {
    left = end;
    right = end;
    current = -1;
  }

  @Override
  public void setToLast() {
    current = Character.codePointBefore(text, end);
    right = end;
    if (current < Unicode.SUPPLEMENTARY_MIN) {
      left = right - 1;
    } else {
      left = right - 2;
    }
  }

  @Override
  public void setTo(final int index) {
    if ((index < start) || (index > end)) {
      throw new IndexOutOfBoundsException();
    }
    final char ch = text.charAt(index);
    if (Unicode.isHighSurrogate(ch)) {
      current = Character.codePointAt(text, index);
      left = index;
      if (current < Unicode.SUPPLEMENTARY_MIN) {
        right = left + 1;
      } else {
        right = left + 2;
      }
    } else if (Unicode.isLowSurrogate(ch)) {
      current = Character.codePointBefore(text, index);
      right = index;
      if (current < Unicode.SUPPLEMENTARY_MIN) {
        left = right - 1;
      } else {
        left = right - 2;
      }
    } else {
      current = ch;
      left = index;
      right = index + 1;
    }
  }

  @Override
  public void forward() {
    if (right < end) {
      current = Character.codePointAt(text, right);
      left = right;
      if (current < Unicode.SUPPLEMENTARY_MIN) {
        right = left + 1;
      } else {
        right = left + 2;
      }
    } else {
      left = right;
      current = -1;
    }
  }

  @Override
  public void backward() {
    if (left > start) {
      current = Character.codePointBefore(text, left);
      right = left;
      if (current < Unicode.SUPPLEMENTARY_MIN) {
        left = right - 1;
      } else {
        left = right - 2;
      }
    }
  }

  @Override
  public CharSequenceCodePointIterator clone() {
    final CharSequenceCodePointIterator cloned = new CharSequenceCodePointIterator();
    cloned.text = text;
    cloned.start = start;
    cloned.end = end;
    cloned.left = left;
    cloned.right = right;
    cloned.current = current;
    return cloned;
  }

  @Override
  public int hashCode() {
    final int multiplier = 71;
    int code = 31;
    code = Hash.combine(code, multiplier, text);
    code = Hash.combine(code, multiplier, start);
    code = Hash.combine(code, multiplier, end);
    code = Hash.combine(code, multiplier, left);
    code = Hash.combine(code, multiplier, right);
    code = Hash.combine(code, multiplier, current);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CharSequenceCodePointIterator other = (CharSequenceCodePointIterator) obj;
    return (start == other.start)
        && (end == other.end)
        && (left == other.left)
        && (right == other.right)
        && (current == other.current)
        && (text.equals(other.text));
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("string", text)
        .append("start", start)
        .append("end", end)
        .append("left", left)
        .append("right", right)
        .append("current", current)
        .toString();
  }

}
