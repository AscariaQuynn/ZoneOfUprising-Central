/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.EntityProfile;
import cz.ascaria.network.central.profiles.UserProfile;
import cz.ascaria.network.central.profiles.WorldProfile;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class EntityProfileMessage extends AbstractMessage {

    public UserProfile userProfile;
    public WorldProfile worldProfile;
    public int idEntityProfile = 0;

    public EntityProfile entityProfile;
    public String error = "[unknown]";

    public EntityProfileMessage() {
        super(true);
    }

    /**
     * Request selected entity profile from given user profile.
     * @param userProfile
     */
    public EntityProfileMessage(UserProfile userProfile) {
        super(true);
        this.userProfile = userProfile;
    }

    /**
     * Request entity profile from given world profile and id entity profile.
     * @param worldProfile
     * @param idEntityProfile
     */
    public EntityProfileMessage(WorldProfile worldProfile, int idEntityProfile) {
        super(true);
        this.worldProfile = worldProfile;
        this.idEntityProfile = idEntityProfile;
    }
}
