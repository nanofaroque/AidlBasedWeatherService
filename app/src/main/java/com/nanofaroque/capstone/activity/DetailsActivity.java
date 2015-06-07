package com.nanofaroque.capstone.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nanofaroque.capstone.R;
import com.nanofaroque.capstone.aidl.WeatherData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ofaro on 5/31/2015.
 */
public class DetailsActivity extends Activity{
    public final String TAG=DetailsActivity.this.getClass().getSimpleName();

    TextView tvCityName;
    TextView tvSpeed;
    TextView tvDeg;
    TextView tvTemp;
    TextView tvHumidity;
    TextView tvSunrise;
    TextView tvSunset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        tvCityName=(TextView)findViewById(R.id.tv_city_name);
        tvSpeed=(TextView)findViewById(R.id.speed);
        tvDeg=(TextView)findViewById(R.id.deg);
        tvTemp=(TextView)findViewById(R.id.temp);
        tvHumidity=(TextView)findViewById(R.id.humidity);
        tvSunrise=(TextView)findViewById(R.id.sunrise);
        tvSunset=(TextView)findViewById(R.id.sunset);

        Intent i=getIntent();
        WeatherData data=i.getParcelableExtra("data");
        tvCityName.setText(data.mName);
        tvSpeed.setText("Speed:"+(int) data.mSpeed);
        tvDeg.setText("Degree:"+(int) data.mDeg);
        tvTemp.setText("Temperature:"+(int) (data.mTemp-275)+"C");
        tvHumidity.setText("Humidity:"+(int) data.mHumidity);
        tvSunrise.setText("Sunrise:"+new Date(data.mSunrise*1000));
        tvSunset.setText("Sunset:"+new Date(data.mSunset*1000));

        Log.d(TAG, data.toString());
    }
}
