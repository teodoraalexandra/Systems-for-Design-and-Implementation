package lab11.core.validators;

public class MovieRentalException extends RuntimeException {
    public MovieRentalException(String message) {
        super(message);
    }

    public MovieRentalException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieRentalException(Throwable cause) {
        super(cause);
    }
}
