package lab9.model;

import javax.persistence.Entity;

@Entity
public class Client extends BaseEntity<Long> {
    private String serialNumber;
    private String name;

    public Client() {
    }

    public Client(String serialNumber, String name) {
        this.serialNumber = serialNumber;
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name=" + name +
                "} " + super.toString();
    }
}
