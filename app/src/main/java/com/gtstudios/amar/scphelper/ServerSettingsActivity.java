package com.gtstudios.amar.scphelper;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import java.util.List;

/**
 * Created by Amar on 2/17/16.
 */
public class ServerSettingsActivity extends PreferenceActivity {
    private Preference.OnPreferenceClickListener onAddServer = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            // need to show a dialog here?
            return false;
        }
    };

    private Preference.OnPreferenceClickListener onServerAddress = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            return false;
        }
    };

    private Preference.OnPreferenceClickListener onUsername = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            return false;
        }
    };

    private Preference.OnPreferenceClickListener onPassword = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            return false;
        }
    };

    @Override
    @SuppressWarnings("deprecated") // this is needed to use addPreferencesFromResource()
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note: Settings are loaded in onResume()

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addPreferencesFromResource(R.xml.pref_server);

        // Add new server
        findPreference("server_name").setOnPreferenceClickListener(onAddServer);
        findPreference("server_address").setOnPreferenceClickListener(onServerAddress);
        findPreference("server_user").setOnPreferenceClickListener(onUsername);
        findPreference("server_pass").setOnPreferenceClickListener(onPassword);
    }
/*
    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
    }*/
}
