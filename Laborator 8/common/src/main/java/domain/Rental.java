package domain;

import java.util.Comparator;

public class Rental extends BaseEntity<Long> {
    private String MID;
    private String CID;
    public static Comparator<Rental> RentalMovieComparator = new Comparator<Rental>() {

        public int compare(Rental c1, Rental c2) {

            String cName1 = c1.getMovieId();
            String cName2 = c2.getMovieId();

            // ascending order
            return cName1.compareTo(cName2);

            // descending order
            // return fruitName2.compareTo(fruitName1);
        }

    };

    public static Comparator<Rental> RentalClientComparator = new Comparator<Rental>() {

        public int compare(Rental c1, Rental c2) {

            String cName1 = c1.getClientId();
            String cName2 = c2.getClientId();

            // ascending order
            return cName1.compareTo(cName2);

            // descending order
            // return fruitName2.compareTo(fruitName1);
        }

    };

    public Rental(String CID, String MID) {
        this.CID = CID;
        this.MID = MID;
    }

    public String getMovieId() {
        return MID;
    }

    public void setMovieId(String MID) {
        this.MID = MID;
    }

    public String getClientId() {
        return CID;
    }

    public void setClientId(String CID) {
        this.CID = CID;
    }
    public String getRentalId(){
        return this.MID + this.CID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Rental assign = (Rental) o;

        if (!MID.equals(assign.MID))
            return false;

        return CID.equals(assign.CID);
    }

    @Override
    public int hashCode() {
        int result = MID != null ? MID.hashCode() : 0;
        result = 31 * result + (CID != null ? CID.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rental{" + "CID = " + CID + ", MID = " + MID + "} " + super.toString();
    }

}