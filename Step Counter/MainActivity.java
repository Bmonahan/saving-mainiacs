package com.example.mattf_000.stepcounttest;


        import android.app.Activity;
        import android.content.Context;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.TextView;
        import android.widget.Toast;


public class MainActivity extends Activity implements SensorEventListener{

    private SensorManager sensorManager;            //NEEDED FOR USE OF SENSORS
    private TextView count;
    boolean activityRunning;                        //IF MOVING TRUE ELSE FALSE PER STANDARD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = (TextView) findViewById(R.id.count);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        //SETS UP A SENSOR TO COUNT THE STEPS
        if(countSensor != null){   //CHECK IF SENSOR IS PRESENT
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
            //SET UP LISTENER
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
            //SHOULD ONLY TRIGGER IF PHONE DOESNT HAVE AVAILABLE SENSOR, ALL MODERN ONES SHOULD THOUGH
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(activityRunning) {
            count.setText(String.valueOf(event.values[0]));
            //IF THE SENSOR DETECTS A STEP HAS OCCURED APPENDS THE COUNT TO THE SCREEN
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //IT TOLD ME I NEEDED THIS METHOD TO SE SENSOR MANAGER BUT IT'S USELESS
        //IN CONTEXT TO OUR APP

    }
}
