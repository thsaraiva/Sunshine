package com.example.android.sunshine.app.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sunshine.app.R;

public class ForecastDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String forecast = intent.getStringExtra(Intent.EXTRA_TEXT);

        View rootView = inflater.inflate(R.layout.forecast_details_fragment_layout, container, false);
        TextView forecastDetail = (TextView) rootView.findViewById(R.id.detail_forecast_textview);
        forecastDetail.setText(forecast);
        return rootView;
    }

}
