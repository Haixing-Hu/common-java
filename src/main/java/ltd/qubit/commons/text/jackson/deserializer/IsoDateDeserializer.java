////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.IsoDateCodec;

import java.util.Date;
import javax.annotation.concurrent.Immutable;

/**
 * 符合 ISO-8601 的时间戳类 {@link Date} 的 JSON 反序列化器，其编码格式为
 * "yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoDateDeserializer extends DateDeserializer {

  private static final long serialVersionUID = 8254014955826356806L;

  public static final IsoDateDeserializer INSTANCE =
      new IsoDateDeserializer();

  public IsoDateDeserializer() {
    super(new IsoDateCodec());
  }
}
