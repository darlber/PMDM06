package com.example.pmdm06.Sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmdm06.R;

public class Gravity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gravitySensor;
    private TextView sensorInfo, sensorDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorDescription = findViewById(R.id.sensor_description);
        sensorDescription.setText("Mide la fuerza de la gravedad sobre el dispositivo.");

        sensorInfo = findViewById(R.id.sensor_info);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Obtener el sensor de gravedad
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        if (gravitySensor == null) {
            // Si el sensor no está disponible, mostrar un mensaje
            Toast.makeText(this, "Sensor de gravedad no disponible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar el listener para el sensor de gravedad
        if (gravitySensor != null) {
            sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar el listener cuando la actividad se pausa
        if (gravitySensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            // Obtener los valores de gravedad en X, Y y Z
            float gravityX = event.values[0];
            float gravityY = event.values[1];
            float gravityZ = event.values[2];

            // Formatear los valores con 2 decimales y mostrarlos en los TextViews
            String gravityData = String.format("Gravedad X: %.2f\nGravedad Y: %.2f\nGravedad Z: %.2f", gravityX, gravityY, gravityZ);
            sensorInfo.setText(gravityData);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
