package cache;

import builder.ResponseBuilder;
import exceptions.ProxyServerException;
import request.HttpRequest;
import response.HttpResponse;
import util.HashUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static common.Constants.SHA_256;
import static common.Constants.UTF_8;
import static common.Messages.CREATE_DIR_ERR;
import static common.Messages.NO_ALGORITHM_ERR;
import static common.Messages.NO_FILE_ERR;
import static common.Messages.UNSUPPORTED_ENCODING_ERR;
import static common.Messages.WRITE_RESPONSE_ERR;

/**
 * Disk supported cache.
 * @author Shaofeng Chen
 * @since 2/9/14
 */
public class DiskCache implements Cache {

    private String cacheRoot;
    private ResponseBuilder responseBuilder;

    public DiskCache(String cacheRoot, ResponseBuilder responseBuilder) {
        this.cacheRoot = cacheRoot;
        validateCacheRoot();
        this.responseBuilder = responseBuilder;
    }

    private void validateCacheRoot() {
        if (!cacheRoot.endsWith("/")) {
            cacheRoot += "/";
        }
        if (!pathExists(this.cacheRoot)) {
            if (!createPath(this.cacheRoot)) {
                throw new ProxyServerException(String.format(CREATE_DIR_ERR, this.cacheRoot));
            }
        }
    }

    private boolean createPath(String path) {
        File rootPath = new File(path);
        return rootPath.mkdirs();
    }

    private boolean pathExists(String path) {
        return new File(path).exists();
    }

    @Override
    public boolean isCached(HttpRequest httpRequest) {
        String filePath = getFilePath(httpRequest);
        return pathExists(filePath);
    }

    /**
     * Get the hash encoded file path from http request.
     * @param httpRequest
     * @return String
     */
    private String getFilePath(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
            byte[] hash = messageDigest.digest(uri.getBytes(UTF_8));
            String fileName = HashUtil.bytesToHex(hash);
            return cacheRoot + fileName;
        } catch (NoSuchAlgorithmException ex) {
            throw new ProxyServerException(String.format(NO_ALGORITHM_ERR, SHA_256));
        } catch (UnsupportedEncodingException ex) {
            throw new ProxyServerException(String.format(UNSUPPORTED_ENCODING_ERR, UTF_8));
        }
    }

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        String filePath = getFilePath(httpRequest);
        File file = new File(filePath);
        HttpResponse httpResponse = new HttpResponse(responseBuilder);
        try {
            httpResponse.buildResponse(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            throw new ProxyServerException(String.format(NO_FILE_ERR),
                    ex);
        }
        return httpResponse;
    }

    @Override
    public void add(HttpRequest httpRequest, HttpResponse httpResponse) {
        String filePath = getFilePath(httpRequest);
        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            outputStream.write(httpResponse.getResponse());
        } catch (IOException ex) {
            throw new ProxyServerException(String.format(WRITE_RESPONSE_ERR, filePath), ex);
        }
    }
}
