/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.models;

import com.jme3.system.AppSettings;
import cz.ascaria.network.central.Main;
import cz.ascaria.network.central.profiles.ServerProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *
 * @author Ascaria Quynn
 */
public class ServerProfileModel extends BaseModel {

    @Override
    public void initialize(AppSettings settings) {
        super.initialize(settings);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    /**
     * Is given server trusted?
     * @param serverProfile
     * @return
     */
    public boolean isTrusted(ServerProfile serverProfile) {
        if(null != serverProfile) {
            ServerProfile dbServerProfile = getServerProfile(serverProfile.getIdServerProfile());
            if(null != dbServerProfile && dbServerProfile.isTrusted() && dbServerProfile.equals(serverProfile)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns server's profile by token.
     * @param email
     * @param password
     * @return
     */
    public ServerProfile getServerProfile(String token) {
        // Create sql string
        String sql = "select idServerProfile, name, state, created, lastLogin"
            + " from serverprofile where token = ?";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            // Execute query and create user profile
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new ServerProfile(rs);
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns server's profile by id.
     * @param idServerProfile
     * @return
     */
    public ServerProfile getServerProfile(int idServerProfile) {
        // Create sql string
        String sql = "select idServerProfile, name, state, created, lastLogin"
            + " from serverprofile where idServerProfile = ?";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idServerProfile);
            // Execute query and create user profile
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new ServerProfile(rs);
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
