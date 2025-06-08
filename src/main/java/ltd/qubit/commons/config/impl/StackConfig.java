////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import ltd.qubit.commons.config.AbstractConfig;
import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.config.Property;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link Config} 接口的实现，它从一个 {@link Config} 栈中获取属性值。
 *
 * <p>属性值在 {@link Config} 栈中从顶部（最后一个放入栈中）到底部（第一个放入栈中）进行查找。
 *
 * @author 胡海星
 */
public class StackConfig extends AbstractConfig {

  private static final long serialVersionUID = 7815389819448399587L;

  /**
   * 配置栈。
   */
  private final Stack<Config> configs;

  /**
   * 构造一个空的 {@link StackConfig}。
   */
  public StackConfig() {
    configs = new Stack<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    if (configs.isEmpty()) {
      return null;
    } else {
      return configs.peek().getDescription();
    }
  }

  /**
   * 将一个 {@link Config} 推入栈中。
   *
   * @param config
   *     要推入栈中的 {@link Config}。
   * @throws NullPointerException
   *     如果 {@code config} 为 null。
   */
  public void push(final Config config) {
    configs.push(Argument.requireNonNull("config", config));
  }

  /**
   * 弹出栈顶的 {@link Config} 对象。
   *
   * @return 栈顶的 {@link Config} 对象。
   * @throws EmptyStackException
   *     如果此栈为空。
   */
  public Config pop() throws EmptyStackException {
    return configs.pop();
  }

  /**
   * 测试此栈是否为空。
   *
   * @return 如果栈为空则返回 true；否则返回 false。
   */
  public boolean isStackEmpty() {
    return configs.isEmpty();
  }

  /**
   * 获取栈的大小。
   *
   * @return 栈的大小。
   */
  public int stackSize() {
    return configs.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    for (final Config config : configs) {
      if (!config.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return getNames().size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<? extends Property> getProperties() {
    final Map<String, Property> propertiesMap = new HashMap<>();
    for (final Config config : configs) {
      for (final Property prop : config.getProperties()) {
        // note that the property in the last-in config will override
        // the properties with the same name in the early config
        propertiesMap.put(prop.getName(), prop);
      }
    }
    return propertiesMap.values();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getNames() {
    final Set<String> names = new HashSet<>();
    for (final Config config : configs) {
      names.addAll(config.getNames());
    }
    return names;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final String name) {
    for (final Config config : configs) {
      if (config.contains(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Property get(final String name) {
    // look up the property from the last to the first
    for (int i = configs.size() - 1; i >= 0; --i) {
      final Config config = configs.get(i);
      if (config.contains(name)) {
        return config.get(name);
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StackConfig cloneEx() {
    final StackConfig result = (StackConfig) super.cloneEx();
    for (final Config config : configs) {
      result.configs.add(config);
    }
    return result;
  }
}