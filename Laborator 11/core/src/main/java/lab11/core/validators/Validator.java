package lab11.core.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
