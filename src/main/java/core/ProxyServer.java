package core;

import cache.Cache;
import cache.FIFOCache;
import exceptions.ProxyServerException;
import request.HttpRequest;
import request.RequestHandler;
import response.HttpResponse;
import util.SocketUtil;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import static common.Messages.NEED_PORT_ARG_INFO;
import static common.Messages.PORT_NUMBER_FMT_ERR;

/**
 * Class for multi-threaded proxy server for http request.
 * @author Shaofeng Chen
 * @since 2/8/14
 */
public class ProxyServer {
    private final int port;
    private ServerSocket serverSocket;
    private final Cache cache;
    private static Logger logger = Logger.getLogger(ProxyServer.class.getName());

    public ProxyServer(int port, Cache cache) {
        this.port = port;
        this.cache = cache;
        serverSocket = SocketUtil.createServerSocket(port);
    }

    public synchronized boolean isCached(HttpRequest httpRequest) {
        return cache.isCached(httpRequest);
    }

    public synchronized HttpResponse getCache(HttpRequest httpRequest) {
        return cache.get(httpRequest);
    }

    public synchronized void addCache(HttpRequest httpRequest, HttpResponse httpResponse) {
        cache.add(httpRequest, httpResponse);
    }

    /**
     * Handle a http request from client.
     * @param client
     */
    private void handle(Socket client) {
        RequestHandler requestHandler = new RequestHandler(client, this);
        requestHandler.start();
    }

    public void run() {
        logger.info("Proxy server running on port:" + port);
        while (true) {
            Socket client = SocketUtil.acceptClient(serverSocket);
            handle(client);
        }
    }

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            Cache cache = new FIFOCache(1000);
            //Cache cache = new DiskCache("tmp/", new ResponseBuilder());
            //Cache cache = new NullCache();
            ProxyServer proxyServer = new ProxyServer(port, cache);
            proxyServer.run();
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ProxyServerException(NEED_PORT_ARG_INFO, ex);
        } catch (NumberFormatException ex) {
            throw new ProxyServerException(PORT_NUMBER_FMT_ERR, ex);
        }
    }
}
