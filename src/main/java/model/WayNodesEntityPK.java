package model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class WayNodesEntityPK implements Serializable {
    private long wayId;
    private int sequenceId;

    @Column(name = "way_id")
    @Id
    public long getWayId() {
        return wayId;
    }

    public void setWayId(long wayId) {
        this.wayId = wayId;
    }

    @Column(name = "sequence_id")
    @Id
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
        int result = (int) (wayId ^ (wayId >>> 32));
        result = 31 * result + sequenceId;
        return result;
    }
}
