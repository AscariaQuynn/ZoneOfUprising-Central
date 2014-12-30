/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.profiles.updaters;

import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.EntityProfile;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class EntityUpdater {

    public enum UnloadType {
        None("None"), // Do not unload
        Silent("Silent"), // Entity should disappear
        Explode("Explode"); // Entity should explode

        private String unloadType;

        UnloadType(String unloadType) {
            this.unloadType = unloadType;
        }

        @Override
        public String toString() {
            return unloadType;
        }
    }

    private int idEntityProfile;
    private String name;

    private float hitPoints;
    private int experience;

    private UnloadType unloadType;

    /**
     * Entity Updater.
     */
    public EntityUpdater() {
    }

    /**
     * Entity Updater.
     * @param entityProfile
     */
    public EntityUpdater(EntityProfile entityProfile) {
        this.idEntityProfile = entityProfile.getIdEntityProfile();
        this.name = entityProfile.getName();

        this.hitPoints = entityProfile.getHitPoints();
        this.experience = entityProfile.getExperience();

        this.unloadType = UnloadType.None;
    }

    public int getIdEntityProfile() {
        return idEntityProfile;
    }

    public String getName() {
        return name;
    }

    /**
     * Should we unload ship?
     * @param unloadType
     */
    public void setUnloadType(UnloadType unloadType) {
        this.unloadType = unloadType;
    }

    /**
     * Should we unload ship?
     * @param unloadType
     * @return
     */
    public boolean isUnloadType(UnloadType unloadType) {
        return this.unloadType == unloadType;
    }

    /**
     * Set hit points for update. When hit points reaches zero, unload type explode is automatically set.
     * @param hitPoints
     * @throws IllegalArgumentException if hit points are negative, infinite or nan.
     */
    public void setHitPoints(float hitPoints) {
        if(Float.isInfinite(hitPoints) || Float.isNaN(hitPoints)) {
            throw new IllegalArgumentException("Hit Points should not be infinite, nor nan.");
        }
        this.hitPoints = Math.max(hitPoints, 0f);
        // When hp reach zero, entity should automatically explode
        if(this.hitPoints == 0f) {
            unloadType = UnloadType.Explode;
        }
    }

    /**
     * Return hitpoints.
     * @return
     * @throws IllegalStateException if hit points are not set via setHitPoints(float)
     */
    public float getHitPoints() {
        if(hitPoints == -1f) {
            throw new IllegalStateException("Hit points are not available for update, you should check it with shouldUpdateHitPoints()");
        }
        return hitPoints;
    }

    /**
     * Add experience.
     * @param experience
     */
    public void addExperience(int experience) {
        this.experience += experience;
    }

    /**
     * Return experience.
     * @return
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Updates this entity updater.
     * @param entityUpdater given entity updater must have same id and name
     */
    public void updateEntity(EntityUpdater entityUpdater) {
        if(!equals(entityUpdater)) {
            throw new IllegalArgumentException("Incompatible EntityUpdater given.");
        }
        hitPoints = entityUpdater.hitPoints;
        experience = entityUpdater.experience;

        unloadType = entityUpdater.unloadType;
    }

    @Override
    public String toString() {
        return "[" + idEntityProfile + ", " + name + "]";
    }

    /**
     * Two entity updaters are compatible, when they have same id and name.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityUpdater && idEntityProfile == ((EntityUpdater)obj).idEntityProfile && name.equals(((EntityUpdater)obj).name);
    }

    @Override
    public int hashCode() {
        int hash = 2;
        hash = 2 * hash + this.idEntityProfile;
        hash = 3 * hash + this.name.hashCode();
        return hash;
    }
}
