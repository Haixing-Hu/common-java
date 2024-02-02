////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.io.IOException;
import java.net.URL;

import ltd.qubit.commons.io.resource.Resource;
import ltd.qubit.commons.text.jackson.XmlMapperUtils;

/**
 * A factory object used to create the XML deserializable objects.
 *
 * @author Haixing Hu
 */
public class XmlDeserializableFactory<T> {

  private Resource resource;

  private Class<T> type;

  public XmlDeserializableFactory() {
    //  empty
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(final Resource resource) {
    this.resource = resource;
  }

  public Class<T> getType() {
    return type;
  }

  public void setType(final Class<T> type) {
    this.type = type;
  }

  public T create() throws IOException {
    final URL url = resource.getURL();
    return XmlMapperUtils.parse(url, type);
  }
}
