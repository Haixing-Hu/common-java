////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * Executes a sequence of translators one after the other.
 *
 * <p>Execution ends whenever the first translator consumes code points from
 * the input.</p>
 *
 * @author Haixing Hu
 */
public class AggregateTranslator extends CharSequenceTranslator {

  /**
   * Translator list.
   */
  private final List<CharSequenceTranslator> translators = new ArrayList<>();

  /**
   * Specify the translators to be used at creation time.
   *
   * @param translators
   *     {@link CharSequenceTranslator} array to aggregate.
   */
  public AggregateTranslator(final CharSequenceTranslator... translators) {
    if (translators != null) {
      Stream.of(translators)
          .filter(Objects::nonNull)
          .forEach(this.translators::add);
    }
  }

  public AggregateTranslator(final AggregateTranslator other) {
    this.translators.addAll(other.translators);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The first translator to consume code points from the input is the 'winner'.
   * Execution stops with the number of consumed code points being returned.</p>
   */
  @Override
  public int translate(final CharSequence input, final int index,
      final Appendable appendable) throws IOException {
    for (final CharSequenceTranslator translator : translators) {
      final int consumed = translator.translate(input, index, appendable);
      if (consumed != 0) {
        return consumed;
      }
    }
    return 0;
  }

  @Override
  public AggregateTranslator clone() {
    return new AggregateTranslator(this);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final AggregateTranslator other = (AggregateTranslator) o;
    return Equality.equals(translators, other.translators);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, translators);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("translators", translators)
        .toString();
  }
}
