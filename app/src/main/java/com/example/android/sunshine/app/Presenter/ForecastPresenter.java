package com.example.android.sunshine.app.Presenter;

import android.content.Context;

/**
 * Created by thsaraiva on 17/10/2016.
 */
public interface ForecastPresenter {

    public void onGetCityForecastButtonClicked(Context context, String cityName, String cityPosition);
}
