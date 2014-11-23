/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.ServerProfile;
import cz.ascaria.network.central.profiles.WorldProfile;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class WorldProfileMessage extends AbstractMessage {

    public ServerProfile serverProfile;
    public int idWorldProfile = 0;

    public WorldProfile worldProfile;
    public String error = "[unknown]";

    public WorldProfileMessage() {
        super(true);
    }

    /**
     * Request world profile from given server profile.
     * @param serverProfile
     */
    public WorldProfileMessage(ServerProfile serverProfile) {
        super(true);
        this.serverProfile = serverProfile;
    }

    /**
     * Request world profile from given server profile by id.
     * @param serverProfile
     * @param idWorldProfile
     */
    public WorldProfileMessage(ServerProfile serverProfile, int idWorldProfile) {
        super(true);
        this.serverProfile = serverProfile;
        this.idWorldProfile = idWorldProfile;
    }

    /**
     * Send world profile only.
     * @param serverProfile
     */
    public WorldProfileMessage(WorldProfile worldProfile) {
        super(true);
        this.worldProfile = worldProfile;
    }
}
