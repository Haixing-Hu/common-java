////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;

import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * ChainedStringTransformer链接了一系列字符串转换器。
 *
 * @author 胡海星
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "chained-string-transformer")
public class ChainedStringTransformer implements StringTransformer {

  @XmlElements({
      @XmlElement(name = "lowercase-transformer", type = LowercaseTransformer.class),
      @XmlElement(name = "uppercase-transformer", type = UppercaseTransformer.class),
      @XmlElement(name = "strip-start-transformer", type = StripStartTransformer.class),
      @XmlElement(name = "strip-end-transformer", type = StripEndTransformer.class),
      @XmlElement(name = "strip-transformer", type = StripTransformer.class),
      @XmlElement(name = "regex-transformer", type = RegexTransformer.class)
  })
  @XmlElementWrapper(name = "transformers")
  private List<StringTransformer> transformers;

  /**
   * 构造一个空的 {@link ChainedStringTransformer}。
   */
  public ChainedStringTransformer() {
    transformers = new ArrayList<>();
  }

  /**
   * 构造一个 {@link ChainedStringTransformer}。
   *
   * @param transformers
   *     要使用的转换器列表。
   */
  public ChainedStringTransformer(final List<StringTransformer> transformers) {
    this.transformers = requireNonNull("transformers", transformers);
  }

  /**
   * 获取此链中的转换器列表。
   *
   * @return
   *     此链中的转换器列表。
   */
  public final List<StringTransformer> getTransformers() {
    return transformers;
  }

  /**
   * 设置此链中的转换器列表。
   *
   * @param transformers
   *     新的转换器列表。
   * @return
   *     返回此对象。
   */
  public final ChainedStringTransformer setTransformers(
      final List<StringTransformer> transformers) {
    this.transformers = requireNonNull("transformers", transformers);
    return this;
  }

  /**
   * 返回此链中的转换器数量。
   *
   * @return
   *     此链中的转换器数量。
   */
  public int size() {
    return transformers.size();
  }

  /**
   * 测试此链是否不包含任何转换器。
   *
   * @return
   *     如果此链不包含任何转换器，则返回{@code true}；否则返回{@code false}。
   */
  public boolean isEmpty() {
    return transformers.isEmpty();
  }

  /**
   * 将转换器添加到此链的末尾。
   *
   * @param transformer
   *     要添加的转换器。
   * @return
   *     返回此对象。
   */
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
  public ChainedStringTransformer cloneEx() {
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