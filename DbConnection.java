/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author DELL
 */
public class DbConnection {
    
    private Connection conn;
    private Statement stmt;
    
    public DbConnection(){
        try{
         Class.forName("oracle.jdbc.driver.OracleDriver");
         conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","canteen","canteen");
        }
         catch(ClassNotFoundException e){
         setWarningMessage("Dbconnection",e.toString());
         }
        catch(SQLException e){
            setWarningMessage("Dbconnection",e.toString());
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public Statement getStatement() {
        try{
            if(conn!=null){
                stmt=conn.createStatement();
            }
        }
        catch(SQLException e){
            setWarningMessage("DbConnection.getStatement",e.toString());
        }
        return stmt;
    }
    
    
    public void setWarningMessage(String header,String content){
        Alert alert=new Alert(AlertType.WARNING);
          alert.setHeaderText("Exception :"+header);
          alert.setContentText(content);
          alert.showAndWait();
    }
}
