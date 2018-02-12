package model;

import com.sun.scenario.effect.light.PointLight;
import org.hibernate.annotations.Type;
import org.postgis.Geometry;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;

@Entity
@Table(name = "nodes", schema = "osm", catalog = "osm")
public class NodesEntity {
    private long id;
    private int version;
    private int userId;
    private Timestamp tstamp;
    private long changesetId;
    @Type(type = "jts_geometry")
    private Point geom;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "version", nullable = false)
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "tstamp", nullable = false)
    public Timestamp getTstamp() {
        return tstamp;
    }

    public void setTstamp(Timestamp tstamp) {
        this.tstamp = tstamp;
    }

    @Basic
    @Column(name = "changeset_id", nullable = false)
    public long getChangesetId() {
        return changesetId;
    }

    public void setChangesetId(long changesetId) {
        this.changesetId = changesetId;
    }


    public Point getGeom() {
        return geom;
    }

    public void setGeom(Point geom){
        this.geom = geom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodesEntity that = (NodesEntity) o;

        if (id != that.id) return false;
        if (version != that.version) return false;
        if (userId != that.userId) return false;
        if (changesetId != that.changesetId) return false;
        if (tstamp != null ? !tstamp.equals(that.tstamp) : that.tstamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + version;
        result = 31 * result + userId;
        result = 31 * result + (tstamp != null ? tstamp.hashCode() : 0);
        result = 31 * result + (int) (changesetId ^ (changesetId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        String result="";
        try{
            result="NodesEntity{" +
                    "id=" + id +
                    ", version=" + version +
                    ", userId=" + userId +
                    ", tstamp=" + tstamp +
                    ", changesetId=" + changesetId +
                    ", geom='" + geom + '\'' +
                    '}';
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
