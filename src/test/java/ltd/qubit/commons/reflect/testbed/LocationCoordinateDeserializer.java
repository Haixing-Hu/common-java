////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import ltd.qubit.commons.text.jackson.deserializer.BigDecimalDeserializer;

/**
 * 地理位置坐标的JSON反序列化器。
 *
 * @author 胡海星
 */
public class LocationCoordinateDeserializer extends BigDecimalDeserializer {

  private static final long serialVersionUID = 7166442348959619965L;

  public LocationCoordinateDeserializer() {
    super(new LocationCoordinateCodec());
  }

}
