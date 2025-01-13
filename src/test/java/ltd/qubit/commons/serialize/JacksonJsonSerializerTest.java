////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.serialize;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.testbed.model.City;
import ltd.qubit.commons.testbed.model.Info;
import ltd.qubit.commons.testbed.model.Location;

public class JacksonJsonSerializerTest {

  private final RandomEx random = new RandomEx();

  private Info generateInfo() {
    final Info info = new Info();
    info.setId(random.nextLong());
    info.setCode(random.nextString(1, 64));
    info.setName(random.nextString(1, 128));
    return info;
  }

  private Location generationLocation() {
    final Location location = new Location();
    location.setLatitude(random.nextBigDecimal(0.0, 180.0));
    location.setLongitude(random.nextBigDecimal(0.0, 100.0));
    return location;
  }


  private City generateCity() {
    final City city = new City();
    city.setName(random.nextString(10));
    city.setCode(random.nextString(1, 64));
    city.setName(random.nextString(1, 128));
    city.setProvince(generateInfo());
    city.setPhoneArea(random.nextString(3));
    city.setPostalcode(random.nextString(8));
    city.setIcon(random.nextString(6, 32));
    city.setUrl(random.nextString(10, 128));
    city.setDescription(null);
    city.setLocation(generationLocation());
    return city;
  }

  private List<City> generateCities() {
    final List<City> cities = new ArrayList<>();
    final int n = random.nextInt(2, 10);
    for (int i = 0; i < n; ++i) {
      cities.add(generateCity());
    }
    return cities;
  }

  @Test
  public void testSerializeList() throws IOException {
    final List<City> cities = generateCities();
    final JacksonJsonSerializer<City> serializer = new JacksonJsonSerializer<>();
    final StringWriter writer = new StringWriter();
    serializer.init(writer);
    serializer.serializeList(writer, cities);
    serializer.close(writer);
    System.out.println(writer);
  }
}
