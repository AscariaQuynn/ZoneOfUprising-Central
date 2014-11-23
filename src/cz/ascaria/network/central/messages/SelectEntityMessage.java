/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.EntityProfile;
import cz.ascaria.network.central.profiles.UserProfile;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class SelectEntityMessage extends AbstractMessage {

    public UserProfile userProfile;
    public EntityProfile entityProfile;
    public String error = "[unknown]";

    public SelectEntityMessage() {
        super(true);
    }

    /**
     * Select Entity.
     * @param userProfile
     * @param entityProfile
     */
    public SelectEntityMessage(UserProfile userProfile, EntityProfile entityProfile) {
        super(true);
        this.userProfile = userProfile;
        this.entityProfile = entityProfile;
    }

    /**
     * Select Profile error.
     * @param error
     */
    public SelectEntityMessage(String error) {
        super(true);
        this.error = error;
    }
}
