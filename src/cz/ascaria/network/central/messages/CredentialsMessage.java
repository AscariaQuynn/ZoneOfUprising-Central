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
public class CredentialsMessage extends AbstractMessage {

    public String email;
    public String password;

    public CredentialsMessage() {
        super(true);
    }

    /**
     * Login to central server.
     * @param email
     * @param password
     */
    public CredentialsMessage(String email, String password) {
        super(true);
        this.email = email;
        this.password = password;
    }
}
