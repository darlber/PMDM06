package com.example.pmdm06.Sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmdm06.R;

public class Temperature extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private TextView sensorDescription;
    private TextView sensorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorDescription = findViewById(R.id.sensor_description);
        sensorInfo = findViewById(R.id.sensor_info);

        sensorDescription.setText(R.string.temp_desc);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (temperatureSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_TEMPERATURE) {
            float temperature = event.values[0];
            sensorInfo.setText(String.format(getString(R.string.temp_values), temperature));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
