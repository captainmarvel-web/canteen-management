/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import beans.UserBeans;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DELL
 */
public class Sales {
    private DbConnection db;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private int total;
       
    public Sales(){
        try{
            db = new DbConnection();
            conn = db.getConnection();
            stmt = db.getStatement();
            rs = stmt.executeQuery("Select * from bill_gen");
            if(rs.next()){
                total = rs.getInt(1);
            }
            
        } catch (SQLException e) {
          Logger.getLogger(Bill.class.getName()).log(Level.SEVERE, null, e);
           db.setWarningMessage("Bill", e.toString());
        }
    }
    
    public UserBeans setSales(ResultSet rs){
        UserBeans e = null;
        if(rs!=null){
            try
            {
                e = new UserBeans();
                e.setBid(rs.getInt("b_id"));
                e.setBdate(rs.getString("bill_date")); 
                e.setBtotal(rs.getDouble("bill_total")); 
             }
            catch(SQLException ex){
                db.setWarningMessage("Bill_gen.setSales", ex.toString());
            }
        }
        return e;
    }
    public ObservableList<UserBeans> showTableRecords() {
       ObservableList list = null;
         try{
                list = FXCollections.observableArrayList();
                rs = stmt.executeQuery("select * from bill_gen");
                while(rs.next()){
                    list.add(setSales(rs));
                }
              // updateRecord();               
            }
            catch(SQLException ex){
                db.setWarningMessage("Bill_gen Model.showTableRecord1", ex.toString());
            }
        return list;
    }
    
}
