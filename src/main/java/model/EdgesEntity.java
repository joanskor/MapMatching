package model;

import com.vividsolutions.jts.geom.LineString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "osm_2po_4pgr", schema = "osm", catalog = "osm")
public class EdgesEntity {
    private int id;
    private Long osmId;
    private String osmName;
    private String osmMeta;
    private Long osmSourceId;
    private Long osmTargetId;
    private Integer clazz;
    private Integer flags;
    private Integer source;
    private Integer target;
    private Double km;
    private Integer kmh;
    private Double cost;
    private Double reverseCost;
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
    private LineString geomWay;
    @Transient
    public Double distanceCost=0d;
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "osm_id", nullable = true)
    public Long getOsmId() {
        return osmId;
    }

    public void setOsmId(Long osmId) {
        this.osmId = osmId;
    }

    @Basic
    @Column(name = "osm_name", nullable = true, length = -1)
    public String getOsmName() {
        return osmName;
    }

    public void setOsmName(String osmName) {
        this.osmName = osmName;
    }

    @Basic
    @Column(name = "osm_meta", nullable = true, length = -1)
    public String getOsmMeta() {
        return osmMeta;
    }

    public void setOsmMeta(String osmMeta) {
        this.osmMeta = osmMeta;
    }

    @Basic
    @Column(name = "osm_source_id", nullable = true)
    public Long getOsmSourceId() {
        return osmSourceId;
    }

    public void setOsmSourceId(Long osmSourceId) {
        this.osmSourceId = osmSourceId;
    }

    @Basic
    @Column(name = "osm_target_id", nullable = true)
    public Long getOsmTargetId() {
        return osmTargetId;
    }

    public void setOsmTargetId(Long osmTargetId) {
        this.osmTargetId = osmTargetId;
    }

    @Basic
    @Column(name = "clazz", nullable = true)
    public Integer getClazz() {
        return clazz;
    }

    public void setClazz(Integer clazz) {
        this.clazz = clazz;
    }

    @Basic
    @Column(name = "flags", nullable = true)
    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    @Basic
    @Column(name = "source", nullable = true)
    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    @Basic
    @Column(name = "target", nullable = true)
    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    @Basic
    @Column(name = "km", nullable = true, precision = 0)
    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    @Basic
    @Column(name = "kmh", nullable = true)
    public Integer getKmh() {
        return kmh;
    }

    public void setKmh(Integer kmh) {
        this.kmh = kmh;
    }

    @Basic
    @Column(name = "cost", nullable = true, precision = 0)
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "reverse_cost", nullable = true, precision = 0)
    public Double getReverseCost() {
        return reverseCost;
    }

    public void setReverseCost(Double reverseCost) {
        this.reverseCost = reverseCost;
    }

    @Basic
    @Column(name = "x1", nullable = true, precision = 0)
    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    @Basic
    @Column(name = "y1", nullable = true, precision = 0)
    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    @Basic
    @Column(name = "x2", nullable = true, precision = 0)
    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    @Basic
    @Column(name = "y2", nullable = true, precision = 0)
    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }

    @Basic
    @Column(name = "geom_way", nullable = true)
    public LineString getGeomWay() {
        return geomWay;
    }

    public void setGeomWay(LineString geomWay) {
        this.geomWay = geomWay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgesEntity that = (EdgesEntity) o;

        if (id != that.id) return false;
        if (osmId != null ? !osmId.equals(that.osmId) : that.osmId != null) return false;
        if (osmName != null ? !osmName.equals(that.osmName) : that.osmName != null) return false;
        if (osmMeta != null ? !osmMeta.equals(that.osmMeta) : that.osmMeta != null) return false;
        if (osmSourceId != null ? !osmSourceId.equals(that.osmSourceId) : that.osmSourceId != null) return false;
        if (osmTargetId != null ? !osmTargetId.equals(that.osmTargetId) : that.osmTargetId != null) return false;
        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) return false;
        if (flags != null ? !flags.equals(that.flags) : that.flags != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        if (km != null ? !km.equals(that.km) : that.km != null) return false;
        if (kmh != null ? !kmh.equals(that.kmh) : that.kmh != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (reverseCost != null ? !reverseCost.equals(that.reverseCost) : that.reverseCost != null) return false;
        if (x1 != null ? !x1.equals(that.x1) : that.x1 != null) return false;
        if (y1 != null ? !y1.equals(that.y1) : that.y1 != null) return false;
        if (x2 != null ? !x2.equals(that.x2) : that.x2 != null) return false;
        if (y2 != null ? !y2.equals(that.y2) : that.y2 != null) return false;
        if (geomWay != null ? !geomWay.equals(that.geomWay) : that.geomWay != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (osmId != null ? osmId.hashCode() : 0);
        result = 31 * result + (osmName != null ? osmName.hashCode() : 0);
        result = 31 * result + (osmMeta != null ? osmMeta.hashCode() : 0);
        result = 31 * result + (osmSourceId != null ? osmSourceId.hashCode() : 0);
        result = 31 * result + (osmTargetId != null ? osmTargetId.hashCode() : 0);
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        result = 31 * result + (flags != null ? flags.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (km != null ? km.hashCode() : 0);
        result = 31 * result + (kmh != null ? kmh.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (reverseCost != null ? reverseCost.hashCode() : 0);
        result = 31 * result + (x1 != null ? x1.hashCode() : 0);
        result = 31 * result + (y1 != null ? y1.hashCode() : 0);
        result = 31 * result + (x2 != null ? x2.hashCode() : 0);
        result = 31 * result + (y2 != null ? y2.hashCode() : 0);
        result = 31 * result + (geomWay != null ? geomWay.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EdgesEntity{" +
                "id=" + id +
                ", osmId=" + osmId +
                ", osmName='" + osmName + '\'' +
                ", osmMeta='" + osmMeta + '\'' +
                ", osmSourceId=" + osmSourceId +
                ", osmTargetId=" + osmTargetId +
                ", clazz=" + clazz +
                ", flags=" + flags +
                ", source=" + source +
                ", target=" + target +
                ", km=" + km +
                ", kmh=" + kmh +
                ", cost=" + cost +
                ", reverseCost=" + reverseCost +
                ", x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", geomWay=" + geomWay +
                '}';
    }

    public void calculateCost(List<PointGPX> points){
        ArrayList<Double> distances = new ArrayList<>();
        for (PointGPX point : points){
            if (point.way_distances.get(this.getOsmId())!=null){
                distances.add(point.way_distances.get(this.getOsmId()));
            }
        }
        Double sum=0d;
        for(Double d : distances){
            sum+=d;
        }
        this.distanceCost=sum/distances.size();
    }
}
