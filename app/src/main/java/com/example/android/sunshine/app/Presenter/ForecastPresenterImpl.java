package com.example.android.sunshine.app.Presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.sunshine.app.API.WeatherForecastAPI;
import com.example.android.sunshine.app.Model.DailyForecast;
import com.example.android.sunshine.app.View.CityForecastListActivity;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thsaraiva on 25/10/2016.
 */

public class ForecastPresenterImpl implements ForecastPresenter {

    private final String API_BASE_URL = "http://api.openweathermap.org/";
    private final String APPID_PARAMS = "APPID";
    private final String API_KEY = "674241f50ea6f65a948e32c5f74c5132";
    private final String MODE_PARAMS = "mode";
    private String mode = "json";
    private final String UNITS_PARAMS = "units";
    private String units = "metric";
    private final String DAYS_PARAMS = "cnt";
    private String numberOfDays = "7";

    private static ForecastPresenterImpl fpi;

    private OkHttpClient.Builder httpClientBuilder;
    private Retrofit retrofit;

    private ForecastPresenterImpl() {
        retroFitInitialization();
    }

    private void retroFitInitialization() {
        httpClientBuilder = new OkHttpClient.Builder();
        //generates an interceptor responsible for including authentication parameters in every request
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalHttpUrl = originalRequest.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(APPID_PARAMS, API_KEY)
                        .addQueryParameter(MODE_PARAMS, mode)
                        .addQueryParameter(UNITS_PARAMS, units)
                        .addQueryParameter(DAYS_PARAMS, numberOfDays)
                        .build();
                Request newRequest = originalRequest.newBuilder()
                        .url(url).build();
                return chain.proceed(newRequest);
            }
        });

        //Add interceptor responsible for logging network communication for debug purposes
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        OkHttpClient OkHttpClient = httpClientBuilder.build();

        retrofit = new Retrofit.Builder().client(OkHttpClient).addConverterFactory(GsonConverterFactory.create()).baseUrl(API_BASE_URL).build();
    }

    public static ForecastPresenterImpl getInstance() {
        if (fpi == null) {
            fpi = new ForecastPresenterImpl();
        }
        return fpi;
    }

    @Override
    public void onGetCityForecastButtonClicked(Context context, String cityName, String cityCode) {
        getForecast(cityCode);
        Intent startCityForecastActivity = new Intent(context, CityForecastListActivity.class);
        startCityForecastActivity.putExtra("city_name", cityName);
        startCityForecastActivity.putExtra("city_position", cityCode);
        context.startActivity(startCityForecastActivity);
    }

    private void getForecast(String cityCode) {
        WeatherForecastAPI weatherForecastAPI = retrofit.create(WeatherForecastAPI.class);
        Call<DailyForecast> dailyWeatherForecastCall = weatherForecastAPI.getDailyForecast(cityCode);

        //makes an asynchronous call to obtain the list of comics.
        dailyWeatherForecastCall.enqueue(new Callback<DailyForecast>() {
            @Override
            public void onResponse(Call<DailyForecast> call, Response<DailyForecast> response) {
                int code = response.code();
                if (code == 200) {
                    DailyForecast df = response.body();
                    int dataContainer = df.cnt;
                    Log.d("ComicsListActivity", "WUHUW PASSOU!!! PORRA!");
                } else {
                    Log.d("ComicsListActivity", "Request successful but something went wrong parsing!!");
                }
            }

            @Override
            public void onFailure(Call<DailyForecast> call, Throwable t) {
                Log.d("MyForecast", "Request failed! onFailure: stacktrace: " + t.getLocalizedMessage());
            }
        });
    }
}
