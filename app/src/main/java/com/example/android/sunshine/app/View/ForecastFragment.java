package com.example.android.sunshine.app.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.sunshine.app.AsyncTasks.FetchWeatherForecast;
import com.example.android.sunshine.app.Presenter.ForecastPresenter;
import com.example.android.sunshine.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    final static int PAGES_NUMBER = 5;

    private ArrayAdapter<String> adapter;

    private ForecastPresenter forecastPresenter;

    public static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
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
            PagerFragment pf = new PagerFragment();
            Bundle args = new Bundle();
            args.putInt("num", position);
            pf.setArguments(args);
            return pf;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return PAGES_NUMBER;
        }
    }

    public static class PagerFragment extends Fragment {

        int mNum;

        /**
         * Default constructor.  <strong>Every</strong> fragment must have an
         * empty constructor, so it can be instantiated when restoring its
         * activity's state.  It is strongly recommended that subclasses do not
         * have other constructors with parameters, since these constructors
         * will not be called when the fragment is re-instantiated; instead,
         * arguments can be supplied by the caller with {@link #setArguments}
         * and later retrieved by the Fragment with {@link #getArguments}.
         * <p/>
         * <p>Applications should generally not implement a constructor.  The
         * first place application code an run where the fragment is ready to
         * be used is in #onAttach(Activity), the point where the fragment
         * is actually associated with its activity.  Some applications may also
         * want to implement {@link #onInflate} to retrieve attributes from a
         * layout resource, though should take care here because this happens for
         * the fragment is attached to its activity.
         */
        public PagerFragment() {
            super();

        }

        /**
         * Called to have the fragment instantiate its user interface view.
         * This is optional, and non-graphical fragments can return null (which
         * is the default implementation).  This will be called between
         * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
         * <p/>
         * <p>If you return a View from here, you will later be called in
         * {@link #onDestroyView} when the view is being released.
         *
         * @param inflater           The LayoutInflater object that can be used to inflate
         *                           any views in the fragment,
         * @param container          If non-null, this is the parent view that the fragment's
         *                           UI should be attached to.  The fragment should not add the view itself,
         *                           but this can be used to generate the LayoutParams of the view.
         * @param savedInstanceState If non-null, this fragment is being re-constructed
         *                           from a previous saved state as given here.
         * @return Return the View for the fragment's UI, or null.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_pager, container, false);
            TextView text = (TextView) view.findViewById(R.id.text);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            text.setText("Page #" + mNum);
            if (view != null) {
                return view;
            }
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p/>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p/>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_refresh == item.getItemId()) {
            //start AsyncTask
            FetchWeatherForecast fetchForecast = new FetchWeatherForecast(adapter);
            fetchForecast.execute();
            return true;
        } else if (R.id.action_settings == item.getItemId()) {
            Intent settings = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);


        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager()));

        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Saturday");
        days.add("Sunday");

        adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_TextView, days);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = adapter.getItem(position);
//                Toast.makeText(getActivity(),forecast,Toast.LENGTH_LONG).show();
                Intent openDetailsActivity = new Intent(getActivity(), DetailActivity.class);
                openDetailsActivity.putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(openDetailsActivity);
            }
        });

        return rootView;
    }
}