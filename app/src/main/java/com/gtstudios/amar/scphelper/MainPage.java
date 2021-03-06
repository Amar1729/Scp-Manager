package com.gtstudios.amar.scphelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

import com.gtstudios.amar.scphelper.settings.MainSettingsActivity;

public class MainPage extends AppCompatActivity {

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onFabClicked);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //*/

        counter = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Show app settings UI

            Intent intent = new Intent(MainPage.this, MainSettingsActivity.class);
            startActivity(intent);

            // From transdroid:
            // WebsearchSettingsActivity_.intent(MainSettingsActivity.this).start();
            // My derivation:
            // MainSettingsActivity.intent(MainPage.this).start();

            return true;
        }

        if (id == R.id.send_tors) {
            // Try to send tors
            // Bring up dialog to double-check
            // Sort tors into buckets (i.e. movies, tv shows, etc)
            // Ask for confirmation, then send
            Toast.makeText(this, "Files sent! Woo! (not really tho yet)", Toast.LENGTH_SHORT).show();

            // Attempt to write user settings?
            showUserSettings();

            return true;
        }

        // Default case
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onFabClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainPage.this, "I will attempt to add a torrent :(", Toast.LENGTH_SHORT).show();

            TextView t = (TextView) findViewById(R.id.txtbox);
            t.setText("Hallo: " + counter);
            counter++;

            // This probably doesn't work yet - does FPH need a layout?
            //Intent intent = new Intent(MainPage.this, FilePickerHelper.class);
            //startActivity(intent);
        }
    };

    private void showUserSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //StringBuilder builder = new StringBuilder();
        String builder;

        /*
        * edit.remove("server_name_" + max);
        //edit.remove("server_type_" + max);
        edit.remove("server_address_" + max);
        edit.remove("server_localaddress_" + max);
        edit.remove("server_localnetwork_" + max);
        edit.remove("server_port_" + max);
        edit.remove("server_user_" + max);
        edit.remove("server_pass_" + max);
        */

        builder = "\n Nickname: " + prefs.getString("server_name", "NULL") +
                "\n Username: " + prefs.getString("server_user", "NULL") +
                "\n Address: " + prefs.getString("server_address", "NULL");

        TextView t = (TextView) findViewById(R.id.txtbox);
        t.setText(builder);
    }
}
