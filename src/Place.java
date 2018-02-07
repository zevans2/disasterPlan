public class Place {
    String name;
    String region;
    String country;
    double latitude;
    double longitude;
    double distanceFromOrigin;

    public Place(String name, String region, String country, double latitude, double longitude){
        this.name = name;
        this.region = region;
        this.country =country;
        this.latitude=latitude;
        this.longitude=longitude;
        distanceFromOrigin = -99;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public double getDistanceFromOrigin() {
        return distanceFromOrigin;
    }

    public void setDistanceFromOrigin(double distanceFromOrigin) {
        this.distanceFromOrigin = distanceFromOrigin;
    }

    @Override
    public String toString(){
        return "Place{"+
                "name='"+name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", latitude="+latitude +
                ", longitude="+longitude+
                ", distanceFromOrigin=" + distanceFromOrigin +
                ')';
    }//end toString

}
