/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.models;

import com.jme3.system.AppSettings;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import cz.ascaria.network.central.Main;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Ascaria Quynn
 */
abstract public class BaseModel {

    protected AppSettings settings;

    private MysqlDataSource dataSource;

    public void initialize(AppSettings settings) {
        this.settings = settings;
    }

    public void cleanup() {
        settings = null;
        dataSource = null;
    }

    private MysqlDataSource getDataSource() {
        if(null == dataSource) {
            Main.LOG.info("Preparing MySQL data source.");
            dataSource = new MysqlDataSource();
            dataSource.setUser(settings.getString("dbUser"));
            dataSource.setPassword(settings.getString("dbPassword"));
            dataSource.setServerName(settings.getString("dbServerName"));
            dataSource.setPortNumber(settings.getInteger("dbPortNumber"));
            dataSource.setDatabaseName(settings.getString("dbDatabaseName"));
        }
        return dataSource;
    }

    protected Connection getConnection() throws SQLException {
        Main.LOG.info("Returning MySQL connection.");
        return getDataSource().getConnection();
    }
}
