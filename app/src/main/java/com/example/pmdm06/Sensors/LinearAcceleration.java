package com.example.pmdm06.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmdm06.R;


public class LinearAcceleration extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor linearAccelerationSensor;
    private TextView sensorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_acceleration);

        sensorInfo = findViewById(R.id.sensor_info);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        TextView sensorDescription = findViewById(R.id.sensor_description);
        sensorDescription.setText("Mide la aceleración en los ejes X, Y y Z sin incluir la gravedad.");

        if (linearAccelerationSensor == null) {
            sensorInfo.setText("Sensor no disponible");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (linearAccelerationSensor != null) {
            sensorManager.registerListener(this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (linearAccelerationSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        String data = String.format("X: %.2f m/s²\nY: %.2f m/s²\nZ: %.2f m/s²", x, y, z);
        sensorInfo.setText(data);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}



