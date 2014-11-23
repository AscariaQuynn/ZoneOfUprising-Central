/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.EntityProfile;
import cz.ascaria.network.central.profiles.UserProfile;
import java.util.ArrayList;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class UserFleetMessage extends AbstractMessage {

    public UserProfile userProfile;

    public ArrayList<EntityProfile> fleet;
    public String error = "[unknown]";

    public UserFleetMessage() {
        super(true);
    }

    /**
     * User Fleet.
     * @param userProfile
     */
    public UserFleetMessage(UserProfile userProfile) {
        super(true);
        this.userProfile = userProfile;
    }
}
