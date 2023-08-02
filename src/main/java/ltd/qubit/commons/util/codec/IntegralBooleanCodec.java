////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * The codec of the {@link Boolean} type which maps {@code true} to "1" and
 * {@code false} to "0".
 *
 * @author Haixing Hu
 */
public class IntegralBooleanCodec extends BooleanCodec {

  public IntegralBooleanCodec() {
    super("1", "0", true);
  }
}
