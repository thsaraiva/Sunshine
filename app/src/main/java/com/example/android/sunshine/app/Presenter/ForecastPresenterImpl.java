package com.example.android.sunshine.app.Presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.sunshine.app.API.WeatherForecastAPI;
import com.example.android.sunshine.app.Model.DailyForecast;
import com.example.android.sunshine.app.Model.HourForecast;
import com.example.android.sunshine.app.Model.Weather;
import com.example.android.sunshine.app.ModelView.DailyForecastModelView;
import com.example.android.sunshine.app.ModelView.WeatherForecastModelView;
import com.example.android.sunshine.app.View.CityForecastListActivity;

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
    private final String UNITS_PARAMS = "units";
    private String units = "metric";

    private static ForecastPresenterImpl fpi;

    private OkHttpClient.Builder httpClientBuilder;
    private Retrofit retrofit;
    private CityForecastListActivity mActivity;

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
        this.mActivity = activity;
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
    public void onGetCityForecastButtonClicked(final Context context, final String cityName, final String cityCode) {

        WeatherForecastAPI weatherForecastAPI = retrofit.create(WeatherForecastAPI.class);
        Call<DailyForecast> dailyWeatherForecastCall = weatherForecastAPI.getDailyForecast(cityCode);

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
                            dailyForecastModelView = new DailyForecastModelView();
                        } else if (!lastResultDay.equalsIgnoreCase(dayAndMonth)) {
                            //se não é o primeira vez e mudou de dia
                            dailyWeatherForecastList.add(dailyForecastModelView);
                            dailyForecastModelView = new DailyForecastModelView();
                            lastResultDay = dayAndMonth;
                        }
                        dailyForecastModelView.addWeatherForecastModelView(modelView);
                    }
                    startCityForecastListActivity(context, cityName, cityCode, dailyWeatherForecastList);
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

    public void startCityForecastListActivity(Context context, String cityName, String cityCode, ArrayList<DailyForecastModelView> dailyWeatherForecastList) {
        Intent startCityForecastActivity = new Intent(context, CityForecastListActivity.class);
        startCityForecastActivity.putExtra("city_name", cityName);
        startCityForecastActivity.putExtra("city_position", cityCode);
        startCityForecastActivity.putParcelableArrayListExtra("daily_weather_forecast_list", dailyWeatherForecastList);
        context.startActivity(startCityForecastActivity);
    }
}
