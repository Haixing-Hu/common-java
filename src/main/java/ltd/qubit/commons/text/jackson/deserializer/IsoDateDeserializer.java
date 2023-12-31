////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.IsoDateCodec;

import javax.annotation.concurrent.Immutable;
import java.util.Date;

/**
 * 符合 ISO-8601 的时间戳类 {@link Date} 的 JSON 反序列化器，其编码格式为
 * "yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author Haixing Hu
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
