package socket.common;

public class ClientServiceException extends RuntimeException {

    public ClientServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientServiceException(Throwable cause) {
        super(cause);
    }
}
