package com.example.android.sunshine.app.View;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sunshine.app.R;

public class ForecastDetailsFragment extends Fragment {
    private static final String FORECAST_POSITION = "FORECAST_POSITION";
    private int mPosition;

    public static ForecastDetailsFragment newInstance(int position) {
        ForecastDetailsFragment fdf = new ForecastDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(FORECAST_POSITION, position);
        fdf.setArguments(arguments);
        return fdf;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle arguments = getArguments();
        mPosition = (arguments != null) ? arguments.getInt(FORECAST_POSITION) : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forecast_details_fragment_layout, container, false);
        TextView forecastDetail = (TextView) rootView.findViewById(R.id.detail_forecast_textview);
        forecastDetail.setText(""+mPosition);
        if (rootView != null) {
            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
