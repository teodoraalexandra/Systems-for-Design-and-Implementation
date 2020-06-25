package socket.common.domain;

import javafx.util.Pair;

public class Transaction extends BaseEntity<Pair<Long, Long>> {
    private int transactionNumber;
    private String transactionCode;
    private String orderDate;

    public Transaction() {
    }

    public Transaction(int transactionNumber, String transactionCode, String orderDate) {
        this.transactionNumber = transactionNumber;
        this.transactionCode = transactionCode;
        this.orderDate = orderDate;
    }

    /**
     * Get Transaction Code.
     *
     * @return the transaction's code
     */
    public String getTransactionCode() {
        return transactionCode;
    }

    /**
     * Set Transaction Code.
     *
     * @param transactionCode
     */
    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    /**
     * Get Order Date.
     *
     * @return the transaction's date
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * Set Order Date.
     *
     * @param orderDate
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Get Transaction Number.
     *
     * @return the transaction's number
     */
    public int getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * Set Transaction Number.
     *
     *
     * @param transactionNumber
     */
    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * toString
     *
     * @return the transaction in String format
     */
    @Override
    public String toString() {
        return "Transaction{ " +
                "Number of transaction=" + transactionNumber + '\'' +
                ", Code='" + transactionCode + '\'' +
                ", Date=" + orderDate +
                "} " + super.toString();
    }
}

