package util;

import exceptions.ProxyServerException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static common.Messages.ACCEPT_SOCK_ERR;
import static common.Messages.CREATE_SERVER_SOCK_ERR;
import static common.Messages.GET_INPUT_STREAM_ERR;
import static common.Messages.GET_OUTPUT_STEAM_ERR;
import static common.Messages.CLOSE_SOCK_ERR;
import static common.Messages.UNKNOWN_HOST_ERR;

/**
 * Utility functions for socket related behavior.
 * @author Shaofeng Chen
 * @since 2/9/14
 */
public class SocketUtil {

    /**
     * Get input stream from socket.
     * @param socket
     * @return InputStream
     */
    public static InputStream getInputStream(Socket socket) {
        try {
            return socket.getInputStream();
        } catch (IOException ex) {
            throw new ProxyServerException(GET_INPUT_STREAM_ERR, ex);
        }
    }

    /**
     * Get output stream from socket.
     * @param socket
     * @return OutputStream
     */
    public static OutputStream getOutputStream(Socket socket) {
        try {
            return socket.getOutputStream();
        } catch (IOException ex) {
            throw new ProxyServerException(GET_OUTPUT_STEAM_ERR, ex);
        }
    }

    /**
     * Close the specified socket.
     * @param socket
     */
    public static void closeSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException ex) {
            throw new ProxyServerException(CLOSE_SOCK_ERR, ex);
        }
    }

    /**
     * Create a socket to remote host on specified port.
     * @param host
     * @param port
     * @return Socket
     */
    public static Socket createSocket(String host, int port) {
        try {
            return new Socket(host, port);
        } catch (UnknownHostException ex) {
            throw new ProxyServerException(String.format(UNKNOWN_HOST_ERR, host), ex);
        } catch (IOException ex) {
            throw new ProxyServerException(ex);
        }
    }

    /**
     * Create server socket on specified port.
     * @param port
     * @return ServerSocket
     */
    public static ServerSocket createServerSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException ex) {
            throw new ProxyServerException(String.format(CREATE_SERVER_SOCK_ERR, port), ex);
        }
    }

    /**
     * Accept a client socket from server socket.
     * @param serverSocket
     * @return Socket
     */
    public static Socket acceptClient(ServerSocket serverSocket) {
        try {
            return serverSocket.accept();
        } catch (IOException ex) {
            throw new ProxyServerException(ACCEPT_SOCK_ERR, ex);
        }
    }
}
