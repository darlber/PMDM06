package com.example.pmdm06.ArrayAdapter;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pmdm06.R;
import com.example.pmdm06.Sensors.Accelerometer;
import com.example.pmdm06.Sensors.AmbientTemperature;
import com.example.pmdm06.Sensors.Gravity;
import com.example.pmdm06.Sensors.Gyroscope;
import com.example.pmdm06.Sensors.LightSensor;
import com.example.pmdm06.Sensors.LinearAcceleration;
import com.example.pmdm06.Sensors.MagneticField;
import com.example.pmdm06.Sensors.Orientation;
import com.example.pmdm06.Sensors.Pressure;
import com.example.pmdm06.Sensors.Proximity;
import com.example.pmdm06.Sensors.RelativeHumidity;
import com.example.pmdm06.Sensors.RotationVector;
import com.example.pmdm06.Sensors.Temperature;

import java.util.List;

public class SensorAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> sensorNames;
    private final List<Integer> sensorTypes;
    private final SensorManager sensorManager;

    // Constructor para recibir contexto y las listas de nombres y tipos de sensores
    public SensorAdapter(Context context, List<String> sensorNames, List<Integer> sensorTypes) {
        this.context = context;
        this.sensorNames = sensorNames;
        this.sensorTypes = sensorTypes;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sensor_item, parent, false);
        }

        // Obtener los elementos del layout
        TextView sensorName = convertView.findViewById(R.id.sensor_name);
        ImageView sensorStatus = convertView.findViewById(R.id.sensor_status);

        // Asignar nombre al TextView
        sensorName.setText(sensorNames.get(position));

        // Verificar si el dispositivo tiene el sensor
        Sensor sensor = sensorManager.getDefaultSensor(sensorTypes.get(position));
        if (sensor != null) {
            // Sensor encontrado, mostrar círculo verde
            sensorStatus.setImageResource(R.drawable.green_circle);
        } else {
            // Sensor no encontrado, mostrar círculo gris
            sensorStatus.setImageResource(R.drawable.grey_circle);
        }
        // Configurar el evento de clic en el elemento
        convertView.setOnClickListener(v -> {
            String sensorNameText = sensorNames.get(position);
            Log.d("SensorDebug", "Clicked sensor: " + sensorNameText);
            boolean isSensorAvailable = sensorAvailable(position);

            if (!isSensorAvailable) {
                String message = "Sensor no disponible";
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                return; // Si no está disponible, no hacer nada más
            }

            // Si el sensor está disponible, se abre el intent
            Intent intent;
            if ("Aceleración Lineal".equals(sensorNameText)) {
                intent = new Intent(context, LinearAcceleration.class);
            } else if ("Acelerómetro".equals(sensorNameText)) {
                intent = new Intent(context, Accelerometer.class);
            } else if ("Gravedad".equals(sensorNameText)) {
                intent = new Intent(context, Gravity.class);
            } else if ("Giroscopio".equals(sensorNameText)) {
                intent = new Intent(context, Gyroscope.class);
            } else if ("Sensor de Luz Ambiental".equals(sensorNameText)) {
                intent = new Intent(context, LightSensor.class);
            } else if ("Brújula".equals(sensorNameText)) {
                intent = new Intent(context, MagneticField.class);
            } else if ("Orientación (Obsoleto)".equals(sensorNameText)) {
                intent = new Intent(context, Orientation.class);
            } else if ("Sensor de Proximidad".equals(sensorNameText)) {
                intent = new Intent(context, Proximity.class);
            } else if ("Temperatura del Dispositivo".equals(sensorNameText)) {
                intent = new Intent(context, Temperature.class);
            } else if ("Temperatura Ambiental".equals(sensorNameText)) {
                intent = new Intent(context, AmbientTemperature.class);
            } else if ("Humedad Relativa".equals(sensorNameText)) {
                intent = new Intent(context, RelativeHumidity.class);
            } else if ("Sensor de Presión".equals(sensorNameText)) {
                intent = new Intent(context, Pressure.class);
            } else if ("Vector de Rotación".equals(sensorNameText)) {
                intent = new Intent(context, RotationVector.class);
            } else {
                return; // Evita abrir una actividad incorrecta
            }
            intent.putExtra("sensor_name", sensorNames.get(position));
            context.startActivity(intent);
        });

        return convertView;
    }

    private boolean sensorAvailable(int position) {
        return sensorManager.getDefaultSensor(sensorTypes.get(position)) != null;
    }

    @Override
    public int getCount() {
        return sensorNames.size();
    }

    @Override
    public Object getItem(int position) {
        return sensorNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
