package model;

import java.util.Date;
import java.util.HashMap;

public class PointGPX {

    private double latitude;
    private double longitude;
    private Date time;
    public HashMap<Long,Double> way_distances = new HashMap<>();
    public PointGPX(double latitude, double longitude, Date time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
