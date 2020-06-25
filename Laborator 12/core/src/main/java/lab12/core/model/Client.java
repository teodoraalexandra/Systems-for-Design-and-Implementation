package lab12.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="client")

@NamedEntityGraph(name = "clientWithMovies",
        attributeNodes = @NamedAttributeNode(value = "rentals")
)
public class Client extends BaseEntity<Long>{

    @Column(name="serialnumber")
    private String serialNumber;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rental> rentals;

    //Getters and setters

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

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }
}
