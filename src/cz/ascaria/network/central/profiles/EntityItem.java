/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.profiles;

import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.utils.PropertiesHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class EntityItem {

    /**
     * The type of the entity item.
     */
    public enum Type {
        SpaceShip("SpaceShip"),
        Engine("Engine"),
        Turret("Turret"),
        MissilePod("MissilePod"),
        Light("Light"),
        Resistor("Resistor");

        private String type;

        Type(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }   

    private int idEntityItem;
    private int idItem;
    private String name;
    private Type type;
    private String path;
    private float price = 1f;
    private String properties = "";
    private String eiProperties = "";
    private Date created;

    private PropertiesHelper propertiesHelper = null;

    public EntityItem() {
        this.created = new Date(0);
    }

    /**
     * @param rs
     * @throws SQLException
     */
    public EntityItem(ResultSet rs) throws SQLException {
        this.idEntityItem = rs.getInt("idEntityItem");
        this.idItem = rs.getInt("idItem");
        this.name = rs.getString("name");
        this.type = Type.valueOf(rs.getString("type"));
        this.path = rs.getString("path");
        this.price = rs.getFloat("price");
        this.properties = rs.getString("properties");
        this.eiProperties = rs.getString("eiproperties");
        this.created = rs.getDate("created");
    }

    /**
     * Primary ID.
     * @return
     */
    public int getIdEntityItem() {
        return idEntityItem;
    }

    public int getIdItem() {
        return idItem;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public float getPrice() {
        return price;
    }

    public boolean isType(Type type) {
        return this.type == type;
    }

    public Type getType() {
        return type;
    }

    private PropertiesHelper getPropertiesHelper() {
        if(null == propertiesHelper) {
            propertiesHelper = new PropertiesHelper(properties, eiProperties);
        }
        return propertiesHelper;
    }

    public PropertiesHelper getProperties() {
        return getPropertiesHelper();
    }

    public float getPropertyFloat(String name, float defaultValue) {
        return getPropertiesHelper().getFloat(name, defaultValue);
    }

    public double getPropertyDouble(String name, float defaultValue) {
        return getPropertiesHelper().getDouble(name, defaultValue);
    }

    public Vector3f getPropertyVector3f(String name, Vector3f defaultValue) {
        return getPropertiesHelper().getVector3f(name, defaultValue);
    }

    public Vector4f getPropertyVector4f(String name, Vector4f defaultValue) {
        return getPropertiesHelper().getVector4f(name, defaultValue);
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "[" + idItem + ", " + name + ", " + path + "]";
    }

    /**
     * Two entity profiles are equal, when they have same id, name and path.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityItem && idItem == ((EntityItem)obj).idItem && name.equals(((EntityItem)obj).name) && path.equals(((EntityItem)obj).path);
    }

    @Override
    public int hashCode() {
        int hash = 4;
        hash = 69 * hash + this.idItem;
        return hash;
    }
}
