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
public class TokenMessage extends AbstractMessage {

    public String token;

    public TokenMessage() {
        super(true);
    }

    /**
     * Login to central server.
     * @param token
     */
    public TokenMessage(String token) {
        super(true);
        this.token = token;
    }
}
