////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.StringUtils.addPrefixToEachLine;

public class JsonMapperUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(JsonMapperUtils.class);

  private JsonMapperUtils() {}

  /**
   * 从JSON字符串资源文件解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param file
   *     指定的资源文件。
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final File file, final Class<T> cls)
      throws IOException {
    return parse(file, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串资源文件解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param file
   *     指定的资源文件。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final File file, final Class<T> cls, final JsonMapper mapper)
      throws IOException {
    final URL url = file.toURI().toURL();
    return parse(url, cls, mapper);
  }

  /**
   * 从JSON字符串资源URL解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param url
   *     指定的资源URL。
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final URL url, final Class<T> cls)
      throws IOException {
    return parse(url, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串资源URL解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param url
   *     指定的资源URL。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final URL url, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    return mapper.readValue(url, cls);
  }

  /**
   * 从JSON字符串输入流解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param in
   *     指定的输入流.
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final InputStream in, final Class<T> cls)
      throws IOException {
    return parse(in, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串输入流解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param in
   *     指定的输入流.
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final InputStream in, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    return mapper.readValue(in, cls);
  }

  /**
   * 从JSON字符串reader解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param reader
   *     指定的reader.
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final Reader reader, final Class<T> cls)
      throws IOException {
    return parse(reader, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串reader解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param reader
   *     指定的reader.
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final Reader reader, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    return mapper.readValue(reader, cls);
  }

  /**
   * 从JSON字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param json
   *     指定的JSON字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws JsonProcessingException
   *     若发生任何解析错误。
   */
  public static <T> T parse(final String json, final Class<T> cls)
      throws JsonProcessingException {
    return parse(json, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param json
   *     指定的JSON字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws JsonProcessingException
   *     若发生任何解析错误。
   */
  public static <T> T parse(final String json, final Class<T> cls,
      final JsonMapper mapper) throws JsonProcessingException {
    return mapper.readValue(json, cls);
  }

  /**
   * 从JSON字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param json
   *     指定的JSON字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> T parseNoThrow(final String json, final Class<T> cls) {
    return parseNoThrow(json, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param json
   *     指定的JSON字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> T parseNoThrow(final String json, final Class<T> cls,
      final JsonMapper mapper) {
    try {
      return mapper.readValue(json, cls);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to parse the object {} from the JSON: {}",
              cls.getName(), json, e);
      return null;
    }
  }

  public static <T> List<T> parseList(final File file, final Class<T> cls)
      throws IOException {
    return parseList(file, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseList(final File file, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    try (final InputStream in = new FileInputStream(file)) {
      return parseList(in, cls, mapper);
    }
  }

  public static <T> List<T> parseList(final URL url, final Class<T> cls)
      throws IOException {
    return parseList(url, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseList(final URL url, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    try (final InputStream in = url.openStream()) {
      return parseList(in, cls, mapper);
    }
  }

  public static <T> List<T> parseList(final InputStream in, final Class<T> cls)
      throws IOException {
    return parseList(in, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseList(final InputStream in, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    final InputStreamReader reader = new InputStreamReader(in, UTF_8);
    return parseList(reader, cls, mapper);
  }

  public static <T> List<T> parseList(final Reader reader, final Class<T> cls)
      throws IOException {
    return parseList(reader, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseList(final Reader reader, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    final TypeFactory factory = mapper.getTypeFactory();
    final JavaType listType = factory.constructCollectionType(List.class, cls);
    return mapper.readValue(reader, listType);
  }

  public static <T> List<T> parseList(final String json, final Class<T> cls)
      throws JsonProcessingException {
    return parseList(json, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseList(final String json, final Class<T> cls,
      final JsonMapper mapper) throws JsonProcessingException {
    final TypeFactory factory = mapper.getTypeFactory();
    final JavaType listType = factory.constructCollectionType(List.class, cls);
    return mapper.readValue(json, listType);
  }

  public static <T> List<T> parseListNoThrow(final Reader reader, final Class<T> cls) {
    return parseListNoThrow(reader, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseListNoThrow(final Reader reader, final Class<T> cls,
      final JsonMapper mapper) {
    try {
      return parseList(reader, cls, mapper);
    } catch (final IOException e) {
      LOGGER.error("Failed to parse the object {} from the JSON: {}",
          cls.getName(), reader, e);
      return null;
    }
  }

  public static <T> List<T> parseListNoThrow(final String json, final Class<T> cls) {
    return parseListNoThrow(json, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseListNoThrow(final String json, final Class<T> cls,
      final JsonMapper mapper) {
    try {
      return parseList(json, cls, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to parse the object {} from the JSON: {}",
          cls.getName(), json, e);
      return null;
    }
  }

  /**
   * 将指定的对象格式化为JSON字符串。
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @return
   *     该对象的JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNoThrow(final T obj) {
    return formatNoThrow(obj, new CustomizedJsonMapper());
  }

  /**
   * 将指定的对象格式化为JSON字符串。
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @param mapper
   *     用于进行JSON序列化的mapper。
   * @return
   *     该对象的JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNoThrow(final T obj, final JsonMapper mapper) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the object {} to JSON: {}", obj.getClass().getName(), obj, e);
      return null;
    }
  }

  /**
   * 将指定的对象格式化为JSON字符串。
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @return
   *     该对象的pretty printed JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String prettyFormatNoThrow(final T obj) {
    final CustomizedJsonMapper mapper = new CustomizedJsonMapper();
    mapper.setPrettyPrint(true);
    return formatNoThrow(obj, mapper);
  }

  /**
   * 将指定的对象格式化为JSON字符串。
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @param pretty
   *     是否进行 pretty print。
   * @return
   *     该对象的JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNoThrow(final T obj, final boolean pretty) {
    return (pretty ? prettyFormatNoThrow(obj) : formatNoThrow(obj));
  }

  // stop checkstyle: LineLength
  /**
   * 将指定的对象格式化为正则化的JSON字符串。
   * <p>
   * 所谓正则化JSON字符串形式，是指序列化后的JSON对象的属性都按照属性名的字典序从小到大
   * 排序；若属性值是{@link Map}类型，则按照其主键的字典序从小到大排序。
   * 例如对于下述{@code Person}对象：
   * <p>
   * <pre><code>
   * class Person {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private Organization company;
   *   private Map&lt;String, String&gt; payload;
   *   private Instant createTime;
   * }
   *
   * class Organization {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private String address;
   * }
   * </code></pre>
   * <p>
   * 正常序列化后得到的JSON字符串应该是：
   * <p>
   * <pre><code>
   * {
   *   "id": 12345,
   *   "name": "张三",
   *   "code": "abc",
   *   "company": {
   *     "id": 547362,
   *     "name": "XX科技有限公司",
   *     "code": "xx-tech",
   *     "address": "江苏省南京市秦淮区XX路32号",
   *   },
   *   "payload": {
   *     "job-title": "engineer",
   *     "age": "32"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z"
   * }
   * </code></pre>
   * 但是对其所有属性或子属性按照属性路径名字典序排序后应该是：
   * <p><pre><code>
   * {
   *   "id": 12345,
   *   "code": "abc",
   *   "company": {
   *     "address": "江苏省南京市秦淮区XX路32号",
   *     "code": "xx-tech",
   *     "id": 547362,
   *     "name": "XX科技有限公司"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z",
   *   "name": "张三",
   *   "payload": {
   *     "age": "32",
   *     "job-title": "engineer"
   *   }
   * }
   * </code></pre>
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化JSON字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三","payload":{"age":"32","job-title":"engineer"}}</code></pre>
   * </p>
   *
   * FIXME: 对于集合属性值，如何确保排序？
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @return
   *     该对象的正则化JSON字符形式。
   * @throws JsonProcessingException
   *     若发生JSON序列化错误。
   */
  @NotNull
  public static <T> String formatNormalized(final T obj)
      throws JsonProcessingException {
    final JsonMapper mapper = CustomizedJsonMapper.createNormalized();
    return mapper.writeValueAsString(obj);
  }

  /**
   * 将指定的对象格式化为正则化的JSON字符串。
   * <p>
   * 所谓正则化JSON字符串形式，是指序列化后的JSON对象的属性都按照属性名的字典序从小到大
   * 排序；若属性值是{@link Map}类型，则按照其主键的字典序从小到大排序。
   * 例如对于下述{@code Person}对象：
   * <p>
   * <pre><code>
   * class Person {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private Organization company;
   *   private Map&lt;String, String&gt; payload;
   *   private Instant createTime;
   * }
   *
   * class Organization {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private String address;
   * }
   * </code></pre>
   * <p>
   * 正常序列化后得到的JSON字符串应该是：
   * <p>
   * <pre><code>
   * {
   *   "id": 12345,
   *   "name": "张三",
   *   "code": "abc",
   *   "company": {
   *     "id": 547362,
   *     "name": "XX科技有限公司",
   *     "code": "xx-tech",
   *     "address": "江苏省南京市秦淮区XX路32号",
   *   },
   *   "payload": {
   *     "job-title": "engineer",
   *     "age": "32"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z"
   * }
   * </code></pre>
   * 但是对其所有属性或子属性按照属性路径名字典序排序后应该是：
   * <p><pre><code>
   * {
   *   "id": 12345,
   *   "code": "abc",
   *   "company": {
   *     "address": "江苏省南京市秦淮区XX路32号",
   *     "code": "xx-tech",
   *     "id": 547362,
   *     "name": "XX科技有限公司"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z",
   *   "name": "张三",
   *   "payload": {
   *     "age": "32",
   *     "job-title": "engineer"
   *   }
   * }
   * </code></pre>
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化JSON字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三","payload":{"age":"32","job-title":"engineer"}}</code></pre>
   * </p>
   *
   * FIXME: 对于集合属性值，如何确保排序？
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @param mapper
   *     用于进行JSON序列化的mapper。
   * @return
   *     该对象的正则化JSON字符形式。
   * @throws JsonProcessingException
   *     若发生JSON序列化错误。
   */
  @NotNull
  public static <T> String formatNormalized(final T obj, final JsonMapper mapper)
      throws JsonProcessingException {
    final SerializationConfig newConfig = mapper.getSerializationConfig()
        .with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)   // 属性按照字典序排序
        .with(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS) // map entry 按照 key 的字典序排序
        .without(SerializationFeature.INDENT_OUTPUT);         // 阻止 pretty print
    // 注意：一但 JsonMapper 对象进行过序列化或反序列化，其 MapperFeature 就不能修改
    // （修改后也无效）。因此我们需要 clone 一个新的 JsonMapper
    final JsonMapper newMapper = mapper.copy();
    newMapper.setConfig(newConfig);
    final String result = newMapper.writeValueAsString(obj);
    return result;
  }

  /**
   * 将指定的对象格式化为正则化的JSON字符串。
   * <p>
   * 所谓正则化JSON字符串形式，是序列化后的JSON对象的属性都按照属性名的字典序从小到大排序。
   * 例如对于下述{@code Person}对象：
   * <p>
   * <pre><code>
   * class Person {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private Organization company;
   *   private Instant createTime;
   * }
   *
   * class Organization {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private String address;
   * }
   * </code></pre>
   * <p>
   * 正常序列化后得到的JSON字符串应该是：
   * <p>
   * <pre><code>
   * {
   *   "id": 12345,
   *   "name": "张三",
   *   "code": "abc",
   *   "company": {
   *     "id": 547362,
   *     "name": "XX科技有限公司",
   *     "code": "xx-tech",
   *     "address": "江苏省南京市秦淮区XX路32号",
   *   },
   *   "create_time": "2022-09-10T16:23:42Z"
   * }
   * </code></pre>
   * 但是对其所有属性或子属性按照属性路径名字典序排序后应该是：
   * <p><pre><code>
   * {
   *   "id": 12345,
   *   "code": "abc",
   *   "company": {
   *     "address": "江苏省南京市秦淮区XX路32号",
   *     "code": "xx-tech",
   *     "id": 547362,
   *     "name": "XX科技有限公司"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z",
   *   "name": "张三"
   * }
   * </code></pre>
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化JSON字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三"}</code></pre>
   * </p>
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @return
   *     该对象的正则化JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNormalizedNoThrow(final T obj) {
    final JsonMapper mapper = CustomizedJsonMapper.createNormalized();
    try {
      return formatNormalized(obj, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the object {} to normalized JSON: {}",
          obj.getClass().getName(), obj, e);
      return null;
    }
  }

  /**
   * 将指定的对象格式化为正则化的JSON字符串。
   * <p>
   * 所谓正则化JSON字符串形式，是序列化后的JSON对象的属性都按照属性名的字典序从小到大排序。
   * 例如对于下述{@code Person}对象：
   * <p>
   * <pre><code>
   * class Person {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private Organization company;
   *   private Instant createTime;
   * }
   *
   * class Organization {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private String address;
   * }
   * </code></pre>
   * <p>
   * 正常序列化后得到的JSON字符串应该是：
   * <p>
   * <pre><code>
   * {
   *   "id": 12345,
   *   "name": "张三",
   *   "code": "abc",
   *   "company": {
   *     "id": 547362,
   *     "name": "XX科技有限公司",
   *     "code": "xx-tech",
   *     "address": "江苏省南京市秦淮区XX路32号",
   *   },
   *   "create_time": "2022-09-10T16:23:42Z"
   * }
   * </code></pre>
   * 但是对其所有属性或子属性按照属性路径名字典序排序后应该是：
   * <p><pre><code>
   * {
   *   "id": 12345,
   *   "code": "abc",
   *   "company": {
   *     "address": "江苏省南京市秦淮区XX路32号",
   *     "code": "xx-tech",
   *     "id": 547362,
   *     "name": "XX科技有限公司"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z",
   *   "name": "张三"
   * }
   * </code></pre>
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化JSON字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三"}</code></pre>
   * </p>
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @param mapper
   *     用于进行JSON序列化的mapper。
   * @return
   *     该对象的正则化JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNormalizedNoThrow(final T obj,
      final JsonMapper mapper) {
    try {
      if (obj == null) {
        return null;  // 对于 null 输入，返回 null 而不是空 Map
      }
      return formatNormalized(obj, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the object {} to normalized JSON: {}",
          obj.getClass().getName(), obj, e);
      return null;
    }
  }

  public static <T> String formatList(final List<T> list)
      throws JsonProcessingException {
    return formatList(list, new CustomizedJsonMapper());
  }

  public static <T> String formatList(final List<T> list, final JsonMapper mapper)
      throws JsonProcessingException {
    return mapper.writeValueAsString(list);
  }

  public static <T> Writer formatList(final List<T> list, final JsonMapper mapper,
      final Writer output) throws IOException {
    outputJsonArrayOpenTag(output);
    output.write('\n');
    long count = 0;
    for (final T obj : list) {
      if (count > 0) {
        output.write(",\n");
      }
      final String json = mapper.writeValueAsString(obj);
      final String indentedJson = addPrefixToEachLine(json, " ");
      output.write(indentedJson);
      ++count;
    }
    if (count > 0) {
      output.write('\n');
    }
    outputJsonArrayCloseTag(output);
    return output;
  }


  @Nullable
  public static <T> String formatListNoThrow(final List<T> list) {
    try {
      return formatList(list, new CustomizedJsonMapper());
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the list to JSON: {}", list, e);
      return null;
    }
  }

  @Nullable
  public static <T> String formatListNoThrow(final List<T> list, final JsonMapper mapper) {
    try {
      return formatList(list, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the list to JSON: {}", list, e);
      return null;
    }
  }

  /**
   * 输出 JSON 数组的开始标记。
   *
   * @param writer
   *     输出流。
   * @return
   *     输出流参数本身。
   * @throws IOException
   *     若发生 I/O 错误。
   */
  public static Writer outputJsonArrayOpenTag(final Writer writer) throws IOException {
    writer.write('[');
    return writer;
  }

  /**
   * 输出 JSON 数组的结束标记。
   *
   * @param writer
   *     输出流。
   * @return
   *     输出流参数本身。
   * @throws IOException
   *     若发生 I/O 错误。
   */
  public static Writer outputJsonArrayCloseTag(final Writer writer) throws IOException {
    writer.write(']');
    return writer;
  }

  /**
   * 将对象转换为属性路径-值映射。
   *
   * @param <T>
   *     对象的类型。
   * @param obj
   *     要转换的对象。
   * @param mapper
   *     用于序列化的 JsonMapper。
   * @return
   *     属性路径-值映射，其中键是属性路径（用点号分隔的嵌套属性），值是属性值的字符串表示。
   *     如果输入对象为 null，则返回空 Map。
   * @throws JsonProcessingException
   *     如果序列化过程中发生错误。
   */
  public static <T> Map<String, String> toPathValueMap(@Nullable final T obj,
      @NotNull final JsonMapper mapper) throws JsonProcessingException {
    if (obj == null) {
      return Collections.emptyMap();
    }
    final Map<String, String> result = new HashMap<>();
    final JsonNode node = mapper.valueToTree(obj);
    addPathValues("", node, result);
    return result;
  }

  /**
   * 将对象转换为属性路径-值映射。
   *
   * @param <T>
   *     对象的类型。
   * @param obj
   *     要转换的对象。
   * @return
   *     属性路径-值映射，其中键是属性路径（用点号分隔的嵌套属性），值是属性值的字符串表示。
   *     如果输入对象为 null，则返回空 Map。
   * @throws JsonProcessingException
   *     如果序列化过程中发生错误。
   */
  public static <T> Map<String, String> toPathValueMap(@Nullable final T obj)
      throws JsonProcessingException {
    return toPathValueMap(obj, new CustomizedJsonMapper());
  }

  /**
   * 将对象转换为属性路径-值映射，不抛出异常。
   *
   * @param <T>
   *     对象的类型。
   * @param obj
   *     要转换的对象。
   * @return
   *     属性路径-值映射，其中键是属性路径（用点号分隔的嵌套属性），值是属性值的字符串表示。
   *     如果输入对象为 null 则返回空Map；如果发生错误，则返回 null。
   */
  @Nullable
  public static <T> Map<String, String> toPathValueMapNoThrow(@Nullable final T obj) {
    return toPathValueMapNoThrow(obj, new CustomizedJsonMapper());
  }

  /**
   * 将对象转换为属性路径-值映射，不抛出异常。
   *
   * @param <T>
   *     对象的类型。
   * @param obj
   *     要转换的对象。
   * @param mapper
   *     用于序列化的 JsonMapper。
   * @return
   *     属性路径-值映射，其中键是属性路径（用点号分隔的嵌套属性），值是属性值的字符串表示。
   *     如果输入对象为 null 则返回空Map；如果发生错误，则返回 null。
   */
  @Nullable
  public static <T> Map<String, String> toPathValueMapNoThrow(@Nullable final T obj,
      @NotNull final JsonMapper mapper) {
    if (obj == null) {
      return Collections.emptyMap();
    }
    try {
      return toPathValueMap(obj, mapper);
    } catch (final Exception e) {
      LOGGER.error("Failed to convert object to path-value map", e);
      return null;
    }
  }

  private static void addPathValues(final String path,
                                  final JsonNode node,
                                  final Map<String, String> result) {
    if (node == null || node.isNull()) {
      result.put(path, "null");
      return;
    }
    if (node.isValueNode()) {
      final String value = node.isNull() ? "null" : node.asText();
      result.put(path, (value == null ? "null" : value));
    } else if (node.isObject()) {
      final ObjectNode obj = (ObjectNode) node;
      final Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
      while (fields.hasNext()) {
        final Map.Entry<String, JsonNode> entry = fields.next();
        final String fieldName = entry.getKey();
        final String newPath = path.isEmpty() ? fieldName : path + "." + fieldName;
        addPathValues(newPath, entry.getValue(), result);
      }
    } else if (node.isArray()) {
      final ArrayNode array = (ArrayNode) node;
      for (int i = 0; i < array.size(); i++) {
        final String newPath = path + "[" + i + "]";
        addPathValues(newPath, array.get(i), result);
      }
    }
  }

  // resume checkstyle: LineLength
}