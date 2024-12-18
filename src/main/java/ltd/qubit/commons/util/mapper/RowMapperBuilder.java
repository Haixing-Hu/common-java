////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.reflect.impl.SetterMethod;
import ltd.qubit.commons.reflect.impl.SetterMethodWithType;

import static ltd.qubit.commons.reflect.FieldUtils.getField;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyPath;

/**
 * {@link RowMapper}的构建器。
 * <p>
 * 使用方法为：
 * <pre><code>
 * final RowMapperBuilder&lt;MyEntity&gt; builder = new RowMapperBuilder&lt;&gt;(MyEntity.class);
 * final RowMapper&lt;T&gt; mapper = builder.add("Header1", MyEntity::getField1)
 *       .add("Header2", MyEntity::getField2)
 *       .add("Header3", MyEntity::getField3)
 *       .continueLastRow(true)
 *       .build();
 * </code></pre>
 *
 * @param <T>
 *     被映射的实体的类型。
 * @author 胡海星
 */
public class RowMapperBuilder<T> {

  final Class<T> type;

  List<String> headers = new ArrayList<>();

  Map<String, GetterMethod<T, ?>> getterMap = new HashMap<>();

  Map<String, SetterMethodWithType<T, ?>> setterMap = new HashMap<>();

  Map<String, String> propertyMap = new HashMap<>();

  Set<String> continueLastRowHeaders = new HashSet<>();

  SetterMethod<T, Integer> rowNumberSetter;

  ColumnHeaderTransformer columnHeaderTransformer;

  boolean firstRowAsHeaders = true;

  public RowMapperBuilder(final Class<T> type) {
    this.type = type;
  }

  public List<String> getHeaders() {
    return headers;
  }

  public Map<String, String> getPropertyMap() {
    return propertyMap;
  }

  public Map<String, GetterMethod<T, ?>> getGetterMap() {
    return getterMap;
  }

  public Map<String, SetterMethodWithType<T, ?>> getSetterMap() {
    return setterMap;
  }

  public boolean isContinueLastRow(final String header) {
    return continueLastRowHeaders.contains(header);
  }

  /**
   * 设置指定的列是否延续上一行的数据。
   *
   * @param header
   *     指定列的列头名称。
   * @param continueLastRow
   *     指定的列是否延续上一行的数据。如果为{@code true}，则当当前行的该列数据为空时，
   *     将使用上一行该列的数据，并将当前行该列的数据设置为上一行该列的数据。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public RowMapperBuilder<T> setContinueLastRow(final String header,
      final boolean continueLastRow) {
    if (continueLastRow) {
      continueLastRowHeaders.add(header);
    } else {
      continueLastRowHeaders.remove(header);
    }
    return this;
  }

  /**
   * 设置是否把第一行数据当做是列头。
   *
   * @param firstRowAsHeaders
   *     是否把第一行数据当做是列头。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public RowMapperBuilder<T> setFirstRowAsHeaders(final boolean firstRowAsHeaders) {
    this.firstRowAsHeaders = firstRowAsHeaders;
    return this;
  }

  /**
   * 设置行号的setter方法。
   *
   * @param rowNumberSetter
   *     行号的setter方法。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public RowMapperBuilder<T> rowNumberSetter(final SetterMethod<T, Integer> rowNumberSetter) {
    this.rowNumberSetter = rowNumberSetter;
    return this;
  }

  public RowMapperBuilder<T> columnHeaderTransformer(final ColumnHeaderTransformer columnHeaderTransformer) {
    this.columnHeaderTransformer = columnHeaderTransformer;
    return this;
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("生日", User::getBirthday)
   *    .addLambda("年龄", (user) -&gt; Period.between(user.getBirthday(), LocalDate.now()).getYears())
   *    .build();
   * </code></pre>
   *
   * @param <R>
   *     该列数据所对应的实体属性的类型。
   * @param header
   *     列头名称。
   * @param lambda
   *     一个lambda表达式，用于获取某个实体对应于该列的数据。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <R> RowMapperBuilder<T> addLambda(final String header, final GetterMethod<T, R> lambda) {
    return addLambda(header, lambda, false);
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("生日", User::getBirthday)
   *    .addLambda("年龄", (user) -&gt; Period.between(user.getBirthday(), LocalDate.now()).getYears())
   *    .build();
   * </code></pre>
   *
   * @param <R>
   *     该列数据所对应的实体属性的类型。
   * @param header
   *     列头名称。
   * @param lambda
   *     一个lambda表达式，用于获取某个实体对应于该列的数据。
   * @param continueLastRow
   *     该列是否延续上一行的数据。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <R> RowMapperBuilder<T> addLambda(final String header, final GetterMethod<T, R> lambda,
      final boolean continueLastRow) {
    headers.add(header);
    getterMap.put(header, lambda);
    if (continueLastRow) {
      continueLastRowHeaders.add(header);
    } else {
      continueLastRowHeaders.remove(header);
    }
    return this;
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("年龄", User::getAge)
   *    .build();
   * </code></pre>
   *
   * @param <R>
   *     该列数据所对应的实体属性的类型。
   * @param header
   *     列头名称。
   * @param getter
   *     该列数据所对应的实体属性的getter方法。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <R> RowMapperBuilder<T> add(final String header, final GetterMethod<T, R> getter) {
    return add(header, getter, false);
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName, false)
   *    .add("年龄", User::getAge, true)
   *    .build();
   * </code></pre>
   *
   * @param <R>
   *     该列数据所对应的实体属性的类型。
   * @param header
   *     列头名称。
   * @param getter
   *     该列数据所对应的实体属性的getter方法。
   * @param continueLastRow
   *     该列是否延续上一行的数据。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <R> RowMapperBuilder<T> add(final String header, final GetterMethod<T, R> getter,
      final boolean continueLastRow) {
    headers.add(header);
    getterMap.put(header, getter);
    // try to get the field from the getter
    final Field field = getField(type, getter);
    if (field != null) {
      propertyMap.put(header, field.getName());
    }
    if (continueLastRow) {
      continueLastRowHeaders.add(header);
    } else {
      continueLastRowHeaders.remove(header);
    }
    return this;
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("年龄", User::getAge)
   *    .build();
   * </code></pre>
   *
   * @param <R>
   *     该列数据所对应的实体属性的类型。
   * @param header
   *     列头名称。
   * @param argumentClass
   *     该列数据所对应的实体属性的Setter方法的参数的类对象。
   * @param setter
   *     该列数据所对应的实体属性的setter方法。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <R> RowMapperBuilder<T> add(final String header, final Class<R> argumentClass,
      final SetterMethod<T, R> setter) {
    return add(header, argumentClass, setter, false);
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName, false)
   *    .add("年龄", User::getAge, true)
   *    .build();
   * </code></pre>
   *
   * @param <R>
   *     该列数据所对应的实体属性的类型。
   * @param header
   *     列头名称。
   * @param argumentClass
   *     该列数据所对应的实体属性的Setter方法的参数的类对象。
   * @param setter
   *     该列数据所对应的实体属性的setter方法。
   * @param continueLastRow
   *     该列是否延续上一行的数据。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <R> RowMapperBuilder<T> add(final String header, final Class<R> argumentClass,
      final SetterMethod<T, R> setter, final boolean continueLastRow) {
    headers.add(header);
    setterMap.put(header, new SetterMethodWithType<>(argumentClass, setter));
    // try to get the field from the setter
    final Field field = getField(type, setter);
    if (field != null) {
      propertyMap.put(header, field.getName());
    }
    if (continueLastRow) {
      continueLastRowHeaders.add(header);
    } else {
      continueLastRowHeaders.remove(header);
    }
    return this;
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("年龄", User::getAge)
   *    .add("机构", User::getOrganization, Organization::getName)
   *    .build();
   * </code></pre>
   *
   * @param <P1>
   *     该列数据所对应的实体的属性路径上第1个属性的类型。
   * @param <R>
   *     该列数据所对应的实体的属性路径上最后一个属性的类型。
   * @param header
   *     列头名称。
   * @param g1
   *     该列数据所对应的实体的属性路径上第1个属性的getter方法。
   * @param g2
   *     该列数据所对应的实体的属性路径上第2个属性的getter方法。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <P1, R> RowMapperBuilder<T> add(final String header,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, R> g2) {
    return add(header, g1, g2, false);
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("年龄", User::getAge)
   *    .add("机构", User::getOrganization, Organization::getName, true)
   *    .build();
   * </code></pre>
   *
   * @param <P1>
   *     该列数据所对应的实体的属性路径上第1个属性的类型。
   * @param <R>
   *     该列数据所对应的实体的属性路径上最后一个属性的类型。
   * @param header
   *     列头名称。
   * @param g1
   *     该列数据所对应的实体的属性路径上第1个属性的getter方法。
   * @param g2
   *     该列数据所对应的实体的属性路径上第2个属性的getter方法。
   * @param continueLastRow
   *     该列是否延续上一行的数据。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <P1, R> RowMapperBuilder<T> add(final String header,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, R> g2,
      final boolean continueLastRow) {
    headers.add(header);
    final String path = getPropertyPath(type, g1, g2);
    propertyMap.put(header, path);
    if (continueLastRow) {
      continueLastRowHeaders.add(header);
    } else {
      continueLastRowHeaders.remove(header);
    }
    return this;
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("年龄", User::getAge)
   *    .add("机构地址", User::getOrganization, Organization::getContact, Contact::getAddress)
   *    .build();
   * </code></pre>
   *
   * @param <P1>
   *     该列数据所对应的实体的属性路径上第1个属性的类型。
   * @param <P2>
   *     该列数据所对应的实体的属性路径上第2个属性的类型。
   * @param <R>
   *     该列数据所对应的实体的属性路径上最后一个属性的类型。
   * @param header
   *     列头名称。
   * @param g1
   *     该列数据所对应的实体的属性路径上第1个属性的getter方法。
   * @param g2
   *     该列数据所对应的实体的属性路径上第2个属性的getter方法。
   * @param g3
   *     该列数据所对应的实体的属性路径上第3个属性的getter方法。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <P1, P2, R> RowMapperBuilder<T> add(final String header,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3) {
    return add(header, g1, g2, g3, false);
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("年龄", User::getAge)
   *    .add("机构地址", User::getOrganization, Organization::getContact, Contact::getAddress, true)
   *    .build();
   * </code></pre>
   *
   * @param <P1>
   *     该列数据所对应的实体的属性路径上第1个属性的类型。
   * @param <P2>
   *     该列数据所对应的实体的属性路径上第2个属性的类型。
   * @param <R>
   *     该列数据所对应的实体的属性路径上最后一个属性的类型。
   * @param header
   *     列头名称。
   * @param g1
   *     该列数据所对应的实体的属性路径上第1个属性的getter方法。
   * @param g2
   *     该列数据所对应的实体的属性路径上第2个属性的getter方法。
   * @param g3
   *     该列数据所对应的实体的属性路径上第3个属性的getter方法。
   * @param continueLastRow
   *     该列是否延续上一行的数据。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <P1, P2, R> RowMapperBuilder<T> add(final String header,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3,
      final boolean continueLastRow) {
    headers.add(header);
    final String path = getPropertyPath(type, g1, g2, g3);
    propertyMap.put(header, path);
    if (continueLastRow) {
      continueLastRowHeaders.add(header);
    } else {
      continueLastRowHeaders.remove(header);
    }
    return this;
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("年龄", User::getAge)
   *    .add("机构所在城市", User::getOrganization, Organization::getContact, Contact::getCity, Info::getName)
   *    .build();
   * </code></pre>
   *
   * @param <P1>
   *     该列数据所对应的实体的属性路径上第1个属性的类型。
   * @param <P2>
   *     该列数据所对应的实体的属性路径上第2个属性的类型。
   * @param <P3>
   *     该列数据所对应的实体的属性路径上第3个属性的类型。
   * @param <R>
   *     该列数据所对应的实体的属性路径上最后一个属性的类型。
   * @param header
   *     列头名称。
   * @param g1
   *     该列数据所对应的实体的属性路径上第1个属性的getter方法。
   * @param g2
   *     该列数据所对应的实体的属性路径上第2个属性的getter方法。
   * @param g3
   *     该列数据所对应的实体的属性路径上第3个属性的getter方法。
   * @param g4
   *     该列数据所对应的实体的属性路径上第4个属性的getter方法。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <P1, P2, P3, R> RowMapperBuilder<T> add(final String header,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4) {
    return add(header, g1, g2, g3, g4, false);
  }

  /**
   * 添加一个列的映射。
   * <p>
   * 使用例子：
   * <pre><code>
   * final RowMapper&lt;User&gt; mapper = new RowMapperBuilder&lt;&gt;(User.class)
   *    .add("姓名", User::getName)
   *    .add("年龄", User::getAge)
   *    .add("机构所在城市", User::getOrganization, Organization::getContact, Contact::getCity, Info::getName, true)
   *    .build();
   * </code></pre>
   *
   * @param <P1>
   *     该列数据所对应的实体的属性路径上第1个属性的类型。
   * @param <P2>
   *     该列数据所对应的实体的属性路径上第2个属性的类型。
   * @param <P3>
   *     该列数据所对应的实体的属性路径上第3个属性的类型。
   * @param <R>
   *     该列数据所对应的实体的属性路径上最后一个属性的类型。
   * @param header
   *     列头名称。
   * @param g1
   *     该列数据所对应的实体的属性路径上第1个属性的getter方法。
   * @param g2
   *     该列数据所对应的实体的属性路径上第2个属性的getter方法。
   * @param g3
   *     该列数据所对应的实体的属性路径上第3个属性的getter方法。
   * @param g4
   *     该列数据所对应的实体的属性路径上第4个属性的getter方法。
   * @param continueLastRow
   *     该列是否延续上一行的数据。
   * @return
   *     此{@link RowMapperBuilder}。
   */
  public <P1, P2, P3, R> RowMapperBuilder<T> add(final String header,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4,
      final boolean continueLastRow) {
    headers.add(header);
    final String path = getPropertyPath(type, g1, g2, g3, g4);
    propertyMap.put(header, path);
    if (continueLastRow) {
      continueLastRowHeaders.add(header);
    } else {
      continueLastRowHeaders.remove(header);
    }
    return this;
  }

  /**
   * 根据已有的配置，创建一个新的{@link RowMapper}.
   *
   * @return
   *     新创建的{@link RowMapper}。
   */
  public BasicRowMapper<T> build() {
    return new BasicRowMapper<>(this);
  }
}
