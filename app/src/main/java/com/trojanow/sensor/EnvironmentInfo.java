package com.trojanow.sensor;

/**
 * Created by pabloivan57 on 3/27/15.
 */
public class EnvironmentInfo {

    private Double humidity;
    private Double temperature;

    public EnvironmentInfo() {
        humidity = 0.0;
        temperature = 0.0;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
