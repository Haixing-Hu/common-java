////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.text.Joiner;
import ltd.qubit.commons.text.Splitter;

/**
 * The customized JAXB data type adaptor for the {@code List<Integer>} type.
 *
 * @author Haixing Hu
 */
public class IntListXmlAdapter extends XmlAdapter<String, List<Integer>> {

  @Override
  public List<Integer> unmarshal(final String str) {
    final List<Integer> list = new ArrayList<>();
    final List<String> values = new Splitter()
        .byChar(',')
        .strip(true)
        .ignoreEmpty(true)
        .split(str);
    for (final String value : values) {
      final Integer x = Integer.valueOf(value);
      list.add(x);
    }
    return list;
  }

  @Override
  public String marshal(final List<Integer> list) {
    return new Joiner(',').addAll(list).toString();
  }
}
