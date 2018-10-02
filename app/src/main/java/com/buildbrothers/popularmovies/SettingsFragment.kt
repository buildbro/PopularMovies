package com.buildbrothers.popularmovies

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import android.support.v7.preference.PreferenceScreen


/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        addPreferencesFromResource(R.xml.pref_movies)

        val sharedPreferences = preferenceScreen.sharedPreferences

        val preferenceScreen = preferenceScreen

        val count = preferenceScreen.preferenceCount

        for (i in 0 until count) {
            val p = preferenceScreen.getPreference(i)
            if (notCheckBox(p)) {
                val value = sharedPreferences.getString(p.key, "")
                setPreferenceSummery(p, value!!)
            }
        }
        //starts live change listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        val preference = findPreference(key)

        if (preference != null) {
            if (notCheckBox(preference)) {
                val value = sharedPreferences.getString(preference.key, "")
                setPreferenceSummery(preference, value!!)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        PreferenceManager.getDefaultSharedPreferences(context)
                .unregisterOnSharedPreferenceChangeListener(this)
    }

    //sets summary for each pref item where applicable
    private fun setPreferenceSummery(preference: Preference, value: Any) {

        val stringValue = value.toString()

        if (preference is ListPreference) {
            //get index of current value of list to help lookup correct display value in list entries
            val prefIndex = preference.findIndexOfValue(stringValue)
            if (prefIndex >= 0) {
                preference.summary = preference.entries[prefIndex]
            }
        } else {
            //for other preference just use the simple string
            preference.summary = stringValue
        }
    }

    //check if pref is not CheckBox and return true when condition is met
    private fun notCheckBox(pref: Preference): Boolean =
            pref !is CheckBoxPreference

}
