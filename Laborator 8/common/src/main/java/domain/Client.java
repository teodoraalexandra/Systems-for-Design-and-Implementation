package domain;
import java.util.Comparator;

public class Client extends BaseEntity<Long> {
    
    private String serialNumber;
    private String name;
    public static Comparator<Client> ClientNameComparator = new Comparator<Client>() {

        public int compare(Client c1, Client c2) {

            String cName1 = c1.getName();
            String cName2 = c2.getName();

            // ascending order
            return cName1.compareTo(cName2);

            // descending order
            // return fruitName2.compareTo(fruitName1);
        }

    };
    public static Comparator<Client> ClientSerialNumberComparator = new Comparator<Client>() {

        public int compare(Client c1, Client c2) {

            String cName1 = c1.getSerialNumber();
            String cName2 = c2.getSerialNumber();

            // ascending order
            return cName1.compareTo(cName2);

            // descending order
            // return fruitName2.compareTo(fruitName1);
        }

    };

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Client Client = (Client) o;

        if (!serialNumber.equals(Client.serialNumber))
            return false;

        return name.equals(Client.name);
    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Client{" + "serialNumber = " + serialNumber + ' ' + ", name = " + name + "} " + super.toString();
    }
}