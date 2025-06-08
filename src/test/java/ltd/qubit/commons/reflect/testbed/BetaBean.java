////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

public class BetaBean extends AbstractChild {

  private String secret = "utah";

  public String getSecret() {
    return secret;
  }

  public void setNoGetterProperty(final String secret) {
    this.secret = secret;
  }

  public void setNoGetterMappedProperty(final String secret, final String key) {
    this.secret = "MAP:" + secret;
  }

  public BetaBean(final String name) {
    setName(name);
  }
}