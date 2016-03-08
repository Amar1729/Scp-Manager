package settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Amar on 3/8/16.
 *
 * This file heavily copied/modified from Eric Kok (Transdroid)
 *
 * org.transdroid.core.app.settings
 *      ApplicationSettings.java
 *
 */
public class ApplicationSettings {

    protected Context context;
    private SharedPreferences prefs;

    public static final int DEFAULTSERVER_LASTUSED = -2;
    public static final int DEFAULTSERVER_ASKONADD = -1;

    protected ApplicationSettings(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Returns the order number/identifying key of the last normal server
     * @return The zero-based order number (index) of the last stored normal server settings
     */
    // public int getMaxNormalServer()
    //      changed because I don't use distinct seedbox methods
    public int getMaxServer() {
        for (int i = 0; true; i++) {
            if (prefs.getString("server_type_" + i, null) == null || prefs.getString("server_address_" + i, null) == null)
                return i - 1;
        }
    }

    public List<ServerSetting> getAllServerSettings() {
        List<ServerSetting> all = new ArrayList<>();

        // In Transdroid this method found Seedbox settings also
        all.addAll(getServerSettings());
        return all;
    }

    // public List<ServerSetting> getNormalServerSettings()
    //      changed because I don't use distinct seedbox methods
    public List<ServerSetting> getServerSettings() {
        List<ServerSetting> servers = new ArrayList<>();
        for (int i = 0; i <= getMaxServer(); i++) {
            servers.add(getServerSetting(i));
        }
        return Collections.unmodifiableList(servers);
    }

    /**
     * Returns the user-specified server settings for a normal (non-seedbox) server. WARNING: This method does not check
     * if the settings actually exist and may rely on empty defaults if called for a non-existing server.
     * @param order The order number/identifying key of the normal server's settings to retrieve
     * @return The server settings object, loaded from shared preferences
     */
    public ServerSetting getServerSetting(int order) {
        // @formatter:off
        //Daemon type = Daemon.fromCode(prefs.getString("server_type_" + order, null));
        //boolean ssl = prefs.getBoolean("server_sslenabled_" + order, false);

        String port = prefs.getString("server_port_" + order, null);
        if (TextUtils.isEmpty(port))
            port = Integer.toString(22);
        String localPort = prefs.getString("server_localport_" + order, null);
        if (TextUtils.isEmpty(localPort))
            localPort = port; // Default to the normal (non-local) port

        try {
            parseInt(port, /*Daemon.getDefaultPortNumber(type, ssl)*/22);
        } catch (NumberFormatException e) {
            port = Integer.toString(/*Daemon.getDefaultPortNumber(type, ssl)*/22);
        }
        try {
            parseInt(localPort, parseInt(port, /*Daemon.getDefaultPortNumber(type, ssl))*/22));
        } catch (NumberFormatException e) {
            localPort = port;
        }

        return new ServerSetting(
                order,
                prefs.getString("server_name_" + order, null),
                trim(prefs.getString("server_address_" + order, null)),
                //prefs.getString("server_address_" + order, null).trim(),
                trim(prefs.getString("server_localaddress_" + order, null)),
                //prefs.getString("server_localaddress_" + order, null).trim(),
                parseInt(localPort, parseInt(port, 22)),
                prefs.getString("server_localnetwork_" + order, null),
                parseInt(port, 22),
                prefs.getString("server_user_" + order, null),
                prefs.getString("server_pass_" + order, null)
        );
        // @formatter:on
    }

    /**
     * Removes all settings related to a configured server. Since servers are ordered, the order of the remaining
     * servers will be updated accordingly.
     * @param order The identifying order number/key of the settings to remove
     */
    public void removeServerSettings(int order) {
        if (prefs.getString("server_type_" + order, null) == null)
            return; // The settings that were requested to be removed do not exist

        // Copy all settings higher than the supplied order number to the previous spot
        Editor edit = prefs.edit();
        int max = getMaxServer();
        for (int i = order; i < max; i++) {
            edit.putString("server_name_" + i, prefs.getString("server_name_" + (i + 1), null));
            //edit.putString("server_type_" + i, prefs.getString("server_type_" + (i + 1), null));
            edit.putString("server_address_" + i, prefs.getString("server_address_" + (i + 1), null));
            edit.putString("server_localaddress_" + i, prefs.getString("server_localaddress_" + (i + 1), null));
            edit.putString("server_localnetwork_" + i, prefs.getString("server_localnetwork_" + (i + 1), null));
            edit.putString("server_port_" + i, prefs.getString("server_port_" + (i + 1), null));
            //edit.putBoolean("server_sslenabled_" + i, prefs.getBoolean("server_sslenabled_" + (i + 1), false));
            //edit.putBoolean("server_ssltrustall_" + i, prefs.getBoolean("server_ssltrustall_" + (i + 1), false));
            //edit.putString("server_ssltrustkey_" + i, prefs.getString("server_ssltrustkey_" + (i + 1), null));
            //edit.putString("server_folder_" + i, prefs.getString("server_folder_" + (i + 1), null));
            //edit.putBoolean("server_disableauth_" + i, prefs.getBoolean("server_disableauth_" + (i + 1), false));
            edit.putString("server_user_" + i, prefs.getString("server_user_" + (i + 1), null));
            edit.putString("server_pass_" + i, prefs.getString("server_pass_" + (i + 1), null));
        }

        // Remove the last settings, of which we are now sure are no longer required
        edit.remove("server_name_" + max);
        //edit.remove("server_type_" + max);
        edit.remove("server_address_" + max);
        edit.remove("server_localaddress_" + max);
        edit.remove("server_localnetwork_" + max);
        edit.remove("server_port_" + max);
        edit.remove("server_user_" + max);
        edit.remove("server_pass_" + max);

        // Perhaps we should also update the default server to match the server's new id or remove the default selection
        // in case it was this server that was removed
        int defaultServer = getDefaultServerKey();
        if (defaultServer == order) {
            edit.remove("header_defaultserver");
        } else if (defaultServer > order) {
            // Move 'up' one place to account for the removed server setting
            edit.putInt("header_defaultserver", --order);
        }

        edit.apply();

    }

    /**
     * Returns the settings of the server that was explicitly selected by the user to select as default or, when no
     * specific default server was selected, the last used server settings. As opposed to getDefaultServerKey(int), this
     * method checks whether the particular server still exists (and returns the first server if not). If no servers are
     * configured, null is returned.
     * @return A server settings object of the server to use by default, or null if no server is yet configured
     */
    public ServerSetting getDefaultServer() {

        int defaultServer = getDefaultServerKey();
        if (defaultServer == DEFAULTSERVER_LASTUSED || defaultServer == DEFAULTSERVER_ASKONADD) {
            return getLastUsedServer();
        }

        // Use the explicitly selected default server
        int max = getMaxServer(); // Zero-based index, so with max == 0 there is 1 server
        if (max < 0) {
            // No servers configured
            return null;
        }
        if (defaultServer < 0 || defaultServer > max) {
            // Last server was never set or no longer exists
            return getServerSetting(0);
        }
        return getServerSetting(defaultServer);

    }

    /**
     * Returns the unique key of the server setting that the user selected as their default server, or code indicating
     * that the last used server should be selected by default; use with getDefaultServer directly. WARNING: the
     * returned string may no longer refer to a known server setting key.
     * @return An integer; if it is 0 or higher it represents the unique key of a configured server setting, -2 means
     *         the last used server should be selected as default instead and -1 means the last used server should be
     *         selected by default for viewing yet it should always ask when adding a new torrent
     */
    public int getDefaultServerKey() {
        String defaultServer = prefs.getString("header_defaultserver", Integer.toString(DEFAULTSERVER_LASTUSED));
        try {
            return Integer.parseInt(defaultServer);
        } catch (NumberFormatException e) {
            // This should NEVER happen but if the setting somehow is not a number, return the default
            return DEFAULTSERVER_LASTUSED;
        }
    }

    /**
     * Returns the settings of the server that was last used by the user. As opposed to getLastUsedServerKey(int), this
     * method checks whether a server was already registered as being last used and check whether the server still
     * exists. It returns the first server if that fails. If no servers are configured, null is returned.
     * @return A server settings object of the last used server (or, if not known, the first server), or null if no
     *         servers exist
     */
    public ServerSetting getLastUsedServer() {
        int max = getMaxServer(); // Zero-based index, so with max == 0 there is 1 server
        if (max < 0) {
            // No servers configured
            return null;
        }
        int last = getLastUsedServerKey();
        if (last < 0 || last > max) {
            // Last server was never set or no longer exists
            return getServerSetting(0);
        }
        return getServerSetting(last);
    }

    /**
     * Returns the order number/unique key of the server that the used last used; use with getServerSettings(int) or
     * call getLastUsedServer directly. WARNING: the returned integer may no longer refer to a known server settings
     * object: check the bounds.
     * @return An integer indicating the order number/key or the last used server, or -1 if it was not set
     */
    public int getLastUsedServerKey() {
        return prefs.getInt("system_lastusedserver", -1);
    }

    /**
     * Registers some server as being the last used by the user
     * @param server The settings of the server that the user last used
     */
    public void setLastUsedServer(ServerSetting server) {
        setLastUsedServerKey(server.getOrder());
    }

    /**
     * Registers the order number/unique key of some server as being last used by the user
     * @param order The key identifying the specific server
     */
    public void setLastUsedServerKey(int order) {
        prefs.edit().putInt("system_lastusedserver", order).apply();
    }

    /**
     * Trims away whitespace around a string, or returns null if str is null
     * @param str The string to trim, or null
     * @return The trimmed string, or null if str is null
     */
    private String trim(String str) {
        if (str == null) return null;
        return str.trim();
    }

    private int parseInt(String string, int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
