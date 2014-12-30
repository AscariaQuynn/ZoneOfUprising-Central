/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.profiles.EntityItem;
import java.util.ArrayList;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class EntityItemsByTypeMessage extends AbstractMessage {

    public EntityItem.Type type;

    public ArrayList<EntityItem> items;
    public String error = "[unknown]";

    public EntityItemsByTypeMessage() {
        super(true);
    }

    /**
     * User Fleet.
     * @param userProfile
     */
    public EntityItemsByTypeMessage(EntityItem.Type type) {
        super(true);
        this.type = type;
    }
}
