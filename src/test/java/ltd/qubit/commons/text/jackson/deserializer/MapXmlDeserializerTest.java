////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapXmlDeserializerTest {
  static class Key {
    private int id;
    private String name;

    public Key() {
      // empty
    }

    public int getId() {
      return id;
    }

    public void setId(final int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(final String name) {
      this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if ((o == null) || (getClass() != o.getClass())) {
        return false;
      }
      final Key other = (Key) o;
      return Equality.equals(id, other.id)
          && Equality.equals(name, other.name);
    }

    @Override
    public int hashCode() {
      final int multiplier = 7;
      int result = 3;
      result = Hash.combine(result, multiplier, id);
      result = Hash.combine(result, multiplier, name);
      return result;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("id", id)
          .append("name", name)
          .toString();
    }
  }

  static class Value {
    private int id;
    private String name;
    private double score;

    public Value() {
      // empty
    }

    public int getId() {
      return id;
    }

    public void setId(final int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public double getScore() {
      return score;
    }

    public void setScore(final double score) {
      this.score = score;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if ((o == null) || (getClass() != o.getClass())) {
        return false;
      }
      final Value other = (Value) o;
      return Equality.equals(id, other.id)
          && Equality.equals(name, other.name)
          && Equality.equals(score, other.score);
    }

    @Override
    public int hashCode() {
      final int multiplier = 7;
      int result = 3;
      result = Hash.combine(result, multiplier, id);
      result = Hash.combine(result, multiplier, name);
      result = Hash.combine(result, multiplier, score);
      return result;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("id", id)
          .append("name", name)
          .append("score", score)
          .toString();
    }
  }

  static class Obj {
    private Map<Integer, String> simpleMap;
    private Map<Key, Value> complexMap;

    public Obj() {
      // empty
    }

    public Map<Integer, String> getSimpleMap() {
      return simpleMap;
    }

    public void setSimpleMap(final Map<Integer, String> simpleMap) {
      this.simpleMap = simpleMap;
    }

    public Map<Key, Value> getComplexMap() {
      return complexMap;
    }

    public void setComplexMap(final Map<Key, Value> complexMap) {
      this.complexMap = complexMap;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if ((o == null) || (getClass() != o.getClass())) {
        return false;
      }
      final Obj other = (Obj) o;
      return Equality.equals(simpleMap, other.simpleMap)
          && Equality.equals(complexMap, other.complexMap);
    }

    @Override
    public int hashCode() {
      final int multiplier = 7;
      int result = 3;
      result = Hash.combine(result, multiplier, simpleMap);
      result = Hash.combine(result, multiplier, complexMap);
      return result;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("simpleMap", simpleMap)
          .append("complexMap", complexMap)
          .toString();
    }
  }

  @Test
  public void testMapXmlDeserializer() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final Obj obj = new Obj();
    obj.setSimpleMap(Map.of(1, "one", 2, "two", 3, "three"));
    final Key key1 = new Key();
    key1.setId(1);
    key1.setName("one");
    final Key key2 = new Key();
    key2.setId(2);
    key2.setName("two");
    final Key key3 = new Key();
    key3.setId(3);
    key3.setName("three");
    final Value value1 = new Value();
    value1.setId(1);
    value1.setName("one");
    value1.setScore(1.0);
    final Value value2 = new Value();
    value2.setId(2);
    value2.setName("two");
    value2.setScore(2.0);
    final Value value3 = new Value();
    value3.setId(3);
    value3.setName("three");
    value3.setScore(3.0);
    obj.setComplexMap(Map.of(key1, value1, key2, value2, key3, value3));
    final String xml = mapper.writeValueAsString(obj);
    System.out.println(xml);
    final Obj obj2 = mapper.readValue(xml, Obj.class);
    System.out.println(obj2);
    assertEquals(obj, obj2);
  }
}
