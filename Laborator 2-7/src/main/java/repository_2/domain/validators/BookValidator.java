package repository_2.domain.validators;

import repository_2.domain.Book;

public class BookValidator implements Validator<Book> {
    /**
     * Validates the given book entity.
     *
     * @param entity
     *            must not be null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public void validate(Book entity) throws ValidatorException {
        if (entity == null) throw new ValidatorException("Book entity is null.");

        if (entity.getId() < 0) throw new ValidatorException("Book id must be a positive number.");
        if (entity.getId() == null) throw new ValidatorException("Book id is null.");

        if (entity.getTitle() == null || entity.getTitle().isEmpty()) throw new ValidatorException("Book title is null.");
        if (entity.getAuthor() == null || entity.getAuthor().isEmpty()) throw new ValidatorException("Book author is null.");

        if (entity.getPrice() < 0) throw new ValidatorException("Book price must be greater than 0.");
        if (entity.getPrice() != (int) entity.getPrice()) throw new ValidatorException("Book price must be of type int.");
    }
}
