package builder;

import exceptions.ProxyServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static common.Constants.*;
import static common.Messages.*;
/**
 * Class for build http request from socket input stream.
 * @author Shaofeng Chen
 * @since 2/7/14
 */
public class RequestBuilder {
    private String host;
    private int port;
    private List<String> headers;
    private String method;
    private String path;
    private String version;

    public RequestBuilder() {
        headers = new ArrayList<String>();
        host = "";
        port = HTTP_PORT;
        method = HTTP_GET;
        path = DEFAULT_PATH;
        version = DEFAULT_HTTP_VERSION;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getMethod() {
        return method;
    }

    public String getHeadId() {
        return method + getUri() + version;
    }

    public String getUri() {
        return path.startsWith(HTTP_PREFIX) ? path : host+path;
    }

    public String getHeadersAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String header : headers) {
            stringBuilder.append(header).append(CRLF);
        }
        stringBuilder.append(CRLF);
        return stringBuilder.toString();
    }

    private void buildHeader(String header) {
        if (isMethodHeader(header)) {
            buildMethod(header);
        }
        if (isHostHeader(header)) {
            buildHost(header);
        }
        if (!isConnectionHeader(header)) {
            headers.add(header);
        } else {
            headers.add(DEFAULT_CONN_HEADER);
        }
    }

    private boolean isHostHeader(String header) {
        return header.startsWith(HOST_PREFIX);
    }

    private void buildHost(String header) {
        String[] components = header.split(" ");
        String hostName = components[1];
        if (hostName.indexOf(":") > 0) {
            String[] hostComponents = hostName.split(":");
            host = hostComponents[0];
            port = Integer.parseInt(hostComponents[1]);
        } else {
            host = hostName;
        }
    }

    /**
     * Build the http request headers from socket input stream, all the necessary headers will be
     * built during this process.
     * @param inputStream
     */
    public void build(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String header = bufferedReader.readLine();
            while (!headerEnds(header)) {
                buildHeader(header);
                header = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            throw new ProxyServerException(READ_HEADER_ERR, ex);
        }
    }

    private boolean headerEnds(String header) {
        return header == null || header.length() == 0;
    }

    private boolean isMethodHeader(String header) {
        return header.startsWith(HTTP_GET) || header.startsWith(HTTP_POST);
    }

    private void buildMethod(String header) {
        String[] components = header.split(" ");
        method = components[0];
        path = components[1];
        version = components[2];
    }

    protected boolean isConnectionHeader(String header) {
        return header.startsWith(CONN_PREFIX) || header.startsWith(PROXY_CONN_PREFIX);
    }
}
