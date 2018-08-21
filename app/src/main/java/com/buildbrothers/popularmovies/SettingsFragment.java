package com.buildbrothers.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_movies);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();

        PreferenceScreen preferenceScreen = getPreferenceScreen();

        int count = preferenceScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference p = preferenceScreen.getPreference(i);
            if (notCheckBox(p)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummery(p, value);
            }
        }
        //starts live change listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);

        if (preference != null) {
            if (notCheckBox(preference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummery(preference, value);
            }
        }
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    //sets summary for each pref item where applicable
    private void setPreferenceSummery(Preference preference, Object value){

        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            //get index of current value of list to help lookup correct display value in list entries
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            //for other preference just use the simple string
            preference.setSummary(stringValue);
        }
    }

    //check if pref is not CheckBox and return true when condition is met
    private boolean notCheckBox(Preference pref) {

        return !(pref instanceof CheckBoxPreference);
    }

}
