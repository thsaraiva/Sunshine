package com.example.android.sunshine.app.Presenter;

import com.example.android.sunshine.app.View.CityForecastListActivity;
import com.example.android.sunshine.app.View.MainActivity;

/**
 * Created by thsaraiva on 17/10/2016.
 */
public interface ForecastPresenter {

    void getCityForecastDataOnNetwork(String cityPosition, String unit);

    void onTakeCityForecastListActivity(CityForecastListActivity activity);

    void onDestroyCityForecastListActivity(CityForecastListActivity activity);

    void onTakeMainActivity(MainActivity activity);

    void onDestroyMainActivity(MainActivity activity);

    void checkIfSettingsHaveChanged(String currentCityCode, String currentUnit);
}
