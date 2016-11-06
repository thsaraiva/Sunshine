package com.example.android.sunshine.app.Model;

/**
 * Created by thsaraiva on 25/10/2016.
 */

public class DailyForecast {

    public int cnt; // Number of lines returned by this API call
    public HourForecast[] list; //daily forecast list
    public City city;// this is the city which forecast was returned

}
