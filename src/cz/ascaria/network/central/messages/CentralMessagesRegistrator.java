/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.messages;

import com.jme3.network.serializing.Serializer;
import cz.ascaria.network.central.profiles.EntityItem;
import cz.ascaria.network.central.profiles.EntityProfile;
import cz.ascaria.network.central.profiles.ServerProfile;
import cz.ascaria.network.central.profiles.UserProfile;
import cz.ascaria.network.central.profiles.WorldProfile;
import cz.ascaria.network.central.profiles.updaters.EntityUpdater;

/**
 *
 * @author Ascaria Quynn
 */
public class CentralMessagesRegistrator {
    /**
     * Registers all messages.
     */
    public static void registerMessages() {

        // Info messages
        Serializer.registerClass(AlertMessage.class);

        // Login classes
        Serializer.registerClass(CredentialsMessage.class);
        Serializer.registerClass(TokenMessage.class);
        Serializer.registerClass(AuthUserProfileMessage.class);

        // Profile classes
        Serializer.registerClass(WorldProfileMessage.class);
        Serializer.registerClass(EntityProfileMessage.class);
        Serializer.registerClass(EntityUpdaterMessage.class);
        Serializer.registerClass(SelectEntityMessage.class);
        Serializer.registerClass(ServerProfileMessage.class);
        Serializer.registerClass(WorldFleetMessage.class);
        Serializer.registerClass(ServerWorldsMessage.class);
        Serializer.registerClass(UserProfileMessage.class);
        Serializer.registerClass(UserFleetMessage.class);

        // Misc. classes
        Serializer.registerClasses(
            UserProfile.class,
            ServerProfile.class,
            WorldProfile.class,
            EntityProfile.class,
            EntityUpdater.class,
            EntityItem.class
        );
    }
}
