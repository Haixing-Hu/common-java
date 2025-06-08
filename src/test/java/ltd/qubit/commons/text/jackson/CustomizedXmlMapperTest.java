////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.text.testbed.DictEntryInfo;
import ltd.qubit.commons.text.testbed.Document;
import ltd.qubit.commons.text.testbed.Gender;
import ltd.qubit.commons.text.testbed.MixJsonXmlAnnotation;
import ltd.qubit.commons.text.testbed.OrganizationNoAnnotation;
import ltd.qubit.commons.text.testbed.PersonNoAnnotation;
import ltd.qubit.commons.text.testbed.ValueEnum;
import ltd.qubit.commons.text.testbed.WithEnum;
import ltd.qubit.commons.util.pair.KeyValuePair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static ltd.qubit.commons.test.XmlUnitUtils.assertXPathEquals;
import static ltd.qubit.commons.test.XmlUnitUtils.assertXmlEqual;

public class CustomizedXmlMapperTest {

  @Test
  public void testXmlSerializationWithoutAnnotation()
      throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final OrganizationNoAnnotation o1 = new OrganizationNoAnnotation();
    o1.setId(547362L);
    o1.setCode("xx-tech");
    o1.setName("XX科技有限公司");
    o1.setAddress("江苏省南京市秦淮区XX路32号");
    o1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    o1.setModifyTime(Instant.parse("2022-09-28T10:40:32Z"));
    o1.setDeleteTime(null);

    final String xml1 = mapper.writeValueAsString(o1);
    System.out.println(xml1);
    final OrganizationNoAnnotation o2 = mapper.readValue(xml1, OrganizationNoAnnotation.class);
    assertEquals(o1, o2);

    final PersonNoAnnotation p1 = new PersonNoAnnotation();
    p1.setId(12345L);
    p1.setCode("abc");
    p1.setName("张三");
    p1.setGender(Gender.MALE);
    p1.setCompany(o1);
    p1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    p1.setModifyTime(Instant.parse("2022-09-28T10:40:32Z"));
    final Map<String, String> payload = new HashMap<>();
    payload.put("job-title", "engineer");
    payload.put("age", "32");
    p1.setPayload(payload);

    p1.setJobTags(Arrays.asList("t1", "t2", "t3"));

    final String xml2 = mapper.writeValueAsString(p1);
    System.out.println(xml2);
    final PersonNoAnnotation p2 = mapper.readValue(xml2, PersonNoAnnotation.class);
    assertEquals(p1, p2);
  }

  @Test
  public void testMixJsonXmlAnnotation() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final MixJsonXmlAnnotation m1 = new MixJsonXmlAnnotation();
    m1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    m1.setDeleteTime(Instant.parse("2022-09-28T09:38:45.752Z"));

    final String xml = mapper.writeValueAsString(m1);
    System.out.println(xml);
    final MixJsonXmlAnnotation m2 = mapper.readValue(xml, MixJsonXmlAnnotation.class);
    assertEquals(m1, m2);
  }

  @Test
  public void testStringTrim() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final OrganizationNoAnnotation o1 = new OrganizationNoAnnotation();
    o1.setId(547362L);
    o1.setCode("xx-tech");
    o1.setName("XX科技有限公司");
    o1.setAddress("江苏省南京市秦淮区XX路32号");
    o1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    o1.setModifyTime(Instant.parse("2022-09-28T10:40:32Z"));
    o1.setDeleteTime(null);

    final PersonNoAnnotation p1 = new PersonNoAnnotation();
    p1.setId(12345L);
    p1.setCode("abc");
    p1.setName("张三");
    p1.setGender(Gender.MALE);
    p1.setCompany(o1);
    p1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    p1.setModifyTime(Instant.parse("2022-09-28T10:40:32Z"));
    final Map<String, String> payload = new HashMap<>();
    payload.put("job-title", "engineer");
    payload.put("age", "32");
    p1.setPayload(payload);
    p1.setJobTags(Arrays.asList("t1", "t2", "t3"));

    final String xml =
        "<person-no-annotation>\n"
      + "    <id>\n"
      + "        12345 \n"
      + "    </id>\n"
      + "    <code>abc \n"
      + "    </code> \n"
      + "    <name> 张三\n"
      + "    </name> \n"
      + "    <company>\n"
      + "        <id>547362</id> \n"
      + "        <code>xx-tech </code> \n"
      + "        <name>  XX科技有限公司 \n  </name> \n"
      + "        <address>江苏省南京市秦淮区XX路32号 </address> \n"
      + "        <create-time> 2022-09-28T09:38:45.752Z </create-time> \n"
      + "        <modify-time> 2022-09-28T10:40:32Z \n </modify-time> \n"
      + "    </company>\n"
      + "    <gender>\n"
      + "        MALE \n"
      + "    </gender>\n"
      + "    <create-time>\n"
      + "        2022-09-28T09:38:45.752Z \n"
      + "    </create-time>\n"
      + "    <modify-time>\n"
      + "        2022-09-28T10:40:32Z \n"
      + "    </modify-time>\n"
      + "    <payload>\n"
      + "        <entry><key>  job-title </key><value> engineer </value></entry> \n"
      + "        <entry><key>age</key><value>  32 \n</value></entry> \n"
      + "    </payload>\n"
      + "    <job-tags>\n"
      + "        <job-tag> t1 </job-tag> \n"
      + "        <job-tag> t2 </job-tag> \n"
      + "        <job-tag>    t3\t\n </job-tag> \n"
      + "    </job-tags>\n"
      + "</person-no-annotation>\n";

    System.out.println(xml);
    final PersonNoAnnotation p2 = mapper.readValue(xml, PersonNoAnnotation.class);
    assertEquals(p1, p2);
  }

  @Test
  public void testXmlOuputEscapeCharacter() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final KeyValuePair k = new KeyValuePair();
    k.setKey("$<_/;2");
    k.setValue("_!GcpaYO(k]YTO*5\nTLhui?:E~)v'AN<I");
    final String xml = mapper.writeValueAsString(k);
    System.out.println(xml);
    final KeyValuePair k2 = mapper.readValue(xml, KeyValuePair.class);
    assertEquals(k, k2);
  }

  @Disabled
  @Test
  public void testXmlSerializationWithLeadingTrainingSpace()
      throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final KeyValuePair obj = new KeyValuePair();
    obj.setKey("key");
    obj.setValue(" value ");
    String xml = mapper.writeValueAsString(obj);
    System.out.println(xml);
    assertXPathEquals(xml, "key-value-pair/key", obj.getKey());
    assertXPathEquals(xml, "key-value-pair/value", obj.getValue());
    KeyValuePair other = mapper.readValue(xml, KeyValuePair.class);
    assertEquals(obj, other);

    obj.setKey("key");
    obj.setValue("value");
    xml = mapper.writeValueAsString(obj);
    System.out.println(xml);
    assertXPathEquals(xml, "key-value-pair/key", obj.getKey());
    assertXPathEquals(xml, "key-value-pair/value", obj.getValue());
    other = mapper.readValue(xml, KeyValuePair.class);
    assertEquals(obj, other);
  }

  @Test
  public void testEnumSerialize() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final WithEnum foo = new WithEnum();
    foo.setValue(ValueEnum.VALUE_1);
    final String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertXmlEqual("<with-enum><value>VALUE_1</value></with-enum>", json);
  }

  @Test
  public void testEnumDeserialize() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final WithEnum foo = new WithEnum();
    foo.setValue(ValueEnum.VALUE_1);
    String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertXmlEqual("<with-enum><value>VALUE_1</value></with-enum>", json);
    json = "<with-enum><value>VALUE_1</value></with-enum>";
    final WithEnum f1 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f1);
    json = "<with-enum><value>value_1</value></with-enum>";
    final WithEnum f2 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f2);
    json = "<with-enum><value>value-1</value></with-enum>";
    final WithEnum f3 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f3);
    json = "<with-enum><value>vaLue-1</value></with-enum>";
    final WithEnum f4 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f4);

    json = "<with-enum><value></value></with-enum>";
    final WithEnum f5 = mapper.readValue(json, WithEnum.class);
    foo.setValue(null);
    assertEquals(foo, f5);

    json = "<with-enum><value>  </value></with-enum>";
    final WithEnum f6 = mapper.readValue(json, WithEnum.class);
    foo.setValue(null);
    assertEquals(foo, f6);

    json = "<with-enum></with-enum>";
    final WithEnum f7 = mapper.readValue(json, WithEnum.class);
    foo.setValue(null);
    assertEquals(foo, f7);

    json = "<with-enum><value>the-value-3</value></with-enum>";
    final WithEnum f8 = mapper.readValue(json, WithEnum.class);
    foo.setValue(ValueEnum.the_Value_3);
    assertEquals(foo, f8);

    final String errorJson = "<with-enum><value>value-3</value></with-enum>";
    assertThrows(InvalidFormatException.class,
        () -> mapper.readValue(errorJson, WithEnum.class));
  }

  @Test
  public void testSerializeXmlElementAnnotatedAtGetter()
      throws JsonProcessingException {
    final XmlMapper xmlMapper = new CustomizedXmlMapper();
    final DictEntryInfo obj = new DictEntryInfo();

    obj.setId(123L);
    obj.setCode("QD");
    obj.setName("每天使用一次");
    obj.setParams(null);
    String xml = xmlMapper.writeValueAsString(obj);
    System.out.println(xml);
    assertXmlEqual("<dict-entry-info>\n"
            + "  <id>123</id>\n"
            + "  <code>QD</code>\n"
            + "  <name>每天使用一次</name>\n"
            + "  <display-name>每天使用一次</display-name>\n"
            + "  <display-code>QD</display-code>\n"
            + "</dict-entry-info>",
        xml);

    obj.setId(4578237832L);
    obj.setCode("Q{0}H");
    obj.setName("每{0}小时使用一次");
    obj.setParams(new String[]{"3"});
    xml = xmlMapper.writeValueAsString(obj);
    System.out.println(xml);
    assertXmlEqual("<dict-entry-info>\n"
            + "  <id>4578237832</id>\n"
            + "  <code>Q{0}H</code>\n"
            + "  <name>每{0}小时使用一次</name>\n"
            + "  <params>\n"
            + "    <param>3</param>\n"
            + "  </params>\n"
            + "  <display-name>每3小时使用一次</display-name>\n"
            + "  <display-code>Q3H</display-code>\n"
            + "</dict-entry-info>",
        xml);

    obj.setId(2342343243L);
    obj.setCode("MCD{0}D{1}");
    obj.setName("月经第{0}天至第{1}天使用");
    obj.setParams(new String[]{"3", "5"});
    xml = xmlMapper.writeValueAsString(obj);
    System.out.println(xml);
    assertXmlEqual("<dict-entry-info>\n"
            + "  <id>2342343243</id>\n"
            + "  <code>MCD{0}D{1}</code>\n"
            + "  <name>月经第{0}天至第{1}天使用</name>\n"
            + "  <params>\n"
            + "    <param>3</param>\n"
            + "    <param>5</param>\n"
            + "  </params>\n"
            + "  <display-name>月经第3天至第5天使用</display-name>\n"
            + "  <display-code>MCD3D5</display-code>\n"
            + "</dict-entry-info>",
        xml);

    obj.setId(123123423332L);
    obj.setCode("OTH");
    obj.setName("{0}");
    obj.setParams(new String[]{"特定的XX方法"});
    xml = xmlMapper.writeValueAsString(obj);
    System.out.println(xml);
    assertXmlEqual("<dict-entry-info>\n"
            + "  <id>123123423332</id>\n"
            + "  <code>OTH</code>\n"
            + "  <name>{0}</name>\n"
            + "  <params>\n"
            + "    <param>特定的XX方法</param>\n"
            + "  </params>\n"
            + "  <display-name>特定的XX方法</display-name>\n"
            + "  <display-code>OTH</display-code>\n"
            + "</dict-entry-info>",
        xml);

    obj.setId(21321434246879L);
    obj.setCode("QW({0},{1},{2})");
    obj.setName("每周{0},{1},{2}使用");
    obj.setParams(new String[]{"2", "4", "6"});
    xml = xmlMapper.writeValueAsString(obj);
    System.out.println(xml);
    assertXmlEqual("<dict-entry-info>\n"
            + "  <id>21321434246879</id>\n"
            + "  <code>QW({0},{1},{2})</code>\n"
            + "  <name>每周{0},{1},{2}使用</name>\n"
            + "  <params>\n"
            + "    <param>2</param>\n"
            + "    <param>4</param>\n"
            + "    <param>6</param>\n"
            + "  </params>\n"
            + "  <display-name>每周2,4,6使用</display-name>\n"
            + "  <display-code>QW(2,4,6)</display-code>\n"
            + "</dict-entry-info>",
        xml);
  }

  @Test
  public void testDeserializeXmlElementAnnotatedAtGetter()
      throws JsonProcessingException {
    final XmlMapper xmlMapper = new CustomizedXmlMapper();
    final String xml = "<dict-entry-info>\n"
        + "  <id>123</id>\n"
        + "  <code>QD</code>\n"
        + "  <name>每天使用一次</name>\n"
        + "  <display-name>每天使用一次</display-name>\n"
        + "  <display-code>QD</display-code>\n"
        + "</dict-entry-info>";

    final DictEntryInfo expected = new DictEntryInfo();
    expected.setId(123L);
    expected.setCode("QD");
    expected.setName("每天使用一次");
    expected.setParams(null);
    System.out.println("Deserialize from XML:\n" + xml);
    final DictEntryInfo actual = xmlMapper.readValue(xml, DictEntryInfo.class);
    System.out.println(actual);
    assertEquals(expected, actual);
  }

  @XmlRootElement(name = "my-root")
  static class ClassWithXmlRootElement {
    private final int value;

    public ClassWithXmlRootElement(final int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  @XmlRootElement
  static class ClassWithDefaultXmlRootElement {
    private final int value;

    public ClassWithDefaultXmlRootElement(final int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  @Test
  public void testSerializeClassWithXmlRootElement()
      throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    Object obj = new ClassWithXmlRootElement(123);
    String xml = mapper.writeValueAsString(obj);
    String expected = "<my-root><value>123</value></my-root>";
    System.out.println("Expected XML: " + expected);
    System.out.println("Actual XML: " + xml);
    assertXmlEqual(expected, xml);

    obj = new ClassWithDefaultXmlRootElement(123);
    xml = mapper.writeValueAsString(obj);
    expected = "<class-with-default-xml-root-element><value>123</value></class-with-default-xml-root-element>";
    System.out.println("Expected XML: " + expected);
    System.out.println("Actual XML: " + xml);
    assertXmlEqual(expected, xml);
  }

  @XmlRootElement(name = "my-root")
  static class ClassWithXmlElement {
    @XmlElement(name = "int-value")
    private final int value;

    public ClassWithXmlElement(final int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  @XmlRootElement
  static class ClassWithDefaultXmlElement {
    @XmlElement
    private final int value;

    public ClassWithDefaultXmlElement(final int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  @Test
  public void testSerializeClassWithXmlElement()
      throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    Object obj = new ClassWithXmlElement(123);
    String xml = mapper.writeValueAsString(obj);
    String expected = "<my-root><int-value>123</int-value></my-root>";
    System.out.println("Expected XML: " + expected);
    System.out.println("Actual XML: " + xml);
    assertXmlEqual(expected, xml);

    obj = new ClassWithDefaultXmlElement(123);
    xml = mapper.writeValueAsString(obj);
    expected = "<class-with-default-xml-element><value>123</value></class-with-default-xml-element>";
    System.out.println("Expected XML: " + expected);
    System.out.println("Actual XML: " + xml);
    assertXmlEqual(expected, xml);
  }

  @XmlRootElement(name = "my-list-wrapper")
  private static class ListWrapper<T> {

    @XmlNoElementWrapper
    @XmlElement(name = "my-list-item")
    private final List<T> list;

    public ListWrapper(final List<T> list) {
      this.list = list;
    }

    public final List<T> getList() {
      return list;
    }
  }

  @Test
  public void testSerializeListWrapper() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final List<ClassWithXmlElement> list = new ArrayList<>();
    list.add(new ClassWithXmlElement(1));
    list.add(new ClassWithXmlElement(2));
    list.add(new ClassWithXmlElement(3));
    final ListWrapper<ClassWithXmlElement> wrapper = new ListWrapper<>(list);
    final String xml = mapper.writeValueAsString(wrapper);
    final String expected = "<my-list-wrapper>"
        + "<my-list-item><int-value>1</int-value></my-list-item>"
        + "<my-list-item><int-value>2</int-value></my-list-item>"
        + "<my-list-item><int-value>3</int-value></my-list-item>"
        + "</my-list-wrapper>";
    System.out.println("Expected XML: " + expected);
    System.out.println("Actual XML: " + xml);
    assertXmlEqual(expected, xml);
  }

  @Test
  public void testXmlElementNameForContainerType() throws JsonProcessingException {
    final DictEntryInfo info = new DictEntryInfo();
    info.setId(123L);
    info.setCode("Q{0}H");
    info.setName("每{0}小时使用一次");
    info.setParams(new String[] {"3"});
    final XmlMapper mapper = new CustomizedXmlMapper();
    final String xml = mapper.writeValueAsString(info);
    System.out.println(xml);

    final String expected = "<dict-entry-info>\n"
        + "  <id>123</id>\n"
        + "  <code>Q{0}H</code>\n"
        + "  <name>每{0}小时使用一次</name>\n"
        + "  <params>\n"
        + "    <param>3</param>\n"
        + "  </params>\n"
        + "  <display-code>Q3H</display-code>\n"
        + "  <display-name>每3小时使用一次</display-name>\n"
        + "</dict-entry-info>\n";

    final Diff diff = DiffBuilder.compare(expected).withTest(xml)
                               .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndText))
                               .ignoreWhitespace()
                               .checkForSimilar()
                               .build();
    assertFalse(diff.hasDifferences(), () -> "XMLs are different: " + diff);
  }

  @Test
  public void testXmlElementNameForMapType() throws JsonProcessingException {
    final Document doc = new Document();
    doc.setId("1234567");
    doc.setTitle("title");
    doc.setContent("content");
    doc.setMetadata(new TreeMap<>());
    doc.getMetadata().put("author", "author");
    doc.getMetadata().put("date", "2023-01-01");
    doc.setScore(new BigDecimal("0.382"));
    final XmlMapper mapper = new CustomizedXmlMapper();
    final String xml = mapper.writeValueAsString(doc);
    System.out.println("The document is serialized to:\n" + xml);
    final String expected = "<document>\n"
        + "  <id>1234567</id>\n"
        + "  <title>title</title>\n"
        + "  <content>content</content>\n"
        + "  <metadata>\n"
        + "    <entry>\n"
        + "      <key>author</key>\n"
        + "      <value>author</value>\n"
        + "    </entry>\n"
        + "    <entry>\n"
        + "      <key>date</key>\n"
        + "      <value>2023-01-01</value>\n"
        + "    </entry>\n"
        + "  </metadata>\n"
        + "  <score>0.382000</score>\n"
        + "</document>\n";
    final Diff diff = DiffBuilder.compare(expected).withTest(xml)
                                 .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndText))
                                 .ignoreWhitespace()
                                 .checkForSimilar()
                                 .build();
    assertFalse(diff.hasDifferences(), () -> "XMLs are different: " + diff);
  }

  public static class Foo {
    private final String name;
    private final String value;

    public Foo(final String name, final String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public String getValue() {
      return value;
    }
  }

  @Test
  public void testMapperForClassWithoutDefaultConstructor() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final Foo foo = new Foo("foo", "bar");
    final String xml = "<foo><name>foo</name><value>bar</value></foo>";
    final Foo f1 = mapper.readValue(xml, Foo.class);
    assertEquals(foo.getName(), f1.getName());
    assertEquals(foo.getValue(), f1.getValue());
  }


  private static class ImmutableClass {
    private String str;

    public ImmutableClass(final String someStr) {
      this.str = someStr;
    }

    public String getStr() {
      return str;
    }
  }

  private static class ImmutableMap {
    private final Map<String, Object> params;

    public ImmutableMap(final Map<String, Object> params) {
      this.params = params;
    }

    public Map<String, Object> getParams() {
      return params;
    }
  }

  @Test
  public void shouldSuccessDeserialize() throws IOException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final String ser = mapper.writeValueAsString(new ImmutableClass("Hi")); // => { "str": "Hi" }
    final ImmutableClass deser = mapper.readValue(ser, ImmutableClass.class);
    assertEquals(deser.getStr(), "Hi");
  }

  @Disabled
  @Test
  public void shouldSuccessDeserializeImmutableMap() throws IOException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final String ser = mapper.writeValueAsString(new ImmutableMap(Map.of("key", "value"))); // => { "params": { "key": "value" } }
    final ImmutableMap deser = mapper.readValue(ser, ImmutableMap.class);
    assertEquals(deser.getParams(), Map.of("key", "value"));
  }

  @Test
  public void testDeserializeEmptyMapElement() throws JsonProcessingException {
    final String xml = "<document>\n"
        + "  <id>1234567</id>\n"
        + "  <title>title</title>\n"
        + "  <content>content</content>\n"
        + "  <metadata/>\n"
        + "  <score>0.382000</score>\n"
        + "</document>\n";
    final XmlMapper mapper = new CustomizedXmlMapper();
    final Document doc = mapper.readValue(xml, Document.class);
    assertEquals("1234567", doc.getId());
    assertEquals("title", doc.getTitle());
    assertEquals("content", doc.getContent());
    assertNotNull(doc.getMetadata());
    assertTrue(doc.getMetadata().isEmpty());
    assertEquals(new BigDecimal("0.382000"), doc.getScore());
  }

  @Test
  public void testDeserializeNullMapElement() throws JsonProcessingException {
    final String xml = "<document>\n"
        + "  <id>1234567</id>\n"
        + "  <title>title</title>\n"
        + "  <content>content</content>\n"
        + "  <score>0.382000</score>\n"
        + "</document>\n";
    final XmlMapper mapper = new CustomizedXmlMapper();
    final Document doc = mapper.readValue(xml, Document.class);
    assertEquals("1234567", doc.getId());
    assertEquals("title", doc.getTitle());
    assertEquals("content", doc.getContent());
    assertNull(doc.getMetadata());
    assertEquals(new BigDecimal("0.382000"), doc.getScore());
  }

  @Test
  public void testSerializeEmptyMapElement() throws JsonProcessingException {
    final Document doc = new Document();
    doc.setId("1234567");
    doc.setTitle("title");
    doc.setContent("content");
    doc.setMetadata(new TreeMap<>());
    doc.setScore(new BigDecimal("0.382000"));
    final XmlMapper mapper = new CustomizedXmlMapper();
    final String xml = mapper.writeValueAsString(doc);
    final String expected = "<document>\n"
        + "  <id>1234567</id>\n"
        + "  <title>title</title>\n"
        + "  <content>content</content>\n"
        + "  <metadata/>\n"
        + "  <score>0.382000</score>\n"
        + "</document>\n";
    final Diff diff = DiffBuilder.compare(expected).withTest(xml)
                                 .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndText))
                                 .ignoreWhitespace()
                                 .checkForSimilar()
                                 .build();
    assertFalse(diff.hasDifferences(), () -> "XMLs are different: " + diff);
  }

  @Test
  public void testSerializeNullMapElement() throws JsonProcessingException {
    final Document doc = new Document();
    doc.setId("1234567");
    doc.setTitle("title");
    doc.setContent("content");
    doc.setMetadata(null);
    doc.setScore(new BigDecimal("0.382000"));
    final XmlMapper mapper = new CustomizedXmlMapper();
    final String xml = mapper.writeValueAsString(doc);
    final String expected = "<document>\n"
        + "  <id>1234567</id>\n"
        + "  <title>title</title>\n"
        + "  <content>content</content>\n"
        + "  <score>0.382000</score>\n"
        + "</document>\n";
    final Diff diff = DiffBuilder.compare(expected).withTest(xml)
                                 .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndText))
                                 .ignoreWhitespace()
                                 .checkForSimilar()
                                 .build();
    assertFalse(diff.hasDifferences(), () -> "XMLs are different: " + diff);
  }
}