package lab9.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}