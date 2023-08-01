////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.text.Stripper;

/**
 * The XML adapter used to trim strings.
 *
 * @author Haixing Hu
 */
public class TrimStringXmlAdapter extends XmlAdapter<String, String> {

  @Override
  public String unmarshal(final String v) {
    return new Stripper().strip(v);
  }

  @Override
  public String marshal(final String v) {
    return new Stripper().strip(v);
  }
}
