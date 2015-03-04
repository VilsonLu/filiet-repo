/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author Vilson
 */
public abstract class DBFactory {
    private static String dbDriver = "";
    private static String dbUrl = "";
    private static String dbName = "";
    private static String username = "";
    private static String password = "";
    
    
    public static DBConnection getInstance(){
        
    	Properties property = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream("./resources/db.properties");
			property.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbDriver = property.getProperty("dbDriver");
		dbUrl = property.getProperty("dbUrl");
		dbName = property.getProperty("dbName");
		username = property.getProperty("username");
		password = property.getProperty("password");
		
        return new DBConnection();
    }
    
    public abstract Connection getConnection();

    /**
     * @return the dbDriver
     */
    public String getDbDriver() {
        return dbDriver;
    }

    /**
     * @param dbDriver the dbDriver to set
     */
    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    /**
     * @return the dbUrl
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * @param dbUrl the dbUrl to set
     */
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
