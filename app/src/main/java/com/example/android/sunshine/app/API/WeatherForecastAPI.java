package com.example.android.sunshine.app.API;

import com.example.android.sunshine.app.Model.DailyForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by thsaraiva on 25/10/2016.
 */

public interface WeatherForecastAPI {

    @GET("/data/2.5/forecast")
    Call<DailyForecast> getDailyForecast(@Query("id") String city_id);
}
