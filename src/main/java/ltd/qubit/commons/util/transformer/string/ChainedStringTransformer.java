////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;

import java.util.ArrayList;
import java.util.List;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A ChainedStringTransformer chains a list of string transformers.
 *
 * @author Haixing Hu
 */
public class ChainedStringTransformer implements StringTransformer {

  @XmlElements({
      @XmlElement(name = "lowercase-transformer", type = LowercaseTransformer.class),
      @XmlElement(name = "uppercase-transformer", type = UppercaseTransformer.class),
      @XmlElement(name = "strip-start-transformer", type = StripStartTransformer.class),
      @XmlElement(name = "strip-end-transformer", type = StripEndTransformer.class),
      @XmlElement(name = "strip-transformer", type = StripTransformer.class),
      @XmlElement(name = "regex-transformer", type = RegexTransformer.class)
  })
  private List<StringTransformer> transformers;

  public ChainedStringTransformer() {
    transformers = new ArrayList<>();
  }

  public ChainedStringTransformer(final List<StringTransformer> transformers) {
    this.transformers = requireNonNull("transformers", transformers);
  }

  public final List<StringTransformer> getTransformers() {
    return transformers;
  }

  public final ChainedStringTransformer setTransformers(
      final List<StringTransformer> transformers) {
    this.transformers = requireNonNull("transformers", transformers);
    return this;
  }

  public int size() {
    return transformers.size();
  }

  public boolean isEmpty() {
    return transformers.isEmpty();
  }

  public ChainedStringTransformer add(final StringTransformer transformer) {
    if (transformer == null) {
      throw new NullPointerException();
    }
    transformers.add(transformer);
    return this;
  }

  @Override
  public String transform(final String str) {
    String result = str;
    for (final StringTransformer transformer : transformers) {
      result = transformer.transform(result);
    }
    return result;
  }

  @Override
  public ChainedStringTransformer clone() {
    return new ChainedStringTransformer(Assignment.deepClone(transformers));
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ChainedStringTransformer other = (ChainedStringTransformer) o;
    return Equality.equals(transformers, other.transformers);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, transformers);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("transformers", transformers)
            .toString();
  }
}
