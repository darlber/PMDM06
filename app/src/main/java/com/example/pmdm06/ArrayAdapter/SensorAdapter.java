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
import com.example.pmdm06.Sensors.LinearAcceleration;

import java.util.List;

public class SensorAdapter extends BaseAdapter {

    private Context context;
    private List<String> sensorNames;
    private List<Integer> sensorTypes;
    private SensorManager sensorManager;

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
            } else {
                return; // Evita abrir una actividad incorrecta
            }
            intent.putExtra("sensor_name", sensorNames.get(position));
            context.startActivity(intent);
        });
        ;


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
