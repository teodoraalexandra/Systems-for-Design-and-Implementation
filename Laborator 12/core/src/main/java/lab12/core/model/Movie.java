package lab12.core.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Table(name="movie")

@NamedEntityGraph(name = "movieWithClients",
        attributeNodes = @NamedAttributeNode(value = "rentals")
)
public class Movie extends BaseEntity<Long>{

    @Column(name="serialnumber")
    private String serialNumber;

    @NaturalId
    @Column(name="title")
    private String title;

    @Column(name="director")
    private String director;

    @Column(name="duration")
    private int duration;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rental> rentals = new HashSet<>();

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }
}
