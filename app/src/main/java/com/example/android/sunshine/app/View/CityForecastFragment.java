package com.example.android.sunshine.app.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sunshine.app.R;

public class CityForecastFragment extends Fragment {

    private static final String CITY_POSITION = "city_position";
    private static final String CITY_NAME = "city_name";

    private String mCityName;
    private int mCityPosition;


    public CityForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cityName     Parameter 1.
     * @param cityPosition Parameter 2.
     * @return A new instance of fragment CityForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityForecastFragment newInstance(String cityName, int cityPosition) {
        CityForecastFragment fragment = new CityForecastFragment();
        Bundle args = new Bundle();
        args.putString(CITY_NAME, cityName);
        args.putInt(CITY_POSITION, cityPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCityName = getArguments().getString(CITY_NAME);
            mCityPosition = getArguments().getInt(CITY_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.city_forecast_fragment_layout, container, false);
        TextView cityNameTextView = (TextView) view.findViewById(R.id.city_name);
        cityNameTextView.setText(mCityName);

        return view;
    }

}
