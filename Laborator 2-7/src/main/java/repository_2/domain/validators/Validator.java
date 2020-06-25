package repository_2.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
