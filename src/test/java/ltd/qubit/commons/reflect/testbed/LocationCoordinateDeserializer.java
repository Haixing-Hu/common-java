////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import ltd.qubit.commons.text.jackson.deserializer.BigDecimalDeserializer;

/**
 * 地理位置坐标的JSON反序列化器。
 *
 * @author Haixing Hu
 */
public class LocationCoordinateDeserializer extends BigDecimalDeserializer {

  private static final long serialVersionUID = 7166442348959619965L;

  public LocationCoordinateDeserializer() {
    super(new LocationCoordinateCodec());
  }

}
