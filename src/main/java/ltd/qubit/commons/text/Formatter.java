////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

/**
 * 格式化器的通用接口。
 *
 * @param <INPUT>
 *          格式化输入的类型。
 * @param <OUTPUT>
 *          格式化输出的类型。
 * @author 胡海星
 */
public interface Formatter<INPUT, OUTPUT> {

  /**
   * 将输入格式化为输出。
   *
   * @param input
   *          要格式化的输入对象。
   * @return 格式化结果的输出对象。
   */
  OUTPUT format(INPUT input);
}