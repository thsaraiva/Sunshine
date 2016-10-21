package com.example.android.sunshine.app.View;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.android.sunshine.app.R;

public class MainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnGetCityForecastClickListener mGetCityForecastListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Called when a fragment is first attached to its activity.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mGetCityForecastListener = (OnGetCityForecastClickListener) activity;
        } catch (ClassCastException exception) {
            throw new ClassCastException(activity.toString() + "must implement OnGetCityForecastClickListener interface ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //infla layout do MainFragment
        View view = inflater.inflate(R.layout.main_fragment_layout, container, false);

        //Constroi adpter com os valores das cidades que vao aparecer no Spinner e seta Adpter nele.
        ArrayAdapter<CharSequence> citiesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.pref_cities_entries, android.R.layout.simple_spinner_item);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner citiesSpinner = (Spinner) view.findViewById(R.id.city_spinner);
        citiesSpinner.setAdapter(citiesAdapter);

        Button getForecastButton = (Button) view.findViewById(R.id.get_forecast_button);
        getForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner citySpinner = (Spinner) getActivity().findViewById(R.id.city_spinner);
                mGetCityForecastListener.onGetCityForecastClick(citySpinner.getSelectedItem().toString(), citySpinner.getSelectedItemPosition());
            }
        });

        return view;
    }

    // Container Activity must implement this interface
    public interface OnGetCityForecastClickListener {
        public void onGetCityForecastClick(String cityName, int cityPosition);
    }
}
