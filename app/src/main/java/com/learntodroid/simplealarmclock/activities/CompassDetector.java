package com.learntodroid.simplealarmclock.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by fgh19 on 2/22/2018.
 */

public class CompassDetector implements SensorEventListener {

    float[] mGravity;
    float[] mGeomagnetic;

    Double azimut;
    private OnCompassListener mListener;

    public void setOnCompassListener(OnCompassListener listener) {
        this.mListener = listener;
    }

    public interface OnCompassListener {
        public void onDirections(Double azimut);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mListener != null){
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                mGravity = event.values;
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                mGeomagnetic = event.values;
            if (mGravity != null && mGeomagnetic != null) {
                float R[] = new float[9];
                float I[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
                Log.i("sign", Boolean.toString(success));
                if (success) {
                    Log.i("sign","masuk semua");
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    azimut = orientation[0]*180/3.14;
                    Log.i("sign", Double.toString(azimut));
                    mListener.onDirections(azimut);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // no need
    }
}
