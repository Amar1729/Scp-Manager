package com.gtstudios.amar.scphelper.settings;

import android.content.Context;
import android.preference.Preference;

import settings.ServerSetting;

/**
 * Created by Amar on 3/8/16.
 *
 * This file is taken (verbatim) from Eric Kok (Transdroid)
 * org.transdroid.core.gui.settings:
 *      ServerPreference.java
 */

/**
 * Represents a {@link ServerSetting} in a preferences screen.
 * @author Eric Kok
 */
public class ServerPreference extends Preference {

    private static final int ORDER_START = 1;

    protected ServerSetting serverSetting;
    private OnServerClickedListener onServerClickedListener = null;

    public ServerPreference(Context context) {
        super(context);
        setOnPreferenceClickListener(onPreferenceClicked);
    }

    /**
     * Set the server settings object that is bound to this preference item
     * @param serverSetting The server settings
     * @return Itself, for method chaining
     */
    public ServerPreference setServerSetting(ServerSetting serverSetting) {
        this.serverSetting = serverSetting;
        setTitle(serverSetting.getName());
        setSummary(serverSetting.getId());
        setOrder(ORDER_START + serverSetting.getOrder());
        return this;
    }

    /**
     * Set a listener that will be notified of click events on this preference
     * @param onServerClickedListener The click listener to register
     * @return Itself, for method chaining
     */
    public ServerPreference setOnServerClickedListener(OnServerClickedListener onServerClickedListener) {
        this.onServerClickedListener = onServerClickedListener;
        return this;
    }

    private OnPreferenceClickListener onPreferenceClicked = new OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (onServerClickedListener != null)
                onServerClickedListener.onServerClicked(serverSetting);
            return true;
        }
    };

    /*public*/ interface OnServerClickedListener {
        void onServerClicked(ServerSetting serverSetting);
    }

}
