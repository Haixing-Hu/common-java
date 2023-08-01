////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import ltd.qubit.commons.util.pair.NameValuePair;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

public class UriBuilderTest {

  @Test
  public void testHierarchicalUri() throws Exception {
    final URI uri = new URI("http", "stuff", "localhost", 80, "/some stuff", "param=stuff", "fragment");
    final UriBuilder uribuilder = new UriBuilder(uri);
    final URI result = uribuilder.build();
    assertEquals(new URI("http://stuff@localhost:80/some%20stuff?param=stuff#fragment"), result);
  }

  @Test
  public void testMutationToRelativeUri() throws Exception {
    final URI uri = new URI("http://stuff@localhost:80/stuff?param=stuff#fragment");
    final UriBuilder uribuilder = new UriBuilder(uri).setHost(null);
    final URI result = uribuilder.build();
    assertEquals(new URI("http:///stuff?param=stuff#fragment"), result);
  }

  @Test
  public void testMutationRemoveFragment() throws Exception {
    final URI uri = new URI("http://stuff@localhost:80/stuff?param=stuff#fragment");
    final URI result = new UriBuilder(uri).setFragment(null).build();
    assertEquals(new URI("http://stuff@localhost:80/stuff?param=stuff"), result);
  }

  @Test
  public void testMutationRemoveUserInfo() throws Exception {
    final URI uri = new URI("http://stuff@localhost:80/stuff?param=stuff#fragment");
    final URI result = new UriBuilder(uri).setUserInfo(null).build();
    assertEquals(new URI("http://localhost:80/stuff?param=stuff#fragment"), result);
  }

  @Test
  public void testMutationRemovePort() throws Exception {
    final URI uri = new URI("http://stuff@localhost:80/stuff?param=stuff#fragment");
    final URI result = new UriBuilder(uri).setPort(-1).build();
    assertEquals(new URI("http://stuff@localhost/stuff?param=stuff#fragment"), result);
  }

  @Test
  public void testOpaqueUri() throws Exception {
    final URI uri = new URI("stuff", "some-stuff", "fragment");
    final UriBuilder uribuilder = new UriBuilder(uri);
    final URI result = uribuilder.build();
    assertEquals(uri, result);
  }

  @Test
  public void testOpaqueUriMutation() throws Exception {
    final URI uri = new URI("stuff", "some-stuff", "fragment");
    final UriBuilder uribuilder = new UriBuilder(uri).setCustomQuery("param1&param2=stuff").setFragment(null);
    assertEquals(new URI("stuff:?param1&param2=stuff"), uribuilder.build());
  }

  @Test
  public void testHierarchicalUriMutation() throws Exception {
    final UriBuilder uribuilder = new UriBuilder("/").setScheme("http").setHost("localhost").setPort(80).setPath("/stuff");
    assertEquals(new URI("http://localhost:80/stuff"), uribuilder.build());
  }

  @Test
  public void testEmpty() throws Exception {
    final UriBuilder uribuilder = new UriBuilder();
    final URI result = uribuilder.build();
    assertEquals(new URI(""), result);
  }

  @Test
  public void testSetUserInfo() throws Exception {
    final URI uri = new URI("http", null, "localhost", 80, "/", "param=stuff", null);
    final UriBuilder uribuilder = new UriBuilder(uri).setUserInfo("user", "password");
    final URI result = uribuilder.build();
    assertEquals(new URI("http://user:password@localhost:80/?param=stuff"), result);
  }

  @Test
  public void testRemoveParameters() throws Exception {
    final URI uri = new URI("http", null, "localhost", 80, "/", "param=stuff", null);
    final UriBuilder uribuilder = new UriBuilder(uri).removeQuery();
    final URI result = uribuilder.build();
    assertEquals(new URI("http://localhost:80/"), result);
  }

  @Test
  public void testSetParameter() throws Exception {
    final URI uri = new URI("http", null, "localhost", 80, "/", "param=stuff&blah&blah", null);
    final UriBuilder uribuilder = new UriBuilder(uri).setParameter("param", "some other stuff")
        .setParameter("blah", "blah");
    final URI result = uribuilder.build();
    assertEquals(new URI("http://localhost:80/?param=some+other+stuff&blah=blah"), result);
  }

  @Test
  public void testSetParametersWithEmptyArg() throws Exception {
    final URI uri = new URI("http", null, "localhost", 80, "/test", "param=test", null);
    final UriBuilder uribuilder = new UriBuilder(uri).setParameters();
    final URI result = uribuilder.build();
    assertEquals(new URI("http://localhost:80/test"), result);
  }

  @Test
  public void testSetParametersWithEmptyList() throws Exception {
    final URI uri = new URI("http", null, "localhost", 80, "/test", "param=test", null);
    final UriBuilder uribuilder = new UriBuilder(uri).setParameters(Collections.emptyList());
    final URI result = uribuilder.build();
    assertEquals(new URI("http://localhost:80/test"), result);
  }

  @Test
  public void testParameterWithSpecialChar() throws Exception {
    final URI uri = new URI("http", null, "localhost", 80, "/", "param=stuff", null);
    final UriBuilder uribuilder = new UriBuilder(uri).addParameter("param", "1 + 1 = 2")
        .addParameter("param", "blah&blah");
    final URI result = uribuilder.build();
    assertEquals(new URI("http://localhost:80/?param=stuff&param=1+%2B+1+%3D+2&" +
        "param=blah%26blah"), result);
  }

  @Test
  public void testAddParameter() throws Exception {
    final URI uri = new URI("http", null, "localhost", 80, "/", "param=stuff&blah&blah", null);
    final UriBuilder uribuilder = new UriBuilder(uri).addParameter("param", "some other stuff")
        .addParameter("blah", "blah");
    final URI result = uribuilder.build();
    assertEquals(new URI("http://localhost:80/?param=stuff&blah&blah&" +
        "param=some+other+stuff&blah=blah"), result);
  }

  @Test
  public void testQueryEncoding() throws Exception {
    final URI uri1 = new URI("https://somehost.com/stuff?client_id=1234567890" +
        "&redirect_uri=https%3A%2F%2Fsomehost.com%2Fblah+blah%2F");
    final URI uri2 = new UriBuilder("https://somehost.com/stuff")
        .addParameter("client_id","1234567890")
        .addParameter("redirect_uri","https://somehost.com/blah blah/").build();
    assertEquals(uri1, uri2);
  }

  @Test
  public void testQueryAndParameterEncoding() throws Exception {
    final URI uri1 = new URI("https://somehost.com/stuff?param1=12345&param2=67890");
    final URI uri2 = new UriBuilder("https://somehost.com/stuff")
        .setCustomQuery("this&that")
        .addParameter("param1","12345")
        .addParameter("param2","67890").build();
    assertEquals(uri1, uri2);
  }

  @Test
  public void testPathEncoding() throws Exception {
    final URI uri1 = new URI("https://somehost.com/some%20path%20with%20blanks/");
    final URI uri2 = new UriBuilder()
        .setScheme("https")
        .setHost("somehost.com")
        .setPath("/some path with blanks/")
        .build();
    assertEquals(uri1, uri2);
  }

  @Test
  public void testAgainstURI() throws Exception {
    // Check that the URI generated by URI builder agrees with that generated by using URI directly
    final String scheme="https";
    final String host="localhost";
    final String specials="/abcd!$&*()_-+.,=:;'~@[]?<>|#^%\"{}\\\u00a3`\u00ac\u00a6xyz"; // N.B. excludes space
    final URI uri = new URI(scheme, specials, host, 80, specials, specials, specials);

    final URI bld = new UriBuilder()
        .setScheme(scheme)
        .setHost(host)
        .setUserInfo(specials)
        .setPath(specials)
        .setCustomQuery(specials)
        .setFragment(specials)
        .build();

    assertEquals(uri.getHost(), bld.getHost());

    assertEquals(uri.getUserInfo(), bld.getUserInfo());

    assertEquals(uri.getPath(), bld.getPath());

    assertEquals(uri.getQuery(), bld.getQuery());

    assertEquals(uri.getFragment(), bld.getFragment());

  }

  @Test
  public void testAgainstURIEncoded() throws Exception {
    // Check that the encoded URI generated by URI builder agrees with that generated by using URI directly
    final String scheme="https";
    final String host="localhost";
    final String specials="/ abcd!$&*()_-+.,=:;'~<>/@[]|#^%\"{}\\`xyz"; // N.B. excludes \u00a3\u00ac\u00a6
    final URI uri = new URI(scheme, specials, host, 80, specials, specials, specials);

    final URI bld = new UriBuilder()
        .setScheme(scheme)
        .setHost(host)
        .setUserInfo(specials)
        .setPath(specials)
        .setCustomQuery(specials)
        .setFragment(specials)
        .build();

    assertEquals(uri.getHost(), bld.getHost());

    assertEquals(uri.getRawUserInfo(), bld.getRawUserInfo());

    assertEquals(uri.getRawPath(), bld.getRawPath());

    assertEquals(uri.getRawQuery(), bld.getRawQuery());

    assertEquals(uri.getRawFragment(), bld.getRawFragment());

  }

  @Test
  public void testBuildAddParametersUTF8() throws Exception {
    assertAddParameters(UTF_8);
  }

  @Test
  public void testBuildAddParametersISO88591() throws Exception {
    assertAddParameters(ISO_8859_1);
  }

  public void assertAddParameters(final Charset charset) throws Exception {
    final URI uri = new UriBuilder("https://somehost.com/stuff")
        .setCharset(charset)
        .addParameters(createParameters()).build();

    assertBuild(charset, uri);
  }

  @Test
  public void testBuildSetParametersUTF8() throws Exception {
    assertSetParameters(UTF_8);
  }

  @Test
  public void testBuildSetParametersISO88591() throws Exception {
    assertSetParameters(ISO_8859_1);
  }

  public void assertSetParameters(final Charset charset) throws Exception {
    final URI uri = new UriBuilder("https://somehost.com/stuff")
        .setCharset(charset)
        .setParameters(createParameters()).build();

    assertBuild(charset, uri);
  }

  public void assertBuild(final Charset charset, final URI uri) throws Exception {
    final String encodedData1 = URLEncoder.encode("\"1\u00aa position\"", charset.displayName());
    final String encodedData2 = URLEncoder.encode("Jos\u00e9 Abra\u00e3o", charset.displayName());

    final String uriExpected = String.format("https://somehost.com/stuff?parameter1=value1&parameter2=%s&parameter3=%s", encodedData1, encodedData2);

    assertEquals(uriExpected, uri.toString());
  }

  private List<NameValuePair> createParameters() {
    final List<NameValuePair> parameters = new ArrayList<>();
    parameters.add(new NameValuePair("parameter1", "value1"));
    parameters.add(new NameValuePair("parameter2", "\"1\u00aa position\""));
    parameters.add(new NameValuePair("parameter3", "Jos\u00e9 Abra\u00e3o"));
    return parameters;
  }

  @Test
  public void testMalformedPath() throws Exception {
    final String path = "@notexample.com/mypath";
    final URI uri = new UriBuilder(path).setHost("example.com").build();
    assertEquals("example.com", uri.getHost());
  }

  @Test
  public void testRelativePath() throws Exception {
    final URI uri = new UriBuilder("./mypath").build();
    assertEquals(new URI("./mypath"), uri);
  }

  @Test
  public void testRelativePathWithAuthority() throws Exception {
    final URI uri = new UriBuilder("./mypath").setHost("somehost").setScheme("http").build();
    assertEquals(new URI("http://somehost/./mypath"), uri);
  }

  @Test
  public void testMultipleLeadingPathSlashes() throws Exception {
    final URI uri = new UriBuilder()
        .setScheme("ftp")
        .setHost("somehost")
        .setPath("//blah//blah")
        .build();
    assertThat(uri, CoreMatchers.equalTo(URI.create("ftp://somehost//blah//blah")));
  }

  @Test
  public void testPathNoLeadingSlash() throws Exception {
    final URI uri = new UriBuilder()
        .setScheme("ftp")
        .setPath("blah")
        .build();
    assertThat(uri, CoreMatchers.equalTo(URI.create("ftp:/blah")));
  }

  @Test
  public void testOpaque() throws Exception {
    final UriBuilder uriBuilder = new UriBuilder("http://host.com");
    final URI uri = uriBuilder.build();
    assertThat(uriBuilder.isOpaque(), CoreMatchers.equalTo(uri.isOpaque()));
  }
}
