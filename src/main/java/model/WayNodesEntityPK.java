package model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class WayNodesEntityPK implements Serializable {
    private WaysEntity wayId;
    private int sequenceId;
    @Id
    @ManyToOne
    @JoinColumn(name="way_id")
    public WaysEntity getWayId() {
        return wayId;
    }

    public void setWayId(WaysEntity wayId) {
        this.wayId = wayId;
    }
    @Id
    @Column(name = "sequence_id")
    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WayNodesEntityPK that = (WayNodesEntityPK) o;

        if (wayId != that.wayId) return false;
        if (sequenceId != that.sequenceId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (wayId.getId() ^ (wayId.getId() >>> 32));
        result = 31 * result + sequenceId;
        return result;
    }
}
