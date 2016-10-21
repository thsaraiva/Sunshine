package com.example.android.sunshine.app.View;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.sunshine.app.R;

import java.util.ArrayList;
import java.util.List;

public class CityForecastListFragment extends Fragment {

    private static final String CITY_POSITION = "city_position";
    private static final String CITY_NAME = "city_name";
    private static final int PAGES_NUMBER = 5;

    private String mCityName;
    private int mCityPosition;

    public static CityForecastListFragment newInstance(String cityName, int cityPosition) {
        CityForecastListFragment fragment = new CityForecastListFragment();
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
        View rootView = inflater.inflate(R.layout.city_forecast_fragment_layout, container, false);

        TextView cityNameTextView = (TextView) rootView.findViewById(R.id.city_name);
        if (cityNameTextView != null) {
            cityNameTextView.setText(mCityName);
        }

        //set the adapter in the ViewPager
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.city_forecast_view_pager);
        if (viewPager != null) {
            viewPager.setAdapter(new DailyForecastPagerAdapter(getFragmentManager()));
        }

        if (rootView != null) {
            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static class DailyForecastPagerAdapter extends FragmentPagerAdapter {

        public DailyForecastPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * This method may be called by the ViewPager to obtain a title string
         * to describe the specified page. This method may return null
         * indicating no title for this page. The default implementation returns
         * null.
         *
         * @param position The position of the title requested
         * @return A title for the requested page
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return "Título da página #" + position;
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            return DailyForecastListFragment.newInstance(position);
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return PAGES_NUMBER;
        }
    }

    /**
     * Each fragment of this represents one page in the view pager.
     * Each fragment contains a list with weather forecast for different times of an specific day.
     */
    public static class DailyForecastListFragment extends Fragment {

        private static final String PAGE_NUMBER = "pageNumber";
        private int mNum;

        public static DailyForecastListFragment newInstance(int pageNumber) {
            DailyForecastListFragment newFrag = new DailyForecastListFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(PAGE_NUMBER, pageNumber);
            newFrag.setArguments(arguments);
            return newFrag;
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
            Bundle arguments = getArguments();
            mNum = arguments != null ? arguments.getInt(PAGE_NUMBER) : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.daily_forecast_list_layout, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);

            List<String> days = new ArrayList<>();
            days.add("Page #" + mNum + " - Monday");
            days.add("Page #" + mNum + " - Tuesday");
            days.add("Page #" + mNum + " - Wednesday");
            days.add("Page #" + mNum + " - Thursday");
            days.add("Page #" + mNum + " - Friday");
            days.add("Page #" + mNum + " - Saturday");
            days.add("Page #" + mNum + " - Sunday");
            ArrayAdapter<String> dailyForecastListAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast, days);
            listView.setAdapter(dailyForecastListAdapter);

//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    String forecast = dailyForecastListAdapter.getItem(position);
////                Toast.makeText(getActivity(),forecast,Toast.LENGTH_LONG).show();
//                    Intent openDetailsActivity = new Intent(getActivity(), CityForecastDetailsActivity.class);
//                    openDetailsActivity.putExtra(Intent.EXTRA_TEXT, forecast);
//                    startActivity(openDetailsActivity);
//                }
//            });
            if (rootView != null) {
                return rootView;
            }
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

}