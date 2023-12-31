////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDate;

/**
 * 符合 ISO-8601 的本地日期类 {@link LocalDate} 的 JSON 反序列化器，其编码格式为
 * "yyyy-mm-dd"。
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalDateDeserializer extends LocalDateDeserializer {

  private static final long serialVersionUID = 7884285368967920278L;

  public static final IsoLocalDateDeserializer INSTANCE =
      new IsoLocalDateDeserializer();

  public IsoLocalDateDeserializer() {
    super(new IsoLocalDateCodec());
  }
}
