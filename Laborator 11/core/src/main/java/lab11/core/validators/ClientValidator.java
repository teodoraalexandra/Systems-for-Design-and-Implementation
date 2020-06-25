package lab11.core.validators;

import lab11.core.model.Client;

public class ClientValidator implements Validator<Client> {
    /**
     * Validates the given client entity.
     *
     * @param entity
     *            must not be null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public void validate(Client entity) throws ValidatorException {
        if (entity == null) throw new ValidatorException("Entity is null.");

        if (entity.getId() < 0) throw new ValidatorException("Id must be a positive number.");
        if (entity.getId() == null) throw new ValidatorException("Id is null.");

        if (entity.getSerialNumber() == null || entity.getSerialNumber().isEmpty()) throw new ValidatorException("Serial number is null.");
        if (entity.getName() == null || entity.getName().isEmpty()) throw new ValidatorException("Name is null.");
    }
}
