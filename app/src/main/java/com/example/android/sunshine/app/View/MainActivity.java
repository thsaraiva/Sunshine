package com.example.android.sunshine.app.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.sunshine.app.Presenter.ForecastPresenter;
import com.example.android.sunshine.app.Presenter.ForecastPresenterImpl;
import com.example.android.sunshine.app.R;


public class MainActivity extends ActionBarActivity implements MainFragment.OnGetCityForecastClickListener {

    private static final String ACTIVITY_TAG = "MainActivity";
    private ForecastPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        if (mPresenter == null) {
            mPresenter = ForecastPresenterImpl.getInstance();
        }
        mPresenter.onTakeMainActivity(this);
    }

    @Override
    public void onGetCityForecastClick(String cityName, int cityPosition) {

        //gets the cityCode for the city selected by the user
        String cityCode;
        String[] cityCodes = getResources().getStringArray(R.array.pref_cities_values);
        cityCode = ((cityCodes != null) && (cityCodes.length > 0)) ? cityCodes[cityPosition] : "" + 0;

        //saves the selected city code in the Settings Shared Preference file
        SharedPreferences settingsSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settingsSharedPreferences.edit();
        editor.putString(getString(R.string.pref_city_key), cityCode);
        editor.commit();

        //fetch the data from the network
        mPresenter.getCityForecastDataOnNetwork(cityCode, settingsSharedPreferences.getString(getString(R.string.pref_unit_key), getString(R.string.pref_unit_default_value)));

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

    private void myLog(String msg) {
        Log.v(ACTIVITY_TAG, this.getClass().getCanonicalName().toString() + msg);
    }

    @Override
    protected void onStop() {
        myLog(".onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        myLog(".onDestroy()");
        if (mPresenter != null) {
            mPresenter.onDestroyMainActivity(this);
        }
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        myLog(".onStart()");
        super.onStart();

    }


    @Override
    protected void onRestart() {
        myLog(".onRestart()");
        super.onRestart();

    }

    @Override
    protected void onResume() {
        myLog(".onResume()");
        super.onResume();

    }

    @Override
    protected void onPause() {
        myLog(".onPause()");
        super.onPause();
    }
}
