////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

public class ParserCursor {
  private final int lowerBound;
  private final int upperBound;
  private int pos;

  public ParserCursor(final int lowerBound, final int upperBound) {
    if (lowerBound < 0) {
      throw new IndexOutOfBoundsException("Lower bound cannot be negative");
    } else if (lowerBound > upperBound) {
      throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
    } else {
      this.lowerBound = lowerBound;
      this.upperBound = upperBound;
      this.pos = lowerBound;
    }
  }

  public int getLowerBound() {
    return this.lowerBound;
  }

  public int getUpperBound() {
    return this.upperBound;
  }

  public int getPos() {
    return this.pos;
  }

  public void updatePos(final int pos) {
    if (pos < this.lowerBound) {
      throw new IndexOutOfBoundsException("pos: " + pos + " < lowerBound: " + this.lowerBound);
    } else if (pos > this.upperBound) {
      throw new IndexOutOfBoundsException("pos: " + pos + " > upperBound: " + this.upperBound);
    } else {
      this.pos = pos;
    }
  }

  public boolean atEnd() {
    return this.pos >= this.upperBound;
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append('[')
        .append(this.lowerBound)
        .append('>')
        .append(this.pos)
        .append('>')
        .append(this.upperBound)
        .append(']');
    return builder.toString();
  }
}
