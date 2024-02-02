////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ltd.qubit.commons.io.IoUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link PathResource}.
 *
 * @author Philippe Marschall
 * @author Phillip Webb
 * @author Nicholas Williams
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @author Arjen Poutsma
 * @author Haixing Hu
 */
class PathResourceTest {

  private static final String TEST_DIR =
      platformPath("src/test/resources/ltd/qubit/commons/io/resource");

  private static final String TEST_FILE =
      platformPath("src/test/resources/ltd/qubit/commons/io/resource/example.properties");

  private static final String NON_EXISTING_FILE =
      platformPath("src/test/resources/ltd/qubit/commons/io/resource/doesnotexist.properties");


  private static String platformPath(final String string) {
    return string.replace('/', File.separatorChar);
  }

  @Test
  void nullPath() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> new PathResource((Path) null))
        .withMessageContaining("Path must not be null");
  }

  @Test
  void nullPathString() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> new PathResource((String) null))
        .withMessageContaining("Path must not be null");
  }

  @Test
  void nullUri() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> new PathResource((URI) null))
        .withMessageContaining("URI must not be null");
  }

  @Test
  void createFromPath() {
    final Path path = Paths.get(TEST_FILE);
    final PathResource resource = new PathResource(path);
    assertThat(resource.getPath()).isEqualTo(TEST_FILE);
  }

  @Test
  void createFromString() {
    final PathResource resource = new PathResource(TEST_FILE);
    assertThat(resource.getPath()).isEqualTo(TEST_FILE);
  }

  @Test
  void createFromUri() {
    final File file = new File(TEST_FILE);
    final PathResource resource = new PathResource(file.toURI());
    assertThat(resource.getPath())
        .isEqualTo(file.getAbsoluteFile().toString());
  }

  @Test
  void getPathForFile() {
    final PathResource resource = new PathResource(TEST_FILE);
    assertThat(resource.getPath()).isEqualTo(TEST_FILE);
  }

  @Test
  void getPathForDir() {
    final PathResource resource = new PathResource(TEST_DIR);
    assertThat(resource.getPath()).isEqualTo(TEST_DIR);
  }

  @Test
  void fileExists() {
    final PathResource resource = new PathResource(TEST_FILE);
    assertThat(resource.exists()).isTrue();
  }

  @Test
  void dirExists() {
    final PathResource resource = new PathResource(TEST_DIR);
    assertThat(resource.exists()).isTrue();
  }

  @Test
  void fileDoesNotExist() {
    final PathResource resource = new PathResource(NON_EXISTING_FILE);
    assertThat(resource.exists()).isFalse();
  }

  @Test
  void fileIsReadable() {
    final PathResource resource = new PathResource(TEST_FILE);
    assertThat(resource.isReadable()).isTrue();
  }

  @Test
  void nonExistingFileIsNotReadable() {
    final PathResource resource = new PathResource(NON_EXISTING_FILE);
    assertThat(resource.isReadable()).isFalse();
  }

  @Test
  void directoryIsNotReadable() {
    final PathResource resource = new PathResource(TEST_DIR);
    assertThat(resource.isReadable()).isFalse();
  }

  @Test
  void getInputStream() throws IOException {
    final PathResource resource = new PathResource(TEST_FILE);
    final InputStream is = resource.getInputStream();
    try (is) {
      final byte[] bytes = IoUtils.toByteArray(is);
      assertThat(bytes).hasSizeGreaterThan(0);
    }
  }

  @Test
  void getInputStreamForDir() {
    final PathResource resource = new PathResource(TEST_DIR);
    assertThatExceptionOfType(FileNotFoundException.class)
        .isThrownBy(resource::getInputStream);
  }

  @Test
  void getInputStreamForNonExistingFile() {
    final PathResource resource = new PathResource(NON_EXISTING_FILE);
    assertThatExceptionOfType(FileNotFoundException.class)
        .isThrownBy(resource::getInputStream);
  }

  @Test
  void getUrl() throws IOException {
    final PathResource resource = new PathResource(TEST_FILE);
    assertThat(resource.getURL().toString())
        .endsWith("commons/io/resource/example.properties");
  }

  @Test
  void getUri() throws IOException {
    final PathResource resource = new PathResource(TEST_FILE);
    assertThat(resource.getURI().toString())
        .endsWith("commons/io/resource/example.properties");
  }

  @Test
  void getFile() throws IOException {
    final PathResource resource = new PathResource(TEST_FILE);
    final File file = new File(TEST_FILE);
    assertThat(resource.getFile().getAbsoluteFile())
        .isEqualTo(file.getAbsoluteFile());
  }

  @Test
  void getFileUnsupported() {
    final Path path = mock();
    given(path.normalize()).willReturn(path);
    given(path.toFile()).willThrow(new UnsupportedOperationException());
    final PathResource resource = new PathResource(path);
    assertThatExceptionOfType(FileNotFoundException.class)
        .isThrownBy(resource::getFile);
  }

  @Test
  void contentLength() throws IOException {
    final PathResource resource = new PathResource(TEST_FILE);
    final File file = new File(TEST_FILE);
    assertThat(resource.contentLength()).isEqualTo(file.length());
  }

  @Test
  void contentLengthForDirectory() throws IOException {
    final PathResource resource = new PathResource(TEST_DIR);
    final File file = new File(TEST_DIR);
    assertThat(resource.contentLength()).isEqualTo(file.length());
  }

  @Test
  void lastModified() throws IOException {
    final PathResource resource = new PathResource(TEST_FILE);
    final File file = new File(TEST_FILE);
    assertThat(resource.lastModified() / 1000).isEqualTo(file.lastModified() / 1000);
  }

  @Test
  void createRelativeFromDir() {
    final Resource resource = new PathResource(TEST_DIR).createRelative("example.properties");
    assertThat(resource).isEqualTo(new PathResource(TEST_FILE));
  }

  @Test
  void createRelativeFromFile() {
    final Resource resource = new PathResource(TEST_FILE).createRelative("../example.properties");
    assertThat(resource).isEqualTo(new PathResource(TEST_FILE));
  }

  @Test
  void filename() {
    final Resource resource = new PathResource(TEST_FILE);
    assertThat(resource.getFilename()).isEqualTo("example.properties");
  }

  @Test
  void description() {
    final Resource resource = new PathResource(TEST_FILE);
    assertThat(resource.getDescription()).contains("path [");
    assertThat(resource.getDescription()).contains(TEST_FILE);
  }

  @Test
  void fileIsWritable() {
    final PathResource resource = new PathResource(TEST_FILE);
    assertThat(resource.isWritable()).isTrue();
  }

  @Test
  void directoryIsNotWritable() {
    final PathResource resource = new PathResource(TEST_DIR);
    assertThat(resource.isWritable()).isFalse();
  }

  @Test
  void equalsAndHashCode() {
    final Resource resource1 = new PathResource(TEST_FILE);
    final Resource resource2 = new PathResource(TEST_FILE);
    final Resource resource3 = new PathResource(TEST_DIR);
    assertThat(resource1).isEqualTo(resource1);
    assertThat(resource1).isEqualTo(resource2);
    assertThat(resource2).isEqualTo(resource1);
    assertThat(resource1).isNotEqualTo(resource3);
    assertThat(resource1).hasSameHashCodeAs(resource2);
    assertThat(resource1).doesNotHaveSameHashCodeAs(resource3);
  }

  @Test
  void getOutputStreamForExistingFile(@TempDir final Path temporaryFolder) throws IOException {
    final PathResource resource = new PathResource(temporaryFolder.resolve("test"));
    IoUtils.copy("test".getBytes(StandardCharsets.UTF_8), resource.getOutputStream());
    assertThat(resource.contentLength()).isEqualTo(4L);
  }

  @Test
  void getOutputStreamForNonExistingFile(@TempDir final Path temporaryFolder) throws IOException {
    final File file = temporaryFolder.resolve("test").toFile();
    file.delete();
    final PathResource resource = new PathResource(file.toPath());
    IoUtils.copy("test".getBytes(), resource.getOutputStream());
    assertThat(resource.contentLength()).isEqualTo(4L);
  }

  @Test
  void getOutputStreamForDirectory() {
    final PathResource resource = new PathResource(TEST_DIR);
    assertThatExceptionOfType(FileNotFoundException.class)
        .isThrownBy(resource::getOutputStream);
  }

  @Test
  void getReadableByteChannel() throws IOException {
    final PathResource resource = new PathResource(TEST_FILE);
    try (final ReadableByteChannel channel = resource.readableChannel()) {
      final ByteBuffer buffer = ByteBuffer.allocate((int) resource.contentLength());
      channel.read(buffer);
      buffer.rewind();
      assertThat(buffer.limit()).isGreaterThan(0);
    }
  }

  @Test
  void getReadableByteChannelForDir() throws IOException {
    final PathResource resource = new PathResource(TEST_DIR);
    try {
      resource.readableChannel();
    }
    catch (final AccessDeniedException ex) {
      // on Windows
    }
  }

  @Test
  void getReadableByteChannelForNonExistingFile() {
    final PathResource resource = new PathResource(NON_EXISTING_FILE);
    assertThatExceptionOfType(FileNotFoundException.class)
        .isThrownBy(resource::readableChannel);
  }

  @Test
  void getWritableChannel(@TempDir final Path temporaryFolder) throws IOException {
    final Path testPath = temporaryFolder.resolve("test");
    Files.createFile(testPath);
    final PathResource resource = new PathResource(testPath);
    final ByteBuffer buffer = ByteBuffer.wrap("test".getBytes(StandardCharsets.UTF_8));
    try (final WritableByteChannel channel = resource.writableChannel()) {
      channel.write(buffer);
    }
    assertThat(resource.contentLength()).isEqualTo(4L);
  }

}
