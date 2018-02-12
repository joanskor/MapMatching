package model;

import javax.persistence.*;

@Entity
@Table(name = "way_nodes", schema = "osm", catalog = "osm")
@IdClass(WayNodesEntityPK.class)
public class WayNodesEntity {
    private long wayId;
    private long nodeId;
    private int sequenceId;

    @Id
    @Column(name = "way_id")
    public long getWayId() {
        return wayId;
    }

    public void setWayId(long wayId) {
        this.wayId = wayId;
    }

    @Basic
    @Column(name = "node_id")
    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
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

        WayNodesEntity that = (WayNodesEntity) o;

        if (wayId != that.wayId) return false;
        if (nodeId != that.nodeId) return false;
        if (sequenceId != that.sequenceId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (wayId ^ (wayId >>> 32));
        result = 31 * result + (int) (nodeId ^ (nodeId >>> 32));
        result = 31 * result + sequenceId;
        return result;
    }
}
