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
 * Interface that is directly implemented by PrivateBean.
 *
 * @author Haixing Hu
 */
public interface PrivateDirect extends PrivateIndirect {

  /**
   * A property accessible via a directly implemented interface.
   */
  String getBar();

  /**
   * A method accessible via a directly implemented interface.
   */
  String methodBar(String in);

}
