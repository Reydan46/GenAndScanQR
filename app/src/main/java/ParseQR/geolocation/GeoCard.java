package ParseQR.geolocation;

public class GeoCard {

    private double lat;
    private double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String buildString() {

        String geoCardString = GeoCostant.KEY_GEO +
                lat +
                "," +
                lon;
        return geoCardString;

    }
}
