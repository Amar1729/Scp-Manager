package com.gtstudios.amar.scphelper;

/**
 * Created by Amar on 2/13/16.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import java.util.List;

public class MainSettingsActivity extends PreferenceActivity {

    protected SharedPreferences prefs;

    private OnPreferenceClickListener onAddServer = new OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            Intent intent = new Intent(MainSettingsActivity.this, ServerSettingsActivity.class);
            startActivity(intent);
            return true;
        }
    };

    private OnPreferenceClickListener onAddDir = new OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            //WatchSettingsActivity.intent(MainSettingsActivity.this).start();
            Intent intent = new Intent(MainSettingsActivity.this, WatchSettingsActivity.class);
            startActivity(intent);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note: Settings are loaded in onResume()
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
    }

    @SuppressWarnings("deprecated") // this is needed to use addPreferencesFromResource()
    @Override
    public void onResume() {
        super.onResume();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // This should clear duped headers from the screen (since this is resume, not create)\
        prefs = getPreferenceManager().getSharedPreferences();
        if (getPreferenceScreen() != null) {
            getPreferenceScreen().removeAll();
        }

        //setContentView(R.xml.preferences);    // don't use this
        //loadHeadersFromResource(R.xml.preferences, target);   //seems to be the strat
        addPreferencesFromResource(R.xml.preferences);

        // Add new server
        findPreference("header_addserver").setOnPreferenceClickListener(onAddServer);

        // Add watch dir
        findPreference("header_adddir").setOnPreferenceClickListener(onAddDir);
    }
}
