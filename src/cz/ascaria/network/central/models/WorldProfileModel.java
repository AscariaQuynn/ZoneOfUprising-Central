/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.models;

import com.jme3.system.AppSettings;
import cz.ascaria.network.central.Main;
import cz.ascaria.network.central.profiles.ServerProfile;
import cz.ascaria.network.central.profiles.WorldProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Ascaria Quynn
 */
public class WorldProfileModel extends BaseModel {

    @Override
    public void initialize(AppSettings settings) {
        super.initialize(settings);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    /**
     * Returns World Profile for given Server Profile.
     * @param serverProfile
     * @param idWorldProfile 0 for skip
     * @return
     */
    public WorldProfile getWorldProfile(ServerProfile serverProfile, int idWorldProfile) {
        // Create sql string
        String sql = "select w.name, w.path, w.type, w.created,"
            + " wp.idWorldProfile, wp.idServerProfile"
            + " from world w"
            + " inner join worldprofile wp on wp.idWorld = w.idWorld"
            + " where wp.idServerProfile = ? and" + (idWorldProfile > 0 ? " wp.idWorldProfile = ?" : " wp.isSelected = ?")
            + " limit 1";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, serverProfile.getIdServerProfile());
            stmt.setInt(2, idWorldProfile > 0 ? idWorldProfile : 1);
            // Execute query and create user profile
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new WorldProfile(rs);
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns server's worlds.
     * @param serverProfile
     * @return
     */
    public ArrayList<WorldProfile> getServerWorlds(ServerProfile serverProfile) {
        // Create worlds container
        ArrayList<WorldProfile> worlds = new ArrayList<WorldProfile>();
        // Create sql string
        String sql = "select wp.idWorldProfile, wp.idServerProfile,"
                    + " w.name, w.path, w.type, w.created"
            + " from world w"
            + " inner join worldprofile wp on wp.idWorld = w.idWorld"
            + " where wp.idServerProfile = ?"
            + " order by wp.idWorldProfile asc"
            + " limit 99";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, serverProfile.getIdServerProfile());
            // Execute query and create server worlds
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                worlds.add(new WorldProfile(rs));
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        // Return worlds
        return worlds;
    }
}
