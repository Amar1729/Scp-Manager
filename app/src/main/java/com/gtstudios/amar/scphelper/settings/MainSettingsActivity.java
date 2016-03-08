package com.gtstudios.amar.scphelper.settings;

/**
 * Created by Amar on 2/13/16.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import settings.ServerSetting;
import settings.ApplicationSettings;

import com.gtstudios.amar.scphelper.R;
import com.gtstudios.amar.scphelper.settings.ServerPreference.OnServerClickedListener;

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

    private OnServerClickedListener onServerClicked = new OnServerClickedListener() {
        // @Override
        public void onServerClicked(ServerSetting serverSetting) {
            Intent intent = new Intent(MainSettingsActivity.this, ServerSettingsActivity.class);
            startActivity(intent);
            //return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note: Settings are loaded in onResume()

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
    }

    @SuppressWarnings("deprecated") // this is needed to use addPreferencesFromResource()
    @Override
    public void onResume() {
        super.onResume();

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // This should clear duped stuff from the screen (since this is resume, not create)
        prefs = getPreferenceManager().getSharedPreferences();
        if (getPreferenceScreen() != null) {
            getPreferenceScreen().removeAll();
        }

        // load preferences and attach actions
        addPreferencesFromResource(R.xml.preferences);
        findPreference("header_addserver").setOnPreferenceClickListener(onAddServer);
        findPreference("header_adddir").setOnPreferenceClickListener(onAddDir);


        // Following snippets copied from Transdroid:
        //      MainSettingsActivity.java

        // Keep a list of the server codes and names (for default server selection)
        List<String> serverCodes = new ArrayList<>();
        List<String> serverNames = new ArrayList<>();
        serverCodes.add(Integer.toString(ApplicationSettings.DEFAULTSERVER_LASTUSED));
        serverCodes.add(Integer.toString(ApplicationSettings.DEFAULTSERVER_ASKONADD));
        serverNames.add(getString(R.string.pref_defaultserver_lastused));
        serverNames.add(getString(R.string.pref_defaultserver_askonadd));

        // Add existing servers
        List<ServerSetting> servers = ApplicationSettings.getServerSettings();
        for (ServerSetting serverSetting : servers) {
            getPreferenceScreen().addPreference(
                    new ServerPreference(this)
                            .setServerSetting(serverSetting)
                            .setOnServerClickedListener(onServerClicked));

            // careful with null check - not sure if getId() returns null when it always should
            if (serverSetting.getId() != null) {
                serverCodes.add(Integer.toString(serverSetting.getOrder()));
                serverNames.add(serverSetting.getName());
            }
        }
    }

    // Added 03-07-16
    //  From: http://developer.android.com/training/implementing-navigation/ancestral.html
    //
    // This is probably slightly different from Transdroid's implementation but I need up actionbar support
    // Also I think NavUtils is old (not deprecated?) so look into updates
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
