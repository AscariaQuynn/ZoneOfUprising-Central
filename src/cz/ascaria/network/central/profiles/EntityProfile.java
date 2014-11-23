/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.profiles;

import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.updaters.EntityUpdater;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class EntityProfile implements Profile {

    private int idEntityProfile = 0;
    private int idUserProfile = 0;
    private int idWorldProfile = 0;
    private boolean isSelected = false;
    private String name = "";
    private String path = "";
    private float mass = 1f;
    private float hitPoints = 1f;
    private int gunSlots = 0;
    private int missileSlots = 0;
    private String containerName = "shipsNode";
    private float spawnRadius = 1f;
    private Date created = new Date(0);

    private int experience = 0;

    private LinkedList<EntityItem> items = new LinkedList<EntityItem>();

    public EntityProfile() {
    }

    /**
     * @param rs
     * @throws SQLException
     */
    public EntityProfile(ResultSet rs) throws SQLException {
        this.idEntityProfile = rs.getInt("idEntityProfile");
        this.idUserProfile = rs.getInt("idUserProfile");
        this.idWorldProfile = rs.getInt("idWorldProfile");
        this.isSelected = rs.getBoolean("isSelected");
        this.name = rs.getString("name");
        this.path = rs.getString("path");
        this.mass = rs.getFloat("mass");
        this.hitPoints = rs.getFloat("hitPoints");
        this.gunSlots = rs.getInt("gunSlots");
        this.missileSlots = rs.getInt("missileSlots");
        this.containerName = rs.getString("containerName");
        this.spawnRadius = rs.getFloat("spawnRadius");
        this.created = rs.getDate("created");

        this.experience = rs.getInt("experience");
    }

    public void addItem(EntityItem item) {
        items.add(item);
    }

    public void removeItem(EntityItem item) {
        items.remove(item);
    }

    public LinkedList<EntityItem> getItems() {
        return items;
    }

    /**
     * Primary ID.
     * @return
     */
    public int getIdEntityProfile() {
        return idEntityProfile;
    }

    /**
     * ID of user which this entity belongs to, or 0 if it is not any users' entity.
     * @return
     */
    public int getIdUserProfile() {
        return idUserProfile;
    }

    /**
     * ID of world which this entity belongs to, or 0 if it is not any worlds' entity.
     * @return
     */
    public int getIdWorldProfile() {
        return idWorldProfile;
    }

    @Override
    public int getUniqueId() {
        return idEntityProfile;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public float getMass() {
        return mass;
    }

    public float getHitPoints() {
        return hitPoints;
    }

    public int getGunSlots() {
        return gunSlots;
    }

    public int getMissileSlots() {
        return missileSlots;
    }

    public String getContainerName() {
        return containerName;
    }

    public float getSpawnRadius() {
        return spawnRadius;
    }

    public Date getCreated() {
        return created;
    }

    public int getExperience() {
        return experience;
    }

    public void updateEntity(EntityUpdater entityUpdater) {
        // Update experience
        experience = entityUpdater.getExperience();
    }

    @Override
    public String toString() {
        return "[" + idEntityProfile + ", " + name + ", " + path + "]";
    }

    /**
     * Two entity profiles are equal, when they have same id, name and path.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityProfile && idEntityProfile == ((EntityProfile)obj).idEntityProfile && name.equals(((EntityProfile)obj).name) && path.equals(((EntityProfile)obj).path);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.idEntityProfile;
        return hash;
    }
}
