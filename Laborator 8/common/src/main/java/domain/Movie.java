package domain;

import java.io.Serializable;

public class Movie extends BaseEntity<Long>  {
    private String serial;
    private  String title;
    private  String director;
    private  int duration;

    public Movie( String serial,  String title,  String director,  int duration) {
        this.serial = serial;
        this.title = title;
        this.director = director;
        this.duration = duration;
    }


    public String getSerial() {
        return serial;
    }

    public void setSerial( String description) {
        this.serial = description;
    }
    public String getDirector() {
        return director;
    }
    public String getTitle() {
        return title;
    }
    public int getDuration() {
        return duration;
    }
    public void setTitle( String description) {
        this.title = description;
    }
    public void setDirector( String description) {
        this.director = description;
    }
    public void setDuration( int description) {
        this.duration = description;
    }

    @Override
    public boolean equals( Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Movie movie = (Movie) o;


        return serial.equals(movie.serial);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + serial.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Movie{ serial number = " + serial + ", title = " + title + ", diretor = " + director + ", duration = "+ Integer.toString(duration) + "} " + super.toString();
    }
}