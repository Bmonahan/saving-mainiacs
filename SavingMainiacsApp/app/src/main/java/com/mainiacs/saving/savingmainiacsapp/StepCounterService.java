package com.mainiacs.saving.savingmainiacsapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zax on 4/27/17.
 */

public class StepCounterService extends IntentService implements SensorEventListener {


    UserProfile user;
    SensorManager sensorManager;
    Sensor countSensor;
    public boolean stepCounterActive;

    public StepCounterService() {
        super("StepCounterService");
    }

    public StepCounterService(UserProfile user, Context mContext) {
        super("StepCounterService");

//        this.user = user;
        this.user = null;

        PackageManager pm = mContext.getPackageManager();
        if(!pm.hasSystemFeature(pm.FEATURE_SENSOR_STEP_COUNTER)) {
            Toast.makeText(mContext, "Count sensor not available!", Toast.LENGTH_LONG).show();
            stepCounterActive = false;
        }
        else {

            sensorManager = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
            countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

            if (countSensor != null) {
                sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
            } else {
                Toast.makeText(mContext, "Failed to create countSensor.", Toast.LENGTH_LONG).show();
            }

            stepCounterActive = true;
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public void SetUser(UserProfile user) {
        this.user = user;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(user != null) {
            user.TempSteps((int) event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
