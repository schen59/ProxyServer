package cache;

import request.HttpRequest;
import response.HttpResponse;

/**
 * Common interface for cache implementation.
 * @author Shaofeng Chen
 * @since 2/9/14
 */
public interface Cache {

    /**
     * Check if the specified http request has been cached.
     * @param httpRequest
     * @return boolean
     */
    public boolean isCached(HttpRequest httpRequest);

    /**
     * Add the http response for a specified http request into cache.
     * @param httpRequest
     * @param httpResponse
     */
    public void add(HttpRequest httpRequest, HttpResponse httpResponse);

    /**
     * Get the http response for the specified http request from cache.
     * @param httpRequest
     * @return HttpResponse
     */
    public HttpResponse get(HttpRequest httpRequest);
}
