package exceptions;

/**
 * Customized exception class.
 * @author Shaofeng Chen
 * @since 2/7/14
 */
public class ProxyServerException extends RuntimeException {

    public ProxyServerException(String message) {
        super(message);
    }

    public ProxyServerException(String message, Exception ex) {
        super(message, ex);
    }

    public ProxyServerException(Exception ex) {
        super(ex);
    }
}
