package tests.builder;

import builder.RequestBuilder;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Test class for RequestBuilder.
 * @author Shaofeng Chen
 * @since 2/10/14
 */
public class RequestBuilderTest {

    private static final String REQUEST = "GET / www.google.com HTTP/1.0\r\n" +
            "Host: www.google.com\r\n" +
            "Connection: keep-alive\r\n\r\n";
    @Test
    public void testBuild() {
        InputStream inputStream = new ByteArrayInputStream(REQUEST.getBytes());
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.build(inputStream);
    }

    @Test
    public void testGetHost() {
        InputStream inputStream = new ByteArrayInputStream(REQUEST.getBytes());
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.build(inputStream);
        assertEquals(requestBuilder.getHost(), "www.google.com");
    }

    @Test
    public void testGetMethod() {
        InputStream inputStream = new ByteArrayInputStream(REQUEST.getBytes());
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.build(inputStream);
        assertEquals(requestBuilder.getMethod(), "GET");
    }

    @Test
    public void testGetPort() {
        InputStream inputStream = new ByteArrayInputStream(REQUEST.getBytes());
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.build(inputStream);
        assertEquals(requestBuilder.getPort(), 80);
    }

    @Test
    public void testGetUri() {
        InputStream inputStream = new ByteArrayInputStream(REQUEST.getBytes());
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.build(inputStream);
        assertEquals(requestBuilder.getUri(), "www.google.com/");
    }
}
