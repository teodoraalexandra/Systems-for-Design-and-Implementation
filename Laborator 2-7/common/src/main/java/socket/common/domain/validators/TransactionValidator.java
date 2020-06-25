package socket.common.domain.validators;

import socket.common.domain.Transaction;

public class TransactionValidator implements Validator<Transaction> {
    /**
     * Validates the given transaction entity.
     *
     * @param entity
     *            must not be null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public void validate(Transaction entity) throws ValidatorException {
        if (entity == null) throw new ValidatorException("Transaction entity is null.");

        if (entity.getId().getKey() < 0) throw new ValidatorException("Book id must be a positive number.");
        if (entity.getId().getValue() < 0) throw new ValidatorException("Client id must be a positive number.");
        if (entity.getId().getKey() == null) throw new ValidatorException("Book id is null.");
        if (entity.getId().getValue() == null) throw new ValidatorException("Client id is null.");

        if (entity.getTransactionCode() == null || entity.getTransactionCode().isEmpty()) throw new ValidatorException("Transaction code is null.");
        if (entity.getOrderDate() == null || entity.getOrderDate().isEmpty()) throw new ValidatorException("Transaction date is null.");

        if (entity.getTransactionNumber() < 0) throw new ValidatorException("Transaction number must be greater than 0.");
        if (entity.getTransactionNumber() != (int) entity.getTransactionNumber()) throw new ValidatorException("Transaction number must be of type int.");
    }
}
