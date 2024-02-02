////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import ltd.qubit.commons.io.IoUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Tests for various {@link Resource} implementations.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @author Sam Brannen
 * @author Brian Clozel
 * @author Haixing Hu
 */
class ResourceTest {

  @ParameterizedTest(name = "{index}: {0}")
  @MethodSource("resource")
  void resourceIsValid(final Resource resource) throws Exception {
    assertThat(resource.getFilename()).isEqualTo("ResourceTest.class");
    assertThat(resource.getURL().getFile()).endsWith("ResourceTest.class");
    assertThat(resource.exists()).isTrue();
    assertThat(resource.isReadable()).isTrue();
    assertThat(resource.contentLength()).isGreaterThan(0);
    assertThat(resource.lastModified()).isGreaterThan(0);
    assertThat(resource.getContentAsByteArray()).containsExactly(Files.readAllBytes(Path.of(resource.getURI())));
  }

  @ParameterizedTest(name = "{index}: {0}")
  @MethodSource("resource")
  void resourceCreateRelative(final Resource resource) throws Exception {
    final Resource relative1 = resource.createRelative("ClassPathResourceTest.class");
    assertThat(relative1.getFilename()).isEqualTo("ClassPathResourceTest.class");
    assertThat(relative1.getURL().getFile().endsWith("ClassPathResourceTest.class")).isTrue();
    assertThat(relative1.exists()).isTrue();
    assertThat(relative1.isReadable()).isTrue();
    assertThat(relative1.contentLength()).isGreaterThan(0);
    assertThat(relative1.lastModified()).isGreaterThan(0);
  }

  @ParameterizedTest(name = "{index}: {0}")
  @MethodSource("resource")
  void resourceCreateRelativeWithFolder(final Resource resource) throws Exception {
    final Resource relative2 = resource.createRelative("ClassPathResourceTest.class");
    assertThat(relative2.getFilename()).isEqualTo("ClassPathResourceTest.class");
    assertThat(relative2.getURL().getFile()).endsWith("ClassPathResourceTest.class");
    assertThat(relative2.exists()).isTrue();
    assertThat(relative2.isReadable()).isTrue();
    assertThat(relative2.contentLength()).isGreaterThan(0);
    assertThat(relative2.lastModified()).isGreaterThan(0);
  }

  @ParameterizedTest(name = "{index}: {0}")
  @MethodSource("resource")
  void resourceCreateRelativeWithDotPath(final Resource resource) throws Exception {
    final Resource relative3 = resource.createRelative("../FilenameUtilsTest.class");
    assertThat(relative3.getFilename()).isEqualTo("FilenameUtilsTest.class");
    assertThat(relative3.getURL().getFile()).endsWith("FilenameUtilsTest.class");
    assertThat(relative3.exists()).isTrue();
    assertThat(relative3.isReadable()).isTrue();
    assertThat(relative3.contentLength()).isGreaterThan(0);
    assertThat(relative3.lastModified()).isGreaterThan(0);
  }

  @ParameterizedTest(name = "{index}: {0}")
  @MethodSource("resource")
  void resourceCreateRelativeUnknown(final Resource resource) throws Exception {
    final Resource relative4 = resource.createRelative("X.class");
    assertThat(relative4.exists()).isFalse();
    assertThat(relative4.isReadable()).isFalse();
    assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(relative4::contentLength);
    assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(relative4::lastModified);
    assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(relative4::getInputStream);
    assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(relative4::readableChannel);
    assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(relative4::getContentAsByteArray);
    assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(() -> relative4.getContentAsString(UTF_8));
  }

  private static Stream<Arguments> resource() throws URISyntaxException {
    final URL resourceClass = ResourceTest.class.getResource("ResourceTest.class");
    final Path resourceClassFilePath = Paths.get(resourceClass.toURI());
    return Stream.of(
        arguments(named("ClassPathResource", new ClassPathResource("ltd/qubit/commons/io/resource/ResourceTest.class"))),
        arguments(named("ClassPathResource with ClassLoader", new ClassPathResource("ltd/qubit/commons/io/resource/ResourceTest.class", ResourceTest.class.getClassLoader()))),
        arguments(named("ClassPathResource with Class", new ClassPathResource("ResourceTest.class", ResourceTest.class))),
        arguments(named("FileSystemResource", new FileSystemResource(resourceClass.getFile()))),
        arguments(named("FileSystemResource with File", new FileSystemResource(new File(resourceClass.getFile())))),
        arguments(named("FileSystemResource with File path", new FileSystemResource(resourceClassFilePath))),
        arguments(named("UrlResource", new UrlResource(resourceClass)))
    );
  }


  @Nested
  class ByteArrayResourceTest {

    @Test
    void hasContent() throws Exception {
      final String testString = "testString";
      final byte[] testBytes = testString.getBytes();
      final Resource resource = new ByteArrayResource(testBytes);
      assertThat(resource.exists()).isTrue();
      assertThat(resource.isOpen()).isFalse();
      final byte[] contentBytes = resource.getContentAsByteArray();
      assertThat(contentBytes).containsExactly(testBytes);
      String contentString = resource.getContentAsString(StandardCharsets.US_ASCII);
      assertThat(contentString).isEqualTo(testString);
      final InputStream is = resource.getInputStream();
      try (is) {
        contentString = IoUtils.toString(is);
      }
      assertThat(contentString).isEqualTo(testString);
      assertThat(new ByteArrayResource(testBytes)).isEqualTo(resource);
    }

    @Test
    void isNotOpen() {
      final Resource resource = new ByteArrayResource("testString".getBytes());
      assertThat(resource.exists()).isTrue();
      assertThat(resource.isOpen()).isFalse();
    }

    @Test
    void hasDescription() {
      final Resource resource = new ByteArrayResource("testString".getBytes(), "my description");
      assertThat(resource.getDescription()).contains("my description");
    }
  }


  @Nested
  class InputStreamResourceTest {

    @Test
    void hasContent() throws Exception {
      final String testString = "testString";
      final byte[] testBytes = testString.getBytes();
      final InputStream is = new ByteArrayInputStream(testBytes);
      final Resource resource1 = new InputStreamResource(is);
      final InputStream is1 = resource1.getInputStream();
      final String content;
      try (is1) {
        content = IoUtils.toString(is1);
      }
      assertThat(content).isEqualTo(testString);
      assertThat(new InputStreamResource(is)).isEqualTo(resource1);
      assertThatIllegalStateException().isThrownBy(resource1::getInputStream);

      final Resource resource2 = new InputStreamResource(new ByteArrayInputStream(testBytes));
      assertThat(resource2.getContentAsByteArray()).containsExactly(testBytes);
      assertThatIllegalStateException().isThrownBy(resource2::getContentAsByteArray);

      final Resource resource3 = new InputStreamResource(new ByteArrayInputStream(testBytes));
      assertThat(resource3.getContentAsString(StandardCharsets.US_ASCII)).isEqualTo(testString);
      assertThatIllegalStateException().isThrownBy(() -> resource3.getContentAsString(StandardCharsets.US_ASCII));
    }

    @Test
    void isOpen() {
      final InputStream is = new ByteArrayInputStream("testString".getBytes());
      final Resource resource = new InputStreamResource(is);
      assertThat(resource.exists()).isTrue();
      assertThat(resource.isOpen()).isTrue();
    }

    @Test
    void hasDescription() {
      final InputStream is = new ByteArrayInputStream("testString".getBytes());
      final Resource resource = new InputStreamResource(is, "my description");
      assertThat(resource.getDescription()).contains("my description");
    }
  }


  @Nested
  class FileSystemResourceTest {

    @Test
    void sameResourceIsEqual() {
      final String file = getClass().getResource("ResourceTest.class").getFile();
      final Resource resource = new FileSystemResource(file);
      assertThat(resource).isEqualTo(new FileSystemResource(file));
    }

    @Test
    void sameResourceFromFileIsEqual() {
      final File file = new File(getClass().getResource("ResourceTest.class").getFile());
      final Resource resource = new FileSystemResource(file);
      assertThat(resource).isEqualTo(new FileSystemResource(file));
    }

    @Test
    void sameResourceFromFilePathIsEqual() throws Exception {
      final Path filePath = Paths.get(getClass().getResource("ResourceTest.class").toURI());
      final Resource resource = new FileSystemResource(filePath);
      assertThat(resource).isEqualTo(new FileSystemResource(filePath));
    }

    @Test
    void sameResourceFromDotPathIsEqual() {
      final Resource resource = new FileSystemResource("common/io/resource/ResourceTest.class");
      assertThat(new FileSystemResource("common/../common/io/./resource/ResourceTest.class")).isEqualTo(resource);
    }

    @Test
    void relativeResourcesAreEqual() throws Exception {
      final Resource resource = new FileSystemResource("dir/");
      final Resource relative = resource.createRelative("subdir");
      assertThat(relative).isEqualTo(new FileSystemResource("dir/subdir"));
    }

    @Test
    void readableChannelProvidesContent() throws Exception {
      final Resource resource = new FileSystemResource(getClass().getResource("ResourceTest.class").getFile());
      try (final ReadableByteChannel channel = resource.readableChannel()) {
        final ByteBuffer buffer = ByteBuffer.allocate((int) resource.contentLength());
        channel.read(buffer);
        buffer.rewind();
        assertThat(buffer.limit()).isGreaterThan(0);
      }
    }

    @Test
    void urlAndUriAreNormalizedWhenCreatedFromFile() throws Exception {
      final Path path = Path.of("src/test/resources/scanned-resources/resource#test1.txt").toAbsolutePath();
      assertUrlAndUriBehavior(new FileSystemResource(path.toFile()));
    }

    @Test
    void urlAndUriAreNormalizedWhenCreatedFromPath() throws Exception {
      final Path path = Path.of("src/test/resources/scanned-resources/resource#test1.txt").toAbsolutePath();
      assertUrlAndUriBehavior(new FileSystemResource(path));
    }

    /**
     * The following assertions serve as regression tests for the lack of the
     * "authority component" (//) in the returned URI/URL. For example, we are
     * expecting file:/my/path (or file:/C:/My/Path) instead of file:///my/path.
     */
    private void assertUrlAndUriBehavior(final Resource resource) throws IOException {
      assertThat(resource.getURL().toString()).matches("^file:\\/[^\\/].+test1\\.txt$");
      assertThat(resource.getURI().toString()).matches("^file:\\/[^\\/].+test1\\.txt$");
    }
  }


  @Nested
  class UrlResourceTest {

    private MockWebServer server = new MockWebServer();

    @Test
    void sameResourceWithRelativePathIsEqual() throws Exception {
      final Resource resource = new UrlResource("file:core/io/ResourceTest.class");
      assertThat(new UrlResource("file:core/../core/io/./ResourceTest.class")).isEqualTo(resource);
    }

    @Test
    void filenameIsExtractedFromFilePath() throws Exception {
      assertThat(new UrlResource("file:test?argh").getFilename()).isEqualTo("test");
      assertThat(new UrlResource("file:/test?argh").getFilename()).isEqualTo("test");
      assertThat(new UrlResource("file:test.txt?argh").getFilename()).isEqualTo("test.txt");
      assertThat(new UrlResource("file:/test.txt?argh").getFilename()).isEqualTo("test.txt");
      assertThat(new UrlResource("file:/dir/test?argh").getFilename()).isEqualTo("test");
      assertThat(new UrlResource("file:/dir/test.txt?argh").getFilename()).isEqualTo("test.txt");
      assertThat(new UrlResource("file:\\dir\\test.txt?argh").getFilename()).isEqualTo("test.txt");
      assertThat(new UrlResource("file:\\dir/test.txt?argh").getFilename()).isEqualTo("test.txt");
    }

    @Test
    void filenameIsExtractedFromURL() throws Exception {
      assertThat(new UrlResource(new URL("file:test?argh")).getFilename()).isEqualTo("test");
      assertThat(new UrlResource(new URL("file:/test?argh")).getFilename()).isEqualTo("test");
      assertThat(new UrlResource(new URL("file:test.txt?argh")).getFilename()).isEqualTo("test.txt");
      assertThat(new UrlResource(new URL("file:/test.txt?argh")).getFilename()).isEqualTo("test.txt");
      assertThat(new UrlResource(new URL("file:/dir/test?argh")).getFilename()).isEqualTo("test");
      assertThat(new UrlResource(new URL("file:/dir/test.txt?argh")).getFilename()).isEqualTo("test.txt");
      assertThat(new UrlResource(new URL("file:\\dir\\test.txt?argh")).getFilename()).isEqualTo("test.txt");
      assertThat(new UrlResource(new URL("file:\\dir/test.txt?argh")).getFilename()).isEqualTo("test.txt");
    }

    @Test
    void filenameContainingHashTagIsExtractedFromFilePathUnencoded() throws Exception {
      final String unencodedPath = "/dir/test#1.txt";
      final String encodedPath = "/dir/test%231.txt";

      final URI uri = new URI("file", unencodedPath, null);
      final URL url = uri.toURL();
      assertThat(uri.getPath()).isEqualTo(unencodedPath);
      assertThat(uri.getRawPath()).isEqualTo(encodedPath);
      assertThat(url.getPath()).isEqualTo(encodedPath);

      final UrlResource urlResource = new UrlResource(url);
      assertThat(urlResource.getURI().getPath()).isEqualTo(unencodedPath);
      assertThat(urlResource.getFilename()).isEqualTo("test#1.txt");
    }

    @Test
    void factoryMethodsProduceEqualResources() throws Exception {
      final Resource resource1 = new UrlResource("file:core/io/ResourceTest.class");
      final Resource resource2 = UrlResource.from("file:core/io/ResourceTest.class");
      final Resource resource3 = UrlResource.from(resource1.getURI());

      assertThat(resource2.getURL()).isEqualTo(resource1.getURL());
      assertThat(resource3.getURL()).isEqualTo(resource1.getURL());

      assertThat(UrlResource.from("file:core/../core/io/./ResourceTest.class")).isEqualTo(resource1);
      assertThat(UrlResource.from("file:/dir/test.txt?argh").getFilename()).isEqualTo("test.txt");
      assertThat(UrlResource.from("file:\\dir\\test.txt?argh").getFilename()).isEqualTo("test.txt");
      assertThat(UrlResource.from("file:\\dir/test.txt?argh").getFilename()).isEqualTo("test.txt");
    }

    @Test
    void relativeResourcesAreEqual() throws Exception {
      final Resource resource = new UrlResource("file:dir/");
      final Resource relative = resource.createRelative("subdir");
      assertThat(relative).isEqualTo(new UrlResource("file:dir/subdir"));
    }

    @Test
    void missingRemoteResourceDoesNotExist() throws Exception {
      final String baseUrl = startServer();
      final UrlResource resource = new UrlResource(baseUrl + "/missing");
      assertThat(resource.exists()).isFalse();
    }

    @Test
    void remoteResourceExists() throws Exception {
      final String baseUrl = startServer();
      final UrlResource resource = new UrlResource(baseUrl + "/resource");
      assertThat(resource.exists()).isTrue();
      assertThat(resource.contentLength()).isEqualTo(6);
    }

    @Test
    void canCustomizeHttpUrlConnectionForExists() throws Exception {
      final String baseUrl = startServer();
      final CustomResource resource = new CustomResource(baseUrl + "/resource");
      assertThat(resource.exists()).isTrue();
      final RecordedRequest request = this.server.takeRequest();
      assertThat(request.getMethod()).isEqualTo("HEAD");
      assertThat(request.getHeader("Framework-Name")).isEqualTo("Spring");
    }

    @Test
    void canCustomizeHttpUrlConnectionForRead() throws Exception {
      final String baseUrl = startServer();
      final CustomResource resource = new CustomResource(baseUrl + "/resource");
      assertThat(resource.getInputStream()).hasContent("Spring");
      final RecordedRequest request = this.server.takeRequest();
      assertThat(request.getMethod()).isEqualTo("GET");
      assertThat(request.getHeader("Framework-Name")).isEqualTo("Spring");
    }

    @Test
    void useUserInfoToSetBasicAuth() throws Exception {
      startServer();
      final UrlResource resource = new UrlResource("http://alice:secret@localhost:"
          + this.server.getPort() + "/resource");
      assertThat(resource.getInputStream()).hasContent("Spring");
      final RecordedRequest request = this.server.takeRequest();
      final String authorization = request.getHeader("Authorization");
      assertThat(authorization).isNotNull().startsWith("Basic ");
      assertThat(new String(Base64.getDecoder().decode(
          authorization.substring(6)), StandardCharsets.ISO_8859_1)).isEqualTo("alice:secret");
    }

    @AfterEach
    void shutdown() throws Exception {
      this.server.shutdown();
    }

    private String startServer() throws Exception {
      this.server.setDispatcher(new ResourceDispatcher());
      this.server.start();
      return "http://localhost:" + this.server.getPort();
    }

    class CustomResource extends UrlResource {

      public CustomResource(final String path) throws MalformedURLException {
        super(path);
      }

      @Override
      protected void customizeConnection(final HttpURLConnection con) {
        con.setRequestProperty("Framework-Name", "Spring");
      }
    }

    class ResourceDispatcher extends Dispatcher {
      @Nonnull
      @Override
      public MockResponse dispatch(final RecordedRequest request) {
        if ("/resource".equals(request.getPath())) {
          switch (request.getMethod()) {
            case "HEAD":
              return new MockResponse().addHeader("Content-Length", "6");
            case "GET":
              return new MockResponse()
                  .addHeader("Content-Length", "6")
                  .addHeader("Content-Type", "text/plain")
                  .setBody("Spring");
            default:
              return new MockResponse().setResponseCode(404);
          }
        }
        return new MockResponse().setResponseCode(404);
      }
    }
  }


  @Nested
  class AbstractResourceTest {

    @Test
    void missingResourceIsNotReadable() {
      final String name = "test-resource";

      final Resource resource = new AbstractResource() {
        @Override
        public String getDescription() {
          return name;
        }
        @Override
        public InputStream getInputStream() throws IOException {
          throw new FileNotFoundException();
        }
      };

      assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(resource::getURL)
                                                            .withMessageContaining(name);
      assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(resource::getFile)
                                                            .withMessageContaining(name);
      assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(() ->
          resource.createRelative("/testing")).withMessageContaining(name);
      assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(resource::getContentAsByteArray);
      assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(
          () -> resource.getContentAsString(StandardCharsets.US_ASCII));
      assertThat(resource.getFilename()).isNull();
    }

    @Test
    void hasContentLength() throws Exception {
      final AbstractResource resource = new AbstractResource() {
        @Override
        public InputStream getInputStream() {
          return new ByteArrayInputStream(new byte[] {'a', 'b', 'c'});
        }
        @Override
        public String getDescription() {
          return "";
        }
      };
      assertThat(resource.contentLength()).isEqualTo(3L);
    }
  }

}
