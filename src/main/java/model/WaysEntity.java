package model;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "ways", schema = "osm", catalog = "osm")
public class WaysEntity {
    private long id;
    private int userId;
    private Timestamp tstamp;
    private long changesetId;
    private List<NodesEntity> nodes;
    private Polygon bbox;
    private LineString linestring;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "tstamp")
    public Timestamp getTstamp() {
        return tstamp;
    }

    public void setTstamp(Timestamp tstamp) {
        this.tstamp = tstamp;
    }

    @Basic
    @Column(name = "changeset_id")
    public long getChangesetId() {
        return changesetId;
    }

    public void setChangesetId(long changesetId) {
        this.changesetId = changesetId;
    }

    @OneToMany
    @Column(name = "nodes")
    public List<NodesEntity> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodesEntity> nodes) {
        this.nodes = nodes;
    }

    @Basic
    @Column(name = "bbox")
    public Polygon getBbox() {
        return bbox;
    }

    public void setBbox(Polygon bbox) {
        this.bbox = bbox;
    }

    @Basic
    @Column(name = "linestring")
    public LineString getLinestring() {
        return linestring;
    }

    public void setLinestring(LineString linestring) {
        this.linestring = linestring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaysEntity that = (WaysEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (changesetId != that.changesetId) return false;
        if (tstamp != null ? !tstamp.equals(that.tstamp) : that.tstamp != null) return false;
        if (nodes != null ? !nodes.equals(that.nodes) : that.nodes != null) return false;
        if (bbox != null ? !bbox.equals(that.bbox) : that.bbox != null) return false;
        if (linestring != null ? !linestring.equals(that.linestring) : that.linestring != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + userId;
        result = 31 * result + (tstamp != null ? tstamp.hashCode() : 0);
        result = 31 * result + (int) (changesetId ^ (changesetId >>> 32));
        result = 31 * result + (nodes != null ? nodes.hashCode() : 0);
        result = 31 * result + (bbox != null ? bbox.hashCode() : 0);
        result = 31 * result + (linestring != null ? linestring.hashCode() : 0);
        return result;
    }
}
