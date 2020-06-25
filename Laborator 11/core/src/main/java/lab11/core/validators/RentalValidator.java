package lab11.core.validators;

import lab11.core.model.Rental;

public class RentalValidator implements Validator<Rental> {
    /**
     * Validates the given rental entity.
     *
     * @param entity
     *            must not be null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public void validate(Rental entity) throws ValidatorException {
        if (entity == null) throw new ValidatorException("Entity is null.");

        if (entity.getId() < 0) throw new ValidatorException("Id must be a positive number.");
        if (entity.getId() == null) throw new ValidatorException("Id is null.");

        if (entity.getClientId() < 0) throw new ValidatorException("Client id must be greater than 0.");
        if (entity.getClientId() != (int) entity.getClientId()) throw new ValidatorException("Client id must be of type int.");

        if (entity.getMovieId() < 0) throw new ValidatorException("Movie id must be greater than 0.");
        if (entity.getMovieId() != (int) entity.getMovieId()) throw new ValidatorException("Movie id must be of type int.");
    }
}
