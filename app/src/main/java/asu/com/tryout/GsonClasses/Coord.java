package asu.com.tryout.GsonClasses;

public class Coord {
    double lon;
    double lat;

    public double getLon(){ return lon;}
    public double getLat(){ return  lat;}

    @Override
    public String toString() {
        return lon + " - " + lat;
    }
}
