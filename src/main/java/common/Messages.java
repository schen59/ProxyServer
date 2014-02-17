package common;

/**
 * Class for common messages used.
 * @author Shaofeng Chen
 * @since 2/9/14
 */
public class Messages {
    public static final String READ_HEADER_ERR = "Error while reading headers from input stream.";
    public static final String READ_BODY_ERR = "Error while reading response body from input " +
            "stream.";
    public static final String CREATE_SERVER_SOCK_ERR = "Error creating server socket at port: %s.";
    public static final String ACCEPT_SOCK_ERR = "Error accepting socket from client.";
    public static final String NEED_PORT_ARG_INFO = "Need port number as argument.";
    public static final String PORT_NUMBER_FMT_ERR = "Please give port number as integer.";
    public static final String REPLY_CLIENT_ERR = "Error replying back to client.";
    public static final String SEND_TO_HOST_ERR = "Error sending request to host: %s.";
    public static final String GET_INPUT_STREAM_ERR = "Error getting input stream from socket.";
    public static final String GET_OUTPUT_STEAM_ERR = "Error getting output stream from socket.";
    public static final String CLOSE_SOCK_ERR = "Error closing socket.";
    public static final String UNKNOWN_HOST_ERR = "Error creating socket to unknown host: %s.";
    public static final String CREATE_DIR_ERR = "Error creating directories on path: %s.";
    public static final String NO_ALGORITHM_ERR = "No such algorithm %s exists.";
    public static final String UNSUPPORTED_ENCODING_ERR = "Unsupported encoding %s.";
    public static final String NO_FILE_ERR = "File does not exist on path: %s.";
    public static final String WRITE_RESPONSE_ERR = "Error writing response to file path: %s.";
}
