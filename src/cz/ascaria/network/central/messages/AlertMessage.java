/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class AlertMessage extends AbstractMessage {

    public String title = "Alert message";
    public String message;

    public AlertMessage() {
        this("");
    }

    public AlertMessage(String message) {
        super(true);
        this.message = message;
    }

    public AlertMessage(String title, String message) {
        super(true);
        this.title = title;
        this.message = message;
    }
}
