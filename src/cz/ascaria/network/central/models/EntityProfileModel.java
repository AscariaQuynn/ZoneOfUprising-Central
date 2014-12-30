/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.models;

import com.jme3.system.AppSettings;
import cz.ascaria.network.central.Main;
import cz.ascaria.network.central.profiles.EntityItem;
import cz.ascaria.network.central.profiles.EntityProfile;
import cz.ascaria.network.central.profiles.UserProfile;
import cz.ascaria.network.central.profiles.WorldProfile;
import cz.ascaria.network.central.profiles.updaters.EntityUpdater;
import cz.ascaria.network.central.utils.FasterMath;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Ascaria Quynn
 */
public class EntityProfileModel extends BaseModel {

    @Override
    public void initialize(AppSettings settings) {
        super.initialize(settings);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    /**
     * Adds items to entity.
     * @param entityProfile
     * @return
     */
    public EntityProfile fillEntityItems(EntityProfile entityProfile) {
        // Create sql string
        String sql = "select ei.idEntityItem, ei.properties as eiproperties,"
                    + " i.idItem, i.name, i.type, i.path, i.mass, i.price, i.properties, i.created"
            + " from item i"
            + " inner join entityitem ei on ei.idItem = i.idItem"
            + " where ei.idEntityProfile = ?"
            + " limit 99";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, entityProfile.getIdEntityProfile());
            // Execute query and create entity's items
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                EntityItem entityItem = new EntityItem(rs);
                entityItem.getPropertyFloat("a", 0f);
                entityProfile.addItem(entityItem);
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        // Return entity with items
        return entityProfile;
    }

    /**
     * Return entity items by type.
     * @param type
     * @return
     */
    public ArrayList<EntityItem> getEntityItemsByType(EntityItem.Type type) {
        // Create array for entity items by type
        ArrayList<EntityItem> entityItems = new ArrayList<EntityItem>();
        // Create sql string
        String sql = "select 0 as idEntityItem, \"\" as eiproperties, i.idItem, i.name, i.type, i.path, i.mass, i.price, i.properties, i.created"
            + " from item i"
            + " where i.type = ?"
            + " limit 99";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type.name());
            // Execute query and add entity items
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                EntityItem entityItem = new EntityItem(rs);
                entityItem.getPropertyFloat("a", 0f);
                entityItems.add(entityItem);
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        // Return entity items
        return entityItems;
    }

    /**
     * Has given User Profile selected Entity Profile?
     * @param userProfile
     * @return
     */
    public boolean hasEntityProfile(UserProfile userProfile) {
        return null != userProfile && null != getEntityProfile(userProfile);
    }

    /**
     * Returns selected Entity Profile for given User Profile.
     * @param userProfile
     * @return returns null if no selected entity was found
     */
    public EntityProfile getEntityProfile(UserProfile userProfile) {
        // Create sql string
        String sql = "select ep.idEntityProfile, ep.idUserProfile, ep.idWorldProfile,"
                    + " ep.isSelected, ep.name, ep.created, ep.experience,"
                    + " e.path, e.mass, e.hitPoints, e.gunSlots, e.missileSlots, e.containerName, e.spawnRadius"
            + " from entity e"
            + " inner join entityprofile ep on ep.idEntity = e.idEntity"
            + " where ep.idUserProfile = ? and ep.isSelected = ?"
            + " limit 1";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userProfile.getIdUserProfile());
            stmt.setInt(2, 1);
            // Execute query and create user profile
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return fillEntityItems(new EntityProfile(rs));
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns Entity Profile.
     * @param worldProfile
     * @param idEntityProfile
     * @return returns null if no entity was found
     */
    public EntityProfile getEntityProfile(WorldProfile worldProfile, int idEntityProfile) {
        // Create sql string
        String sql = "select ep.idEntityProfile, ep.idUserProfile, ep.idWorldProfile,"
                    + " ep.isSelected, ep.name, ep.created, ep.experience,"
                    + " e.path, e.mass, e.hitPoints, e.gunSlots, e.missileSlots, e.containerName, e.spawnRadius"
            + " from entity e"
            + " inner join entityprofile ep on ep.idEntity = e.idEntity"
            + " where ep.idWorldProfile = ? and ep.idEntityProfile = ?"
            + " limit 1";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, worldProfile.getIdWorldProfile());
            stmt.setInt(2, idEntityProfile);
            // Execute query and create user profile
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return fillEntityItems(new EntityProfile(rs));
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns user's fleet.
     * @param userProfile
     * @return
     */
    public ArrayList<EntityProfile> getUserFleet(UserProfile userProfile) {
        // Create fleet container
        ArrayList<EntityProfile> fleet = new ArrayList<EntityProfile>();
        // Create sql string
        String sql = "select ep.idEntityProfile, ep.idUserProfile, ep.idWorldProfile,"
                    + " ep.isSelected, ep.name, ep.created, ep.experience,"
                    + " e.path, e.mass, e.hitPoints, e.gunSlots, e.missileSlots, e.containerName, e.spawnRadius"
            + " from entity e"
            + " inner join entityprofile ep on ep.idEntity = e.idEntity"
            + " where ep.idUserProfile = ?"
            + " order by ep.idEntityProfile asc"
            + " limit 99";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userProfile.getIdUserProfile());
            // Execute query and create user fleet
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                fleet.add(fillEntityItems(new EntityProfile(rs)));
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        // Return fleet
        return fleet;
    }

    /**
     * Returns user's fleet.
     * @param userProfile
     * @return
     */
    public ArrayList<EntityProfile> getWorldFleet(WorldProfile worldProfile) {
        // Create fleet container
        ArrayList<EntityProfile> fleet = new ArrayList<EntityProfile>();
        // Create sql string
        String sql = "select ep.idEntityProfile, ep.idUserProfile, ep.idWorldProfile,"
                    + " ep.isSelected, ep.name, ep.created, ep.experience,"
                    + " e.path, e.mass, e.hitPoints, e.gunSlots, e.missileSlots, e.containerName, e.spawnRadius"
            + " from entity e"
            + " inner join entityprofile ep on ep.idEntity = e.idEntity"
            + " where ep.idWorldProfile = ?"
            + " order by ep.idEntityProfile asc"
            + " limit 99";
        // Create connection and prepared statement
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, worldProfile.getIdWorldProfile());
            // Execute query and create user fleet
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                fleet.add(fillEntityItems(new EntityProfile(rs)));
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        // Return fleet
        return fleet;
    }

    /**
     * Selects given entity for given user.
     * @param userProfile
     * @param entityProfile
     * @return was entity selected?
     */
    public boolean selectEntity(UserProfile userProfile, EntityProfile entityProfile) {
        try {
            // Start transaction
            Connection conn = getConnection();
            conn.setAutoCommit(false);
            // Create sql string for reseting selected entity
            String sql = "update entityprofile"
                + " set isSelected = ?"
                + " where idUserProfile = ? and (isSelected = ? or idEntityProfile = ?)";
            // Create prepared statement for batch operations
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                // Unselect selected entity
                stmt.setInt(1, 0);
                stmt.setInt(2, userProfile.getIdUserProfile());
                stmt.setInt(3, 1);
                stmt.setInt(4, 0); // this cancels "idEntityProfile = ?", because no user entity have id 0
                stmt.addBatch();
                // Select given entity
                stmt.setInt(1, 1);
                stmt.setInt(2, userProfile.getIdUserProfile());
                stmt.setInt(3, 1); // this cancels "isSelected = ?", because no user entity is selected after previous query
                stmt.setInt(4, entityProfile.getIdEntityProfile());
                stmt.addBatch();
                // Execute batch statement and check if all statements modified the database
                long sum = FasterMath.sum(stmt.executeBatch());
                if(sum == 0) {
                    throw new Exception("Selecting user entity failed.");
                }
                // Commit transaction
                conn.commit();
                return true;
            } catch(Exception ex) {
                conn.rollback();
                Main.LOG.log(Level.SEVERE, null, ex);
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateEntity(EntityUpdater entityUpdater) {
        try {
            // Start transaction
            Connection conn = getConnection();
            conn.setAutoCommit(false);
            // Create sql string for reseting selected entity
            String sql = "update entityprofile"
                + " set experience = ?"
                + " where idEntityProfile = ?";
            // Create prepared statement for batch operations
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, entityUpdater.getExperience());
                stmt.setInt(2, entityUpdater.getIdEntityProfile());
                stmt.executeUpdate();
                // Commit transaction
                conn.commit();
                return true;
            } catch(Exception ex) {
                conn.rollback();
                Main.LOG.log(Level.SEVERE, null, ex);
            }
        } catch(Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
