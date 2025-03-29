////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import ltd.qubit.commons.config.impl.DefaultConfig;
import ltd.qubit.commons.net.interceptor.HttpLoggingInterceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link HttpClientBuilder} class.
 *
 * @author Haixing Hu
 */
public class HttpClientBuilderTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientBuilderTest.class);
  private HttpClientBuilder builder;
  private DefaultConfig config;

  @BeforeEach
  public void setUp() {
    config = new DefaultConfig();
    builder = new HttpClientBuilder(LOGGER, config);
  }

  @Test
  public void testDefaultConfiguration() {
    // Test default values
    assertEquals(HttpClientBuilder.DEFAULT_CONNECTION_TIMEOUT, builder.getConnectionTimeout());
    assertEquals(HttpClientBuilder.DEFAULT_READ_TIMEOUT, builder.getReadTimeout());
    assertEquals(HttpClientBuilder.DEFAULT_WRITE_TIMEOUT, builder.getWriteTimeout());
    assertEquals(HttpClientBuilder.DEFAULT_USE_PROXY, builder.isUseProxy());
    assertEquals(HttpClientBuilder.DEFAULT_PROXY_TYPE, builder.getProxyType());
    assertEquals(HttpClientBuilder.DEFAULT_USE_HTTP_LOGGING, builder.isUseHttpLogging());
    assertNull(builder.getProxyHost());
    assertEquals(0, builder.getProxyPort());
    assertNull(builder.getProxyUsername());
    assertNull(builder.getProxyPassword());
  }

  @Test
  public void testCustomConfiguration() {
    // Test setting custom values
    builder.setConnectionTimeout(30)
           .setReadTimeout(60)
           .setWriteTimeout(90)
           .setUseProxy(true)
           .setProxyType(Proxy.Type.HTTP)
           .setProxyHost("proxy.example.com")
           .setProxyPort(8080)
           .setProxyUsername("user")
           .setProxyPassword("pass")
           .setUseHttpLogging(false);

    assertEquals(30, builder.getConnectionTimeout());
    assertEquals(60, builder.getReadTimeout());
    assertEquals(90, builder.getWriteTimeout());
    assertTrue(builder.isUseProxy());
    assertEquals("HTTP", builder.getProxyType());
    assertEquals("proxy.example.com", builder.getProxyHost());
    assertEquals(8080, builder.getProxyPort());
    assertEquals("user", builder.getProxyUsername());
    assertEquals("pass", builder.getProxyPassword());
    assertFalse(builder.isUseHttpLogging());
  }

  @Test
  public void testBuildClient() {
    // Test building a client with default configuration
    final OkHttpClient client = builder.build();

    assertNotNull(client);
    assertEquals(HttpClientBuilder.DEFAULT_CONNECTION_TIMEOUT, client.connectTimeoutMillis() / 1000);
    assertEquals(HttpClientBuilder.DEFAULT_READ_TIMEOUT, client.readTimeoutMillis() / 1000);
    assertEquals(HttpClientBuilder.DEFAULT_WRITE_TIMEOUT, client.writeTimeoutMillis() / 1000);
    assertNull(client.proxy());

    // Verify that logging interceptor is added when useLogging is true
    boolean hasLoggingInterceptor = false;
    for (final okhttp3.Interceptor interceptor : client.interceptors()) {
      if (interceptor instanceof HttpLoggingInterceptor) {
        hasLoggingInterceptor = true;
        break;
      }
    }
    assertTrue(hasLoggingInterceptor);
  }

  @Test
  public void testBuildClientWithCustomConfiguration() {
    // Test building a client with custom configuration
    builder.setConnectionTimeout(30)
           .setReadTimeout(60)
           .setWriteTimeout(90)
           .setUseHttpLogging(false);

    final OkHttpClient client = builder.build();

    assertNotNull(client);
    assertEquals(30, client.connectTimeoutMillis() / 1000);
    assertEquals(60, client.readTimeoutMillis() / 1000);
    assertEquals(90, client.writeTimeoutMillis() / 1000);

    // Verify that logging interceptor is not added when useLogging is false
    boolean hasLoggingInterceptor = false;
    for (final okhttp3.Interceptor interceptor : client.interceptors()) {
      if (interceptor instanceof HttpLoggingInterceptor) {
        hasLoggingInterceptor = true;
        break;
      }
    }
    assertFalse(hasLoggingInterceptor);
  }

  @Test
  public void testProxyConfiguration() {
    // Test proxy configuration
    builder.setUseProxy(true)
           .setProxyType(Proxy.Type.HTTP)
           .setProxyHost("proxy.example.com")
           .setProxyPort(8080);

    final Proxy proxy = builder.getProxy();
    assertNotNull(proxy);
    assertEquals(Proxy.Type.HTTP, proxy.type());
    assertInstanceOf(InetSocketAddress.class, proxy.address());
    final InetSocketAddress address = (InetSocketAddress) proxy.address();
    assertEquals("proxy.example.com", address.getHostName());
    assertEquals(8080, address.getPort());
  }

  @Test
  public void testAddInterceptor() {
    // Test adding custom interceptors
    final TestInterceptor interceptor1 = new TestInterceptor("interceptor1");
    final TestInterceptor interceptor2 = new TestInterceptor("interceptor2");

    builder.addInterceptor(interceptor1)
           .addInterceptor(interceptor2);

    final OkHttpClient client = builder.build();

    // Verify that custom interceptors are added
    boolean hasInterceptor1 = false;
    boolean hasInterceptor2 = false;
    for (final okhttp3.Interceptor interceptor : client.interceptors()) {
      if (interceptor == interceptor1) {
        hasInterceptor1 = true;
      } else if (interceptor == interceptor2) {
        hasInterceptor2 = true;
      }
    }
    assertTrue(hasInterceptor1);
    assertTrue(hasInterceptor2);
  }

  @Test
  public void testClearInterceptors() {
    // Test clearing interceptors
    final TestInterceptor interceptor = new TestInterceptor("test");

    builder.addInterceptor(interceptor)
           .clearInterceptors();

    final OkHttpClient client = builder.build();

    // Verify that custom interceptor is not added after clearing
    boolean hasTestInterceptor = false;
    for (final okhttp3.Interceptor i : client.interceptors()) {
      if (i == interceptor) {
        hasTestInterceptor = true;
        break;
      }
    }
    assertFalse(hasTestInterceptor);

    // Logging interceptor should still be added if useLogging is true
    boolean hasLoggingInterceptor = false;
    for (final okhttp3.Interceptor i : client.interceptors()) {
      if (i instanceof HttpLoggingInterceptor) {
        hasLoggingInterceptor = true;
        break;
      }
    }
    assertTrue(hasLoggingInterceptor);
  }

  @Test
  public void testActualHttpRequest() throws Exception {
    // Test making an actual HTTP request with the built client
    try (final MockWebServer server = new MockWebServer()) {
      // Setup mock server
      server.enqueue(new MockResponse()
          .setResponseCode(200)
          .setBody("Hello, World!"));
      server.start();

      // Configure builder to use shorter timeouts for test
      builder.setConnectionTimeout(5)
             .setReadTimeout(5)
             .setWriteTimeout(5);

      // Build client and make request
      final OkHttpClient client = builder.build();
      final Request request = new Request.Builder()
          .url(server.url("/"))
          .build();

      try (final Response response = client.newCall(request).execute()) {
        assertEquals(200, response.code());
        assertEquals("Hello, World!", response.body().string());
      }
    }
  }

  /**
   * A test interceptor for testing purposes.
   */
  private static class TestInterceptor implements okhttp3.Interceptor {
    private final String name;

    public TestInterceptor(final String name) {
      this.name = name;
    }

    @Override
    public Response intercept(final Chain chain) throws java.io.IOException {
      return chain.proceed(chain.request());
    }

    @Override
    public String toString() {
      return "TestInterceptor[" + name + "]";
    }
  }
}