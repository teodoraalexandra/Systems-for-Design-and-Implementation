package lab12.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental extends BaseEntity<Long>{

    //optional = false
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientid")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieid")
    private Movie movie;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}

