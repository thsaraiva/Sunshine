package com.example.android.sunshine.app.Presenter;

import android.content.Context;

import com.example.android.sunshine.app.View.CityForecastListActivity;

/**
 * Created by thsaraiva on 17/10/2016.
 */
public interface ForecastPresenter {

    public void onGetCityForecastButtonClicked(Context context, String cityName, String cityPosition);

    public void onTakeCityForecastListActivity(CityForecastListActivity activity);
}
