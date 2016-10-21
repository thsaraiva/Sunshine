package com.example.android.sunshine.app.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.android.sunshine.app.AsyncTasks.FetchWeatherForecast;
import com.example.android.sunshine.app.R;

public class CityForecastListActivity extends ActionBarActivity {
    public static final String CITY_POSITION = "city_position";
    public static final String CITY_NAME = "city_name";

    //TODO:still not sure if this is the best place for this.
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_forecast_activity_layout);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            CityForecastListFragment cityForecastListFragment = CityForecastListFragment.newInstance(intent.getStringExtra(CITY_NAME), intent.getIntExtra(CITY_POSITION, 0));
            getSupportFragmentManager().beginTransaction().add(R.id.activity_city_forecast, cityForecastListFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.city_forecast_list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_refresh == item.getItemId()) {
            //start AsyncTask
            FetchWeatherForecast fetchForecast = new FetchWeatherForecast(adapter);
            fetchForecast.execute();
            return true;
        } else if (R.id.action_settings == item.getItemId()) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}