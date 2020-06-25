package repository_2.domain.validators;

import repository_2.domain.Client;

public class ClientValidator implements Validator<Client> {
    /**
     * Validates the given client.
     *
     * @param entity
     *            must not be null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public void validate(Client entity) throws ValidatorException {
        if (entity == null) throw new ValidatorException("Client entity is null.");

        if (entity.getId() < 0) throw new ValidatorException("Client id must be a positive number.");
        if (entity.getId() == null) throw new ValidatorException("Client id is null");

        if (entity.getFirstName() == null || entity.getFirstName().isEmpty()) throw new ValidatorException("Client first name is null.");
        if (entity.getLastName() == null || entity.getLastName().isEmpty()) throw new ValidatorException("Client last name is null.");

        if (entity.getAge() < 0) throw new ValidatorException("Client age must be greater than 0.");
        if (entity.getAge() != (int) entity.getAge()) throw new ValidatorException("Client age must be of type int.");
    }
}
