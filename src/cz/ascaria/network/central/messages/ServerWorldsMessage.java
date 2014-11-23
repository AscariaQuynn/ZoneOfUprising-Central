/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.ServerProfile;
import cz.ascaria.network.central.profiles.WorldProfile;
import java.util.ArrayList;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class ServerWorldsMessage extends AbstractMessage {

    public ServerProfile serverProfile;

    public ArrayList<WorldProfile> worlds;
    public String error = "[unknown]";

    public ServerWorldsMessage() {
        super(true);
    }

    /**
     * Request server world profiles from given server profile.
     * @param serverProfile
     */
    public ServerWorldsMessage(ServerProfile serverProfile) {
        super(true);
        this.serverProfile = serverProfile;
    }
}
