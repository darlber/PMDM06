package com.example.pmdm06;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmdm06.ArrayAdapter.SensorAdapter;

import java.util.ArrayList;
import java.util.List;

/*
TODO string.xml
TODO más bonito
 */

public class MainActivity extends AppCompatActivity {

    private List<String> sensorNames;
    private List<Integer> sensorTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView sensorGrid = findViewById(R.id.sensor_grid);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorNames = new ArrayList<>();
        sensorTypes = new ArrayList<>();

        // Definir los 13 sensores comunes
        //https://developer.android.com/develop/sensors-and-location/sensors/sensors_overview?hl=es-419
        addSensor(Sensor.TYPE_LINEAR_ACCELERATION, "Aceleración Lineal");
        addSensor(Sensor.TYPE_ACCELEROMETER, "Acelerómetro");
        addSensor(Sensor.TYPE_MAGNETIC_FIELD, "Brújula");
        addSensor(Sensor.TYPE_GYROSCOPE, "Giroscopio");
        addSensor(Sensor.TYPE_GRAVITY, "Gravedad");
        addSensor(Sensor.TYPE_RELATIVE_HUMIDITY, "Humedad Relativa");
        addSensor(Sensor.TYPE_LIGHT, "Sensor de Luz Ambiental");
        addSensor(Sensor.TYPE_AMBIENT_TEMPERATURE, "Temperatura Ambiental");
        addSensor(Sensor.TYPE_ORIENTATION, "Orientación (Obsoleto)");
        addSensor(Sensor.TYPE_PRESSURE, "Sensor de Presión");
        addSensor(Sensor.TYPE_PROXIMITY, "Sensor de Proximidad");
        addSensor(Sensor.TYPE_TEMPERATURE, "Temperatura del Dispositivo");
        addSensor(Sensor.TYPE_ROTATION_VECTOR, "Vector de Rotación");

        // Después de definir sensores, verifica cuáles están disponibles
        for (int i = 0; i < sensorTypes.size(); i++) {
            Sensor sensor = sensorManager.getDefaultSensor(sensorTypes.get(i));
            Log.d("SensorCheck", sensorNames.get(i) + " available: " + (sensor != null));
        }
        // Adaptador personalizado para el GridView
        SensorAdapter adapter = new SensorAdapter(this, sensorNames, sensorTypes);
        sensorGrid.setAdapter(adapter);
    }

    private void addSensor(int sensorType, String sensorName) {
        sensorTypes.add(sensorType);
        sensorNames.add(sensorName);
    }

}

        /* LISTADO DE SENSORES mi dispositivo
    SAR ADUX1050
    sns_tilt
    pedometer
    stationary_detect_wakeup
    stationary_detect
    sns_smd
    lsm6dso Accelerometer-Uncalibrated
    sar_detector
    Rotation Vector
    orientation
    NonUi
    pickup
    rohm_bu27030
    ak0991x Magnetometer-Uncalibrated
    oem13_light_smd
    ak0991x Magnetometer
    har_wakeup
    har
    sar_detector_1
    linear_acceleration
    gravity
    sns_geomag_rv
    Game Rotation Vector
    lsm6dso Accelerometer
    lsm6dso Gyroscope-Uncalibrated
    rohm_bu27030 Ambient Light Sensor
    lsm6dso Gyroscope
    Aod
    device_orient
    tsl2540
    Xiaomi Proximity
    Touch Sensor
                 */
