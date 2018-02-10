package model;

public class CoordinatesModel {

    double latitude;
    double longitude;
    double altitude;

    public CoordinatesModel(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getAltitude() {
        return this.altitude;
    }
}
