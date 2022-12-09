package org.gso.brinder.match.model;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

public class Coordonnee {
    @GeoSpatialIndexed
    private double[] location;

    public Coordonnee(double longitude, double latitude){
        location = new double[2];
        location[0] = longitude;
        location[1] = latitude;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    @Override
    public String toString() {
        String str="";
        str = str + "longitude: " + location[0] + ' ';
        str = str + "latitude: " + location[1];
        return  str;
    }
}
