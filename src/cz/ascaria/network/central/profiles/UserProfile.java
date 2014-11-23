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
public class UserProfile implements Profile {

    public enum State {
        Unconfirmed("Unconfirmed"),
        Active("Active"),
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

    private int idUserProfile = 0;
    private String name;
    private double money = 0;
    private String email;
    private State state = State.Active;
    private Date created;
    private Date lastLogin;

    public UserProfile() {
    }

    public UserProfile(ResultSet rs) throws SQLException {
        this.idUserProfile = rs.getInt("idUserProfile");
        this.name = rs.getString("name");
        this.money = rs.getDouble("money");
        this.email = rs.getString("email");
        this.state = State.valueOf(rs.getString("state"));
        this.created = rs.getDate("created");
        this.lastLogin = rs.getDate("lastLogin");
    }

    public UserProfile(int idUserProfile, String name, double money, String email, State state) {
        this.idUserProfile = idUserProfile;
        this.name = name;
        this.money = money;
        this.email = email;
        this.state = state;
    }

    public int getIdUserProfile() {
        return idUserProfile;
    }

    @Override
    public int getUniqueId() {
        return idUserProfile;
    }

    @Override
    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public String getEmail() {
        return email;
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
        return state == State.Active;
    }

    @Override
    public String toString() {
        return "[" + idUserProfile + "; " + name + "; " + email + "; " + state + "]";
    }

    /**
     * Two user profiles are equal, when they have same id.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof UserProfile && ((UserProfile)obj).idUserProfile == idUserProfile;
    }

    @Override
    public int hashCode() {
        int hash = 4;
        hash = 85 * hash + this.idUserProfile;
        return hash;
    }
}
