package cache;

import request.HttpRequest;
import response.HttpResponse;

/**
 * A null cache implementation which means no cache at all.
 * @author Shaofeng Chen
 * @since 2/9/14
 */
public class NullCache implements Cache {

    @Override
    public boolean isCached(HttpRequest httpRequest) {
        return false;
    }

    @Override
    public void add(HttpRequest httpRequest, HttpResponse httpResponse) {}

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return null;
    }
}
