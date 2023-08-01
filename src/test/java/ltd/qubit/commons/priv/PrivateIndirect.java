////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.priv;

/**
 * Interface that is indirectly implemented by PrivateBean.
 *
 * @author Haixing Hu
 */
public interface PrivateIndirect {

  /**
   * A property accessible via an indirectly implemented interface.
   */
  String getBaz();

  /**
   * A method accessible via an indirectly implemented interface.
   */
  String methodBaz(String in);

}
