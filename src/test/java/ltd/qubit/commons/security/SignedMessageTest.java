////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.util.codec.DoubleCodec;
import ltd.qubit.commons.util.codec.IsoInstantCodec;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;

import static ltd.qubit.commons.test.JsonUnitUtils.assertJsonNodeEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link SignedMessage} class.
 *
 * @author Haixing Hu
 */
public class SignedMessageTest {

  private static final int TEST_COUNT = 20;

  private final RandomEx random = new RandomEx();

  private final JsonMapper mapper = new CustomizedJsonMapper();

  private String createString() {
    return random.nextString();
  }

  private String createNullableString() {
    if (random.nextInt() % 5 == 0) {
      return null;
    }
    return random.nextString();
  }

  private App createApp() {
    final App app = new App()
        .setId(random.nextLong())
        .setName(random.nextString())
        .setCode(random.nextString())
        .setOrganizationId(random.nextLong())
        .setOrganizationName(random.nextString())
        .setOrganizationCode(random.nextString())
        .setCategoryId(random.nextLong())
        .setCategoryName(random.nextString())
        .setIcon(random.nextString())
        .setUrl(random.nextString())
        .setDescription(random.nextString())
        .setSecurityKey(random.nextString())
        .setToken(random.nextString())
        .setTokenCreateTime(random.nextInstant())
        .setTokenExpiredTime(random.nextInstant())
        .setRsaPublicKey(random.nextString())
        .setRsaPrivateKey(random.nextString())
        .setPredefined(random.nextBoolean())
        .setCreateTime(random.nextInstant())
        .setModifyTime(random.nextInstant())
        .setDeleteTime(random.nextInstant());
    return app;
  }

  @Test
  public void testJsonSerializeDeserialize() throws Exception {
    final IsoInstantCodec instantCodec = new IsoInstantCodec();
    final DoubleCodec doubleCodec = new DoubleCodec();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final App app = createApp();
      final SignedMessage<App> obj = new SignedMessage<>(app);
      obj.setSignature(createString()).setReturnUrl(createNullableString());
      System.out.println(obj);
      final String json = mapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(obj);
      System.out.println(json);
      assertJsonNodeEquals(json, "data.id", obj.getData().getId());
      assertJsonNodeEquals(json, "data.name", obj.getData().getName());
      assertJsonNodeEquals(json, "data.code", obj.getData().getCode());
      assertJsonNodeEquals(json, "data.organization_id",
          obj.getData().getOrganizationId());
      assertJsonNodeEquals(json, "data.organization_name",
          obj.getData().getOrganizationName());
      assertJsonNodeEquals(json, "data.organization_code",
          obj.getData().getOrganizationCode());
      assertJsonNodeEquals(json, "data.category_id",
          obj.getData().getCategoryId());
      assertJsonNodeEquals(json, "data.category_name",
          obj.getData().getCategoryName());
      assertJsonNodeEquals(json, "data.icon", obj.getData().getIcon());
      assertJsonNodeEquals(json, "data.url", obj.getData().getUrl());
      assertJsonNodeEquals(json, "data.description",
          obj.getData().getDescription());
      assertJsonNodeEquals(json, "data.security_key",
          obj.getData().getSecurityKey());
      assertJsonNodeEquals(json, "data.token", obj.getData().getToken());
      assertJsonNodeEquals(json, "data.token_create_time",
          instantCodec.encode(obj.getData().getTokenCreateTime()));
      assertJsonNodeEquals(json, "data.token_expired_time",
          instantCodec.encode(obj.getData().getTokenExpiredTime()));
      assertJsonNodeEquals(json, "data.rsa_public_key",
          obj.getData().getRsaPublicKey());
      assertJsonNodeEquals(json, "data.predefined",
          obj.getData().isPredefined());
      assertJsonNodeEquals(json, "data.create_time",
          instantCodec.encode(obj.getData().getCreateTime()));
      assertJsonNodeEquals(json, "data.modify_time",
          instantCodec.encode(obj.getData().getModifyTime()));
      assertJsonNodeEquals(json, "data.delete_time",
          instantCodec.encode(obj.getData().getDeleteTime()));
      assertJsonNodeEquals(json, "return_url", obj.getReturnUrl());
      assertJsonNodeEquals(json, "signature", obj.getSignature());
      final SignedMessage<App> actual = mapper
          .readValue(json, new TypeReference<SignedMessage<App>>() {
          });
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  static class AppSignedMessage extends SignedMessage<App> {

    private static final long serialVersionUID = 2690347507810097765L;

    public AppSignedMessage() {
    }

    public AppSignedMessage(final App app) {
      super(app);
    }
  }

  @Test
  public void testJsonSerializeDeserialize_ForSubClass() throws Exception {
    final IsoInstantCodec instantCodec = new IsoInstantCodec();
    final DoubleCodec doubleCodec = new DoubleCodec();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final App app = createApp();
      final AppSignedMessage obj = new AppSignedMessage(app);
      obj.setSignature(createString()).setReturnUrl(createNullableString());
      System.out.println(obj);
      final String json = mapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(obj);
      System.out.println(json);
      assertJsonNodeEquals(json, "data.id", obj.getData().getId());
      assertJsonNodeEquals(json, "data.name", obj.getData().getName());
      assertJsonNodeEquals(json, "data.code", obj.getData().getCode());
      assertJsonNodeEquals(json, "data.organization_id",
          obj.getData().getOrganizationId());
      assertJsonNodeEquals(json, "data.organization_name",
          obj.getData().getOrganizationName());
      assertJsonNodeEquals(json, "data.organization_code",
          obj.getData().getOrganizationCode());
      assertJsonNodeEquals(json, "data.category_id",
          obj.getData().getCategoryId());
      assertJsonNodeEquals(json, "data.category_name",
          obj.getData().getCategoryName());
      assertJsonNodeEquals(json, "data.icon", obj.getData().getIcon());
      assertJsonNodeEquals(json, "data.url", obj.getData().getUrl());
      assertJsonNodeEquals(json, "data.description",
          obj.getData().getDescription());
      assertJsonNodeEquals(json, "data.security_key",
          obj.getData().getSecurityKey());
      assertJsonNodeEquals(json, "data.token", obj.getData().getToken());
      assertJsonNodeEquals(json, "data.token_create_time",
          instantCodec.encode(obj.getData().getTokenCreateTime()));
      assertJsonNodeEquals(json, "data.token_expired_time",
          instantCodec.encode(obj.getData().getTokenExpiredTime()));
      assertJsonNodeEquals(json, "data.rsa_public_key",
          obj.getData().getRsaPublicKey());
      assertJsonNodeEquals(json, "data.predefined",
          obj.getData().isPredefined());
      assertJsonNodeEquals(json, "data.create_time",
          instantCodec.encode(obj.getData().getCreateTime()));
      assertJsonNodeEquals(json, "data.modify_time",
          instantCodec.encode(obj.getData().getModifyTime()));
      assertJsonNodeEquals(json, "data.delete_time",
          instantCodec.encode(obj.getData().getDeleteTime()));
      assertJsonNodeEquals(json, "return_url", obj.getReturnUrl());
      assertJsonNodeEquals(json, "signature", obj.getSignature());
      final SignedMessage<App> actual = mapper
          .readValue(json, AppSignedMessage.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  //  @Test
  //  public void testXmlSerializeDeserialize() throws Exception {
  //    IsoInstantXmlAdapter adapter = new IsoInstantXmlAdapter();
  //    for (int i = 0; i < TEST_COUNT; ++i) {
  //      App obj = createApp();
  //      String xml = XmlMapperUtils.format(obj);
  //      System.out.println(xml);
  //      assertXPathEquals(xml, "app/id", obj.getId());
  //      assertXPathEquals(xml, "app/name", obj.getName());
  //      assertXPathEquals(xml, "app/code", obj.getCode());
  //      assertXPathEquals(xml, "app/organization-id", obj.getOrganizationId());
  //      assertXPathEquals(xml, "app/organization-name", obj.getOrganizationName());
  //      assertXPathEquals(xml, "app/organization-code", obj.getOrganizationCode());
  //      assertXPathEquals(xml, "app/category-id", obj.getCategoryId());
  //      assertXPathEquals(xml, "app/category-name", obj.getCategoryName());
  //      assertXPathEquals(xml, "app/icon", obj.getIcon());
  //      assertXPathEquals(xml, "app/url", obj.getUrl());
  //      assertXPathEquals(xml, "app/description", obj.getDescription());
  //      assertXPathEquals(xml, "app/security-key", obj.getSecurityKey());
  //      assertXPathEquals(xml, "app/token", obj.getToken());
  //      assertXPathEquals(xml, "app/token-create-time",
  //          adapter.marshal(obj.getTokenCreateTime()));
  //      assertXPathEquals(xml, "app/token-expired-time",
  //          adapter.marshal(obj.getTokenExpiredTime()));
  //      assertXPathEquals(xml, "app/rsa-public-key", obj.getRsaPublicKey());
  //      assertXPathEquals(xml, "app/predefined", obj.isPredefined());
  //      assertXPathEquals(xml, "app/create-time", adapter.marshal(obj.getCreateTime()));
  //      assertXPathEquals(xml, "app/modify-time", adapter.marshal(obj.getModifyTime()));
  //      assertXPathEquals(xml, "app/delete-time", adapter.marshal(obj.getDeleteTime()));
  //      App actual = JaxbUtils.unmarshal(new StringReader(xml), App.class);
  //      System.out.println(obj.toString());
  //      Assert.assertEquals(obj, actual);
  //    }
  //  }
}
