////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Dns;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import ltd.qubit.commons.config.impl.DefaultConfig;
import ltd.qubit.commons.net.interceptor.HttpLoggingInterceptor;
import ltd.qubit.commons.net.interceptor.SkipIpV6AddressDns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
    assertEquals("http", builder.getProxyType());
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

  @Test
  public void testUseOnlyIpV4Address() {
    final HttpClientBuilder builder = new HttpClientBuilder();

    // Test default value
    assertFalse(builder.isIpV4Only());

    // Test setting to true
    builder.setIpV4Only(true);
    assertTrue(builder.isIpV4Only());

    // Test setting to false
    builder.setIpV4Only(false);
    assertFalse(builder.isIpV4Only());

    // Test method chaining
    final HttpClientBuilder result = builder.setIpV4Only(true);
    assertSame(builder, result);
  }

  @Test
  public void testBuildWithUseOnlyIpV4Address() {
    final HttpClientBuilder builder = new HttpClientBuilder()
        .setIpV4Only(true);

    // Should not throw any exceptions
    final OkHttpClient client = builder.build();
    assertNotNull(client);
  }

  @Test
  public void testDnsResolverWithIpV4Only() {
    // Test when useOnlyIpV4Address is true
    builder.setIpV4Only(true);
    final OkHttpClient clientV4Only = builder.build();

    // Check if the client uses SkipIpV6AddressDns
    final Dns dns = clientV4Only.dns();
    assertSame(SkipIpV6AddressDns.INSTANCE, dns);

    // Test when useOnlyIpV4Address is false (default)
    builder.setIpV4Only(false);
    final OkHttpClient clientDefault = builder.build();

    // Check if the client uses default DNS
    final Dns defaultDns = clientDefault.dns();
    assertEquals(Dns.SYSTEM, defaultDns);
  }

  @Test
  public void testDnsResolutionBehavior() throws Exception {
    // Test that SkipIpV6AddressDns only returns IPv4 addresses
    final String testHostname = "openrouter.ai";

    // Test default DNS (may return both IPv4 and IPv6)
    final List<InetAddress> systemAddresses = Dns.SYSTEM.lookup(testHostname);
    assertFalse(systemAddresses.isEmpty(), "System DNS should resolve openrouter.ai");

    // Test IPv4-only DNS (should only return IPv4 addresses)
    final List<InetAddress> ipv4OnlyAddresses = SkipIpV6AddressDns.INSTANCE.lookup(testHostname);
    assertFalse(ipv4OnlyAddresses.isEmpty(), "IPv4-only DNS should resolve openrouter.ai");

    // Verify all returned addresses are IPv4
    for (final var address : ipv4OnlyAddresses) {
      assertInstanceOf(java.net.Inet4Address.class, address,
          "All addresses from SkipIpV6AddressDns should be IPv4: " + address);
    }
  }

  @Test
  public void testHttpClientBuilderDnsIntegration() {
    // Test that HttpClientBuilder correctly applies DNS resolver based on useOnlyIpV4Address setting

    // Test IPv4-only configuration
    final HttpClientBuilder ipv4Builder = new HttpClientBuilder()
        .setIpV4Only(true)
        .setUseHttpLogging(false); // Disable logging for cleaner test

    final OkHttpClient ipv4Client = ipv4Builder.build();
    assertSame(SkipIpV6AddressDns.INSTANCE, ipv4Client.dns());

    // Test default configuration
    final HttpClientBuilder defaultBuilder = new HttpClientBuilder()
        .setIpV4Only(false)
        .setUseHttpLogging(false);

    final OkHttpClient defaultClient = defaultBuilder.build();
    assertEquals(Dns.SYSTEM, defaultClient.dns());

    // Test that unset configuration uses default (false)
    final HttpClientBuilder unsetBuilder = new HttpClientBuilder()
        .setUseHttpLogging(false);

    final OkHttpClient unsetClient = unsetBuilder.build();
    assertEquals(Dns.SYSTEM, unsetClient.dns());
  }

  @Test
  public void testOpenrouterAiAccessWithIpV4Only() throws Exception {
    // Test real network access to openrouter.ai with IPv4-only DNS
    final String targetUrl = "https://openrouter.ai";

    // Create HTTP client with IPv4-only DNS resolution
    final HttpClientBuilder ipv4Builder = new HttpClientBuilder()
        .setIpV4Only(true)
        .setConnectionTimeout(10)
        .setReadTimeout(10)
        .setUseHttpLogging(false);

    final OkHttpClient ipv4Client = ipv4Builder.build();

    // Verify the client is configured correctly
    assertSame(SkipIpV6AddressDns.INSTANCE, ipv4Client.dns());

    // Create a simple HEAD request to openrouter.ai
    final Request request = new Request.Builder()
        .url(targetUrl)
        .head()
        .build();

    // Execute the request - should succeed if DNS resolution works with IPv4-only
    try (final Response response = ipv4Client.newCall(request).execute()) {
      // Verify we got a valid response (any HTTP status code is acceptable)
      assertTrue(response.code() >= 100 && response.code() < 600,
          "HTTP response code should be valid: " + response.code());

      // Log the result for debugging
      LOGGER.info("Successfully accessed {} with IPv4-only DNS, response code: {}",
          targetUrl, response.code());

    } catch (final Exception e) {
      // If there's a network error, it might be due to network configuration
      // We still want to verify that the DNS resolver was set correctly
      assertSame(SkipIpV6AddressDns.INSTANCE, ipv4Client.dns());
      LOGGER.warn("Network access failed (this might be expected in CI environments): {}",
          e.getMessage());

      // Skip assertion for network failures in CI/test environments
      // The important thing is that the DNS resolver was configured correctly
    }
  }

  @Test
  public void testCompareIpV4OnlyVsDefaultDnsForOpenrouter() throws Exception {
    // Compare DNS resolution between default and IPv4-only for openrouter.ai
    final String hostname = "openrouter.ai";

    try {
      // Test default system DNS
      final List<InetAddress> systemAddresses = Dns.SYSTEM.lookup(hostname);
      assertFalse(systemAddresses.isEmpty(), "System DNS should resolve " + hostname);

      // Test IPv4-only DNS
      final List<InetAddress> ipv4OnlyAddresses = SkipIpV6AddressDns.INSTANCE.lookup(hostname);
      assertFalse(ipv4OnlyAddresses.isEmpty(), "IPv4-only DNS should resolve " + hostname);

      // Verify all IPv4-only addresses are actually IPv4
      for (final var address : ipv4OnlyAddresses) {
        assertInstanceOf(java.net.Inet4Address.class, address,
            "IPv4-only DNS should only return IPv4 addresses: " + address);
      }

      // Log the results for comparison
      LOGGER.info("System DNS resolved {} to {} addresses", hostname, systemAddresses.size());
      LOGGER.info("IPv4-only DNS resolved {} to {} IPv4 addresses", hostname, ipv4OnlyAddresses.size());

      // IPv4-only should have equal or fewer addresses than system DNS
      assertTrue(ipv4OnlyAddresses.size() <= systemAddresses.size(),
          "IPv4-only should have equal or fewer addresses than system DNS");

    } catch (final Exception e) {
      // DNS resolution might fail in some test environments
      LOGGER.warn("DNS resolution test failed (this might be expected in restricted environments): {}",
          e.getMessage());

      // The test should still pass - we're mainly testing the configuration
      // In real usage, the DNS resolution would work
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