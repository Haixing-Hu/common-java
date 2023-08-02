////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
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
