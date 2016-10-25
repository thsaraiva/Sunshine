package com.example.android.sunshine.app.Model;

/**
 * Created by thsaraiva on 25/10/2016.
 */
public class HourForecast {

    public long dt; //Time of data forecasted, unix, UTC
    public MainForecast main;
    public Weather[] weather;
    public Cloud clouds;
    public Wind wind;
    //    public Rain rain;
//    public Snow snow;
    public String dt_text;
}
