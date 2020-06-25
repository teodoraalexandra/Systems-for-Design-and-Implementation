package lab11.core.validators;

import lab11.core.model.Movie;

public class MovieValidator implements Validator<Movie> {
    /**
     * Validates the given movie entity.
     *
     * @param entity
     *            must not be null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public void validate(Movie entity) throws ValidatorException {
        if (entity == null) throw new ValidatorException("Entity is null.");

        if (entity.getId() < 0) throw new ValidatorException("Id must be a positive number.");
        if (entity.getId() == null) throw new ValidatorException("Id is null.");

        if (entity.getSerialNumber() == null || entity.getSerialNumber().isEmpty()) throw new ValidatorException("Serial number is null.");
        if (entity.getTitle() == null || entity.getTitle().isEmpty()) throw new ValidatorException("Title is null.");
        if (entity.getDirector() == null || entity.getDirector().isEmpty()) throw new ValidatorException("Director is null");

        if (entity.getDuration() < 0) throw new ValidatorException("Duration must be greater than 0.");
        if (entity.getDuration() != (int) entity.getDuration()) throw new ValidatorException("Duration must be of type int.");
    }
}
