package lab9.model;

import javax.persistence.Entity;

@Entity
public class Rental extends BaseEntity<Long> {
    private int cid;
    private int mid;

    public Rental() {
    }

    public Rental(int cid, int mid) {
        this.cid = cid;
        this.mid = mid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "cid=" + cid +
                ", mid=" + mid +
                "} " + super.toString();
    }
}
