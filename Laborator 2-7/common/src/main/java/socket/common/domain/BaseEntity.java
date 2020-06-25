package socket.common.domain;

public class BaseEntity<ID> {
    private ID id;

    /**
     * Get ID.
     *
     * @return the entity's id
     */
    public ID getId() {
        return id;
    }

    /**
     * Set ID.
     *
     * @param id
     */
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * toString
     *
     * @return the entity in String format
     */
    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
