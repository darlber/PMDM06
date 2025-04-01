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
    private TextView gravityXText, gravityYText, gravityZText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gravity);

        // Obtener las referencias de los TextViews donde se mostrarán los valores
        // Inicializar los TextViews
        gravityXText = findViewById(R.id.gravity_x);
        gravityYText = findViewById(R.id.gravity_y);
        gravityZText = findViewById(R.id.gravity_z);

        // Inicializar el SensorManager
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
            gravityXText.setText(String.format("Gravedad X: %.2f", gravityX));
            gravityYText.setText(String.format("Gravedad Y: %.2f", gravityY));
            gravityZText.setText(String.format("Gravedad Z: %.2f", gravityZ));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
