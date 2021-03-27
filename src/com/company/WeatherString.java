package com.company;

public class WeatherString {
    String date;
    double temperature;
    double humidity;
    double windSpeed;
    double windDirection;
    String time;

    public WeatherString(String date, String temperature, String humidity, String windSpeed, String windDirection, String time) {
        this.date = date;
        this.temperature = Double.parseDouble(temperature);
        this.humidity = Double.parseDouble(humidity);
        this.windSpeed = Double.parseDouble(windSpeed);
        this.windDirection = Double.parseDouble(windDirection);
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WeatherString{" +
                "date='" + date + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", time='" + time + '\'' +
                '}';
    }
}
