package com.example.android.sunshine.app.ModelView;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by thsaraiva on 26/10/2016.
 * This class represents a collection of weather forecasts for the same day of the month.
 */

public class DailyForecastModelView implements Parcelable {

    public ArrayList<WeatherForecastModelView> mDailyForecastModelView;

    public DailyForecastModelView() {
        mDailyForecastModelView = new ArrayList<>();
    }

    public DailyForecastModelView(Parcel parcel) {
        this.mDailyForecastModelView = parcel.readArrayList(this.getClass().getClassLoader());
    }

    public static final Creator<DailyForecastModelView> CREATOR = new Creator<DailyForecastModelView>() {
        @Override
        public DailyForecastModelView createFromParcel(Parcel in) {
            return new DailyForecastModelView(in);
        }

        @Override
        public DailyForecastModelView[] newArray(int size) {
            return new DailyForecastModelView[size];
        }
    };

    public void addWeatherForecastModelView(WeatherForecastModelView weatherForecastModelView) {
        if (mDailyForecastModelView == null) {
            mDailyForecastModelView = new ArrayList<>();
        }
        mDailyForecastModelView.add(weatherForecastModelView);
    }

    public ArrayList<WeatherForecastModelView> getDailyForecastModelView() {
        return mDailyForecastModelView;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(mDailyForecastModelView);
    }
}
