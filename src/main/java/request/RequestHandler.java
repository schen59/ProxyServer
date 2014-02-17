package request;

import builder.RequestBuilder;
import builder.ResponseBuilder;
import core.ProxyServer;
import exceptions.ProxyServerException;
import response.HttpResponse;
import util.SocketUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import static common.Messages.REPLY_CLIENT_ERR;

/**
 * Class to handle http request which supports multi thread.
 * @author Shaofeng Chen
 * @since 2/8/14
 */
public class RequestHandler extends Thread {

    private Socket client;
    private ProxyServer proxyServer;
    private static Logger logger = Logger.getLogger(RequestHandler.class.getName());

    public RequestHandler(Socket client, ProxyServer proxyServer) {
        this.client = client;
        this.proxyServer = proxyServer;
    }

    @Override
    public void run() {
        RequestBuilder requestBuilder = new RequestBuilder();
        ResponseBuilder responseBuilder = new ResponseBuilder();
        HttpRequest httpRequest = new HttpRequest(requestBuilder, responseBuilder);
        httpRequest.buildRequest(SocketUtil.getInputStream(client));
        logger.info(String.format("Handle request: %s.", httpRequest.getUri()));
        if (httpRequest.isValid() && httpRequest.isGet()) {
            HttpResponse httpResponse = getResponse(httpRequest);
            replyToClient(httpResponse);
        }
        SocketUtil.closeSocket(client);
    }

    /**
     * Get the http response from cache or remote server.
     * @param httpRequest
     * @return HttpResponse
     */
    private HttpResponse getResponse(HttpRequest httpRequest) {
        HttpResponse httpResponse;
        if (proxyServer.isCached(httpRequest)) {
            logger.warning(String.format("Served request %s from cache.", httpRequest.getUri()));
            httpResponse = proxyServer.getCache(httpRequest);
        } else {
            httpResponse = httpRequest.execute();
            proxyServer.addCache(httpRequest, httpResponse);
        }
        return httpResponse;
    }

    /**
     * Return the http response to original socket client.
     * @param httpResponse
     */
    private void replyToClient(HttpResponse httpResponse) {
        try {
            DataOutputStream outputStream = new DataOutputStream(SocketUtil.getOutputStream
                    (client));
            outputStream.write(httpResponse.getResponse());
        } catch (IOException ex) {
            throw new ProxyServerException(REPLY_CLIENT_ERR, ex);
        }
    }
}
