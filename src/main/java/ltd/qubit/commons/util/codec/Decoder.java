////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import javax.annotation.Nullable;

/**
 * 提供将源对象解码为另一个目标对象的接口。
 *
 * @param <FROM>
 *     要解码的源对象的类型。
 * @param <TO>
 *     要解码到的目标对象的类型。
 * @author 胡海星
 */
public interface Decoder<FROM, TO> {

  /**
   * 将一个对象解码为另一个对象。
   *
   * @param source
   *          要解码的源对象。
   * @return 解码结果。
   * @throws DecodingException
   *           如果发生任何解码错误。
   */
  TO decode(FROM source) throws DecodingException;

  /**
   * 将一个对象解码为另一个对象,如果发生任何解码错误,则返回{@code null}。
   *
   * @param source
   *     要解码的源对象。
   * @return
   *     解码结果,如果发生任何解码错误,则为{@code null}。
   */
  @Nullable
  default TO decodeNoThrow(final FROM source) {
    try {
      return decode(source);
    } catch (final DecodingException e) {
      return null;
    }
  }

  /**
   * 将一个对象解码为另一个对象,如果发生任何解码错误,则抛出{@link RuntimeException}。
   *
   * @param source
   *     要解码的源对象。
   * @return
   *     解码结果。
   * @throws RuntimeException
   *     如果发生任何解码错误。
   */
  @Nullable
  default TO decodeThrowRuntime(final FROM source) {
    try {
      return decode(source);
    } catch (final DecodingException e) {
      throw new RuntimeException(e);
    }
  }
}