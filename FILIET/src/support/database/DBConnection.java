/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Vilson
 */
public class DBConnection extends DBFactory {

    public Connection getConnection() {
        try {
            Class.forName(getDbDriver());
            Connection con = DriverManager.getConnection(getDbUrl() + getDbName(), getUsername(), getPassword());
            return con;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
}
