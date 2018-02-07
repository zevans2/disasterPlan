public class Place {
    String name;
    String region;
    String country;
    float latitude;
    float longitude;
    int population;
    double distanceFromOrigin;
    String zipcode;
    String zipType;

    public Place(String name, String region, String country, double latitude, double longitude, int population, String zipcode, String zipType){
        this.name = name;
        this.region = region;
        this.country =country;
        this.latitude= (float) latitude;
        this.longitude= (float) longitude;
        this.population=population;
        this.zipcode=zipcode;
        this.zipType=zipType;
        distanceFromOrigin = -99;

    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipType() {
        return zipType;
    }

    public void setZipType(String zipType) {
        this.zipType = zipType;
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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
                ", population="+population+
                ", zipcode=" + zipcode +
                ", ziptype=" + zipType +
                ", distanceFromOrigin=" + distanceFromOrigin +
                ')';
    }//end toString

}
