package socket.common;

public class TransactionServiceException extends RuntimeException {

    public TransactionServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionServiceException(Throwable cause) {
        super(cause);
    }
}
