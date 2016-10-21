package com.example.android.sunshine.app.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.android.sunshine.app.R;

public class CityForecastActivity extends ActionBarActivity {
    public static final String CITY_POSITION = "city_position";
    public static final String CITY_NAME = "city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_forecast_activity_layout);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            CityForecastFragment cityForecastFragment = CityForecastFragment.newInstance(intent.getStringExtra(CITY_NAME), intent.getIntExtra(CITY_POSITION, 0));
            getSupportFragmentManager().beginTransaction().add(R.id.activity_city_forecast, cityForecastFragment).commit();
        }
    }
}
