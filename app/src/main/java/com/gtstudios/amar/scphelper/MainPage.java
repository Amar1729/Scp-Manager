package com.gtstudios.amar.scphelper;

import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
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
            //mainSettingsActivity.intent(this).start();

            Intent intent = new Intent(this, mainSettingsActivity.class);
            startActivity(intent);

            return true;
        }

        if (id == R.id.send_tors) {
            // Try to send tors
            // Bring up UI to double-check
            // Sort tors into buckets (i.e. movies, tv shows, etc)
            // Ask for confirmation, then send
            Toast.makeText(this, "Files sent! Woo! (not really tho yet)", Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.fab) {
            // Bring up UI for adding torrent (file explorer?)
            Toast.makeText(this, "I will attempt to add a torrent :'(", Toast.LENGTH_LONG).show();



            return true;
        }

        // Default case
        return super.onOptionsItemSelected(item);
    }
}
