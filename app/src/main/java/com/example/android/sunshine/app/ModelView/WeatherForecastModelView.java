package com.example.android.sunshine.app.ModelView;

/**
 * Created by thsaraiva on 25/10/2016.
 */

public class WeatherForecastModelView {

    public String date;
    public String time;
    public double temperature;
    public String description;
    public String iconPath;

    public WeatherForecastModelView(String date, String time, double temperature, String description, String iconPath) {
        this.date = date;
        this.time = time;
        this.temperature = temperature;
        this.description = description;
        this.iconPath = iconPath;
    }
}
