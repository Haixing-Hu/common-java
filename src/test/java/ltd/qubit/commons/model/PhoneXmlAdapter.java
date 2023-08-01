////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The JAXB XML adapter for the {@link Phone} class.
 *
 * @author Haixing Hu
 */
public class PhoneXmlAdapter extends XmlAdapter<String, Phone> {

  private final PhoneCodec codec = new PhoneCodec();

  @Override
  public Phone unmarshal(final String str) throws Exception {
    return codec.decode(str);
  }

  @Override
  public String marshal(final Phone phone) throws Exception {
    return codec.encode(phone);
  }
}
