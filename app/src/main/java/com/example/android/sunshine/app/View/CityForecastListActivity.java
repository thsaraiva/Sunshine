package com.example.android.sunshine.app.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.sunshine.app.ModelView.DailyForecastModelView;
import com.example.android.sunshine.app.ModelView.WeatherForecastModelView;
import com.example.android.sunshine.app.Presenter.ForecastPresenter;
import com.example.android.sunshine.app.Presenter.ForecastPresenterImpl;
import com.example.android.sunshine.app.R;

import java.util.ArrayList;
import java.util.List;

public class CityForecastListActivity extends ActionBarActivity implements CityForecastListFragment.DailyForecastListListener {
    public static final String CITY_POSITION = "city_position";
    public static final String CITY_NAME = "city_name";
    public static final String DAILY_WEATHER_FORECAST_LIST = "daily_weather_forecast_list";

    private ForecastPresenter mPresenter;

    //    private ArrayList<WeatherForecastModelView> mWeatherForecastList;
    private ArrayList<DailyForecastModelView> mDailyWeatherForecastList;

    //TODO:this is NOT the best place for this.
//    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_forecast_activity_layout);

        if (mPresenter == null) {
            mPresenter = ForecastPresenterImpl.getInstance();
        }
        mPresenter.onTakeCityForecastListActivity(this);


        if (savedInstanceState == null) {
            Intent intent = getIntent();
            mDailyWeatherForecastList = intent.getParcelableArrayListExtra(DAILY_WEATHER_FORECAST_LIST);
            CityForecastListFragment cityForecastListFragment = CityForecastListFragment.newInstance(intent.getStringExtra(CITY_NAME), intent.getStringExtra(CITY_POSITION));
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
//        if (R.id.action_refresh == item.getItemId()) {
//            //start AsyncTask
//            FetchWeatherForecast fetchForecast = new FetchWeatherForecast(adapter);
//            fetchForecast.execute();
//            return true;
//        } else
        if (R.id.action_settings == item.getItemId()) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public List<WeatherForecastModelView> getDailyForecastList(int fragmentNumber) {
//        List<WeatherForecastModelView> weatherForecastModelViewList
//                = new ArrayList<>();
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "12:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "13:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "14:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "15:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "16:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "17:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "18:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "19:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "20:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "21:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "22:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "23:00", 23.2, "desc", "10d"));
//        weatherForecastModelViewList.add(new WeatherForecastModelView("26/10", "24:00", 23.2, "desc", "10d"));
//        return weatherForecastModelViewList;
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
}
