////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoDateCodec;

/**
 * 符合 ISO-8601 的时间戳类 {@link Date} 的 JSON 序列化器，其编码格式为
 * "yyyy-MM-dd'T'HH:mm:ss'Z'"。
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
