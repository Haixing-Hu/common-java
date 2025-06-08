////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The customized JAXB data type adapter for the enumeration types.
 *
 * <p>This adapter will automatically change the style of the name of an
 * enumerator, in the following way:
 *
 * <ul>
 * <li>Lower case the name of the enumerator.</li>
 * <li>Replace the '_' with '-'.</li>
 * </ul>
 *
 * <p>For example, suppose we have an enumeration class as follows:
 *
 * <pre>
 * package com.github.haixinghu.csl;
 * public enum MyEnum {
 *  FIRST,
 *  SECOND_ITEM,
 *  THE_THIRD_ITEM,
 * }
 * </pre>
 *
 * <p>We should create an adapter class extending the {@link EnumXmlAdapter} as
 * follows:
 *
 * <pre>
 *  package com.github.haixinghu.csl;
 *  public final class MyEnumXmlAdapter extends EnumXmlAdapter&lt;MyEnum&gt; {
 *    public MyEnumXmlAdapter() {
 *      super(MyEnum.class);
 *    }
 *  }
 * </pre>
 *
 * <p>Then we should use a
 * <a href="http://www.onjava.com/pub/a/onjava/2004/04/21/declarative.html?page=3">
 * package level annotation</a> in the {@code package-info.java} as follows:
 *
 * <pre>
 *  &#64;XmlJavaTypeAdapters({
 *    &#64;XmlJavaTypeAdapter(type=MyEnum.class, value=MyEnumXmlAdapter.class)
 *  })
 *  package com.github.haixinghu.csl;
 *  import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
 *  import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
 * </pre>
 *
 * <p>This will make the JAXB automatically converts the names of enumerators in
 * the {@code MyEnum} enumeration class as follows:
 *
 * <pre>
 *  FIRST &lt;==&gt; "first"
 *  SECOND_ITEM &lt;==&gt; "second-item"
 *  THE_THIRD_ITEM &lt;==&gt; "third-item"
 * </pre>
 *
 * @author Haixing Hu
 */
public class EnumXmlAdapter<T extends Enum<T>> extends XmlAdapter<String, T> {

  private final Class<T> cls;

  protected EnumXmlAdapter(final Class<T> cls) {
    this.cls = cls;
  }

  @Override
  public T unmarshal(final String v) {
    if (v == null) {
      return null;
    } else {
      return Enum.valueOf(cls, v.toUpperCase().replace('-', '_'));
    }
  }

  @Override
  public String marshal(final T v) {
    if (v == null) {
      return null;
    } else {
      return v.name().toLowerCase().replace('_', '-');
    }
  }
}