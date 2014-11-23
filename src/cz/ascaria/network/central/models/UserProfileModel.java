/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.models;

import com.jme3.system.AppSettings;
import cz.ascaria.network.central.Main;
import cz.ascaria.network.central.profiles.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *
 * @author Ascaria Quynn
 */
public class UserProfileModel extends BaseModel {

    @Override
    public void initialize(AppSettings settings) {
        super.initialize(settings);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    /**
     * Returns user's profile by credentials.
     * @param email
     * @param password
     * @return
     */
    public UserProfile getUserProfile(String email, String password) {
        // Create sql string
        String sql = "select idUserProfile, name, money, email, state, created, lastLogin"
            + " from userprofile where email = ? and password = ?";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            // Execute query and create user profile
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new UserProfile(rs);
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns user's profile by id.
     * @param id
     * @return
     */
    public UserProfile getUserProfile(int id) {
        // Create sql string
        String sql = "select idUserProfile, name, money, email, state, created, lastLogin"
            + " from userprofile where idUserProfile = ?";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            // Execute query and create user profile
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new UserProfile(rs);
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
