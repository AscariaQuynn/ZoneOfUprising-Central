/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.ServerProfile;
import cz.ascaria.network.central.profiles.UserProfile;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class AuthUserProfileMessage extends AbstractMessage {

    public ServerProfile serverProfile;
    public UserProfile userProfile;
    public String error = "[unknown]";

    public AuthUserProfileMessage() {
        super(true);
    }

    /**
     * Requesting authorization of user profile on server.
     * @param serverProfile
     * @param userProfile
     */
    public AuthUserProfileMessage(ServerProfile serverProfile, UserProfile userProfile) {
        super(true);
        this.serverProfile = serverProfile;
        this.userProfile = userProfile;
    }

    /**
     * Request for authorization of user profile on server failed.
     * @param error
     */
    public AuthUserProfileMessage(String error) {
        super(true);
        this.error = error;
    }

    /**
     * Has any error occured?
     * @return
     */
    public boolean hasError() {
        return !"[unknown]".equals(error);
    }
}
