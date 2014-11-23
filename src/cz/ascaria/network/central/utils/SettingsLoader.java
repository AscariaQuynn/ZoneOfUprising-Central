/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.utils;

import com.jme3.system.AppSettings;
import cz.ascaria.network.central.Main;
import java.util.logging.Level;

/**
 *
 * @author Ascaria
 */
public class SettingsLoader
{
    /**
     * Creates standard AppSettings and fills it with custom, game related stuff
     * @return 
     */
    public AppSettings load()
    {
        // Load default settings
        AppSettings settings = new AppSettings(true);
        // Try load database connection
        loadDatabase(settings);
        // Try load saved settings and overwrite defaults
        //loadSavedSettings(settings);
        // Return fully prepared settings
        return settings;
    }

    private void loadDatabase(AppSettings settings) {
        settings.putString("dbUser", "zone");
        settings.putString("dbPassword", "hovnokleslo");
        settings.putString("dbServerName", "localhost");
        settings.putInteger("dbPortNumber", 3306);
        settings.putString("dbDatabaseName", "zoneofuprising");
    }

    private void loadSavedSettings(AppSettings settings)
    {
        try {
            settings.load("cz.ascaria.zoneofuprising.central");
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, "Load settings failed", ex);
        }
    }
}
