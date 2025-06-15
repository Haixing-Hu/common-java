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
 * 解析器的通用接口。
 *
 * @param <INPUT>
 *     解析输入的类型。
 * @param <OUTPUT>
 *     解析输出的类型。
 * @author 胡海星
 */
public interface Parser<INPUT, OUTPUT> {

  /**
   * 将输入解析为输出。
   *
   * @param input
   *     要解析的输入对象。
   * @return
   *     作为解析结果的输出对象。
   * @throws ParsingException
   *     如果发生任何错误。
   */
  OUTPUT parse(INPUT input) throws ParsingException;
}