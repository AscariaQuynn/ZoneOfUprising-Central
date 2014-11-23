/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.ServerProfile;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class ServerProfileMessage extends AbstractMessage {

    public ServerProfile serverProfile;
    public String error = "[unknown]";

    public ServerProfileMessage() {
        super(true);
    }

    /**
     * Login to central server succeded.
     * @param serverProfile
     */
    public ServerProfileMessage(ServerProfile serverProfile) {
        super(true);
        this.serverProfile = serverProfile;
    }

    /**
     * Login to central server failed.
     * @param error
     */
    public ServerProfileMessage(String error) {
        super(true);
        this.error = error;
    }
}
