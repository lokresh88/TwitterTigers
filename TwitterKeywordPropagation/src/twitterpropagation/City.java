package twitterpropagation;

public class City {

    private String cityName;
    private double longitude, latitude;
    private Integer longitudeI, latitudeI;

    public City(String cityString, double latitude, double longitude) {
        this.cityName = cityString;
        this.longitude = longitude;
        this.latitude = latitude;
        this.latitudeI = (int)latitude;
        this.longitudeI = (int)longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((latitudeI == null) ? 0 : latitudeI.hashCode());
        result = prime * result
                + ((longitudeI == null) ? 0 : longitudeI.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        City other = (City) obj;
        if (latitudeI == null) {
            if (other.latitudeI != null)
                return false;
        } else if (!latitudeI.equals(other.latitudeI))
            return false;
        if (longitudeI == null) {
            if (other.longitudeI != null)
                return false;
        } else if (!longitudeI.equals(other.longitudeI))
            return false;
        return true;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public Integer getLatitudeI() {
        return latitudeI;
    }
    
    public Integer getLongitudeI() {
        return longitudeI;
    }
    

}
