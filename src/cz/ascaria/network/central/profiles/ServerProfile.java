/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.profiles;

import com.jme3.network.serializing.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class ServerProfile implements Profile {

    public enum State {
        Restricted("Restricted"),
        Trusted("Trusted"),
        Deleted("Deleted");

        private String state;

        State(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return state;
        }
    };

    private int idServerProfile = 0;
    private String name;
    private State state = State.Restricted;
    private Date created;
    private Date lastLogin;

    public ServerProfile() {
    }

    public ServerProfile(ResultSet rs) throws SQLException {
        this.idServerProfile = rs.getInt("idServerProfile");
        this.name = rs.getString("name");
        this.state = State.valueOf(rs.getString("state"));
        this.created = rs.getDate("created");
        this.lastLogin = rs.getDate("lastLogin");
    }

    public ServerProfile(int idServerProfile, String name, State state) {
        this.idServerProfile = idServerProfile;
        this.name = name;
        this.state = state;
    }

    public int getIdServerProfile() {
        return idServerProfile;
    }

    @Override
    public int getUniqueId() {
        return idServerProfile;
    }

    @Override
    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public Date getCreated() {
        return created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public boolean isActive() {
        return state == State.Restricted || state == State.Trusted;
    }

    public boolean isTrusted() {
        return state == State.Trusted;
    }

    @Override
    public String toString() {
        return "[" + idServerProfile + "; " + name + "; " + state + "]";
    }

    /**
     * Two user profiles are equal, when they have same id.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ServerProfile && ((ServerProfile)obj).idServerProfile == idServerProfile;
    }

    @Override
    public int hashCode() {
        int hash = 2;
        hash = 81 * hash + this.idServerProfile;
        return hash;
    }
}
