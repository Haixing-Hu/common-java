////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import ltd.qubit.commons.text.xml.jaxb.EnumXmlAdapter;

/**
 * The JAXB adaptor for the {@link Type} enumeration class.
 *
 * @author Haixing Hu
 */
public final class TypeXmlAdapter extends EnumXmlAdapter<Type> {

  public TypeXmlAdapter() {
    super(Type.class);
  }
}
