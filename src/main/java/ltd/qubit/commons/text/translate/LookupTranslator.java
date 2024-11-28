////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * Translates a value using a lookup table.
 *
 * @author Haixing Hu
 */
public class LookupTranslator extends CharSequenceTranslator {

  /**
   * The mapping to be used in translation.
   */
  private final Map<String, String> lookupMap = new HashMap<>();

  /**
   * The first character of each key in the lookupMap.
   */
  private final BitSet prefixSet = new BitSet();

  /**
   * The length of the shortest key in the lookupMap.
   */
  private final int shortest;

  /**
   * The length of the longest key in the lookupMap.
   */
  private final int longest;

  /**
   * Constructs the lookup table to be used in translation
   *
   * <p>Note that, the key to the lookup table is converted to a {@code String}.
   * This is because we need the key to support {@code hashCode()} and
   * {@code equals(Object)}, allowing it to be the key for a HashMap.
   *
   * @param lookupMap
   *     table of translator mappings.
   */
  public LookupTranslator(final Map<CharSequence, CharSequence> lookupMap) {
    requireNonNull("lookupMap", lookupMap);
    int currentShortest = Integer.MAX_VALUE;
    int currentLongest = 0;
    for (final Entry<CharSequence, CharSequence> pair : lookupMap.entrySet()) {
      this.lookupMap.put(pair.getKey().toString(), pair.getValue().toString());
      this.prefixSet.set(pair.getKey().charAt(0));
      final int keyLen = pair.getKey().length();
      currentShortest = Math.min(currentShortest, keyLen);
      currentLongest = Math.max(currentLongest, keyLen);
    }
    this.shortest = currentShortest;
    this.longest = currentLongest;
  }

  public LookupTranslator(final LookupTranslator other) {
    this.lookupMap.putAll(other.lookupMap);
    this.prefixSet.or(other.prefixSet); // copy other.prefixSet to this.prefixSet
    this.shortest = other.shortest;
    this.longest = other.longest;
  }

  @Override
  public int translate(final CharSequence input, final int index,
      final Appendable appendable) throws IOException {
    // check if translation exists for the input at position index
    if (prefixSet.get(input.charAt(index))) {
      int max = longest;
      if (index + longest > input.length()) {
        max = input.length() - index;
      }
      // implement greedy algorithm by trying maximum match first
      for (int i = max; i >= shortest; i--) {
        final CharSequence subSeq = input.subSequence(index, index + i);
        final String result = lookupMap.get(subSeq.toString());
        if (result != null) {
          appendable.append(result);
          return Character.codePointCount(subSeq, 0, subSeq.length());
        }
      }
    }
    return 0;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final LookupTranslator other = (LookupTranslator) o;
    return Equality.equals(lookupMap, other.lookupMap)
        && Equality.equals(prefixSet, other.prefixSet)
        && Equality.equals(shortest, other.shortest)
        && Equality.equals(longest, other.longest);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, lookupMap);
    result = Hash.combine(result, multiplier, prefixSet);
    result = Hash.combine(result, multiplier, shortest);
    result = Hash.combine(result, multiplier, longest);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("lookupMap", lookupMap)
        .append("prefixSet", prefixSet)
        .append("shortest", shortest)
        .append("longest", longest)
        .toString();
  }

  @Override
  public LookupTranslator cloneEx() {
    return new LookupTranslator(this);
  }
}
