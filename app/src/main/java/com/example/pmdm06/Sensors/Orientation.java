package com.example.pmdm06.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmdm06.R;

public class Orientation extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor orientationSensor;
    private TextView sensorInfo, sensorDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorDescription = findViewById(R.id.sensor_description);
        sensorDescription.setText("Mide la orientación del dispositivo.");

        sensorInfo = findViewById(R.id.sensor_info);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            if (orientationSensor == null) {
                Toast.makeText(this, "Sensor de orientación no disponible", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (orientationSensor != null) {
            sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float azimuth = event.values[0];
            float pitch = event.values[1];
            float roll = event.values[2];

            String data = String.format("Azimut: %.2f°\nInclinación: %.2f°\nRotación: %.2f°", azimuth, pitch, roll);
            sensorInfo.setText(data);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}