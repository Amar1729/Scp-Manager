package com.gtstudios.amar.scphelper;

/**
 * Created by Amar on 2/13/16.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.ListPreference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import java.util.ArrayList;
import java.util.List;

public class mainSettingsActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target) {
        //setContentView(R.xml.preferences);
        loadHeadersFromResource(R.xml.preferences, target);
    }
}
