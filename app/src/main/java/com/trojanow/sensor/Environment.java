package com.trojanow.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;

/**
 * Created by pabloivan57 on 3/27/15.
 */
public class Environment implements SensorEventListener {

    private Activity context;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private EnvironmentInfo environmentInfo;

    public Environment(Activity context) {
        this.context = context;

        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        environmentInfo = new EnvironmentInfo();
    }

    /**
     *
     * @return enviromentInfo
     * Returns an object encapsulating enviromental info retrieved from the device
     */
    public EnvironmentInfo getEnviromentInfo(){
        return this.environmentInfo;
    }

    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == temperatureSensor) {
            System.out.println("checktemp"+event.values[0]);
            environmentInfo.setTemperature(new Double(event.values[0]));
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
