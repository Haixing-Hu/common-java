////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * {@link Boolean}类型的编解码器,它将{@code true}映射到"1",将{@code false}映射到"0"。
 *
 * @author 胡海星
 */
public class IntegralBooleanCodec extends BooleanCodec {

  public IntegralBooleanCodec() {
    super("1", "0", true);
  }
}