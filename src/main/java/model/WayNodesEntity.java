package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "way_nodes", schema = "osm", catalog = "osm")
@IdClass(WayNodesEntityPK.class)
public class WayNodesEntity {

    private WaysEntity wayId;

    private NodesEntity nodeId;

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


    @ManyToOne
    @JoinColumn(name = "node_id")
    public NodesEntity getNodeId() {
        return nodeId;
    }

    public void setNodeId(NodesEntity nodeId) {
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
        return sequenceId == that.sequenceId &&
                Objects.equals(wayId, that.wayId) &&
                Objects.equals(nodeId, that.nodeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(wayId, nodeId, sequenceId);
    }

    @Override
    public String toString() {
        return "WayNodesEntity{" +
                "nodeId=" + nodeId +
                '}';
    }
}
