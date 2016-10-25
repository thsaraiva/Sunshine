package com.example.android.sunshine.app.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.sunshine.app.Presenter.ForecastPresenter;
import com.example.android.sunshine.app.Presenter.ForecastPresenterImpl;
import com.example.android.sunshine.app.R;


public class MainActivity extends ActionBarActivity implements MainFragment.OnGetCityForecastClickListener {

    private ForecastPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        mPresenter = ForecastPresenterImpl.getInstance();
    }

    @Override
    public void onGetCityForecastClick(String cityName, int cityPosition) {

        //get city code from array
        String cityCode;
        String[] cityCodes = getResources().getStringArray(R.array.pref_cities_values);
        cityCode = ((cityCodes != null) && (cityCodes.length >0)) ? cityCodes[cityPosition] : ""+0;

        mPresenter.onGetCityForecastButtonClicked(this, cityName, cityCode);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_settings == item.getItemId()) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
