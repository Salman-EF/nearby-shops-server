/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.models;

import java.util.List;

public class Location {

    private String type;
    private List<Long> coordinates;

    public Location(String type, List<Long> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public List<Long> getCoordinates() {return coordinates;}
    public void setCoordinates(List<Long> coordinates) {this.coordinates = coordinates;}
}
