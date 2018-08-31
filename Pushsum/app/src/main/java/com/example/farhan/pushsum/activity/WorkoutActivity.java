package com.example.farhan.pushsum.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.farhan.pushsum.R;
import com.example.farhan.pushsum.entity.Week;
import com.example.farhan.pushsum.sqlhelper.TrainingDataSource;


public class WorkoutActivity extends AppCompatActivity implements SensorEventListener {

    TextView textView, titolo;
    CardView cardViewSave;
    TrainingDataSource tds;
    SensorManager sensorManager;
    Sensor sensor;
    int count, pushs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        textView = (TextView) findViewById(R.id.txt_pushUp_counter);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        count = 0;
        pushs = 0;
        tds = new TrainingDataSource(this);
        tds.open();
        cardViewSave = (CardView) findViewById(R.id.card_view_save);
        cardViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tds.delete(tds.getWeek());
                tds.insert(new Week(Integer.parseInt(textView.getText().toString()), 2, 3, 4, 5, 6, 7, 8, 9));
                System.out.println(tds.getWeek());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] == 0.0) {
                pushs++;
            }
            textView.setText(pushs + "");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
