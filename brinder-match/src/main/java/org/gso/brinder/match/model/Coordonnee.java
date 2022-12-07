package org.gso.brinder.match.model;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

public class Coordonnee {
    @GeoSpatialIndexed
    private int[] location;

    public Coordonnee(int longitude, int latitude){
        location[0] = longitude;
        location[1] = latitude;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
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
