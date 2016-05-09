/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmth.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BuiNgocTruong
 */
public class MyConnection {
    
    private final String className = "com.mysql.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/new_schema";
    private final String name = "root";
    private final String pass = "quangbach";
    
   
    
    public Connection Connect(){
         Connection connection = null;
        try {
            Class.forName(className);
            connection = DriverManager.getConnection(url,name,pass);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.println("Connect fail" +e);
        }
        
        return connection;
    }
    public static void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args){
        MyConnection myConnect = new MyConnection();
        myConnect.Connect();
    }
}
