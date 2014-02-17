package common;

/**
 * Class for common constants used.
 * @author Shaofeng Chen
 * @since 2/9/14
 */
public class Constants {
    public static final int HTTP_PORT = 80;
    public static final String CRLF = "\r\n";
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String DEFAULT_HTTP_VERSION = "HTTP/1.0";
    public static final String DEFAULT_PATH = "/";
    public static final String HOST_PREFIX = "Host:";
    public static final String CONN_PREFIX = "Connection:";
    public static final String DEFAULT_CONN_HEADER = "Connection: close";
    public static final String HTTP_PREFIX = "http://";
    public static final int BUFFER_SIZE = 8192;
    public static final int MAX_OBJECT_SIZE = 100000;
    public static final String LOCAL_HOST = "localhost";
    public static final String SHA_256 = "SHA-256";
    public static final String UTF_8 = "UTF-8";
    public static final String PROXY_CONN_PREFIX = "Proxy-Connection:";
}
