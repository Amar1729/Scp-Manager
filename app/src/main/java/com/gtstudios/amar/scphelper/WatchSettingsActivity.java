package com.gtstudios.amar.scphelper;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import java.util.List;

/**
 * Created by Amar on 2/17/16.
 */
public class WatchSettingsActivity extends PreferenceActivity {

    private Preference.OnPreferenceClickListener onDirPath = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            return false;
        }
    };

    private Preference.OnPreferenceClickListener onDirName = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            return false;
        }
    };

    @Override
    @SuppressWarnings("deprecated")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addPreferencesFromResource(R.xml.pref_dirs);

        // Add watch dir
        findPreference("dir_path").setOnPreferenceClickListener(onDirPath);
        findPreference("dir_name").setOnPreferenceClickListener(onDirName);
    }
/*
    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
    }*/
}
