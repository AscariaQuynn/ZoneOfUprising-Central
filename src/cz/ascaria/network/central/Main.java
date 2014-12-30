/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central;

import com.jme3.app.AppTask;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import com.jme3.system.AppSettings;
import cz.ascaria.network.central.messages.AlertMessage;
import cz.ascaria.network.central.messages.AuthUserProfileMessage;
import cz.ascaria.network.central.messages.CentralMessagesRegistrator;
import cz.ascaria.network.central.messages.CredentialsMessage;
import cz.ascaria.network.central.messages.EntityItemsByTypeMessage;
import cz.ascaria.network.central.messages.EntityProfileMessage;
import cz.ascaria.network.central.messages.EntityUpdaterMessage;
import cz.ascaria.network.central.messages.SelectEntityMessage;
import cz.ascaria.network.central.messages.ServerProfileMessage;
import cz.ascaria.network.central.messages.ServerWorldsMessage;
import cz.ascaria.network.central.messages.TokenMessage;
import cz.ascaria.network.central.messages.UserFleetMessage;
import cz.ascaria.network.central.messages.UserProfileMessage;
import cz.ascaria.network.central.messages.WorldFleetMessage;
import cz.ascaria.network.central.messages.WorldProfileMessage;
import cz.ascaria.network.central.profiles.ServerProfile;
import cz.ascaria.network.central.profiles.UserProfile;
import cz.ascaria.network.central.utils.SettingsLoader;
import cz.ascaria.network.central.models.EntityProfileModel;
import cz.ascaria.network.central.models.ServerProfileModel;
import cz.ascaria.network.central.models.UserProfileModel;
import cz.ascaria.network.central.models.WorldProfileModel;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ascaria Quynn
 */
public class Main {

    /**
     * Logger for whole app
     */
    final public static Logger LOG = Logger.getLogger("cz.ascaria.network.central");

    private AppSettings settings;

    private int centralServerPort = 6540;
    private ServerWrapper centralServer = new ServerWrapper(centralServerPort);
    private CentralServerListener centralServerListener;

    /**
     * Console for central server.
     */
    private Console console;
    private boolean showConsole = false;

    private ScheduledThreadPoolExecutor executor2 = new ScheduledThreadPoolExecutor(4);

    private final ConcurrentLinkedQueue<AppTask<?>> taskQueue = new ConcurrentLinkedQueue<AppTask<?>>();

    private HashMap<HostedConnection, UserProfile> userProfiles = new HashMap<HostedConnection, UserProfile>();
    private HashMap<HostedConnection, ServerProfile> serverProfiles = new HashMap<HostedConnection, ServerProfile>();

    private UserProfileModel userProfileModel;
    private ServerProfileModel serverProfileModel;
    private WorldProfileModel worldProfileModel;
    private EntityProfileModel entityProfileModel;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Console mode switch
        boolean showConsole = args.length > 0 && args[0].equals("-showConsole");

        logToFile("central");
        Main main = new Main();
        main.setShowConsole(showConsole);
        main.run();

    }

    private static void logToFile(String filePart) {
        try {  
            // Save logs to file, this block configure the logger with handler and formatter
            FileHandler fh = new FileHandler(System.getProperty("user.dir") + "/zou-" + filePart + "-" + System.getProperty("user.name") + ".log");  
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());  
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Returns central server.
     * @return
     */
    public ServerWrapper getCentralServer() {
        return centralServer;
    }

    public ScheduledThreadPoolExecutor getExecutor2() {
        return executor2;
    }

    public Console getConsole() {
        return console;
    }

    /**
     * Is this HostedConnection an user profile?
     * @param conn
     * @return
     */
    public boolean isUserProfile(HostedConnection conn) {
        UserProfile userProfile = conn.getAttribute("userProfile");
        return null != userProfile;
    }

    /**
     * Do user profile exist?
     * @param userProfile
     * @return
     */
    public boolean userProfileExist(UserProfile userProfile) {
        return userProfiles.containsValue(userProfile);
    }

    /**
     * Do user profile exist?
     * @param userProfile
     * @return
     */
    public boolean userProfileExist(HostedConnection source) {
        return userProfiles.containsValue((UserProfile)source.getAttribute("userProfile"));
    }

    /**
     * Add new user profile.
     * @param conn
     * @param userProfile
     */
    public void addUserProfile(HostedConnection conn, UserProfile userProfile) {
        conn.setAttribute("userProfile", userProfile);
        userProfiles.put(conn, userProfile);
    }

    /**
     * Remove user profile.
     * @param conn
     */
    public void removeUserProfile(HostedConnection conn) {
        userProfiles.remove(conn);
    }

    /**
     * Returns user profile by user name.
     * @param userName
     * @return
     */
    public UserProfile getUserProfile(String userName) {
        for(UserProfile userProfile : userProfiles.values()) {
            if(userProfile.getName().equalsIgnoreCase(userName)) {
                return userProfile;
            }
        }
        return null;
    }

    /**
     * Returns user profile by hosted connection.
     * @param source
     * @return
     */
    public UserProfile getUserProfile(HostedConnection source) {
        return source.getAttribute("userProfile");
    }



    /**
     * Is this HostedConnection an server profile?
     * @param conn
     * @return
     */
    public boolean isServerProfile(HostedConnection conn) {
        ServerProfile serverProfile = conn.getAttribute("serverProfile");
        return null != serverProfile;
    }

    /**
     * Do server profile exist?
     * @param serverProfile
     * @return
     */
    public boolean serverProfileExist(ServerProfile serverProfile) {
        return serverProfiles.containsValue(serverProfile);
    }

    /**
     * Add new server profile.
     * @param conn
     * @param serverProfile
     */
    public void addServerProfile(HostedConnection conn, ServerProfile serverProfile) {
        conn.setAttribute("serverProfile", serverProfile);
        serverProfiles.put(conn, serverProfile);
    }

    /**
     * Remove server profile.
     * @param conn
     */
    public void removeServerProfile(HostedConnection conn) {
        serverProfiles.remove(conn);
    }

    /**
     * Returns server profile by server name.
     * @param serverName
     * @return
     */
    public ServerProfile getServerProfile(String serverName) {
        for(ServerProfile serverProfile : serverProfiles.values()) {
            if(serverProfile.getName().equalsIgnoreCase(serverName)) {
                return serverProfile;
            }
        }
        return null;
    }

    /**
     * Returns server profile by hosted connection.
     * @param source
     * @return
     */
    public ServerProfile getServerProfile(HostedConnection source) {
        return source.getAttribute("serverProfile");
    }

    /**
     * Show console?
     * @param showConsole
     */
    private void setShowConsole(boolean showConsole) {
        this.showConsole = showConsole;
    }

    /**
     * Run Central Server.
     */
    public void run() {

        // Register all network messages once
        CentralMessagesRegistrator.registerMessages();

        // Initialize settings
        settings = new SettingsLoader().load();

        // Initialize models
        userProfileModel = new UserProfileModel();
        userProfileModel.initialize(settings);
        serverProfileModel = new ServerProfileModel();
        serverProfileModel.initialize(settings);
        worldProfileModel = new WorldProfileModel();
        worldProfileModel.initialize(settings);
        entityProfileModel = new EntityProfileModel();
        entityProfileModel.initialize(settings);

        // Initialize console
        console = new Console();
        if(showConsole) {
            console.initialize(this);
            console.show();
        }
        console.print("Zone of Uprising Central Server version 0.1 pre alpha");
        try {
            centralServerListener = new CentralServerListener(centralServer);
            centralServerListener.initialize();
            centralServer.initialize(this);
            centralServer.run();
        } catch (Exception ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
            console.println(ex.getLocalizedMessage());
            // Shutdown console
            //console.shutdown();
            // Stop central server
            centralServerListener.cleanup();
            centralServerListener = null;
            centralServer.stop();
        }
    }

    public void stop() {
        // Shutdown console
        console.shutdown();
        // Stop central server
        centralServerListener.cleanup();
        centralServerListener = null;
        centralServer.stop();
        // Stop executor
        executor2.shutdown();
    }

    /**
     * Enqueues a runnable object to execute in the UI thread.
     * Unlike the rest of Swing, this method can be invoked from any thread.
     */
    public void enqueue(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }



    /**
     * Class for responding to network messages.
     */
    private class CentralServerListener implements ConnectionListener, MessageListener<HostedConnection> {

        private ServerWrapper centralServer;

        public CentralServerListener(ServerWrapper centralServer) {
            this.centralServer = centralServer;
        }

        /**
         * Initialize Central Server Listener.
         * @param app
         */
        public void initialize() {
            centralServer.addConnectionListener(this);
            // Prepare for receiving messages
            centralServer.addMessageListener(this,
                CredentialsMessage.class,
                AuthUserProfileMessage.class,
                TokenMessage.class,
                EntityProfileMessage.class,
                EntityUpdaterMessage.class,
                UserFleetMessage.class,
                WorldFleetMessage.class,
                WorldProfileMessage.class,
                ServerWorldsMessage.class,
                SelectEntityMessage.class,
                EntityItemsByTypeMessage.class
            );
        }

        public void cleanup() {
            centralServer.removeMessageListener(this);
            centralServer.removeConnectionListener(this);
        }

        @Override
        public void connectionAdded(Server server, HostedConnection conn) {
            console.println("Client " + conn.getId() + " connected at " + conn.getAddress());
            // Save client
            //this.client = client;
            // TODO: odebrat klienta pokud se neautorizuje do 30sec
        }

        @Override
        public void connectionRemoved(Server server, HostedConnection conn) {
            // Remove client
            //this.client = null;
            console.println("Client " + conn.getId() + " disconnected.");

            if(isServerProfile(conn)) {
                removeServerProfile(conn);
                console.println("Server profile for connection " + conn.getId() + " " + conn.getAttribute("serverProfile") + " was removed.");
            } else if(isUserProfile(conn)) {
                removeUserProfile(conn);
                console.println("User profile for connection " + conn.getId() + " " + conn.getAttribute("userProfile") + " was removed.");
            } else {
                console.println("Profile for connection " + conn.getId() + " was not found.");
            }
        }

        /**
         * Forward received messages.
         * @param source
         * @param m
         */
        @Override
        public void messageReceived(final HostedConnection source, final Message m) {
            enqueue(new Runnable() {
                @Override
                public void run() {
                    if(m instanceof CredentialsMessage) {
                        credentialsMessage(source, (CredentialsMessage)m);
                    } else if(m instanceof AuthUserProfileMessage) {
                        authUserProfileMessage(source, (AuthUserProfileMessage)m);
                    } else if(m instanceof TokenMessage) {
                        tokenMessage(source, (TokenMessage)m);
                    } else if(m instanceof EntityProfileMessage) {
                        entityProfileMessage(source, (EntityProfileMessage)m);
                    } else if(m instanceof EntityUpdaterMessage) {
                        entityUpdaterMessage(source, (EntityUpdaterMessage)m);
                    } else if(m instanceof EntityItemsByTypeMessage) {
                        entityItemsByTypeMessage(source, (EntityItemsByTypeMessage)m);
                    } else if(m instanceof UserFleetMessage) {
                        userFleetMessage(source, (UserFleetMessage)m);
                    } else if(m instanceof WorldFleetMessage) {
                        worldFleetMessage(source, (WorldFleetMessage)m);
                    } else if(m instanceof WorldProfileMessage) {
                        worldProfileMessage(source, (WorldProfileMessage)m);
                    } else if(m instanceof ServerWorldsMessage) {
                        serverWorldsMessage(source, (ServerWorldsMessage)m);
                    }

                    if(m instanceof SelectEntityMessage) {
                        selectEntityMessage(source, (SelectEntityMessage)m);
                    }
                }
            });
        }

        /**
         * User wants to login.
         * @param source
         * @param m
         */
        public void credentialsMessage(HostedConnection source, CredentialsMessage m) {
            console.println("Received credentials message: " + m.email + " " + m.password.replaceAll("(?s).", "*"));

            // Read user profile from database
            UserProfile userProfile = userProfileModel.getUserProfile(m.email, m.password);
            if(null != userProfile) {
                // Check if user is active
                if(!userProfile.isActive()) {
                    console.println("User Profile " + userProfile + " is not active.");
                    source.close("User profile " + userProfile.getName() + " is not active.");
                    return;
                }
                // Check if user profile is already logged in
                if(userProfileExist(userProfile)) {
                    console.println("User profile " + userProfile + " exist, kicking...");
                    source.close("User profile " + userProfile.getName() + " is already logged in.");
                    return;
                }
                // Register user profile
                console.println("Registering " + userProfile + " (" + m.email + ") as game client (user) connection.");
                addUserProfile(source, userProfile);
                // Sent user profile to client
                console.println("Sending user profile to client: " + userProfile);
                source.send(new UserProfileMessage(userProfile));

            } else {
                console.println("User Profile not found, kicking...");
                source.close("User Profile not found.");
            }
        }

        /**
         * Server requests authorization of user profile.
         * @param source
         * @param m
         */
        public void authUserProfileMessage(HostedConnection source, AuthUserProfileMessage m) {
            try {
                if(null == m.serverProfile) {
                    throw new NullPointerException(m.error);
                }
                console.println("Received auth request for user " + m.userProfile + " from server " + m.serverProfile);
                // Compare profiles against database
                if(null == m.serverProfile || null == m.userProfile) {
                    throw new IllegalArgumentException("Server Profile or User Profile or both are missing.");
                }
                ServerProfile serverProfile = serverProfileModel.getServerProfile(m.serverProfile.getIdServerProfile());
                if(!m.serverProfile.equals(serverProfile)) {
                    throw new IllegalStateException("Invalid server profile.");
                }
                UserProfile userProfile = userProfileModel.getUserProfile(m.userProfile.getIdUserProfile());
                if(!m.userProfile.equals(userProfile)) {
                    throw new IllegalStateException("Invalid user profile.");
                }
                // Check if user have selected ship
                if(!entityProfileModel.hasEntityProfile(m.userProfile)) {
                    throw new IllegalStateException("User does not have selected ship.");
                }
                // If user is authorized successfully, return message back to game server
                console.println("Authorization of user " + m.userProfile + " from server " + m.serverProfile + " was successful.");
                source.send(m);
            } catch(Exception ex) {
                Main.LOG.log(Level.SEVERE, null, ex);
                console.println(ex.getLocalizedMessage());
                m.error = ex.getLocalizedMessage();
                source.send(m);
            }
        }

        /**
         * Server wants to login.
         * @param source
         * @param m
         */
        public void tokenMessage(HostedConnection source, TokenMessage m) {
            console.println("Received token message: " + m.token.replaceAll("(?s).", "*") + ", registering as game server connection.");

            // Read server profile from database
            ServerProfile serverProfile = serverProfileModel.getServerProfile(m.token);
            if(null != serverProfile) {
                // Check if server is active
                if(!serverProfile.isActive()) {
                    console.println("Server Profile " + serverProfile + " is not active.");
                    source.close("Server profile " + serverProfile.getName() + " is not active.");
                    return;
                }
                // Check if server profile is already logged in
                if(serverProfileExist(serverProfile)) {
                    console.println("Server profile " + serverProfile + " exist, kicking...");
                    source.close("Server profile " + serverProfile.getName() + " is already logged in.");
                    return;
                }
                // Register server profile
                console.println("Registering " + serverProfile + " (" + m.token + ") as game server connection.");
                addServerProfile(source, serverProfile);
                // Sent server profile to client
                console.println("Sending server profile to client: " + serverProfile);
                source.send(new ServerProfileMessage(serverProfile));

            } else {
                console.println("Server Profile not found, kicking...");
                source.close("Server Profile not found.");
            }
        }

        /**
         * Logged client/game server requests entity.
         * @param source
         * @param m
         */
        public void entityProfileMessage(HostedConnection source, EntityProfileMessage m) {
            if(null != m.userProfile) {
                console.println("Received request entity message for client: " + m.userProfile);
                m.entityProfile = entityProfileModel.getEntityProfile(m.userProfile);
            } else if(null != m.worldProfile && m.idEntityProfile > 0) {
                console.println("Received request entity message for world: " + m.worldProfile + "; with idEntityProfile: " + m.idEntityProfile);
                m.entityProfile = entityProfileModel.getEntityProfile(m.worldProfile, m.idEntityProfile);
            }

            // Send entity profile to client/game server
            if(null != m.entityProfile) {
                console.println("Sending entity profile: " + m.entityProfile);
                source.send(m);
            } else {
                console.println("Entity Profile not found for " + (null != m.userProfile ? "user profile " + m.userProfile : "") + (null != m.worldProfile ? "world profile " + m.worldProfile + "; with idEntityProfile: " + m.idEntityProfile : "") + ".");
                m.error = "Entity Profile not found for: " + (null != m.userProfile ? m.userProfile : "") + (null != m.worldProfile && m.idEntityProfile > 0 ? "id " + m.idEntityProfile : "");
                source.send(m);
            }
        }

        /**
         * Game server requests entity update.
         * @param source
         * @param m
         */
        public void entityUpdaterMessage(HostedConnection source, EntityUpdaterMessage m) {
            try {
                if(!serverProfileModel.isTrusted(getServerProfile(source))) {
                    throw new IllegalAccessException("Server " + getServerProfile(source) + " is not trusted.");
                }
                if(null == m.entityUpdater) {
                    throw new NullPointerException("EntityUpdater is null.");
                }
                // Update entity
                entityProfileModel.updateEntity(m.entityUpdater);
                console.println("Entity " + m.entityUpdater + " updated.");
            } catch(Exception ex) {
                Main.LOG.log(Level.SEVERE, null, ex);
                console.println(ex.getLocalizedMessage());
            }
        }

        /**
         * Client requests entity items by type.
         * @param source
         * @param m
         */
        public void entityItemsByTypeMessage(HostedConnection source, EntityItemsByTypeMessage m) {
            try {
                if(null == m.type) {
                    throw new NullPointerException("EntityItem.Type is null.");
                }
                // Check if user profile is already logged in
                UserProfile userProfile = getUserProfile(source);
                if(!userProfileExist(userProfile)) {
                    console.println("User profile does not exist, kicking...");
                    source.close("User profile for source " + source.getId() + " does not exist.");
                    return;
                }
                m.items = entityProfileModel.getEntityItemsByType(m.type);
                // Send entity items to user
                console.println("Sending entity items of type " + m.type + " to user " + userProfile + ".");
                source.send(m);
            } catch(Exception ex) {
                Main.LOG.log(Level.SEVERE, null, ex);
                console.println(ex.getLocalizedMessage());
                m.error = ex.getLocalizedMessage();
                source.send(m);
            }
        }

        /**
         * Logged client requests his fleet.
         * @param source
         * @param m
         */
        public void userFleetMessage(HostedConnection source, UserFleetMessage m) {
            console.println("Received request user fleet message from client: " + m.userProfile);

            m.fleet = entityProfileModel.getUserFleet(m.userProfile);

            // Send user fleet to client
            console.println("Sending user fleet with " + m.fleet.size() + " units to client: " + m.userProfile);
            source.send(m);
        }

        /**
         * Logged client requests his fleet.
         * @param source
         * @param m
         */
        public void worldFleetMessage(HostedConnection source, WorldFleetMessage m) {
            console.println("Received request world fleet message from client: " + getServerProfile(source));

            m.fleet = entityProfileModel.getWorldFleet(m.worldProfile);

            // Send user fleet to client
            console.println("Sending world fleet with " + m.fleet.size() + " units to client: " + getServerProfile(source));
            source.send(m);
        }

        /**
         * Logged client requests his selected world.
         * @param source
         * @param m
         */
        public void worldProfileMessage(HostedConnection source, WorldProfileMessage m) {
            console.println("Received request world message from client: " + m.serverProfile);

            // Read world profile from database
            m.worldProfile = worldProfileModel.getWorldProfile(m.serverProfile, m.idWorldProfile);
            if(null != m.worldProfile) {
                // Send world profile to client
                console.println("Sending world profile: " + m.worldProfile);
                source.send(m);
            } else {
                console.println("World Profile not found for server profile " + m.serverProfile + ".");
                m.error = "World Profile not found.";
                source.send(m);
            }
        }

        /**
         * Logged client requests his worlds.
         * @param source
         * @param m
         */
        public void serverWorldsMessage(HostedConnection source, ServerWorldsMessage m) {
            console.println("Received request server worlds message from client: " + m.serverProfile);

            m.worlds = worldProfileModel.getServerWorlds(m.serverProfile);

            // Send user fleet to client
            console.println("Sending server worlds with " + m.worlds.size() + " units to client: " + m.serverProfile);
            source.send(m);
        }

        /**
         * Selects entity for given client.
         * @param source
         * @param m
         */
        public void selectEntityMessage(HostedConnection source, SelectEntityMessage m) {
            console.println("Received select entity message for client: " + m.userProfile);
            UserProfile userProfile = source.getAttribute("userProfile");
            if(null != userProfile && userProfile.equals(m.userProfile)) {
                if(null != m.entityProfile) {
                    // Try to select entity
                    boolean wasSelected = entityProfileModel.selectEntity(m.userProfile, m.entityProfile);
                    if(wasSelected) {
                        console.println("Entity " + m.entityProfile + " was selected for user " + m.userProfile);
                        source.send(m);
                    } else {
                        console.println("Failed to select Entity " + m.entityProfile + " for user " + m.userProfile);
                        source.send(new AlertMessage("Entity " + m.entityProfile + " was not selected."));
                    }
                } else {
                    console.println("Error selecting entity. Entity Profile missing: " + m.error);
                    source.send(new AlertMessage("Error selecting entity. Entity Profile missing: " + m.error));
                }
            } else {
                console.println("User profile mismatch: required " + userProfile + " x given " + m.userProfile);
                source.close("User profile mismatch.");
            }
        }
    }
}
