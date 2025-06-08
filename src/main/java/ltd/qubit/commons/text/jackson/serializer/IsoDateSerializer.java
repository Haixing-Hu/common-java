////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoDateCodec;

/**
 * {@link Date} 对象的 JACKSON 序列化器，使用 ISO-8601 格式 "yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoDateSerializer extends DateSerializer {

  private static final long serialVersionUID = 4166452457184587852L;

  public static final IsoDateSerializer INSTANCE = new IsoDateSerializer();

  public IsoDateSerializer() {
    super(new IsoDateCodec());
  }
}