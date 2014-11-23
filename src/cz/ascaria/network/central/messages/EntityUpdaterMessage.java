/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.updaters.EntityUpdater;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class EntityUpdaterMessage extends AbstractMessage {

    public EntityUpdater entityUpdater;
    public String error = "[unknown]";
    

    /**
     * Entity Update.
     */
    public EntityUpdaterMessage() {
        super(true);
    }

    /**
     * Entity Update.
     * @param entityUpdater
     */
    public EntityUpdaterMessage(EntityUpdater entityUpdater) {
        super(true);
        this.entityUpdater = entityUpdater;
    }
}
