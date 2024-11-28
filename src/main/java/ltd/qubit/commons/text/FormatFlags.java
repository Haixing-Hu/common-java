////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.text.NumberFormat.BINARY_RADIX;
import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;
import static ltd.qubit.commons.text.NumberFormat.HEX_RADIX;
import static ltd.qubit.commons.text.NumberFormat.OCTAL_RADIX;

/**
 * A {@link FormatFlags} is a bitwise or combination of format flags.
 *
 * @author Haixing Hu
 */
public class FormatFlags {

  public static final int[] ALLOWED_RADIX = {
    2, 8, 10, 16,
  };

  public static final int DEFAULT_FLAGS = FormatFlag.DEFAULT;

  protected int flags;

  public FormatFlags() {
    flags = FormatFlag.DEFAULT;
  }

  public FormatFlags(final int flags) {
    this.flags = flags;
  }

  public void reset() {
    flags = FormatFlag.DEFAULT;
  }

  public int getFlags() {
    return flags;
  }

  public void setFlags(final int flags) {
    this.flags = flags;
  }

  public boolean isBoolAlpha() {
    return (flags & FormatFlag.BOOL_ALPHA) != 0;
  }

  public void setBoolAlpha(final boolean value) {
    if (value) {
      flags |= FormatFlag.BOOL_ALPHA;
    } else {
      flags &= (~ FormatFlag.BOOL_ALPHA);
    }
  }

  public boolean isGrouping() {
    return (flags & FormatFlag.GROUPING) != 0;
  }

  public void setGrouping(final boolean value) {
    if (value) {
      flags |= FormatFlag.GROUPING;
    } else {
      flags &= (~ FormatFlag.GROUPING);
    }
  }

  public boolean isKeepBlank() {
    return (flags & FormatFlag.KEEP_BLANKS) != 0;
  }

  public void setKeepBlank(final boolean value) {
    if (value) {
      flags |= FormatFlag.KEEP_BLANKS;
    } else {
      flags &= (~ FormatFlag.KEEP_BLANKS);
    }
  }

  public boolean isBinary() {
    return (flags & FormatFlag.BINARY) != 0;
  }

  public void setBinary(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set binary option
      flags |= FormatFlag.BINARY;
    } else {
      flags &= (~ FormatFlag.BINARY);
    }
  }

  public boolean isOctal() {
    return (flags & FormatFlag.OCTAL) != 0;
  }

  public void setOctal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set octal option
      flags |= FormatFlag.OCTAL;
    } else {
      flags &= (~ FormatFlag.OCTAL);
    }
  }

  public boolean isDecimal() {
    return (flags & FormatFlag.DECIMAL) != 0;
  }

  public void setDecimal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set decimal option
      flags |= FormatFlag.DECIMAL;
    } else {
      flags &= (~ FormatFlag.DECIMAL);
    }
  }

  public boolean isHex() {
    return (flags & FormatFlag.HEX) != 0;
  }

  public void setHex(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set hex option
      flags |= FormatFlag.HEX;
    } else {
      flags &= (~ FormatFlag.HEX);
    }
  }

  public int getRadix() {
    switch (flags & FormatFlag.RADIX_MASK) {
      case FormatFlag.BINARY:
        return BINARY_RADIX;
      case FormatFlag.OCTAL:
        return OCTAL_RADIX;
      case FormatFlag.DECIMAL:
        return DECIMAL_RADIX;
      case FormatFlag.HEX:
        return HEX_RADIX;
      default:
        return -1;
    }
  }

  public void setRadix(final int radix) {
    flags &= (~ FormatFlag.RADIX_MASK);
    switch (radix) {
      case BINARY_RADIX:
        flags |= FormatFlag.BINARY;
        break;
      case OCTAL_RADIX:
        flags |= FormatFlag.OCTAL;
        break;
      case DECIMAL_RADIX:
        flags |= FormatFlag.DECIMAL;
        break;
      case HEX_RADIX:
        flags |= FormatFlag.HEX;
        break;
      default:
        throw new IllegalArgumentException("Unsupported radix: " + radix);
    }
  }

  public void clearRadixOptions() {
    // clear all other radix options
    flags &= (~ FormatFlag.RADIX_MASK);
  }

  public boolean isFixPoint() {
    return (flags & FormatFlag.FIXED_POINT) != 0;
  }

  public void setFixPoint(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ FormatFlag.REAL_MASK);
      // set fix point option
      flags |= FormatFlag.FIXED_POINT;
    } else {
      flags &= (~ FormatFlag.FIXED_POINT);
    }
  }

  public boolean isScientific() {
    return (flags & FormatFlag.SCIENTIFIC) != 0;
  }

  public void setScientific(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ FormatFlag.REAL_MASK);
      // set scientific option
      flags |= FormatFlag.SCIENTIFIC;
    } else {
      flags &= (~ FormatFlag.SCIENTIFIC);
    }
  }

  public boolean isShortReal() {
    return (flags & FormatFlag.SHORT_REAL) != 0;
  }

  public void setShortReal(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ FormatFlag.REAL_MASK);
      // set scientific option
      flags |= FormatFlag.SHORT_REAL;
    } else {
      flags &= (~ FormatFlag.SHORT_REAL);
    }
  }

  public void clearRealOptions() {
    // clear all other real options
    flags &= (~ FormatFlag.REAL_MASK);
  }

  public boolean isShowRadix() {
    return (flags & FormatFlag.SHOW_RADIX) != 0;
  }

  public void setShowRadix(final boolean value) {
    if (value) {
      flags |= FormatFlag.SHOW_RADIX;
    } else {
      flags &= (~ FormatFlag.SHOW_RADIX);
    }
  }

  public boolean isShowPoint() {
    return (flags & FormatFlag.SHOW_POINT) != 0;
  }

  public void setShowPoint(final boolean value) {
    if (value) {
      flags |= FormatFlag.SHOW_POINT;
    } else {
      flags &= (~ FormatFlag.SHOW_POINT);
    }
  }

  public boolean isShowPositive() {
    return (flags & FormatFlag.SHOW_POSITIVE) != 0;
  }

  public void setShowPositive(final boolean value) {
    if (value) {
      // clear the show space option
      flags &= (~ FormatFlag.SHOW_SPACE);
      // set the show radix option
      flags |= FormatFlag.SHOW_POSITIVE;
    } else {
      flags &= (~ FormatFlag.SHOW_POSITIVE);
    }
  }

  public boolean isShowSpace() {
    return (flags & FormatFlag.SHOW_SPACE) != 0;
  }

  public void setShowSpace(final boolean value) {
    if (value) {
      // clear the show positive option
      flags &= (~ FormatFlag.SHOW_POSITIVE);
      // set the show space option
      flags |= FormatFlag.SHOW_SPACE;
    } else {
      flags &= (~ FormatFlag.SHOW_SPACE);
    }
  }

  public boolean isUppercaseRadixPrefix() {
    return (flags & FormatFlag.UPPERCASE_RADIX_PREFIX) != 0;
  }

  public void setUppercaseRadixPrefix(final boolean value) {
    if (value) {
      flags |= FormatFlag.UPPERCASE_RADIX_PREFIX;
    } else {
      flags &= (~ FormatFlag.UPPERCASE_RADIX_PREFIX);
    }
  }

  public boolean isUppercaseExponent() {
    return (flags & FormatFlag.UPPERCASE_EXPONENT) != 0;
  }

  public void setUppercaseExponent(final boolean value) {
    if (value) {
      flags |= FormatFlag.UPPERCASE_EXPONENT;
    } else {
      flags &= (~ FormatFlag.UPPERCASE_EXPONENT);
    }
  }

  public boolean isAlignLeft() {
    return (flags & FormatFlag.ALIGN_LEFT) != 0;
  }

  public void setAlignLeft(final boolean value) {
    if (value) {
      // clear the alignment options
      flags &= (~ FormatFlag.ALIGN_MASK);
      // set the left option
      flags |= FormatFlag.ALIGN_LEFT;
    } else {
      flags &= (~ FormatFlag.ALIGN_LEFT);
    }
  }

  public boolean isAlignCenter() {
    return (flags & FormatFlag.ALIGN_CENTER) != 0;
  }

  public void setAlignCenter(final boolean value) {
    if (value) {
      // clear the alignment options
      flags &= (~ FormatFlag.ALIGN_MASK);
      // set the center option
      flags |= FormatFlag.ALIGN_CENTER;
    } else {
      flags &= (~ FormatFlag.ALIGN_CENTER);
    }
  }

  public boolean isAlignRight() {
    return (flags & FormatFlag.ALIGN_RIGHT) != 0;
  }

  public void setAlignRight(final boolean value) {
    if (value) {
      // clear the alignment options
      flags &= (~ FormatFlag.ALIGN_MASK);
      // set the right option
      flags |= FormatFlag.ALIGN_RIGHT;
    } else {
      flags &= (~ FormatFlag.ALIGN_RIGHT);
    }
  }

  public void clearAlignOptions() {
    flags &= (~ FormatFlag.ALIGN_MASK);
  }

  public boolean isUppercase() {
    return (flags & FormatFlag.UPPERCASE) != 0;
  }

  public void setUppercase(final boolean value) {
    if (value) {
      flags &= (~ FormatFlag.CASE_MASK);
      flags |= FormatFlag.UPPERCASE;
    } else {
      flags &= (~ FormatFlag.UPPERCASE);
    }
  }

  public boolean isLowercase() {
    return (flags & FormatFlag.LOWERCASE) != 0;
  }

  public void setLowercase(final boolean value) {
    if (value) {
      flags &= (~ FormatFlag.CASE_MASK);
      flags |= FormatFlag.LOWERCASE;
    } else {
      flags &= (~ FormatFlag.LOWERCASE);
    }
  }

  public boolean isTitlecase() {
    return (flags & FormatFlag.TITLECASE) != 0;
  }

  public void setTitlecase(final boolean value) {
    if (value) {
      flags &= (~ FormatFlag.CASE_MASK);
      flags |= FormatFlag.TITLECASE;
    } else {
      flags &= (~ FormatFlag.TITLECASE);
    }
  }

  public void clearCaseOptions() {
    flags &= (~ FormatFlag.CASE_MASK);
  }

  @Override
  public int hashCode() {
    return flags;
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
    final FormatFlags other = (FormatFlags) obj;
    return (flags == other.flags);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .toString();
  }

}
