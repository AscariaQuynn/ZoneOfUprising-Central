/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.EntityProfile;
import cz.ascaria.network.central.profiles.WorldProfile;
import java.util.ArrayList;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class WorldFleetMessage extends AbstractMessage {

    public WorldProfile worldProfile;

    public ArrayList<EntityProfile> fleet;
    public String error = "[unknown]";

    public WorldFleetMessage() {
        super(true);
    }

    /**
     * World Fleet.
     * @param worldProfile
     */
    public WorldFleetMessage(WorldProfile worldProfile) {
        super(true);
        this.worldProfile = worldProfile;
    }
}
