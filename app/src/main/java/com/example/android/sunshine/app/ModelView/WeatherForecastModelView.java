package com.example.android.sunshine.app.ModelView;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thsaraiva on 25/10/2016.
 */

public class WeatherForecastModelView implements Parcelable {

    public String mDdate;
    public String mTime;
    public double mTemperature;
    public String mDescription;
    public String mIconPath;

    public WeatherForecastModelView(String date, String time, double temperature, String description, String iconPath) {
        mDdate = date;
        mTime = time;
        mTemperature = temperature;
        mDescription = description;
        mIconPath = iconPath;
    }

    protected WeatherForecastModelView(Parcel in) {
        mDdate = in.readString();
        mTime = in.readString();
        mTemperature = in.readDouble();
        mDescription = in.readString();
        mIconPath = in.readString();
    }

    public static final Creator<WeatherForecastModelView> CREATOR = new Creator<WeatherForecastModelView>() {
        @Override
        public WeatherForecastModelView createFromParcel(Parcel in) {
            return new WeatherForecastModelView(in);
        }

        @Override
        public WeatherForecastModelView[] newArray(int size) {
            return new WeatherForecastModelView[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mDdate);
        parcel.writeString(mTime);
        parcel.writeDouble(mTemperature);
        parcel.writeString(mDescription);
        parcel.writeString(mIconPath);
    }
}
