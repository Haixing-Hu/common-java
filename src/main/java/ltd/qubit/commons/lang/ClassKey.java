////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 该类包装类对象，可以用作哈希映射的键。
 *
 * <p>如果我们使用 {@link Class} 对象作为哈希映射的键，会产生很多问题。例如，如果您的代码运行在
 * Web 容器中并且您习惯于对 Web 应用程序进行热部署，那么对单个类对象的保留引用可能导致严重的
 * 永久代内存泄漏。
 *
 * <p>简而言之，问题在于每个类都包含对其类加载器的引用，而每个类加载器都包含对它已加载的每个类的引用。
 * 因此，如果一个类是可达的，那么所有类都是可达的。</p>
 *
 * <p>如果您用作键的某个类被重新加载，那么：</p>
 * <ul>
 * <li>该类的旧版本和新版本将不相等。</li>
 * <li>查找新类最初会得到"未命中"。</li>
 * <li>将新类添加到映射后，您现在将为该类的不同版本拥有两个不同的映射条目。</li>
 * <li>即使该类的两个版本之间没有代码差异，这也适用。它们之间的差异仅仅是因为它们由不同的类加载器加载。</li>
 * </ul>
 *
 * @author 胡海星
 */
public class ClassKey implements Comparable<ClassKey> {

  private final String className;

  /**
   * 构造一个新的 ClassKey。
   *
   * @param cls 要包装的类
   */
  public ClassKey(final Class<?> cls) {
    this.className = cls.getName();
  }

  /**
   * 获取类名。
   *
   * @return 类名
   */
  public String getClassName() {
    return className;
  }

  /**
   * 获取实际的类对象。
   *
   * @return 实际的类对象
   * @throws RuntimeException 如果找不到该类
   */
  public Class<?> getActualClass() {
    try {
      return Class.forName(className);
    } catch (final ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ClassKey other = (ClassKey) o;
    return Equality.equals(className, other.className);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, className);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("className", className)
        .toString();
  }

  @Override
  public int compareTo(final ClassKey other) {
    return className.compareTo(other.className);
  }
}