package com.example.android.sunshine.app.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.android.sunshine.app.R;


public class MainActivity extends ActionBarActivity implements MainFragment.OnGetCityForecastClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
    }

    @Override
    public void onGetCityForecastClick(String cityName, int cityPosition) {
//        Toast.makeText(this, "Selected item name: " + cityName + ", position: " + cityPosition, Toast.LENGTH_LONG).show();
        Intent startCityForecastActivity = new Intent(this,CityForecastActivity.class);
        startCityForecastActivity.putExtra("city_name",cityName);
        startCityForecastActivity.putExtra("city_position",cityPosition);
        startActivity(startCityForecastActivity);

    }
}
