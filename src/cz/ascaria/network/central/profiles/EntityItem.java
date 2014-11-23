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
public class EntityItem {

    private int idEntityItem;
    private int idItem;
    private String name;
    private String itemClass;
    private float price = 1f;
    private String categoryName;
    private String categoryType;
    private String properties = "";
    private Date created;

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
        this.itemClass = rs.getString("class");
        this.price = rs.getFloat("price");
        this.categoryName = rs.getString("categoryName");
        this.categoryType = rs.getString("categoryType");
        this.properties = rs.getString("properties");
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

    public String getItemClass() {
        return itemClass;
    }

    public float getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public boolean isGun() {
        return "gun".equals(categoryType);
    }

    public boolean isMissile() {
        return "missile".equals(categoryType);
    }

    public boolean isLight() {
        return "missile".equals(categoryType);
    }

    public String getProperties() {
        return properties;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "[" + idItem + ", " + name + ", " + itemClass + "]";
    }

    /**
     * Two entity profiles are equal, when they have same id, name and path.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityItem && idItem == ((EntityItem)obj).idItem && name.equals(((EntityItem)obj).name) && itemClass.equals(((EntityItem)obj).itemClass);
    }

    @Override
    public int hashCode() {
        int hash = 4;
        hash = 69 * hash + this.idItem;
        return hash;
    }
}
