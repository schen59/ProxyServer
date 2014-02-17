package request;

import builder.RequestBuilder;
import builder.ResponseBuilder;
import exceptions.ProxyServerException;
import response.HttpResponse;
import util.SocketUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static common.Constants.HTTP_GET;
import static common.Messages.SEND_TO_HOST_ERR;
import static common.Constants.LOCAL_HOST;

/**
 * Class to deal with http request related behavior.
 * @author Shaofeng Chen
 * @since 2/7/14
 */
public class HttpRequest {
    private RequestBuilder requestBuilder;
    private ResponseBuilder responseBuilder;

    public HttpRequest(RequestBuilder requestBuilder, ResponseBuilder responseBuilder) {
        this.requestBuilder = requestBuilder;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Build http requst from socket input stream.
     * @param inputStream
     */
    public void buildRequest(InputStream inputStream){
        requestBuilder.build(inputStream);
    }

    /**
     * Execute the http request to get actual response from server.
     * @return HttpResponse
     */
    public HttpResponse execute() {
        Socket client = SocketUtil.createSocket(requestBuilder.getHost(), requestBuilder.getPort());
        DataOutputStream outputStream = new DataOutputStream(SocketUtil.getOutputStream(client));
        try {
            outputStream.writeBytes(requestBuilder.getHeadersAsString());
            outputStream.flush();
        } catch (IOException ex) {
            throw new ProxyServerException(String.format(SEND_TO_HOST_ERR, requestBuilder.getHost()), ex);
        }
        HttpResponse response = new HttpResponse(responseBuilder);
        response.buildResponse(SocketUtil.getInputStream(client));
        SocketUtil.closeSocket(client);
        return response;
    }

    /**
     * Get request id to distinguish between different requests.
     * @return String
     */
    public String getRequestId() {
        return requestBuilder.getHeadId();
    }

    /**
     * Get uri(host+path) for the http request.
     * @return String
     */
    public String getUri() {
        return requestBuilder.getUri();
    }

    /**
     * Determine if the http request is get request.
     * @return booolean
     */
    public boolean isGet() {
        return requestBuilder.getMethod().equals(HTTP_GET);
    }

    @Override
    public int hashCode() {
        return getRequestId().hashCode();
    }

    @Override
    public boolean equals(Object httpRequest) {
        if (!(httpRequest instanceof HttpRequest)) {
            return false;
        } else if (httpRequest == this) {
            return true;
        } else {
            return getRequestId().equals(((HttpRequest) httpRequest).getRequestId());
        }
    }

    public boolean isValid() {
        return requestBuilder.getHost().length() != 0 && !requestBuilder.getHost().equals
                (LOCAL_HOST);
    }
}
