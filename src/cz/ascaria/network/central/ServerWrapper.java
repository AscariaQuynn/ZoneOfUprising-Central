/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central;

import com.jme3.network.ConnectionListener;
import com.jme3.network.Filter;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import cz.ascaria.network.central.ServerStateListener.StopInfo;
import cz.ascaria.network.central.profiles.UserProfile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 *
 * @author Ascaria Quynn
 */
public class ServerWrapper implements ServerStateListener {

    protected Main app;
    protected ScheduledThreadPoolExecutor executor2;
    protected Console console;

    private List<ServerConnectListener> connectListeners = new CopyOnWriteArrayList<ServerConnectListener>();
    private List<ServerStateListener> stateListeners = new CopyOnWriteArrayList<ServerStateListener>();
    private List<ConnectionListener> connectionListeners = new CopyOnWriteArrayList<ConnectionListener>();

    private List<Pair> messageListeners = new CopyOnWriteArrayList<Pair>();

    private Server server;

    private int port = 0;

    public ServerWrapper(int port) {
        if(port < 1) {
            throw new IllegalArgumentException("Port number must be greater than 0.");
        }
        this.port = port;
    }

    /**
     * Initialize Server Wrapper.
     * @param app
     */
    public void initialize(Main app) {
        this.app = app;
        this.console = app.getConsole();
        this.executor2 = app.getExecutor2();
    }

    /**
     * Add connect listener to listen any errors occured during server startup.
     * @param connectListener
     */
    public void addConnectListener(ServerConnectListener connectListener) {
        connectListeners.add(connectListener);
    }

    /**
     * Remove connect listener.
     * @param connectListener
     */
    public void removeConnectListener(ServerConnectListener connectListener) {
        connectListeners.remove(connectListener);
    }

    /**
     * Adds a listener that will be notified about server state changes.
     * @param listener
     */
    public void addServerStateListener(ServerStateListener listener) {
        stateListeners.add(listener);
    }

    /**
     * Removes a previously registered server state listener.
     * @param listener
     */
    public void removeServerStateListener(ServerStateListener listener) {
        stateListeners.remove(listener);
    }

    /**
     * Adds a listener that will be notified about incoming connections.
     * @param listener
     */
    public void addConnectionListener(ConnectionListener listener) {
        connectionListeners.add(listener);
        if(null != server) {
            server.addConnectionListener(listener);
        }
    }

    /**
     * Removes a previously registered connection listener.
     * @param listener
     */
    public void removeConnectionListener(ConnectionListener listener) {
        connectionListeners.remove(listener);
        if(null != server) {
            server.removeConnectionListener(listener);
        }
    }

    /**
     * Adds a listener that will be notified when any message or object
     * is received from one of the clients.
     * @param listener
     */
    public void addMessageListener(MessageListener<? super HostedConnection> listener) {
        if(listener == null) {
            throw new IllegalArgumentException("Listener cannot be null.");
        }
        messageListeners.add(new Pair(listener));
        if(null != server) {
            server.addMessageListener(listener);
        }
    }

    /**
     * Adds a listener that will be notified when messages of the specified
     * types are received from one of the clients.
     * @param listener
     * @param classes
     */
    public void addMessageListener(MessageListener<? super HostedConnection> listener, Class... classes) {
        if(listener == null) {
            throw new IllegalArgumentException("Listener cannot be null.");
        }
        messageListeners.add(new Pair(listener, classes));
        if(null != server) {
            server.addMessageListener(listener, classes);
        }
    }

    /**
     * Removes a previously registered wildcard listener. This does
     * not remove this listener from any type-specific registrations.
     * @param listener
     */
    public void removeMessageListener(MessageListener<? super HostedConnection> listener) {
        for(Pair pair : messageListeners) {
            if(pair.listener == listener) {
                messageListeners.remove(pair);
                break;
            }
        }
        if(null != server) {
            server.removeMessageListener(listener);
        }
    }

    /**
     * Return connected clients.
     * @return 
     */
    public Collection<HostedConnection> getConnections() {
        if(null == server) {
            throw new IllegalStateException("Central Server is not present.");
        }
        return server.getConnections();
    }

    /**
     * Returns hosted connection by user name.
     * @param name
     * @return
     */
    public HostedConnection getConnection(String name) {
        if(null != name) {
            for(HostedConnection hostedConnection : server.getConnections()) {
                UserProfile userProfile = hostedConnection.getAttribute("userProfile");
                if(null != userProfile && name.equals(userProfile.getName())) {
                    return hostedConnection;
                }
            }
        }
        return null;
    }

    /**
     * Is server running?
     * @return
     */
    public boolean isRunning() {
        return null != server && server.isRunning();
    }

    /**
     * Sends the specified message to all connected clients.
     * @param message
     * @return was message sent?
     */
    public boolean broadcast(Message message) {
        if(null != server) {
            server.broadcast(message);
            return true;
        }
        return false;
    }

    /**
     * Sends the specified message to all connected clients that match the filter.
     * If no filter is specified then this is the same as calling broadcast(message)
     * and the message will be delivered to all connections.
     * @param filter
     * @param message
     * @return was message sent?
     */
    public boolean broadcast(Filter<? super HostedConnection> filter, Message message) {
        if(null != server) {
            server.broadcast(filter, message);
            return true;
        }
        return false;
    }

    /**
     * Sends the specified message over the specified alternate channel to all connected 
     * clients that match the filter.  If no filter is specified then this is the same as
     * calling broadcast(message) and the message will be delivered to all connections.
     * @param channel
     * @param filter
     * @param message
     * @return was message sent?
     */
    public boolean broadcast(int channel, Filter<? super HostedConnection> filter, Message message) {
        if(null != server) {
            server.broadcast(filter, message);
            return true;
        }
        return false;
    }

    /**
     * Run game server.
     * @throws IllegalAccessException
     */
    public void run() throws IllegalAccessException {
        run(port);
    }

    /**
     * Run game server.
     * @param port
     * @throws IllegalAccessException
     */
    public void run(final int port) throws IllegalAccessException {
        if(port < 1) {
            throw new IllegalArgumentException("Port number must be greater than 0.");
        }
        if(null != server && server.isRunning()) {
            throw new IllegalAccessException("Central Server is already running.");
        }
        this.port = port;
        // Connect to server on another thread
        executor2.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    console.println("Creating Central Server at port " + port);
                    Server conn = Network.createServer(port);
                    for(ConnectionListener connectionListener : connectionListeners) {
                        conn.addConnectionListener(connectionListener);
                    }
                    for(Pair pair : messageListeners) {
                        if(null != pair.classes) {
                            conn.addMessageListener(pair.listener, pair.classes);
                        } else {
                            conn.addMessageListener(pair.listener);
                        }
                    }
                    conn.start();
                    for(ServerStateListener stateListener : stateListeners) {
                        stateListener.serverStarted(conn);
                    }
                } catch(Exception ex) {
                    Main.LOG.log(Level.SEVERE, null, ex);
                    for(ServerConnectListener connectListener : connectListeners) {
                        connectListener.startingError(ex);
                    }
                }
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

    /**
     * Stop server.
     */
    public void stop() {
        if(null != server && server.isRunning()) {
            try {
                server.close();
                for(ServerStateListener stateListener : stateListeners) {
                    StopInfo stopInfo = new ServerStateListener.StopInfo();
                    stopInfo.reason = "Regular stop";
                    stateListener.serverStopped(server, stopInfo);
                }
            } catch(Exception ex) {
                Main.LOG.log(Level.SEVERE, null, ex);
                for(ServerConnectListener connectListener : connectListeners) {
                    connectListener.stoppingError(ex);
                }
            }
        }
    }

    @Override
    public void serverStarted(Server server) {
        this.server = server;

        String localhost = "[unknown host]";
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        Console.sysprintln("Central Server started successfully at " + localhost + " port " + port);
    }

    @Override
    public void serverStopped(Server server, StopInfo info) {
        this.server = null;
    }

    private class Pair {

        public MessageListener<? super HostedConnection> listener;
        public Class[] classes;

        public Pair(MessageListener<? super HostedConnection> listener) {
            this.listener = listener;
        }

        public Pair(MessageListener<? super HostedConnection> listener, Class... classes) {
            this.listener = listener;
            this.classes = classes;
        }
    }
}
