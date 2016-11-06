package com.example.android.sunshine.app.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.sunshine.app.API.WeatherForecastAPI;
import com.example.android.sunshine.app.Model.DailyForecast;
import com.example.android.sunshine.app.Model.HourForecast;
import com.example.android.sunshine.app.Model.Weather;
import com.example.android.sunshine.app.ModelView.DailyForecastModelView;
import com.example.android.sunshine.app.ModelView.WeatherForecastModelView;
import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.View.CityForecastListActivity;
import com.example.android.sunshine.app.View.MainActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    //    private final String UNITS_PARAMS = "units";
    private String units = "metric";

    private static ForecastPresenterImpl fpi;

    private OkHttpClient.Builder httpClientBuilder;
    private Retrofit retrofit;
    private CityForecastListActivity mCityForecastListActivity;
    private MainActivity mMainActivity;

    private ForecastPresenterImpl() {
        retroFitInitialization();
    }

    public static ForecastPresenterImpl getInstance() {
        if (fpi == null) {
            fpi = new ForecastPresenterImpl();
        }
        return fpi;
    }

    @Override
    public void onTakeCityForecastListActivity(CityForecastListActivity activity) {
        this.mCityForecastListActivity = activity;
    }

    @Override
    public void onDestroyCityForecastListActivity(CityForecastListActivity activity) {
        if (this.mCityForecastListActivity == activity) {
            this.mCityForecastListActivity = null;
        }
    }

    @Override
    public void onTakeMainActivity(MainActivity activity) {
        this.mMainActivity = activity;
    }

    @Override
    public void onDestroyMainActivity(MainActivity activity) {
        if (this.mMainActivity == activity) {
            this.mMainActivity = null;
        }
    }

    @Override
    public void checkIfSettingsHaveChanged(String currentCityCode, String currentUnit) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mCityForecastListActivity);
        String citySavedInPreference = sharedPreferences.getString(this.mCityForecastListActivity.getString(R.string.pref_city_key), this.mCityForecastListActivity.getString(R.string.pref_city_default_value));
        String unitSavedInPreference = sharedPreferences.getString(this.mCityForecastListActivity.getString(R.string.pref_unit_key), this.mCityForecastListActivity.getString(R.string.pref_unit_default_value));
        if (!currentCityCode.equalsIgnoreCase(citySavedInPreference) || !currentUnit.equalsIgnoreCase(unitSavedInPreference)) {
            getCityForecastDataOnNetwork(citySavedInPreference, unitSavedInPreference);
        }
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


    @Override
    public void getCityForecastDataOnNetwork(final String cityCode, final String unit) {

        WeatherForecastAPI weatherForecastAPI = retrofit.create(WeatherForecastAPI.class);
        Call<DailyForecast> dailyWeatherForecastCall = weatherForecastAPI.getDailyForecast(cityCode, unit);

        //makes an asynchronous call to obtain the list of comics.
        dailyWeatherForecastCall.enqueue(new Callback<DailyForecast>() {
            @Override
            public void onResponse(Call<DailyForecast> call, Response<DailyForecast> response) {
                int code = response.code();
                if (code == 200) {
                    HourForecast[] list = response.body().list;
                    ArrayList<DailyForecastModelView> dailyWeatherForecastList = new ArrayList<>();
                    String lastResultDay = "";
                    DailyForecastModelView dailyForecastModelView = null;
                    for (HourForecast hourForecast : list) {
                        long dateAndTime = hourForecast.dt;
                        Date df = new java.util.Date(dateAndTime * 1000);//must be in miliseconds
                        String dayAndMonth = new SimpleDateFormat("dd/MM", Locale.UK).format(df);
                        String time = new SimpleDateFormat("HH:mm", Locale.UK).format(df);
                        Weather weather = hourForecast.weather[0];
                        WeatherForecastModelView modelView = new WeatherForecastModelView(dayAndMonth, time, hourForecast.main.temp, weather.description, weather.icon);
                        if (lastResultDay.isEmpty() && dailyForecastModelView == null) {
                            //se é a primeira vez
                            dailyForecastModelView = new DailyForecastModelView(dayAndMonth);
                        } else if (!lastResultDay.equalsIgnoreCase(dayAndMonth)) {
                            //se não é o primeira vez e mudou de dia
                            dailyWeatherForecastList.add(dailyForecastModelView);
                            dailyForecastModelView = new DailyForecastModelView(dayAndMonth);
                        }
                        lastResultDay = dayAndMonth;
                        dailyForecastModelView.addWeatherForecastModelView(modelView);
                    }
                    dailyWeatherForecastList.add(dailyForecastModelView);
                    displayNewCityWeatherForecast(response.body().city.name, cityCode, unit, dailyWeatherForecastList);

                    Log.v("ComicsListActivity", "Request successful and data parsed correctly");
                } else {
                    Log.v("ComicsListActivity", "Request successful but something went wrong parsing!!");
                }
            }

            @Override
            public void onFailure(Call<DailyForecast> call, Throwable t) {
                Log.d("MyForecast", "Request failed! onFailure: stacktrace: " + t.getLocalizedMessage());
            }
        });
    }

    public void displayNewCityWeatherForecast(String cityName, String cityCode, String current_unit, ArrayList<DailyForecastModelView> dailyWeatherForecastList) {
        if (mCityForecastListActivity == null) {
            //means I´m creating the forecast list Activity for the first time, so let´s start it.
            Intent startCityForecastActivity = new Intent(mMainActivity, CityForecastListActivity.class);
            startCityForecastActivity.putExtra(CityForecastListActivity.CITY_NAME, cityName);
            startCityForecastActivity.putExtra(CityForecastListActivity.CITY_POSITION, cityCode);
            startCityForecastActivity.putExtra(CityForecastListActivity.CURRENT_UNIT, current_unit);
            startCityForecastActivity.putParcelableArrayListExtra(CityForecastListActivity.DAILY_WEATHER_FORECAST_LIST, dailyWeatherForecastList);
            mMainActivity.startActivity(startCityForecastActivity);
        } else {
            //means I´ve already crated the forecast list Activity and am just refreshing the data
            mCityForecastListActivity.updateCityForecastData(cityName, dailyWeatherForecastList);
        }
    }
}
