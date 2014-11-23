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
public class WorldProfile implements Profile {

    /**
     * The type of the world.
     */
    public enum Type {
        Hangar("Hangar"),
        Gameplay("Gameplay");

        private String type;

        Type(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }    

    private int idWorldProfile = 0;
    private int idServerProfile = 0;
    private String name = "";
    private String path = "";
    private Type type = Type.Gameplay;
    private Date created;

    public WorldProfile() {
    }

    /**
     * @param rs Id of the world should be idServerWorld column
     * @throws SQLException
     */
    public WorldProfile(ResultSet rs) throws SQLException {
        this.idWorldProfile = rs.getInt("idWorldProfile");
        this.idServerProfile = rs.getInt("idServerProfile");
        this.name = rs.getString("name");
        this.path = rs.getString("path");
        this.type = Type.valueOf(rs.getString("type"));
        this.created = rs.getDate("created");
    }

    public WorldProfile(String name, String path, Type type) {
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public int getIdWorldProfile() {
        return idWorldProfile;
    }

    public int getIdServerProfile() {
        return idServerProfile;
    }

    @Override
    public int getUniqueId() {
        return idWorldProfile;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }

    public Date getCreated() {
        return created;
    }

    public boolean isType(Type type) {
        return this.type == type;
    }

    @Override
    public String toString() {
        return "[" + idWorldProfile + ", " + name + ", " + path + ", " + type + "]";
    }

    /**
     * Two world profiles are equal, when they have same name, path and type.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof WorldProfile && idWorldProfile == ((WorldProfile)obj).idWorldProfile && name.equals(((WorldProfile)obj).name) && path.equals(((WorldProfile)obj).path) && type.toString().equals(((WorldProfile)obj).type.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 82 * hash + this.idWorldProfile;
        return hash;
    }
}
