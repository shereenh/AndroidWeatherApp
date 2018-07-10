package asu.com.tryout;

public class WeatherLog {

    //public Weather weather;
    String mainW;
    String descriptionW;
    //public Main main;
    double temperatureM;
    double pressureM;
    double humidityM;
    //public Wind wind;
    double speedW;
    //public Sys sys;
    String countryS;
    String city;
    int iconValue;


    public WeatherLog(String mainW,double temperatureM,double pressureM, double humidityM,
                    double speedW, String countryS,String city,int iconValue){

        this.mainW = mainW;
        this.descriptionW = descriptionW;
        this.temperatureM = temperatureM;
        this.pressureM = pressureM;
        this.humidityM = humidityM;
        this.speedW = speedW;
        this.countryS = countryS;
        this.city = city;
        this.iconValue = iconValue;

    }
    public String getMainW() {
        return mainW;
    }

    public void setMainW(String mainW) {
        this.mainW = mainW;
    }

    public String getDescriptionW() {
        return descriptionW;
    }

    public void setDescriptionW(String descriptionW) {
        this.descriptionW = descriptionW;
    }

    public double getTemperatureM() {
        return temperatureM;
    }

    public void setTemperatureM(double temperatureM) {
        this.temperatureM = temperatureM;
    }

    public double getPressureM() {
        return pressureM;
    }

    public void setPressureM(double pressureM) {
        this.pressureM = pressureM;
    }

    public double getHumidityM() {
        return humidityM;
    }

    public void setHumidityM(double humidityM) {
        this.humidityM = humidityM;
    }

    public double getSpeedW() {
        return speedW;
    }

    public void setSpeedW(double speedW) {
        this.speedW = speedW;
    }

    public String getCountryS() {
        return countryS;
    }

    public void setCountryS(String countryS) {
        this.countryS = countryS;
    }

    public int getIconValue(){
        return iconValue;
    }

    public String getCity() {
        return city;
    }
}
