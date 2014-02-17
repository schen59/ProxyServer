package cache;

import request.HttpRequest;
import response.HttpResponse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Class for first in first out cache, constant time access.
 * @author Shaofeng Chen
 * @since 2/9/14
 */
public class FIFOCache implements Cache {

    private Map<HttpRequest, HttpResponse> cache;
    private Queue<HttpRequest> queue;
    private int capacity;

    public FIFOCache(int capacity) {
        cache = new HashMap<HttpRequest, HttpResponse>();
        queue = new LinkedList<HttpRequest>();
        this.capacity = capacity;
    }

    @Override
    public boolean isCached(HttpRequest httpRequest) {
        return cache.containsKey(httpRequest);
    }

    private boolean isFull() {
        return cache.size() == capacity;
    }

    private void remove() {
        HttpRequest httpRequest = queue.remove();
        cache.remove(httpRequest);
    }

    @Override
    public void add(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isFull()) {
            remove();
        }
        cache.put(httpRequest, httpResponse);
        queue.add(httpRequest);
    }

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return cache.get(httpRequest);
    }
}
