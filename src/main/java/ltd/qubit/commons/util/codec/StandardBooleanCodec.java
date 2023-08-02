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
 * The codec of the {@link Boolean} type which maps {@code true} to "true" and
 * {@code false} to "false".
 *
 * @author Haixing Hu
 */
public class StandardBooleanCodec extends BooleanCodec {

  public StandardBooleanCodec() {
    super("true", "false", true);
  }
}
