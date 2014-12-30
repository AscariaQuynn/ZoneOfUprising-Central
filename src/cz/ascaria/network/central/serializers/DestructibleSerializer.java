/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.serializers;

import com.jme3.network.serializing.Serializer;
import cz.ascaria.network.central.utils.PropertiesHelper;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author Ascaria Quynn
 */
public class DestructibleSerializer extends Serializer {

    /**
     * Destructible serializer doesn't return desired object, but always null.
     * @param data
     * @param c
     * @return
     * @throws IOException
     */
    @Override
    public PropertiesHelper readObject(ByteBuffer data, Class c) throws IOException {
        int i = 5;
        return null;
    }

    /**
     * Destructible serializer doesn't write anything from given object. Object is destroyed for transfer.
     * @param buffer
     * @param object
     * @throws IOException
     */
    @Override
    public void writeObject(ByteBuffer buffer, Object object) throws IOException {
        int i = 5;
    }
}
