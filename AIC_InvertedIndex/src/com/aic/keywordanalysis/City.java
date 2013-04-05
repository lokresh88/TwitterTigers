package com.aic.keywordanalysis;

public class City {

    private String cityName;
    private double longitude, latitude;

    public City(String cityString, double latitude, double longitude) {
        this.cityName = cityString;
        this.longitude = longitude;
        this.latitude = latitude;
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
        long temp;
        
        // convert double to nearest Int
        //int l = (int)longitude;
        
        
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        if (Double.doubleToLongBits(latitude) != Double
                .doubleToLongBits(other.latitude))
            return false;
        if (Double.doubleToLongBits(longitude) != Double
                .doubleToLongBits(other.longitude))
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
    

}
