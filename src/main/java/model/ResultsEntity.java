package model;

import com.vividsolutions.jts.geom.LineString;

import javax.persistence.*;

@Entity
@Table(name = "results", schema = "osm", catalog = "osm")
public class ResultsEntity {

    private String filename;
    private LineString path;

    @Id
    @Column(name = "filename", nullable = true, length = 200)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Column(name = "path", nullable = true)
    public LineString getPath() {
        return path;
    }

    public void setPath(LineString path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultsEntity that = (ResultsEntity) o;

        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filename != null ? filename.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }

    public ResultsEntity(String filename, LineString path) {
        this.filename = filename;
        this.path = path;
    }
    public ResultsEntity(){};
}
