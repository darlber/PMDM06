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
    private static final int ROTATION_VECTOR_VALUES_LENGTH = 3; // Expected length of rotation vector values.
    private static final int ORIENTATION_VALUES_LENGTH = 3; // Expected length of orientation values.
    private static final int ROTATION_MATRIX_VALUES_LENGTH = 9; // Expected length of rotation matrix values.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        TextView sensorDescriptionTextView = findViewById(R.id.sensor_description);
        sensorInfoTextView = findViewById(R.id.sensor_info);

        sensorDescriptionTextView.setText("Vector de Rotación del Dispositivo");

        // Get SensorManager instance
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Get rotation vector sensor
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (rotationVectorSensor == null) {
            sensorInfoTextView.setText("Sensor no disponible");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the listener for the rotation vector sensor
        if (rotationVectorSensor != null) {
            // Using SENSOR_DELAY_NORMAL is generally recommended unless high precision is needed.
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener when the activity is paused
        if (rotationVectorSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            // Check that the event.values has the expected length
            if (event.values.length >= ROTATION_VECTOR_VALUES_LENGTH) {
                displaySensorData(event.values);
            } else{
                sensorInfoTextView.setText("Invalid rotation vector data");
            }

        }
    }

    private void displaySensorData(float[] rotationVector) {
        // Convert rotation vector to rotation matrix
        float[] rotationMatrix = new float[ROTATION_MATRIX_VALUES_LENGTH];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);

        // Convert rotation matrix to orientation (azimuth, pitch, roll) in radians
        float[] orientation = new float[ORIENTATION_VALUES_LENGTH];
        SensorManager.getOrientation(rotationMatrix, orientation);

        // Convert radians to degrees for better readability
        float azimuth = (float) Math.toDegrees(orientation[0]);
        float pitch = (float) Math.toDegrees(orientation[1]);
        float roll = (float) Math.toDegrees(orientation[2]);

        // Display the values in the TextView
        String info = String.format(
                "Rotation Vector:\nX: %.2f\nY: %.2f\nZ: %.2f\n\n" +
                        "Orientation:\nAzimuth: %.2f°\nPitch: %.2f°\nRoll: %.2f°",
                rotationVector[0], rotationVector[1], rotationVector[2],
                azimuth, pitch, roll
        );

        sensorInfoTextView.setText(info);
    }

        @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
    }
}
