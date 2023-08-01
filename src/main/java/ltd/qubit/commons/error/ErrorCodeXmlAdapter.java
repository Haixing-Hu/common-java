////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.text.xml.jaxb.EnumXmlAdapter;

/**
 * The JAXB adaptor for the {@link ErrorCode} enumeration class.
 *
 * @author Haixing Hu
 */
public final class ErrorCodeXmlAdapter extends EnumXmlAdapter<ErrorCode> {

  public ErrorCodeXmlAdapter() {
    super(ErrorCode.class);
  }
}
