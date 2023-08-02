////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ltd.qubit.commons.lang.Type;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for the {@link StackConfig} class.
 *
 * @author Haixing Hu
 */
public class StackConfigTest {

  public void testIsStackEmpty() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();

    assertTrue(config.isStackEmpty());
    config.push(config1);
    assertFalse(config.isStackEmpty());
  }

  public void testStackSize() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();

    assertEquals(0, config.stackSize());
    config.push(config1);
    assertEquals(1, config.stackSize());
    config.push(config2);
    assertEquals(2, config.stackSize());
    config.push(config3);
    assertEquals(3, config.stackSize());
  }

  public void testIsEmpty() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();

    assertTrue(config.isEmpty());
    config.push(config1);
    assertTrue(config.isEmpty());
    config.push(config2);
    assertTrue(config.isEmpty());
    config.push(config3);
    assertTrue(config.isEmpty());

    config1.add("prop1", Type.INT);
    assertFalse(config.isEmpty());
  }

  public void testSize() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();

    assertEquals(0, config.size());
    config.push(config1);
    assertEquals(0, config.size());
    config.push(config2);
    assertEquals(0, config.size());
    config.push(config3);
    assertEquals(0, config.size());

    config1.add("prop1", Type.INT);
    assertEquals(1, config.size());

    config1.add("prop2", Type.INT);
    assertEquals(2, config.size());

    config2.add("prop3", Type.INT);
    assertEquals(3, config.size());

    config2.add("prop1", Type.INT);
    assertEquals(3, config.size());

    config3.add("prop3", Type.INT);
    assertEquals(3, config.size());
  }

  @Test
  public void testGetNames() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();
    final Set<String> names = new HashSet<>();

    assertEquals(names, config.getNames());
    config.push(config1);
    assertEquals(names, config.getNames());
    config.push(config2);
    assertEquals(names, config.getNames());
    config.push(config3);
    assertEquals(names, config.getNames());

    config1.add("prop1", Type.INT);
    names.add("prop1");
    assertEquals(names, config.getNames());

    config1.add("prop2", Type.INT);
    names.add("prop2");
    assertEquals(names, config.getNames());

    config2.add("prop3", Type.INT);
    names.add("prop3");
    assertEquals(names, config.getNames());

    config2.add("prop1", Type.INT);
    assertEquals(names, config.getNames());

    config3.add("prop3", Type.INT);
    assertEquals(names, config.getNames());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetProperties() {
    final StackConfig config = new StackConfig();
    final DefaultConfig config1 = new DefaultConfig();
    final DefaultConfig config2 = new DefaultConfig();
    final DefaultConfig config3 = new DefaultConfig();
    final Map<String, DefaultProperty> propertiesMap = new HashMap<>();

    Assertions.assertThat(propertiesMap.values())
              .hasSameElementsAs((Collection<DefaultProperty>) config.getProperties());
    config.push(config1);
    Assertions.assertThat(propertiesMap.values())
              .hasSameElementsAs((Collection<DefaultProperty>) config.getProperties());
    config.push(config2);
    Assertions.assertThat(propertiesMap.values())
              .hasSameElementsAs((Collection<DefaultProperty>) config.getProperties());
    config.push(config3);
    Assertions.assertThat(propertiesMap.values())
              .hasSameElementsAs((Collection<DefaultProperty>) config.getProperties());

    final DefaultProperty prop1 = new DefaultProperty("prop1", Type.INT);
    config1.add(prop1);
    propertiesMap.put(prop1.getName(), prop1);
    Assertions.assertThat(propertiesMap.values())
              .hasSameElementsAs((Collection<DefaultProperty>) config.getProperties());

    final DefaultProperty prop2 = new DefaultProperty("prop2", Type.STRING);
    config1.add(prop2);
    propertiesMap.put(prop2.getName(), prop2);
    Assertions.assertThat(propertiesMap.values())
              .hasSameElementsAs((Collection<DefaultProperty>) config.getProperties());

    final DefaultProperty prop3 = new DefaultProperty("prop3", Type.FLOAT);
    config2.add(prop3);
    propertiesMap.put(prop3.getName(), prop3);
    Assertions.assertThat(propertiesMap.values())
              .hasSameElementsAs((Collection<DefaultProperty>) config.getProperties());

    final DefaultProperty prop4 = new DefaultProperty("prop1", Type.FLOAT);
    config2.add(prop4);
    propertiesMap.put(prop4.getName(), prop4);
    Assertions.assertThat(propertiesMap.values())
              .hasSameElementsAs((Collection<DefaultProperty>) config.getProperties());

    final DefaultProperty prop5 = new DefaultProperty("prop3", Type.DOUBLE);
    config3.add(prop5);
    propertiesMap.put(prop5.getName(), prop5);
    Assertions.assertThat(propertiesMap.values())
              .hasSameElementsAs((Collection<DefaultProperty>) config.getProperties());
  }
}
