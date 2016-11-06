package com.example.android.sunshine.app.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.sunshine.app.Adapters.DailyForecastListAdapter;
import com.example.android.sunshine.app.ModelView.DailyForecastModelView;
import com.example.android.sunshine.app.ModelView.WeatherForecastModelView;
import com.example.android.sunshine.app.Presenter.ForecastPresenter;
import com.example.android.sunshine.app.Presenter.ForecastPresenterImpl;
import com.example.android.sunshine.app.R;

import java.util.ArrayList;
import java.util.List;

public class CityForecastListActivity extends ActionBarActivity implements CityForecastListFragment.DailyForecastListListener {
    public static final String CITY_POSITION = "city_position";
    public static final String CURRENT_UNIT = "current_unit";
    public static final String CITY_NAME = "city_name";
    public static final String DAILY_WEATHER_FORECAST_LIST = "daily_weather_forecast_list";
    private static final String ACTIVITY_TAG = "CityForecastListAct";

    private ForecastPresenter mPresenter;
    private ArrayList<DailyForecastModelView> mDailyWeatherForecastList;
    private String currentCityName;
    private String currentCityCode;
    private String currentUnit;

    private List<CityForecastListFragment.DailyForecastPagerAdapter> mDailyForecastPagerAdaptersList;
    private List<DailyForecastListAdapter> mDailyForecastListAdapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_forecast_activity_layout);

        mDailyForecastPagerAdaptersList = new ArrayList<>();
        mDailyForecastListAdapterList = new ArrayList<>();

        if (mPresenter == null) {
            mPresenter = ForecastPresenterImpl.getInstance();
        }
        mPresenter.onTakeCityForecastListActivity(this);


        if (savedInstanceState == null) {
            Intent intent = getIntent();
            mDailyWeatherForecastList = intent.getParcelableArrayListExtra(DAILY_WEATHER_FORECAST_LIST);
            currentCityName = intent.getStringExtra(CITY_NAME);
            currentCityCode = intent.getStringExtra(CITY_POSITION);
            currentUnit = intent.getStringExtra(CURRENT_UNIT);
            CityForecastListFragment cityForecastListFragment = CityForecastListFragment.newInstance(currentCityName, currentCityCode);
            getSupportFragmentManager().beginTransaction().add(R.id.activity_city_forecast, cityForecastListFragment, "CITY_FORECAST_LIST_FRAGMENT").commit();
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
            mPresenter.getCityForecastDataOnNetwork(this.currentCityCode, this.currentUnit);
            return true;
        } else if (R.id.action_settings == item.getItemId()) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public List<WeatherForecastModelView> getDailyForecastList(int fragmentNumber) {
        return mDailyWeatherForecastList.get(fragmentNumber).getDailyForecastModelView();
    }

    @Override
    public void onForecastSelected(int position) {
        Intent openDetailsActivity = new Intent(this, ForecastDetailsActivity.class);
        openDetailsActivity.putExtra("forecast_list_item_position", position);
        startActivity(openDetailsActivity);
    }

    @Override
    public int getPagesNumber() {
        int pages = (mDailyWeatherForecastList != null) ? mDailyWeatherForecastList.size() : 0;
        return pages;
    }

    @Override
    public String getPageTitle(int position) {
        DailyForecastModelView mv = (mDailyWeatherForecastList != null) ? mDailyWeatherForecastList.get(position) : null;
        if (mv != null) {
            return mv.mDay;
        }
        return "";
    }

    @Override
    public void registerDailyForecastPagerAdapter(CityForecastListFragment.DailyForecastPagerAdapter dailyForecastPagerAdapter) {
        mDailyForecastPagerAdaptersList.add(dailyForecastPagerAdapter);
    }

    @Override
    public void registerDailyForecastListAdapter(DailyForecastListAdapter dailyForecastListAdapter) {
        mDailyForecastListAdapterList.add(dailyForecastListAdapter);
    }

    public void updateCityForecastData(String newCityName, ArrayList<DailyForecastModelView> dailyWeatherForecastList) {

        //update adapters
        this.mDailyWeatherForecastList = dailyWeatherForecastList;
        for (CityForecastListFragment.DailyForecastPagerAdapter adapter : mDailyForecastPagerAdaptersList) {
            adapter.notifyDataSetChanged();
        }
        for (DailyForecastListAdapter adapter : mDailyForecastListAdapterList) {
            adapter.notifyDataSetChanged();
        }

        //update city name
        this.currentCityName = newCityName;
        CityForecastListFragment fragment = (CityForecastListFragment) getSupportFragmentManager().findFragmentByTag("CITY_FORECAST_LIST_FRAGMENT");
        fragment.updateCityName(currentCityName);
    }


    private void myLog(String msg) {
        Log.v(ACTIVITY_TAG, this.getClass().getCanonicalName().toString() + msg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLog(".onStop()");
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroyCityForecastListActivity(this);
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
        super.onRestart();
        myLog(".onRestart()");
        mPresenter.checkIfSettingsHaveChanged(currentCityCode, currentUnit);

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
