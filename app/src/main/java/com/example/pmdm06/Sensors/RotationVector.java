package com.example.pmdm06.Sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmdm06.R;

public class RotationVector extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor rotationVectorSensor;
    private TextView sensorInfoTextView;
    private static final int ROTATION_VECTOR_VALUES_LENGTH = 3; // Longitud esperada de los valores del vector de rotación.
    private static final int ORIENTATION_VALUES_LENGTH = 3; // Longitud esperada de los valores de orientación.
    private static final int ROTATION_MATRIX_VALUES_LENGTH = 9; // Longitud esperada de la matriz de rotación.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        TextView sensorDescriptionTextView = findViewById(R.id.sensor_description);
        sensorInfoTextView = findViewById(R.id.sensor_info);

        sensorDescriptionTextView.setText(R.string.rotation_desc);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rotationVectorSensor != null) {
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (rotationVectorSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

            if (event.values.length >= ROTATION_VECTOR_VALUES_LENGTH) {
                displaySensorData(event.values);
            } else{
                sensorInfoTextView.setText(R.string.rotation_err);
            }

        }
    }

    private void displaySensorData(float[] rotationVector) {
        // convierte el vector de rotación a una matriz de rotación
        float[] rotationMatrix = new float[ROTATION_MATRIX_VALUES_LENGTH];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);

        // convierte la matriz de rotación a valores de orientación
        float[] orientation = new float[ORIENTATION_VALUES_LENGTH];
        SensorManager.getOrientation(rotationMatrix, orientation);

        // Convierte los valores de orientación a grados
        float azimuth = (float) Math.toDegrees(orientation[0]);
        float pitch = (float) Math.toDegrees(orientation[1]);
        float roll = (float) Math.toDegrees(orientation[2]);

        String info = String.format(
                getString(R.string.rotation_values),
                rotationVector[0], rotationVector[1], rotationVector[2],
                azimuth, pitch, roll
        );

        sensorInfoTextView.setText(info);
    }

        @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
    }
}
