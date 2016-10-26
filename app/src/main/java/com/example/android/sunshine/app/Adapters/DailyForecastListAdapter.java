package com.example.android.sunshine.app.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.sunshine.app.ModelView.WeatherForecastModelView;
import com.example.android.sunshine.app.R;

import java.util.List;

/**
 * Created by thsaraiva on 26/10/2016.
 */

public class DailyForecastListAdapter extends ArrayAdapter<WeatherForecastModelView> {
    private int layoutId;

    public DailyForecastListAdapter(Context context, int resource, List<WeatherForecastModelView> objects) {
        super(context, resource, objects);
        layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(layoutId, parent, false);

        WeatherForecastModelView currentItem = getItem(position);

        TextView hourTextView = (TextView) rootView.findViewById(R.id.daily_forecast_hour_textview);
        if (hourTextView != null) {
            hourTextView.setText(currentItem.mTime);
        }
        TextView tempTextView = (TextView) rootView.findViewById(R.id.daily_forecast_temperature_textview);
        if (tempTextView != null) {
            tempTextView.setText(currentItem.mTemperature+"°C");
        }
        TextView iconTextView = (TextView) rootView.findViewById(R.id.daily_forecast_icon_textview);
        if (iconTextView != null) {
            iconTextView.setText(currentItem.mIconPath);
        }
        TextView descTextView = (TextView) rootView.findViewById(R.id.daily_forecast_description_textview);
        if (descTextView != null) {
            descTextView.setText(currentItem.mDescription);
        }

        if (rootView != null) {
            return rootView;
        }
        return super.getView(position, convertView, parent);
    }
}
