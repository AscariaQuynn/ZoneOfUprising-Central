/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.utils;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.network.serializing.Serializable;
import cz.ascaria.network.central.Main;
import java.util.logging.Level;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Ascaria Quynn
 */
@Serializable
public class PropertiesHelper {

    private JSONObject properties = new JSONObject();

    /**
     * Creates json helper from multiple properties strings. Each must start with { and must have length greater than 1.
     * If keys match, then the later value for that key will overwrite the previous one.
     * @param propss
     */
    public PropertiesHelper(String... propss) {
        JSONParser jsonParser = new JSONParser();
        try {
            for(String props : propss) {
                try {
                    if(props instanceof String && props.length() > 1 && props.startsWith("{")) {
                        JSONObject jsonProps = (JSONObject)jsonParser.parse(props);
                        properties.putAll((JSONObject)jsonProps);
                    }
                } catch(Exception ex) {
                    Main.LOG.log(Level.SEVERE, props, ex);
                    throw ex;
                }
            }
        } catch(Exception ex) {
            properties.clear();
        }
    }

    public boolean hasNumber(String name) {
        if(properties.containsKey(name)) {
            Object number = properties.get(name);
            return number instanceof Number;
        }
        return false;
    }

    public float getFloat(String name, float defaultValue) {
        if(hasNumber(name)) {
            return ((Number)properties.get(name)).floatValue();
        }
        return defaultValue;
    }

    public double getDouble(String name, float defaultValue) {
        if(hasNumber(name)) {
            return ((Number)properties.get(name)).doubleValue();
        }
        return defaultValue;
    }

    public boolean hasVector3f(String name) {
        if(properties.containsKey(name)) {
            Object jsonArray = properties.get(name);
            if(jsonArray instanceof JSONArray && ((JSONArray)jsonArray).size() == 3) {
                for(Object number : (JSONArray)jsonArray) {
                    if(!(number instanceof Number)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public Vector3f getVector3f(String name, Vector3f defaultValue) {
        if(hasVector3f(name)) {
            JSONArray jsonArray = (JSONArray)properties.get(name);
            return new Vector3f(
                ((Number)jsonArray.get(0)).floatValue(),
                ((Number)jsonArray.get(1)).floatValue(),
                ((Number)jsonArray.get(2)).floatValue()
            );
        }
        return defaultValue;
    }

    public boolean hasVector4f(String name) {
        if(properties.containsKey(name)) {
            Object jsonArray = properties.get(name);
            if(jsonArray instanceof JSONArray && ((JSONArray)jsonArray).size() == 4) {
                for(Object number : (JSONArray)jsonArray) {
                    if(!(number instanceof Number)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public Vector4f getVector4f(String name, Vector4f defaultValue) {
        if(hasVector4f(name)) {
            JSONArray jsonArray = (JSONArray)properties.get(name);
            return new Vector4f(
                ((Number)jsonArray.get(0)).floatValue(),
                ((Number)jsonArray.get(1)).floatValue(),
                ((Number)jsonArray.get(2)).floatValue(),
                ((Number)jsonArray.get(3)).floatValue()
            );
        }
        return defaultValue;
    }

    public boolean hasColorRGBA(String name) {
        if(properties.containsKey(name)) {
            Object jsonArray = properties.get(name);
            if(jsonArray instanceof JSONArray && ((JSONArray)jsonArray).size() == 4) {
                for(Object number : (JSONArray)jsonArray) {
                    if(!(number instanceof Number)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public ColorRGBA getColorRGBA(String name, ColorRGBA defaultValue) {
        if(hasColorRGBA(name)) {
            JSONArray jsonArray = (JSONArray)properties.get(name);
            return new ColorRGBA(
                ((Number)jsonArray.get(0)).floatValue(),
                ((Number)jsonArray.get(1)).floatValue(),
                ((Number)jsonArray.get(2)).floatValue(),
                ((Number)jsonArray.get(3)).floatValue()
            );
        }
        return defaultValue;
    }
}
