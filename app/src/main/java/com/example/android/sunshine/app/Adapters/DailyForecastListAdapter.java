package com.example.android.sunshine.app.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sunshine.app.ModelView.WeatherForecastModelView;
import com.example.android.sunshine.app.R;
import com.squareup.picasso.Picasso;

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
            tempTextView.setText(Math.round(currentItem.mTemperature) + "°C");
        }
        ImageView iconImageView = (ImageView) rootView.findViewById(R.id.daily_forecast_icon_imageview);
        if (iconImageView != null) {
            String imagePath = "http://openweathermap.org/img/w/" + currentItem.mIconPath + ".png";
            if (currentItem.mIconPath != "" && currentItem.mIconPath != null) {
                Picasso.with(rootView.getContext())
                        .load(imagePath)
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image_error)
                        .into(iconImageView);
            } else {
                Picasso.with(rootView.getContext()).load(R.drawable.no_image).into(iconImageView);
                Log.e("ComicsListAdapter", "No image available.Using default");
            }
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
