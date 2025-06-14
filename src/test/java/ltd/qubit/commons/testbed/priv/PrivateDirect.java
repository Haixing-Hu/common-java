////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.priv;

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