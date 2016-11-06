package com.example.android.sunshine.app.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.sunshine.app.R;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends PreferenceActivity {

    private static final String PREF_TAG = "PreferenceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_city_key)), getString(R.string.pref_city_default_value));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_unit_key)), getString(R.string.pref_unit_default_value));

        }

        private void bindPreferenceSummaryToValue(Preference preference, String defaultValue) {

            preference.setOnPreferenceChangeListener(this);
            SharedPreferences settingsSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            preference.getOnPreferenceChangeListener().onPreferenceChange(preference, settingsSharedPreferences.getString(preference.getKey(), defaultValue));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference instanceof EditTextPreference) {
                preference.setSummary(newValue.toString());
            } else if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int indexOfValue = listPreference.findIndexOfValue(newValue.toString());
                if (indexOfValue >= 0) {
                    listPreference.setSummary(listPreference.getEntries()[indexOfValue]);
                }
            }

//            //saves the selected city code or unit in the Settings Shared Preference file
//            SharedPreferences settingsSharedPreferences = getActivity().getSharedPreferences(getString(R.string.settings_shared_preference_file_key), Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = settingsSharedPreferences.edit();
//            String prefKey = preference.getKey();
//            if (prefKey != null && prefKey.equalsIgnoreCase(getActivity().getString(R.string.pref_city_key))) {
//                String cityCode;
//                String[] cityNames = getResources().getStringArray(R.array.pref_cities_entries);
//                List<String> cityNamesList = Arrays.asList(cityNames);
//                int indexOfCityName = cityNamesList.indexOf(newValue.toString());
//                String[] cityCodes = getResources().getStringArray(R.array.pref_cities_values);
//                cityCode = ((cityCodes != null) && (cityCodes.length > 0)) ? cityCodes[indexOfCityName] : "" + 0;
//                editor.putString(getString(R.string.pref_city_key), cityCode);
//
//            } else if (prefKey != null && prefKey.equalsIgnoreCase(getActivity().getString(R.string.pref_unit_key))) {
//                String unitCode;
//                String[] unitNames = getResources().getStringArray(R.array.pref_units_entries);
//                List<String> unitNamesList = Arrays.asList(unitNames);
//                int indexOfUnitName = unitNamesList.indexOf(newValue.toString());
//                String[] unitCodes = getResources().getStringArray(R.array.pref_units_values);
//                unitCode = ((unitCodes != null) && (unitCodes.length > 0)) ? unitCodes[indexOfUnitName] : "" + 0;
//                editor.putString(getString(R.string.pref_unit_key), unitCode);
//            }
//            editor.commit();
            return true;
        }

    }

    private void myLog(String msg) {
        Log.v(PREF_TAG, this.getClass().getCanonicalName().toString() + msg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLog(".onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myLog(".onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        myLog(".onStart()");

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        myLog(".onRestart()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        myLog(".onResume()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        myLog(".onPause()");
    }
}
