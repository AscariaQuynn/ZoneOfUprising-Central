/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.UserProfile;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class UserProfileMessage extends AbstractMessage {

    public UserProfile userProfile;
    public String error = "[unknown]";

    public UserProfileMessage() {
        super(true);
    }

    /**
     * Login to central server succeded.
     * @param userProfile
     */
    public UserProfileMessage(UserProfile userProfile) {
        super(true);
        this.userProfile = userProfile;
    }

    /**
     * Login to central server failed.
     * @param error
     */
    public UserProfileMessage(String error) {
        super(true);
        this.error = error;
    }
}
