package tests.builder;

import builder.ResponseBuilder;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class for ResponseBuilder.
 * @author Shaofeng Chen
 * @since 2/10/14
 */
public class ResponseBuilderTest {
    private static final String RESPONSE = "This is test response.";

    @Test
    public void testBuild() {
        InputStream inputStream = new ByteArrayInputStream(RESPONSE.getBytes());
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.build(inputStream);
    }

    @Test
    public void testGetResponse() {
        InputStream inputStream = new ByteArrayInputStream(RESPONSE.getBytes());
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.build(inputStream);
        assertArrayEquals(RESPONSE.getBytes(), responseBuilder.getResponse());
    }
}
